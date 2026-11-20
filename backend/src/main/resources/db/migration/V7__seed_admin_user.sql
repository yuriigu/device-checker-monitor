INSERT INTO users (
    name,
    email,
    phone,
    password,
    role
)
SELECT
    'admin',
    'admin@checker.local',
    NULL,
    '$2a$10$YMVYshRSvzWsrNkkuRmsSuEnNPwrdwtkqxOeYwYAEMYkIZdJqutYW',
    'ADMIN'
    WHERE NOT EXISTS (
    SELECT 1
    FROM users existing_user
    WHERE existing_user.email = 'admin@checker.local'
       OR existing_user.name = 'admin'
);