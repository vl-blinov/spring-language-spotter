CREATE TABLE IF NOT EXISTS language (
	id SERIAL PRIMARY KEY,
	name VARCHAR
);

CREATE TABLE IF NOT EXISTS country (
	id SERIAL PRIMARY KEY,
	name VARCHAR
);

CREATE TABLE IF NOT EXISTS language_country (
	language_id INTEGER REFERENCES language(id),
	country_id INTEGER REFERENCES country(id)
);

CREATE TABLE IF NOT EXISTS city (
	id SERIAL PRIMARY KEY,
	name VARCHAR,
	country_id INTEGER REFERENCES country(id)
);

CREATE TABLE IF NOT EXISTS education_center (
	id SERIAL PRIMARY KEY,
	name VARCHAR,
	city_id INTEGER REFERENCES city(id),
	registration_fee VARCHAR,
	rating REAL
);

CREATE TABLE IF NOT EXISTS course (
	id SERIAL PRIMARY KEY,
	education_center_id INTEGER REFERENCES education_center(id),
	course_type VARCHAR,
	students_per_class INTEGER,
	course_duration VARCHAR,
	class_time VARCHAR,
	lesson_duration VARCHAR,
	price_per_week VARCHAR
);

CREATE TABLE IF NOT EXISTS accommodation (
	id SERIAL PRIMARY KEY,
	education_center_id INTEGER REFERENCES education_center(id),
	accommodation_type VARCHAR,
	food VARCHAR,
	age_restriction VARCHAR,
	price_per_week VARCHAR
);
