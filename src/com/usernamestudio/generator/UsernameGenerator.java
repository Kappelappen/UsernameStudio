package com.usernamestudio.generator;

import java.util.List;
import java.util.Random;

public class UsernameGenerator {

    private final Random random = new Random();

    public String generate(
            List<String> words,
            int wordCount,
            boolean hasNumbers,
            boolean hasUnderscore,
            boolean hasSymbols,
            boolean capitalize) {

        if (words == null || words.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < wordCount; i++) {

            String word =
                words.get(random.nextInt(words.size()));

            // capitalize styrning
            if (capitalize) {
                word = Character.toUpperCase(word.charAt(0))
                     + word.substring(1).toLowerCase();
            } else {
                word = word.toLowerCase();
            }

            // separator mellan ord
            if (i > 0 && hasUnderscore) {
                sb.append("_");
            }

            sb.append(word);
        }

        // suffix
        if (hasNumbers) {
            sb.append(random.nextInt(100));
        }

        if (hasSymbols) {
            sb.append("!");
        }

        return sb.toString();
    }
}