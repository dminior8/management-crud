package pl.dminior.management_crud.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class FlywayMigrationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate; // Dodajemy JdbcTemplate do wykonywania zapytań SQL

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/v1/products";
    }

    @Test
    void shouldCheckDatabaseContents() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM product");
        assertThat(rows).isNotEmpty();
    }
}
