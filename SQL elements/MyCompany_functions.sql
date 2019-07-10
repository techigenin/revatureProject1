-- Gets the full name of an employee by emp_id
--
-- usage Ex: select get_employee_name(4);
-- returns varchar of employee name
create or replace function get_employee_name (eid integer)
returns varchar (50) as $$
declare
	name varchar(50);
begin
	select into name e.full_name from employee e where e.id = eid; 
	return name;
end $$ language plpgsql;

-- Gets requests placed by a given employee by eid
--
-- usage Ex: select * from get_emp_reqs(11);
-- returns a table filled with info for the employee's requests
create or replace function get_emp_reqs (eid integer) 
returns table (
	eventDate date, 
	eventTime time, 
	eventType varchar(50),
	eventLoc varchar(50), 
	eventDesc varchar(100), 
	eventCost numeric(6,2), 
	gradeFmt varchar(50), 
	appliedDate date,
	status	varchar(20),
	empName varchar(50),
	reqNum integer,
	rmbAmt numeric(6,2)) 
as $$
begin
	return query 
		select e.event_date, e.event_time, et.description,
			e.event_loc, e.event_desc, e.event_cost, e.grade_fmt, 
			r.app_date, rs.description, em.full_name, r.req_num, e.rmb_amt
		from events e inner join requests r 
			on e.event_id = r.event_id 
				inner join request_status rs 
					on r.status = rs.status_num
						inner join event_types et
							on e.event_type = et.event_type
								inner join employee em 
									on r.emp_id = em.id
										where r.emp_id = eid 
											and (r.status = 1 or  --pending
												 r.status = 2 or   -- approved
												 r.status = 3 or   -- denied
												 r.status = 5);    -- awarded
end $$
language plpgsql;

-- Approves an employee's request, whether the approver is direct supervisor, dept head, or benco
-- If a request id marked approved at all three levels, it is marked approved in request table
--
-- usage Ex: select approve_request(2, 1)
-- where the first number is the eid of the approver, and the second is the request approved.
-- returns void.
create or replace function approve_request(uid integer, r_num integer)
	returns void
as $$
declare
	eid integer;
begin
	select into eid r.emp_id from requests r where req_num = r_num;
	
	if (select not exists(select * from requests where req_num = r_num)) then return; end if;
	if (select exists(select * from get_direct_subs(uid)  where subs = eid)) then
		update approvals set dsup_app_sts = 2 where approvals.req_num = r_num;
	end if;
	if (select exists(select * from get_dept_subs(uid)  where subs = eid)) then
		update approvals set dhead_app_sts = 2 where approvals.req_num = r_num;
	end if;
	if (select is_benco(uid)) then
		update approvals set benco_app_sts = 2 where approvals.req_num = r_num;
	end if;

	if (select exists(select * from approvals where req_num = r_num and benco_app_sts = 2 and dhead_app_sts = 2 and dsup_app_sts = 2 )) then
		update requests set status = 2  where requests.req_num = r_num;
	end if;
end $$ language plpgsql;

-- Denies an employee's request, whether the denier is direct supervisor, dept head, or benco
-- If a request receives a denial at any point along the chain, it is marked denied in the request table
--
-- usage Ex: select deny_request(2, 1)
-- where the first number is the eid of the denier, and the second is the request denied.
-- returns void.
create or replace function deny_request(uid integer, r_num integer)
	returns void
as $$
declare
	eid integer;
begin
	select into eid r.emp_id from requests r where req_num = r_num;
	
	if (select not exists(select * from requests where req_num = r_num)) then return; end if;
	if (select exists(select * from get_direct_subs(uid)  where subs = eid)) then
		update approvals set dsup_app_sts = 3 where approvals.req_num = r_num;
	end if;
	if (select exists(select * from get_dept_subs(uid)  where subs = eid)) then
	update approvals set dsup_app_sts = 3 where approvals.req_num = r_num;
		update approvals set dhead_app_sts = 3 where approvals.req_num = r_num;
	end if;
	if (select is_benco(uid)) then
		update approvals set benco_app_sts = 3 where approvals.req_num = r_num;
	end if;
 --If all three approvals are approved, the request is approved. If Any are denied, the request is denied.
	if (select exists(select * from approvals where req_num = r_num and (benco_app_sts = 3 or dhead_app_sts = 3 or dsup_app_sts = 3))) then
		update requests set status = 3  where requests.req_num = r_num;
	end if;
end $$ language plpgsql;

-- Gets all requests currently marked 'pending' filed by employees who report to this employee 
--
-- usage Ex: select * from direct_subs_reqs(3)
-- returns table of requests details
create or replace function
	direct_subs_reqs (u_id integer)
