DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, dateTime, description, calories) VALUES
  (100000, '2019-10-19 07:00', 'breakfast', 700),
  (100000, '2019-10-19 13:00', 'dinner', 800),
  (100000, '2019-10-19 19:00', 'supper', 500),
  (100001, '2019-10-20 08:00', 'breakfast', 700),
  (100001, '2019-10-20 12:00', 'lunch', 900),
  (100001, '2019-10-20 20:00', 'dinner', 800);
