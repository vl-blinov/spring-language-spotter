INSERT INTO language (name) VALUES ('English');
INSERT INTO country (name) VALUES ('Ireland');
INSERT INTO language_country (language_id, country_id) VALUES (1, 1);
INSERT INTO city (name, country_id) VALUES ('Dublin', 1);
INSERT INTO education_center (name, city_id, registration_fee, rating) VALUES ('Erin School of English', 1, '85 €', 4.9);