package org.example;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgreSQLTestContainerExtension implements BeforeAllCallback, AfterAllCallback {
    private static final PostgreSQLContainer<?> postgresqlContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpassword");

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        postgresqlContainer.start();

        String jdbcUrl = postgresqlContainer.getJdbcUrl();
        String username = postgresqlContainer.getUsername();
        String password = postgresqlContainer.getPassword();

        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", username);
        System.setProperty("spring.datasource.password", password);

    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        postgresqlContainer.stop();
    }
}
