package com.emna.spring_batch_test.runner;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

//@Component
public class DatabaseConnectionTester implements CommandLineRunner {

    private final JdbcTemplate erpJdbcTemplate;
    private final JdbcTemplate dwJdbcTemplate;

    public DatabaseConnectionTester(
            @Qualifier("erpDataSource") DataSource erpDataSource,
            @Qualifier("dwDataSource") DataSource dwDataSource) {

        this.erpJdbcTemplate = new JdbcTemplate(erpDataSource);
        this.dwJdbcTemplate = new JdbcTemplate(dwDataSource);
    }

    @Override
    public void run(String... args) {
        System.out.println("=== TEST CONNEXION BDD ===");

        String erpDb = erpJdbcTemplate.queryForObject("select current_database()", String.class);
        String dwDb = dwJdbcTemplate.queryForObject("select current_database()", String.class);

        System.out.println("ERP MT DB connected: " + erpDb);
        System.out.println("DW DB connected: " + dwDb);
    }
}