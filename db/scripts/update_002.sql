CREATE TABLE city (
                      id SERIAL PRIMARY KEY,
                      name TEXT
);

insert into city (name) values ('Moscow');

CREATE TABLE candidate (
                           id SERIAL PRIMARY KEY,
                           name TEXT,
                           cityId int references city(id),
                           created timestamp
);