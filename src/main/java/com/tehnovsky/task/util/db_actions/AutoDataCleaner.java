package com.tehnovsky.task.util.db_actions;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoDataCleaner implements ApplicationListener<ContextClosedEvent> {

    private final DataCleaner dataCleaner;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
//        dataCleaner.dropTablesIfExist(jdbcTemplate);
    }
}
