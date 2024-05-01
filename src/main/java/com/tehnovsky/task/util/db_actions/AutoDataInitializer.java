package com.tehnovsky.task.util.db_actions;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoDataInitializer {

    private final DataInitializer dataInitializer;
    private final JdbcTemplate jdbcTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
//        dataInitializer.initData(jdbcTemplate);
    }
}
