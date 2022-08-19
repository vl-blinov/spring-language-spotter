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

CREATE TABLE IF NOT EXISTS language_city (
	language_id INTEGER REFERENCES language(id),
	city_id INTEGER REFERENCES city(id)
);

CREATE TABLE IF NOT EXISTS education_center (
	id SERIAL PRIMARY KEY,
	name VARCHAR,
	city_id INTEGER REFERENCES city(id),
	address VARCHAR,
	registration_fee_amount NUMERIC,
	registration_fee_currency VARCHAR,
	rating NUMERIC(2, 1)
);

CREATE TABLE IF NOT EXISTS language_education_center (
	language_id INTEGER REFERENCES language(id),
	education_center_id INTEGER REFERENCES education_center(id)
);

CREATE TABLE IF NOT EXISTS course (
	id SERIAL PRIMARY KEY,
	education_center_id INTEGER REFERENCES education_center(id),
	language_id INTEGER REFERENCES language(id),
	course_type VARCHAR,
	students_per_class INTEGER,
	course_duration VARCHAR,
	lessons_per_week INTEGER,
	class_time VARCHAR,
	lesson_duration VARCHAR,
	age_restriction VARCHAR,
	entry_level VARCHAR,
	price_per_week_amount NUMERIC,
	price_per_week_currency VARCHAR
);

CREATE TABLE IF NOT EXISTS accommodation (
	id SERIAL PRIMARY KEY,
	education_center_id INTEGER REFERENCES education_center(id),
	accommodation_type VARCHAR,
	room_type VARCHAR,
	meal_included_type VARCHAR,
	age_restriction VARCHAR,
	price_per_week_amount NUMERIC,
	price_per_week_currency VARCHAR
);
