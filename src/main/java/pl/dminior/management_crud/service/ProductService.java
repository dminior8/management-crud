package pl.dminior.management_crud.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.dminior.management_crud.web.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProductService {
    List<Product> getAllProjects();

    Optional<Product> getProductById(UUID id);

    Product createProduct(Product product);

    Optional<Product> updateProduct(UUID id, Product product);

    boolean deleteProduct(UUID id);
}
