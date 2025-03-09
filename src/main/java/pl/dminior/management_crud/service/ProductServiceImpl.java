package pl.dminior.management_crud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dminior.management_crud.repository.ProductHistoryRepository;
import pl.dminior.management_crud.repository.ProductRepository;
import pl.dminior.management_crud.validation.ProductNameValidator;
import pl.dminior.management_crud.web.model.Product;
import pl.dminior.management_crud.web.model.ProductHistory;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductHistoryRepository productHistoryRepository;
    private final ProductNameValidator productNameValidator;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        if(product.getName() != null) {
            productNameValidator.validateProductName(product.getName());
        }
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> updateProduct(UUID id, Product product) {
        if(product.getName() != null && product.getCategory() != null) {
            productNameValidator.validateProductName(product.getName());
        }

        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product existingProduct = productOpt.get();

            trackChanges(existingProduct, product);

            existingProduct.setName(
                    product.getName() == null
                    ? existingProduct.getName() : product.getName());
            existingProduct.setDescription(
                    product.getDescription() == null
                    ? existingProduct.getDescription() : product.getDescription());
            existingProduct.setPrice(
                    product.getPrice() == null
                            ? existingProduct.getPrice() : product.getPrice());
            existingProduct.setStock(
                    product.getStock() >= 0
                            ? existingProduct.getStock() : product.getStock());
            existingProduct.setCategory(
                    product.getCategory() == null
                            ? existingProduct.getCategory() : product.getCategory());
            existingProduct.setCreatedAt(Timestamp.from(Instant.now()));

            Product updatedProduct = productRepository.save(existingProduct);

            return Optional.of(updatedProduct);
        }
        return Optional.empty();
    }


    @Override
    @Transactional
    public boolean deleteProduct(UUID id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            product.setName(null);
            product.setDescription(null);
            product.setCreatedAt(null);

            productRepository.save(product);

            return true;
        }
        return false;
    }

    private void trackChanges(Product oldProduct, Product newProduct) {
        if (newProduct.getName() != null && !newProduct.getName().equals(oldProduct.getName())) {
            saveHistory(oldProduct, "name", oldProduct.getName(), newProduct.getName());
        }
        if (newProduct.getDescription() != null && !newProduct.getDescription().equals(oldProduct.getDescription())) {
            saveHistory(oldProduct, "description", oldProduct.getDescription(), newProduct.getDescription());
        }
        if (newProduct.getPrice() != null && !newProduct.getPrice().equals(oldProduct.getPrice())) {
            saveHistory(oldProduct, "price", oldProduct.getPrice().toString(), newProduct.getPrice().toString());
        }
        if (newProduct.getCategory() != null && !newProduct.getCategory().equals(oldProduct.getCategory())) {
            saveHistory(oldProduct, "category", oldProduct.getCategory(), newProduct.getCategory());
        }
        if (newProduct.getStock() >= 0 && newProduct.getStock() != oldProduct.getStock()) {
            saveHistory(oldProduct, "stock", Integer.toString(oldProduct.getStock()), Integer.toString(newProduct.getStock()));
        }
    }

    private void saveHistory(Product product, String field, String oldValue, String newValue) {
        ProductHistory history = ProductHistory.builder()
                .product(product)
                .changedField(field)
                .oldValue(oldValue)
                .newValue(newValue)
                .changeTimestamp(Timestamp.from(Instant.now()))
                .build();
        productHistoryRepository.save(history);
    }
}
