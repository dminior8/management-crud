package pl.dminior.management_crud.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dminior.management_crud.web.model.ProductHistory;

import java.util.List;
import java.util.UUID;

//Gdyby robić implementację NoSQL, to:
//kolekcja Product, w nim jedna "kolumna", która jest listą
// (w niej poszczególne ChangeLogi, które też są listami i w nich co się zmieniło i na co:
// old_value1 -> new_value1, old_value2 -> new_value2 itd.

@Repository
public interface ProductHistoryRepository extends JpaRepository<ProductHistory, UUID> {
    List<ProductHistory> findByProductId(UUID productId);
}
