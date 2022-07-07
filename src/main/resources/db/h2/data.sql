INSERT INTO language (name) VALUES ('English');
INSERT INTO country (name) VALUES ('Ireland');
INSERT INTO language_country (language_id, country_id) VALUES (1, 1);
INSERT INTO city (name, country_id) VALUES ('Dublin', 1);
INSERT INTO education_center (name, city_id, registration_fee, rating) VALUES ('erin_school_of_english', 1, '85 €', 4.9);
INSERT INTO course (education_center_id, course_type, students_per_class, course_duration, class_time, lesson_duration, price_per_week)
VALUES (1, 'General English', 15, '1 - 25 weeks', 'Monday - Friday 09:00 - 12:15', '60 minutes', '200 €');
INSERT INTO accommodation (education_center_id, accommodation_type, food, age_restriction, price)
VALUES (1, 'Homestay, single room', 'breakfast', '16+ years old', '260 €');