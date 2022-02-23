CREATE TABLE candidate (
                           id SERIAL PRIMARY KEY,
                           name TEXT,
                           cityId int references city(id),
                           created timestamp
);