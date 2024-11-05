package com.CS.Lab2;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CryptographyService {

    private final Map<Character, Double> englishLetterFrequencies = createEnglishLetterFrequencies();

    private Map<Character, Double> createEnglishLetterFrequencies() {
        Map<Character, Double> frequencies = new LinkedHashMap<>();;
        frequencies.put('E', 12.70);
        frequencies.put('T', 9.06);
        frequencies.put('A', 8.17);
        frequencies.put('O', 7.51);
        frequencies.put('I', 6.97);
        frequencies.put('N', 6.75);
        frequencies.put('S', 6.33);
        frequencies.put('H', 6.09);
        frequencies.put('R', 5.99);
        frequencies.put('D', 4.25);
        frequencies.put('L', 4.03);
        frequencies.put('C', 2.78);
        frequencies.put('U', 2.76);
        frequencies.put('M', 2.41);
        frequencies.put('W', 2.36);
        frequencies.put('F', 2.23);
        frequencies.put('G', 2.02);
        frequencies.put('Y', 1.97);
        frequencies.put('P', 1.93);
        frequencies.put('B', 1.49);
        frequencies.put('V', 0.98);
        frequencies.put('K', 0.77);
        frequencies.put('J', 0.15);
        frequencies.put('X', 0.15);
        frequencies.put('Q', 0.10);
        frequencies.put('Z', 0.07);

        return frequencies;
    }

    public Map<Character, Double> calculateFrequency(String text) {
        Map<Character, Long> frequencyCount = text.toUpperCase().chars()
                .filter(Character::isLetter)
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        long totalLetters = frequencyCount.values().stream()
                .mapToLong(Long::longValue)
                .sum();

        Map<Character, Double> frequencyPercentage = new LinkedHashMap<>();

        if (totalLetters > 0) {
            for (Map.Entry<Character, Long> entry : frequencyCount.entrySet()) {
                double percentage = (entry.getValue().doubleValue() / totalLetters) * 100;
                frequencyPercentage.put(entry.getKey(), percentage);
            }
        }

        List<Map.Entry<Character, Double>> sortedEntries = new ArrayList<>(frequencyPercentage.entrySet());
        sortedEntries.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));

        Map<Character, Double> sortedFrequencyPercentage = new LinkedHashMap<>();
        for (Map.Entry<Character, Double> entry : sortedEntries) {
            sortedFrequencyPercentage.put(entry.getKey(), entry.getValue());
        }

        return sortedFrequencyPercentage;
    }



    public Map<Character, Double> getEnglishLetterFrequencies() {
        return englishLetterFrequencies;
    }

    public String replaceLetters(String text, Map<String, String> replacements) {
        StringBuilder result = new StringBuilder(text.length());

        for (char c : text.toCharArray()) {
            String originalChar = String.valueOf(c);

            if (Character.isLetter(c)) {
                String replacement = replacements.getOrDefault(originalChar.toUpperCase(), originalChar.toUpperCase());

                if (Character.isLowerCase(c)) {
                    result.append(replacement.toLowerCase());
                } else {
                    result.append(replacement.toUpperCase());
                }
            } else {
                result.append(c);  // Keep non-letter characters as they are
            }
        }

        return result.toString();
    }
}