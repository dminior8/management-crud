package pl.dminior.management_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dminior.management_crud.web.model.BlockedWord;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlockedWordRepository extends JpaRepository<BlockedWord, UUID> {

    Optional<BlockedWord> findByName(String word);

    boolean existsByName(String word);
}
