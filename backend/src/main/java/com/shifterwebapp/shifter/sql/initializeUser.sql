INSERT INTO _user (email,
                   password_hash,
                   name,
                   is_admin,
                   points,
                   work_position,
                   company_size)
VALUES (
        'admin@gmail.com',
        '$2a$12$QREFNsS4GKa9soUNUa4rMe1yZtj1wE7Bye8etobRyBWZDiq6Vfnwy',
        'Admin',
        true,
        1000,
        'Administrator',
        'ENTERPRISE'
    )
