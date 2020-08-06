drop table if exists PEOPLE;

create table PEOPLE (
                        id SERIAL PRIMARY KEY,
                        first_name varchar(255) not null,
                        surname varchar(255) not null,
                        email varchar(255) not null,
                        age int not null
);
