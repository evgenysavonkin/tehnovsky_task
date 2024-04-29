package com.tehnovsky.task.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final JdbcTemplate jdbcTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void initData(){
        jdbcTemplate.execute("""
                INSERT INTO user_ (username)
                VALUES ('Bob'),
                       ('Tom'),
                       ('Kate');

                INSERT INTO account (balance, currency, user_id)
                VALUES (10.34, 'BYN', 1),
                       (0, 'USD', 1),
                       (100, 'USD', 2),
                       (0, 'BYN', 2);

                INSERT INTO document (document_number, document_type, user_id)
                VALUES ('MP12345', 'PASSPORT', 1),
                       ('DL12345', 'DRIVER_LICENSE', 1),
                       ('DL54321', 'DRIVER_LICENSE', 2);

                INSERT INTO operation (operation_type, amount, currency, operation_date, user_id)
                VALUES ('DEPOSIT', 10.34, 'BYN', '2024-04-26 13:49:25-07', 1),
                       ('DEPOSIT', 100, 'USD', '2024-04-26 13:49:25-07', 2);
                """);
    }
}
