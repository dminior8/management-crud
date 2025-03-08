package pl.dminior.management_crud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.dminior.management_crud.repository.BlockedWordRepository;
import pl.dminior.management_crud.web.model.BlockedWord;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlockedWordServiceImpl implements BlockedWordService {

    private final BlockedWordRepository blockedWordRepository;

    @Override
    public Set<String> getBlockedWords() {
        return blockedWordRepository.findAll().stream()
                .map(BlockedWord::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public void addBlockedWord(String word) {
        if (!blockedWordRepository.existsByName(word.toLowerCase())) {
            BlockedWord blockedWord = new BlockedWord();
            blockedWord.setName(word.toLowerCase());
            blockedWordRepository.save(blockedWord);
        } else {
            throw new RuntimeException("Blocked word already exists");
        }
    }

    @Override
    public void removeBlockedWord(String word) {
        blockedWordRepository.findByName(word.toLowerCase())
                .ifPresent(blockedWordRepository::delete);
    }

    @Override
    public boolean isNameBlocked(String name) {
        return blockedWordRepository.findAll().stream()
                .anyMatch(blockedWord -> name.toLowerCase().contains(blockedWord.getName()));
    }
}