returns table (
	eventDate date, 
	eventTime time, 
	eventType varchar(50),
	eventLoc varchar(50), 
	eventDesc varchar(100), 
	eventCost numeric(6,2), 
	gradeFmt varchar(50), 
	appliedDate date,
	status	varchar(20),
	empName varchar(50),
	reqNum integer,
	rmbAmt numeric(6,2)) 
as $$
begin
	return query 
		select e.event_date, e.event_time, et.description,
			e.event_loc, e.event_desc, e.event_cost, e.grade_fmt, 
			r.app_date, rs.description, em.full_name, r.req_num, e.rmb_amt
				from employee em inner join requests r 
					on em.id = r.emp_id 
				inner join events e  
					on r.event_id = e.event_id 
				inner join event_types et 
					on e.event_type = et.event_type
				inner join request_status rs
					on r.status = rs.status_num
				where r.req_num in 
					(select r.req_num 
						from approvals a 
							inner join requests r 
								on a.req_num = r.req_num 
									where r.emp_id in 
										(select get_direct_subs(u_id)) and a.dsup_app_sts = 1); 
end $$ language plpgsql;

-- Gets all requests currently marked 'pending' filed by employees in this employees dept 
-- iff this employee is department head, and the request has been approved by direct supervisor
--
-- usage Ex: select * from dept_subs_reqs(2)
-- returns table of requests details
create or replace function
	dept_subs_reqs (u_id integer)
returns table (
	eventDate date, 
	eventTime time, 
	eventType varchar(50),
	eventLoc varchar(50), 
	eventDesc varchar(100), 
	eventCost numeric(6,2), 
	gradeFmt varchar(50), 
	appliedDate date,
	status	varchar(20),
	empName varchar(50),
	reqNum integer,
	rmbAmt numeric(6,2)) 
as $$
begin
	return query 
		select e.event_date, e.event_time, et.description,
			e.event_loc, e.event_desc, e.event_cost, e.grade_fmt, 
			r.app_date, rs.description, em.full_name, r.req_num, e.rmb_amt
				from employee em inner join requests r 
					on em.id = r.emp_id 
				inner join events e  
					on r.event_id = e.event_id 
				inner join event_types et 
					on e.event_type = et.event_type
				inner join request_status rs
					on r.status = rs.status_num
				where r.req_num in 
					(select r.req_num 
						from approvals a 
							inner join requests r 
								on a.req_num = r.req_num
									where r.emp_id in 
										(select get_dept_subs(u_id)) and a.dhead_app_sts = 1 and a.dsup_app_sts = 2); 
end $$ language plpgsql;

-- Gets all requests currently marked 'pending' filed by employees 
-- iff the request has been approved by direct supervisor and department head
--
-- usage Ex: select * from benco_reqs()
-- returns table of requests details
create or replace function
	benco_reqs ()
returns table (
	eventDate date, 
	eventTime time, 
	eventType varchar(50),
	eventLoc varchar(50), 
	eventDesc varchar(100), 
	eventCost numeric(6,2), 
	gradeFmt varchar(50), 
	appliedDate date,
	status	varchar(20),
	empName varchar(50),
	reqNum integer,
	rmbAmt numeric(6,2)) 
as $$
begin
	return query 
		select e.event_date, e.event_time, et.description,
			e.event_loc, e.event_desc, e.event_cost, e.grade_fmt, 
			r.app_date, rs.description, em.full_name, r.req_num, e.rmb_amt
			from employee em inner join requests r 
					on em.id = r.emp_id 
				inner join events e  
					on r.event_id = e.event_id 
				inner join event_types et 
					on e.event_type = et.event_type
				inner join request_status rs
					on r.status = rs.status_num
				where r.req_num in 
					(select r.req_num 
						from approvals a 
							inner join requests r 
								on a.req_num = r.req_num 
									where ((a.benco_app_sts = 1 or a.benco_app_sts = 2) and a.dhead_app_sts = 2 and a.dsup_app_sts = 2)); 
end $$ language plpgsql;

-- Checks the login information of a user attempting to access system
--
-- usage Ex. check_login (cast ('mMoss' as varchar), cast ('1234' as varchar))
-- Returns the user id on succues or 0 on fail.
create or replace function check_login 
	(u_name varchar(50), p_word varchar(50))
returns integer
	as $$
declare
	pass varchar(50);
	id_num integer;
begin
	select into pass pass_word from validation where username = u_name;
	if pass = p_word then
		select into id_num id from validation where username = u_name;
		return id_num; 
	else 
		return 0;
	end if;
end $$
language plpgsql;

