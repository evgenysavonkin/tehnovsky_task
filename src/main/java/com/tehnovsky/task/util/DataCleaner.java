package com.tehnovsky.task.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataCleaner implements ApplicationListener<ContextClosedEvent>, ExitCodeGenerator {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int getExitCode() {
        return 0;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        jdbcTemplate.execute("""
                            DROP TABLE IF EXISTS user_ cascade;
                            DROP TABLE IF EXISTS operation cascade;
                            DROP TABLE IF EXISTS document cascade;
                            DROP TABLE IF EXISTS account cascade;
                """);
    }
}
