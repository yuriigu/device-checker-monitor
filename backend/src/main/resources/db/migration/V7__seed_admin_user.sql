-- SEED DE DESENVOLVIMENTO — login: admin@checker.local / senha: admin123
-- O campo name é usado como nome de usuário, pois a tabela não possui username.
INSERT INTO users (name, email, phone, password, id_role)
SELECT 'admin',
       'admin@checker.local',
       NULL,
       '$2a$10$YMVYshRSvzWsrNkkuRmsSuEnNPwrdwtkqxOeYwYAEMYkIZdJqutYW',
       role.id
FROM roles role
WHERE role.name = 'ADMIN'
  AND NOT EXISTS (
      SELECT 1
      FROM users existing_user
      WHERE existing_user.email = 'admin@checker.local'
         OR existing_user.name = 'admin'
  );
