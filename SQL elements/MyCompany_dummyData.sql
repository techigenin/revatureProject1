insert into employee (id, full_name, reports_to, department, title)
	values (1, 'Anthony Stark', null, 'Owner', 'Company Owner');
insert into employee (id, full_name, reports_to, department, title)
	values (2, 'Matt Berry', 1, 'IT', 'Branch Manager');
insert into employee (id, full_name, reports_to, department, title)
	values (3, 'Jen Barber', 2, 'IT', 'Head of IT');
insert into employee (id, full_name, reports_to, department, title)
	values (4, 'Maurice Moss', 3, 'IT', 'Technician');
insert into employee (id, full_name, reports_to, department, title)
	values (5, 'Roy Trenneman', 3, 'IT', 'Technician');
insert into employee (id, full_name, reports_to, department, title)
	values (11, 'Paul Hollywood', 1, 'Catering', 'Head Chef');
insert into employee (id, full_name, reports_to, department, title)
	values (12, 'Han Lee', 11, 'Catering', 'Counter Manager');
insert into employee (id, full_name, reports_to, department, title)
	values (13, 'Oleg', 12, 'Catering', 'Line Cook');
insert into employee (id, full_name, reports_to, department, title)
	values (14, 'Max Black', 12, 'Catering', 'Waitress');
insert into employee (id, full_name, reports_to, department, title)
	values (15, 'Caroline Channing', 12, 'Catering', 'Waitress');
insert into employee (id, full_name, reports_to, department, title)
	values (21, 'Hugo Matuschek', 1, 'Sales', 'Floor Manager');
insert into employee (id, full_name, reports_to, department, title)
	values (22, 'Pepi Katona', 21, 'Sales', 'Head of fulfillment');	
insert into employee (id, full_name, reports_to, department, title)
	values (23, 'Alfred Kralik', 21, 'Sales', 'Sr Sales Person');
insert into employee (id, full_name, reports_to, department, title)
	values (24, 'Klara Novak', 23, 'Sales', 'Jr Sales Person');
insert into employee (id, full_name, reports_to, department, title)
	values (25, 'Ferencz Vadaz', 22, 'Sales', 'Delivery Boy');
insert into employee (id, full_name, reports_to, department, title)
	values (31, 'Benco Alpha', 1, 'Benco', 'Department Chair');
insert into employee (id, full_name, reports_to, department, title)
	values (32, 'Benco Beta', 31, 'Benco', 'Assistant Benco');
insert into employee (id, full_name, reports_to, department, title)
	values (33, 'Benco Gamma', 31, 'Benco', 'Co-Assistant Benco');

insert into validation (id, username, pass_word) values (1, 'tStark', '1234');
insert into validation (id, username, pass_word) values (2, 'mBerry', '1234');
insert into validation (id, username, pass_word) values (3, 'jBarbe', '1234');
insert into validation (id, username, pass_word) values (4, 'mMoss', '1234');
insert into validation (id, username, pass_word) values (5, 'rTrenn', '1234');
insert into validation (id, username, pass_word) values (11, 'pHolly', '1234');
insert into validation (id, username, pass_word) values (12, 'hLee', '1234');
insert into validation (id, username, pass_word) values (13, 'oleg', '1234');
insert into validation (id, username, pass_word) values (14, 'mBlac', '1234');
insert into validation (id, username, pass_word) values (15, 'cChann', '1234');
insert into validation (id, username, pass_word) values (21, 'hMatus', '1234');
insert into validation (id, username, pass_word) values (22, 'pKaton', '1234');
insert into validation (id, username, pass_word) values (23, 'aKrali', '1234');
insert into validation (id, username, pass_word) values (24, 'kNovak', '1234');
insert into validation (id, username, pass_word) values (25, 'fVadaz', '1234');
insert into validation (id, username, pass_word) values (31, 'bAlpha', '1234');
insert into validation (id, username, pass_word) values (32, 'bBeta', '1234');
insert into validation (id, username, pass_word) values (33, 'bGamma', '1234');

do $$
declare v integer;
begin
	for v in (select e.id from employee e)
	loop
		insert into reimbursements (emp_num, amt_rem) values (v, 1000.00);
	end loop;
end $$

select add_request (cast ('7-10-2019' as date), cast ('15:00' as time), cast ('someplace fun' as varchar), cast ('something fun' as varchar), cast (25.00 as numeric), cast ('pass/fail' as varchar), 4, cast ('6-1-2019' as date), 2, cast ('2 hours' as varchar));
select add_request (cast ('7-13-2019' as date), cast ('15:00' as time), cast ('someplace fun' as varchar), cast ('something fun' as varchar), cast (25.00 as numeric), cast ('pass/fail' as varchar), 4, cast ('6-1-2019' as date), 3, cast ('2 days' as varchar));
select add_request (cast ('7-24-2019' as date), cast ('12:00' as time), cast ('someplace fun' as varchar), cast ('something else fun' as varchar), cast (23.00 as numeric), cast ('letter grades' as varchar), 5, cast ('6-2-2019' as date), 4,cast ('1 week' as varchar));
select add_request (cast ('8-1-2019' as date), cast ('12:00' as time), cast ('someplace fun' as varchar), cast ('something else fun' as varchar), cast (23.00 as numeric), cast ('letter grades' as varchar), 14, cast ('6-2-2019' as date), 1, cast ('4 hours' as varchar));
select add_request (cast ('7-10-2019' as date), cast ('12:00' as time), cast ('someplace fun' as varchar), cast ('something else fun' as varchar), cast (23.00 as numeric), cast ('letter grades' as varchar), 15, cast ('6-2-2019' as date), 4, cast ('2 hours' as varchar));

update approvals set dsup_app_sts = 2 where req_num = 1;
update approvals set dhead_app_sts = 2 where req_num = 1;

select * from events where event_date < '8-01-2019';