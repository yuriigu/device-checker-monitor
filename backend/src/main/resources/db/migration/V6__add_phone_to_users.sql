ALTER TABLE users ADD COLUMN phone VARCHAR(30);

CREATE UNIQUE INDEX uk_users_phone ON users (phone) WHERE phone IS NOT NULL AND phone <> '';
