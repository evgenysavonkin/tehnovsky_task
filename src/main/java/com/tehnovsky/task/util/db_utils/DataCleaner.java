package com.tehnovsky.task.util.db_utils;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataCleaner {

    public void dropTablesIfExist(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("""
                            DROP TABLE IF EXISTS user_ cascade;
                            DROP TABLE IF EXISTS operation cascade;
                            DROP TABLE IF EXISTS document cascade;
                            DROP TABLE IF EXISTS account cascade;
                """);
    }
}
