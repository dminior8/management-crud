package pl.dminior.management_crud.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import pl.dminior.management_crud.web.model.Product;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class ProductRestIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

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

        ResponseEntity<Product> postResponse = testRestTemplate.postForEntity(getBaseUrl(), newProduct, Product.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(postResponse.getBody()).isNotNull();
        Product createdProduct = postResponse.getBody();
        UUID productId = createdProduct.getId();
        assertThat(productId).isNotNull();

        ResponseEntity<Product> getResponse = testRestTemplate.getForEntity(getBaseUrl() + "/" + productId, Product.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getName()).isEqualTo("Laptop X");
        assertThat(getResponse.getBody().getCategory()).isEqualTo("Electronics");
    }

    @Test
    void shouldFetchTestProduct() {
        ResponseEntity<Product[]> response = testRestTemplate.getForEntity(getBaseUrl(), Product[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
        assertThat(Arrays.stream(response.getBody())
                .anyMatch(item -> item.getId().equals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))))
                .isTrue();
    }

    @Test
    void addProductWithDuplicateNameTest() {
        Product secondProduct = Product.builder()
                .name("Laptop X") //Nazwa już istniejąca w bazie
                .description("Gaming Laptop 2")
                .category("Electronics")
                .stock(5)
                .price(1700.00)
                .build();


        ResponseEntity<String> secondResponse = testRestTemplate.postForEntity(getBaseUrl(), secondProduct, String.class);
        assertThat(secondResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(secondResponse.getBody()).contains("Nazwa produktu musi być unikalna");
    }

    @Test
    void addProductWithInvalidNameLengthTest() {
        Product invalidProduct = Product.builder()
                .name("X")
                .description("Invalid Product")
                .category("Electronics")
                .stock(10)
                .price(1500.00)
                .build();

        ResponseEntity<String> response = testRestTemplate.postForEntity(getBaseUrl(), invalidProduct, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Słowo musi mieć od 3 do 20 znaków");
    }

    @Test
    void addProductWithInvalidPriceForCategoryTest() {
        Product invalidProduct = Product.builder()
                .name("Invalid product")
                .description("Product with invalid price")
                .category("Elektronika")
                .stock(10)
                .price(30.00)
                .build();


        ResponseEntity<String> response = testRestTemplate.postForEntity(getBaseUrl(), invalidProduct, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).toLowerCase())
                .contains("cena dla kategorii elektronika powinna być w zakresie: 50 - 50000 pln");


        invalidProduct.setPrice(100000.00);
        ResponseEntity<String> response2 = testRestTemplate.postForEntity(getBaseUrl(), invalidProduct, String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response2.getBody()).toLowerCase())
                .contains("cena dla kategorii elektronika powinna być w zakresie: 50 - 50000 pln");


        invalidProduct.setCategory("Książki");
        invalidProduct.setPrice(1000.00);
        ResponseEntity<String> response3 = testRestTemplate.postForEntity(getBaseUrl(), invalidProduct, String.class);
        assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response3.getBody()).toLowerCase())
                .contains("cena dla kategorii książki powinna być w zakresie: 5 - 500 pln");


        invalidProduct.setCategory("Odzież");
        invalidProduct.setPrice(5.00);
        ResponseEntity<String> response4 = testRestTemplate.postForEntity(getBaseUrl(), invalidProduct, String.class);
        assertThat(response4.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response4.getBody()).toLowerCase())
                .contains("cena dla kategorii odzież powinna być w zakresie: 10 - 5000 pln");
    }


    @Test
    void addProductWithInvalidStockTest() {
        Product invalidProduct = Product.builder()
                .name("Laptop X")
                .description("Gaming Laptop")
                .category("Electronics")
                .stock(-1)
                .price(1500.00)
                .build();

        ResponseEntity<String> response = testRestTemplate.postForEntity(getBaseUrl(), invalidProduct, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Wartość musi być liczbą całkowitą i wynosić co najmniej 0.");
    }

    @Test
    void updateProductWithInvalidDataTest() {
        UUID existingProductId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");

        Product updatedProduct = Product.builder()
                .name("XY") // Zbyt krótka nazwa
                .description("Updated Description")
                .category("Electronics")
                .stock(15)
                .price(2000.00)
                .build();

        ResponseEntity<String> response = testRestTemplate.exchange(
                getBaseUrl() + "/" + existingProductId,
                HttpMethod.PATCH,
                new HttpEntity<>(updatedProduct),
                String.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Słowo musi mieć od 3 do 20 znaków");
    }


}
