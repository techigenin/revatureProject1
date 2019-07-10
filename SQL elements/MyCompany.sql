create table employee (
	id integer primary key,
	full_name varchar(50),
	reports_to	integer,
	department	varchar(20),
	title varchar(20)
);

create table validation (
	username	varchar(50) primary key,
	id	integer unique,
	pass_word	varchar(50)
);

create table events (
	event_id integer primary key,
	event_date	date,
	event_time	time,
	event_loc	varchar(50),
	event_desc	varchar(100),
	event_cost	numeric(6,2),
	grade_fmt	varchar(50),
	event_type	integer,
	event_durr	varchar(50),
	rmb_amt 	numeric(6,2)
);

create table event_types (
	event_type	integer primary key,
	description	varchar(50),
	value	numeric(3,2)
)

create table requests (
	req_num integer primary key,
	emp_id	integer,
	event_id	integer,
	app_date	Date,
	status	integer
);

create table request_status (
	status_num integer primary key,
	description varchar(20)
);

create table request_files (
	req_num		integer primary key,
	dsup_app 	varchar(100),
	dept_app 	varchar(100),
	event_files	varchar(1000)
)

create table request_grades (
	req_num		integer primary key,
	grade		varchar(20),
	grade_files	varchar(200)
)

create table request_grades_response (
	req_num		integer primary key,
	pres		boolean,
	response	integer
)

create table approvals (
	req_num integer primary key,
	dsup_app_sts	integer,
	dhead_app_sts	integer,
	benco_app_sts	integer
);

create table reimbursements (
	emp_num integer primary key,
	amt_rem numeric(6,2)
);

-- Inserts the values into the request_status table
insert into request_status (status_num, description) values (1, 'pending');
insert into request_status (status_num, description) values (2, 'approved');
insert into request_status (status_num, description) values (3, 'denied');
insert into request_status (status_num, description) values (4, 'canceled');
insert into request_status (status_num, description) values (5, 'awarded');
insert into request_status (status_num, description) values (6, 'acknowledged');

-- Insert the values into the event_types table
insert into event_types (event_type, value, description) values (1,  0.80, 'University Courses');
insert into event_types (event_type, value, description) values (2, 0.6, 'Seminars');
insert into event_types (event_type, value, description) values (3, 0.75, 'Certification Preparation Classes');
insert into event_types (event_type, value, description) values (4, 1.0, 'Certification');
insert into event_types (event_type, value, description) values (5, 0.9, 'Technical Training');
insert into event_types (event_type, value, description) values (6, 0.3, 'Other');

-- Sets up foreign key references
alter table requests add foreign key (emp_id) references employee(id);
alter table requests add foreign key (event_id) references events(event_id);
alter table requests add foreign key (status) references request_status(status_num);
alter table request_files add foreign key (req_num) references requests(req_num);
alter table reimbursements add foreign key (emp_num) references employee(id);
alter table approvals add foreign key (req_num) references requests(req_num);
alter table validation add foreign key (id) references employee(id);
alter table events add foreign key (event_type) references event_types(event_type);

