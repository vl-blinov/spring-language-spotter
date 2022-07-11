INSERT INTO language (name) VALUES ('English');
INSERT INTO language (name) VALUES ('Irish');

INSERT INTO country (name) VALUES ('Ireland');
INSERT INTO country (name) VALUES ('United States');

INSERT INTO language_country (language_id, country_id) VALUES (1, 1);
INSERT INTO language_country (language_id, country_id) VALUES (1, 2);
INSERT INTO language_country (language_id, country_id) VALUES (2, 1);

INSERT INTO city (name, country_id) VALUES ('Dublin', 1);
INSERT INTO city (name, country_id) VALUES ('Cork', 1);
INSERT INTO city (name, country_id) VALUES ('New York', 2);
INSERT INTO city (name, country_id) VALUES ('Los Angeles', 2);

INSERT INTO education_center (name, city_id, address, registration_fee_amount, registration_fee_currency, rating)
VALUES ('Erin School Of English', 1, '43 N Great George''s St, Rotunda, Dublin, D01 N6P2, Ireland', 85, '€', 4.9);
INSERT INTO education_center (name, city_id, address, registration_fee_amount, registration_fee_currency, rating)
VALUES ('Ulearn English School', 1, '43/44 Temple Bar, Dublin, D02 KF86, Ireland', 115, '€', 4.8);
INSERT INTO education_center (name, city_id, address, registration_fee_amount, registration_fee_currency, rating)
VALUES ('Cork English College', 2, 'Saint Patrick''s Bridge, Cork, Ireland', 170, '€', 4.8);
INSERT INTO education_center (name, city_id, address, registration_fee_amount, registration_fee_currency, rating)
VALUES ('Cork English World', 2, 'Crawford Business Park, Bishop St, The Lough, Cork, Ireland', 120, '€', 4.5);
INSERT INTO education_center (name, city_id, address, registration_fee_amount, registration_fee_currency, rating)
VALUES ('Kaplan International English', 3, '1790 Broadway, New York, NY 10019, United States', 330, '$', 4.8);
INSERT INTO education_center (name, city_id, address, registration_fee_amount, registration_fee_currency, rating)
VALUES ('Mentor Language Institute', 4, '10880 Wilshire Blvd #122, Los Angeles, CA 90024, United States', 570, '$', 4.9);
INSERT INTO education_center (name, city_id, address, registration_fee_amount, registration_fee_currency, rating)
VALUES ('Gael Linn', 1, '35 Dame St, Dublin 2, D02 H797, Ireland', 60, '$', 4.7);

INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, lessons_per_week, class_time, lesson_duration, age_restriction, entry_level, price_per_week_amount, price_per_week_currency)
VALUES (1, 'General English (Morning classes)', 15, '1 - 12 weeks', 15, 'Monday - Friday 09:00 - 12:15', '60 minutes', '16+ years old', 'Elementary (A1)', 200, '€');
INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, lessons_per_week, class_time, lesson_duration, age_restriction, entry_level, price_per_week_amount, price_per_week_currency)
VALUES (1, 'Work and Study (Morning classes)', 15, '25 weeks', 15, 'Monday - Friday 09:00 - 12:15', '60 minutes', '18+ years old', 'Elementary (A1)', 115, '€');
INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, lessons_per_week, class_time, lesson_duration, age_restriction, entry_level, price_per_week_amount, price_per_week_currency)
VALUES (2, 'General English (Afternoon classes)', 15, '1 - 12 weeks', 15, 'Monday - Friday 13:45 - 17:00', '60 minutes', '16+ years old', 'Elementary (A1)', 140, '€');
INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, lessons_per_week, class_time, lesson_duration, age_restriction, entry_level, price_per_week_amount, price_per_week_currency)
VALUES (2, 'Intensive English', 15, '1 - 24 weeks', 15, 'Monday - Friday 09:00 - 12:20 & 13:45 - 17:00', '60 minutes', '16+ years old', 'Elementary (A1)', 140, '€');
INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, lessons_per_week, class_time, lesson_duration, age_restriction, entry_level, price_per_week_amount, price_per_week_currency)
VALUES (3, 'Business English', 8, '1 - 52 weeks', 20, 'Monday - Friday 09:00 - 13:20', '60 minutes', '17+ years old', 'Upper-Intermediate (B2)', 240, '€');
INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, lessons_per_week, class_time, lesson_duration, age_restriction, entry_level, price_per_week_amount, price_per_week_currency)
VALUES (3, 'Work and Study (Afternoon classes)', 12, '25 weeks', 15, 'Monday - Friday 13:00 - 16:15', '60 minutes', '18+ years old', 'Elementary (A1)', 90, '€');
INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, lessons_per_week, class_time, lesson_duration, age_restriction, entry_level, price_per_week_amount, price_per_week_currency)
VALUES (5, 'Academic Semester', 12, '24 weeks', 30, 'Monday - Friday 09:00 - 15:00', '45 minutes', '16+ years old', 'Elementary (A1)', 350, '$');
INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, lessons_per_week, class_time, lesson_duration, age_restriction, entry_level, price_per_week_amount, price_per_week_currency)
VALUES (6, 'ESL program', 15, '1 - 52 weeks', 20, 'Monday - Friday 09:00 - 13:20', '55 minutes', '18+ years old', 'Elementary (A1)', 290, '$');
INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, lessons_per_week, class_time, lesson_duration, age_restriction, entry_level, price_per_week_amount, price_per_week_currency)
VALUES (7, 'Irish Courses in Dublin', 14, '10 weeks', 2, 'Monday 18:00 - 20:00', '55 minutes', '18+ years old', 'Elementary (A1)', 120, '€');

INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (1, 'Homestay', 'Single room', 'Half board', '16+ years old', 260, '€');
INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (1, 'Homestay', 'Bed in room for two', 'Half board', '16+ years old', 250, '€');
INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (2, 'City Apartments', 'Single room', 'No meals', '18+ years old', 310, '€');
INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (2, 'City Apartments', 'Bed in room for two', 'No meals', '18+ years old', 180, '€');
INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (3, 'Residence', 'Single room', 'No meals', '18+ years old', 185, '€');
INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (3, 'Shared apartment', 'Single room', 'No meals', '18+ years old', 145, '€');
INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (5, 'Residence', 'Single room', 'Breakfast only', '18+ years old', 355, '$');
INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (6, 'Dormitory', 'Bed in room for four', 'No meals', '18+ years old', 270, '$');
INSERT INTO accommodation (education_center_id, accommodation_type, room, food, age_restriction, price_per_week_amount, price_per_week_currency)
VALUES (7, 'Hostel', 'Bed in room for four', 'No meals', '18+ years old', 180, '€');
