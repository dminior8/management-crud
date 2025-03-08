package pl.dminior.management_crud.service;

import java.util.Set;

public interface BlockedWordService {

    Set<String> getBlockedWords();

    void addBlockedWord(String word);

    void removeBlockedWord(String word);

    boolean isNameBlocked(String name);
}
