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
	registration_fee_amount NUMERIC(5, 2),
	registration_fee_currency VARCHAR,
	rating NUMERIC(2, 1)
);

CREATE TABLE IF NOT EXISTS course (
	id SERIAL PRIMARY KEY,
	education_center_id INTEGER REFERENCES education_center(id),
	course_type VARCHAR,
	students_per_class INTEGER,
	course_duration VARCHAR,
	class_time VARCHAR,
	lesson_duration VARCHAR,
	price_per_week_amount NUMERIC(6, 2),
	price_per_week_currency VARCHAR
);

CREATE TABLE IF NOT EXISTS accommodation (
	id SERIAL PRIMARY KEY,
	education_center_id INTEGER REFERENCES education_center(id),
	accommodation_type VARCHAR,
	room VARCHAR,
	food VARCHAR,
	age_restriction VARCHAR,
	price_per_week_amount NUMERIC(6, 2),
	price_per_week_currency VARCHAR
);
