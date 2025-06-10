INSERT INTO users (username, password, balance)
VALUES (                    -- generates a random UUID
           'John Doe',                             -- username
           'John',  -- example hashed password (bcrypt)
           900.00                                -- default balance
       );
