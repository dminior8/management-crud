package pl.dminior.management_crud;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.dminior.management_crud.service.ProductService;
import pl.dminior.management_crud.web.model.Product;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest  // Używamy SpringBootTest do testów integracyjnych
@AutoConfigureMockMvc  // Pozwoli na automatyczne skonfigurowanie MockMvc
public class ManagementCrudIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    public void setUp() {
        sampleProduct = new Product();
        sampleProduct.setId(UUID.randomUUID());
        sampleProduct.setName("Test Product");
        sampleProduct.setPrice(100.0);
        sampleProduct.setCategory("Elektronika");
    }

    @Test
    public void shouldCreateProduct() throws Exception {
        // Arrange
        when(productService.createProduct(any(Product.class))).thenReturn(sampleProduct);

        // Act & Assert
        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.0))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void shouldGetProductById() throws Exception {
        // Arrange
        when(productService.getProductById(sampleProduct.getId())).thenReturn(Optional.of(sampleProduct));

        // Act & Assert
        mockMvc.perform(get("/api/v1/products/{id}", sampleProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.price").value(100.0));
    }

    @Test
    public void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(productService.getProductById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/products/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        // Arrange
        when(productService.updateProduct(any(UUID.class), any(Product.class))).thenReturn(Optional.of(sampleProduct));

        // Act & Assert
        mockMvc.perform(patch("/api/v1/products/{id}", sampleProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    public void shouldReturnNotFoundWhenUpdatingNonExistentProduct() throws Exception {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(productService.updateProduct(any(UUID.class), any(Product.class))).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(patch("/api/v1/products/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleProduct)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        // Arrange
        when(productService.deleteProduct(sampleProduct.getId())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/products/{id}", sampleProduct.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnNotFoundWhenDeletingNonExistentProduct() throws Exception {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(productService.deleteProduct(nonExistentId)).thenReturn(false);

        // Act & Assert
        mockMvc.perform(delete("/api/v1/products/{id}", nonExistentId))
                .andExpect(status().isNotFound());
    }
}
