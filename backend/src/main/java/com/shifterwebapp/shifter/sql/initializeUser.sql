INSERT INTO _user (email,
                   password_hash,
                   name,
                   is_admin,
                   is_enabled,
                   login_provider,
                   points,
                   work_position,
                   company_size,
                   has_used_free_consultation)
VALUES (
        'admin@gmail.com',
        '$2a$12$QREFNsS4GKa9soUNUa4rMe1yZtj1wE7Bye8etobRyBWZDiq6Vfnwy',
        'Admin',
        true,
        true,
        'LOCAL',
        1000,
        'Administrator',
        'ENTERPRISE',
        true
    )
