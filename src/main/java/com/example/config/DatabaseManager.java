package com.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.io.InputStream;
import java.util.Properties;

public class DatabaseManager {

    private static Jdbi readWriteJdbi;
    private static Jdbi readOnlyJdbi;
    private static HikariDataSource readWriteDataSource;
    private static HikariDataSource readOnlyDataSource;
    private static final String CONFIG_FILE = "db.properties";

    public static void initialize() {
        try {
            // Load configuration
            Properties props = new Properties();
            InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
            if (input == null) throw new RuntimeException("Cannot find " + CONFIG_FILE);
            props.load(input);

            // Initialize read-write connection pool
            HikariConfig rwConfig = new HikariConfig();
            rwConfig.setJdbcUrl(props.getProperty("db.readwrite.url"));
            rwConfig.setUsername(props.getProperty("db.readwrite.username"));
            rwConfig.setPassword(props.getProperty("db.readwrite.password"));
            rwConfig.setMaximumPoolSize(10);
            rwConfig.setAutoCommit(true);
            readWriteDataSource = new HikariDataSource(rwConfig);

            // Initialize read-only connection pool
            HikariConfig roConfig = new HikariConfig();
            roConfig.setJdbcUrl(props.getProperty("db.readonly.url"));
            roConfig.setUsername(props.getProperty("db.readonly.username"));
            roConfig.setPassword(props.getProperty("db.readonly.password"));
            roConfig.setMaximumPoolSize(5);
            roConfig.setReadOnly(true);
            roConfig.setAutoCommit(false);
            readOnlyDataSource = new HikariDataSource(roConfig);

            // Create JDBI instances
            readWriteJdbi = Jdbi.create(readWriteDataSource);
            readOnlyJdbi = Jdbi.create(readOnlyDataSource);

            // Install plugins for both instances
            readWriteJdbi.installPlugin(new SqlObjectPlugin());
            readOnlyJdbi.installPlugin(new SqlObjectPlugin());

            System.out.println("✅ JDBI instances initialized (read-write & read-only)");

            // Shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                closeDataSources();
                System.out.println("Database pools closed.");
            }));

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database connections", e);
        }
    }

    public static Jdbi getReadWriteJdbi() {
        if (readWriteJdbi == null) {
            throw new IllegalStateException("DatabaseManager not initialized");
        }
        return readWriteJdbi;
    }

    public static Jdbi getReadOnlyJdbi() {
        if (readOnlyJdbi == null) {
            throw new IllegalStateException("DatabaseManager not initialized");
        }
        return readOnlyJdbi;
    }

    private static void closeDataSources() {
        if (readWriteDataSource != null) {
            readWriteDataSource.close();
        }
        if (readOnlyDataSource != null) {
            readOnlyDataSource.close();
        }
    }
}
