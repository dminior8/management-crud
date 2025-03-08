package pl.dminior.management_crud.web.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.dminior.management_crud.validation.ValidCategoryPrice;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ValidCategoryPrice
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    @Size(min = 3, max = 20, message = "Słowo musi mieć od 3 do 20 znaków")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Nazwa może zawierać tylko litery, cyfry i spacje")
    private String name;

    private String description;

    @NotBlank(message = "Kategoria nie może być pusta")
    private String category;

    @Min(value = 0, message = "Wartość musi być liczbą całkowitą i wynosić co najmniej 0.")
    private int stock;

    private Double price;

    @CreationTimestamp
    private Timestamp createdAt;
}