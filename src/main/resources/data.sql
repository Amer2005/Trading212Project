INSERT INTO users (username, password, balance)
VALUES (                    -- generates a random UUID
           'john_doe',                             -- username
           '$2a$10$abcdefgHashedPasswordHere123',  -- example hashed password (bcrypt)
           10000.00                                -- default balance
       );
