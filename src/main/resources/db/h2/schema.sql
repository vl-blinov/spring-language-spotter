CREATE TABLE IF NOT EXISTS course (
	id SERIAL PRIMARY KEY,
	course_type VARCHAR,
	students_per_class INTEGER,
	course_duration VARCHAR,
	class_time VARCHAR,
	class_duration VARCHAR,
	price VARCHAR
);

CREATE TABLE IF NOT EXISTS accomodation (
	id SERIAL PRIMARY KEY,
	accommodation_type VARCHAR,
	food VARCHAR,
	age_restriction VARCHAR,
	price VARCHAR
);

CREATE TABLE IF NOT EXISTS education_center (
	id SERIAL PRIMARY KEY,
	name VARCHAR,
	course_id INTEGER REFERENCES course(id),
	accommodation_id INTEGER REFERENCES accomodation(id),
	registration_fee VARCHAR,
	rating INTEGER
);

CREATE TABLE IF NOT EXISTS city (
	id SERIAL PRIMARY KEY,
	name VARCHAR,
	education_center_id INTEGER REFERENCES education_center(id)
);

CREATE TABLE IF NOT EXISTS country (
	id SERIAL PRIMARY KEY,
	name VARCHAR,
	city_id INTEGER REFERENCES city(id)
);

CREATE TABLE IF NOT EXISTS language (
	id SERIAL PRIMARY KEY,
	name VARCHAR
);

CREATE TABLE IF NOT EXISTS language_country (
	language_id INTEGER REFERENCES language(id),
	country_id INTEGER REFERENCES country(id)
);
