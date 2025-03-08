package pl.dminior.management_crud.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import pl.dminior.management_crud.service.BlockedWordService;

@Component
@RequiredArgsConstructor
public class ProductNameValidator {

    private final BlockedWordService blockedWordService;

    public void validateProductName(String name) {
        if (blockedWordService.isNameBlocked(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nazwa produktu zawiera zabronione frazy.");
        }
    }
}