-- Checks if an employee is a department head
--
-- usage Ex: select is_dept_head(2)
-- returns true if department head, false otherwise
create or replace function is_dept_head(eid integer)
returns boolean
as $$
declare
	reports_to_dept varchar(20);
	users_dept	varchar(20);
begin
	select into reports_to_dept department from employee where id =
		(select reports_to from employee where id = eid);
	select into users_dept department from employee where id = eid;
	if (users_dept = reports_to_dept) or (reports_to_dept is null) then
		return false;
	else
		return true;
	end if;
end $$
language plpgsql;

-- Checks if an employee is a benco
--
-- usage Ex: select is_benco(32)
-- returns true if benco, false otherwise
create or replace function is_benco(eid integer)
returns boolean
as $$
begin
	if (select department from employee where id = eid) = 'Benco' then
		return true;
	else
		return false;
	end if;
end $$
language plpgsql;

-- if the employee is a department head, 
-- responds with set of employee ids in department
-- otherwise responds with empty table
--
-- usage Ex.: select * from get_dept_subs (2)
-- returns set of employee ids in dept of eid 2 
create or replace function get_dept_subs (eid integer)
returns table (subs integer)
as $$
begin
	if (not is_dept_head(eid)) then -- if user reports to someone in department, not head of department.
		return query select e.id from employee e where e.id = -1; -- garbage
	else
		return query select e.id from employee e where e.id <> eid and 
			(select (department = (select department from employee where id = e.id)) from employee where id = eid);
	end if;
end $$
language plpgsql;

-- if the employee is a direct supervisor, 
-- responds with set of employee ids which report to this eid
-- otherwise responds with empty table
--
-- usage Ex.: select * from get_direct_subs (3)
-- returns set of employee ids who report to eid 3
create or replace function get_direct_subs (eid integer)
returns table (subs integer)
as $$
begin
	return query select id from employee where reports_to = eid;
end $$
language plpgsql;

-- Adds a new request to the system
-- Creates the new rows in events, requests, and approvals table
--
-- Usage Ex: select add_request (cast ('7-5-2019' as date), cast ('12:00' as time), cast ('someplace fun' as varchar), cast ('something else fun' as varchar), cast (23.00 as numeric), cast ('letter grades' as varchar), 15, cast ('6-2-2019' as date), 4, cast ('2 hours' as varchar));
-- JDBC fixes casting problem...
-- returns void
create or replace function 
	add_request(
		in e_date date, 
		in e_time time, 
		in e_loc varchar(50), 
		in e_desc varchar(100), 
		in e_cost numeric(6,2), 
		in g_fmt varchar(50), 
		in user_id integer, 
		in app_date date,
		in e_type integer,
		in e_durr varchar)
returns integer as $$
declare
	event_num numeric;
	request_num numeric;
	rm_amt numeric;
begin
	select into event_num (max(event_id) + 1) from events;
	select into request_num (max(req_num) + 1) from requests;

	select into rm_amt e_cost * (select value from event_types e where e.event_type = e_type);

	if event_num is null then event_num = 1; end if;
	if request_num is null then request_num = 1; end if;

	insert into events (event_id, event_date, event_time, event_loc, event_desc, event_cost, grade_fmt, event_type, event_durr, rmb_amt)
		values (event_num, e_date, e_time, e_loc, e_desc, e_cost, g_fmt, e_type, e_durr, rm_amt);
	insert into requests (req_num, emp_id, event_id, app_date, status)
		values (request_num, user_id, event_num, app_date, 1); -- status is pending at status code 1 
	insert into approvals (req_num, dsup_app_sts, dhead_app_sts, benco_app_sts) values (request_num, 1, 1, 1);

	return request_num;
end $$
language plpgsql;

create or replace function after_grade_app ()
returns trigger as
$$
	declare remaining numeric;
	declare empNum integer;
begin
		if (new.response = 2) then
			update requests r set status = 5 where r.req_num = new.req_num;
			select into remaining  (re.amt_rem - e.rmb_amt) 
				from events e inner join requests r 
					on e.event_id = r.event_id 
						inner join reimbursements re 
							on r.emp_id = re.emp_num
								where r.req_num = new.req_num;
			select into empNum r.emp_id from requests r where r.req_num = new.req_num;
			if (remaining > 0) then				
				update reimbursements set amt_rem = remaining where emp_num = empNum;
			else
				update reimbursements set amt_rem = 0 where emp_num = empNum;
			end if;
		else
			update requests r set status = 3 where r.req_num = new.req_num;
		end if;
		return new;
end $$ language plpgsql;

create trigger benco_grade_app_deny 
	after insert on request_grades_response
	for each row execute procedure after_grade_app();