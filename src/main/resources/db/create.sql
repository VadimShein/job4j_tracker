create table if not exists items(
	id serial primary key,
	name varchar(255),
	created timestamp(255),
	description varchar(255)
);