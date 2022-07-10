INSERT INTO language (name) VALUES ('English');

INSERT INTO country (name) VALUES ('Ireland');

INSERT INTO language_country (language_id, country_id) VALUES (1, 1);

INSERT INTO city (name, country_id) VALUES ('Dublin', 1);

INSERT INTO education_center (name, city_id, address, registration_fee_amount, registration_fee_currency, rating)
VALUES ('Erin School Of English', 1, '43 N Great George''s St, Rotunda, Dublin, D01 N6P2, Ireland', 85, '€', 4.9);

INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, class_time, lesson_duration, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (1, 'General English (Morning classes)', 15, '1 - 12 weeks', 'Monday - Friday 09:00 - 12:15', '60 minutes', '16+ years old', 200, '€');

INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, class_time, lesson_duration, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (1, 'Work and Study (Morning classes)', 15, '25 weeks', 'Monday - Friday 09:00 - 12:15', '60 minutes', '18+ years old', 115, '€');

INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (1, 'Homestay', 'Single room', 'Half board', '16+ years old', 260, '€');

INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (1, 'Homestay', 'Bed in room for two', 'Half board', '16+ years old', 250, '€');

INSERT INTO education_center (name, city_id, address, registration_fee_amount, registration_fee_currency, rating)
VALUES ('Ulearn English School', 1, '43/44 Temple Bar, Dublin, D02 KF86, Ireland', 115, '€', 4.8);

INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, class_time, lesson_duration, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (2, 'General English (Afternoon classes)', 15, '1 - 12 weeks', 'Monday - Friday 13:45 - 17:00', '60 minutes', '16+ years old', 140, '€');

INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, class_time, lesson_duration, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (2, 'Intensive English', 15, '1 - 24 weeks', 'Monday - Friday 09:00 - 12:20 & 13:45 - 17:00', '60 minutes', '16+ years old', 140, '€');

INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (2, 'City Apartments', 'Single room', 'No meals', '18+ years old', 310, '€');

INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (2, 'City Apartments', 'Bed in room for two', 'No meals', '18+ years old', 180, '€');

INSERT INTO city (name, country_id) VALUES ('Cork', 1);

INSERT INTO education_center (name, city_id, address, registration_fee_amount, registration_fee_currency, rating)
VALUES ('Cork English College', 2, 'Saint Patrick''s Bridge, Cork, Ireland', 170, '€', 4.8);

INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, class_time, lesson_duration, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (3, 'Business English', 8, '1 - 52 weeks', 'Monday - Friday 09:00 - 13:20', '60 minutes', '17+ years old', 240, '€');

INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (3, 'Residence', 'Single room', 'No meals', '18+ years old', 185, '€');

INSERT INTO country (name) VALUES ('United States');

INSERT INTO language_country (language_id, country_id) VALUES (1, 2);

INSERT INTO city (name, country_id) VALUES ('New York', 2);

INSERT INTO education_center (name, city_id, address, registration_fee_amount, registration_fee_currency, rating)
VALUES ('Kaplan International English', 3, '1790 Broadway, New York, NY 10019, United States', 330, '$', 4.8);

INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, class_time, lesson_duration, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (4, 'Academic Semester', 12, '24 weeks', 'Monday - Friday 09:00 - 15:00', '45 minutes', '16+ years old', 350, '$');

INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (4, 'Residence', 'Single room', 'Breakfast only', '18+ years old', 355, '$');











