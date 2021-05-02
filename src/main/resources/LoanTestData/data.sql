insert into users (first_name, last_name) values
    ('John', 'Doe'),
    ('Jane', 'Doe'),
    ('Joe', 'Doe');

insert into loans (user_id, name, interest_rate, outstanding_balance, contribution) values
    (1, 'car loan', 1.005, 20000.00, 3000.00),
    (1, 'house loan', 2.020, 15000.00, 2000.00);