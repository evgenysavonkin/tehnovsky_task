INSERT INTO user_ (username)
VALUES ('Bob'),
       ('Tom');

INSERT INTO account (balance, currency, user_id)
VALUES (10.34, 'BYN', 1),
       (0, 'USD', 1),
       (100, 'USD', 2),
       (0, 'BYN', 2);

INSERT INTO document (document_number, document_type, user_id)
VALUES ('MP123456789', 'PASSPORT', 1),
       ('MP54321', 'DRIVER_LICENSE', 2);

INSERT INTO operation (operation_type, amount, currency, operation_date, user_id)
VALUES ('DEPOSIT', 10.34, 'BYN', '2024-04-26 13:49:25-07', 1);

