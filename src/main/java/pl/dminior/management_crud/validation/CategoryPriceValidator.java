package pl.dminior.management_crud.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.dminior.management_crud.web.model.Product;

public class CategoryPriceValidator implements ConstraintValidator<ValidCategoryPrice, Product> {

    @Override
    public boolean isValid(Product product, ConstraintValidatorContext context) {
        if (product == null || product.getCategory() == null) {
            return true;
        }

        double price = product.getPrice();
        boolean isValid;

        switch (product.getCategory().toLowerCase()) {
            case "elektronika":
                isValid = price >= 50 && price <= 50_000;
                break;
            case "książki":
                isValid = price >= 5 && price <= 500;
                break;
            case "odzież":
                isValid = price >= 10 && price <= 5_000;
                break;
            default:
                return true;
        }

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Cena dla kategorii " + product.getCategory() + " powinna być w zakresie: " + getPriceRange(product.getCategory()))
                    .addPropertyNode("price")
                    .addConstraintViolation();
        }

        return isValid;
    }

    private String getPriceRange(String category) {
        switch (category.toLowerCase()) {
            case "elektronika":
                return "50 - 50000 PLN";
            case "książki":
                return "5 - 500 PLN";
            case "odzież":
                return "10 - 5000 PLN";
            default:
                return "Nieznana kategoria";
        }
    }
}

