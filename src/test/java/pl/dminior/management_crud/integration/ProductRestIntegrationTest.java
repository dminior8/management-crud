package pl.dminior.management_crud.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import pl.dminior.management_crud.web.model.Product;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class ProductRestIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/v1/products";
    }

    @Test
    void addAndGetProductTest() {
        Product newProduct = Product.builder()
                .name("Laptop X")
                .description("Gaming Laptop")
                .category("Electronics")
                .stock(10)
                .price(1500.00)
                .build();

        ResponseEntity<Product> postResponse = restTemplate.postForEntity(getBaseUrl(), newProduct, Product.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();
        Product createdProduct = postResponse.getBody();
        UUID productId = createdProduct.getId();
        assertThat(productId).isNotNull();

        ResponseEntity<Product> getResponse = restTemplate.getForEntity(getBaseUrl() + "/" + productId, Product.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getName()).isEqualTo("Laptop X");
        assertThat(getResponse.getBody().getCategory()).isEqualTo("Electronics");
    }

    @Test
    void shouldFetchTestProduct() {
        ResponseEntity<Product[]> response = restTemplate.getForEntity(getBaseUrl(), Product[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(Arrays.stream(response.getBody())
                .anyMatch(item -> item.getId().equals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))))
                .isTrue();
    }
}
