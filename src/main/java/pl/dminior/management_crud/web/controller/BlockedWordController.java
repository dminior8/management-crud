package pl.dminior.management_crud.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dminior.management_crud.service.BlockedWordService;
import pl.dminior.management_crud.web.model.BlockedWord;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/blocked-words")
@RequiredArgsConstructor
public class BlockedWordController {

    private final BlockedWordService blockedWordService;

    @GetMapping
    public Set<String> getBlockedWords() {
        return blockedWordService.getBlockedWords();
    }

    @PostMapping
    public ResponseEntity<String> addBlockedWord(@RequestBody BlockedWord word) {
        blockedWordService.addBlockedWord(word.getName());
        return ResponseEntity.ok("Dodano zabronione słowo: " + word.getName());
    }

    @DeleteMapping("/{word}")
    public ResponseEntity<String> removeBlockedWord(@PathVariable BlockedWord word) {
        blockedWordService.removeBlockedWord(word.getName());
        return ResponseEntity.ok("Usunięto zabronione słowo: " + word.getName());
    }
}