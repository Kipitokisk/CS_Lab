package com.CS.Lab2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CryptographyController {

    @Autowired
    private CryptographyService cryptographyService;

    @PostMapping("/analyze")
    public Map<String, Object> analyzeText(@RequestBody String text) {
        Map<String, Object> response = new HashMap<>();
        Map<Character, Double> textFrequency = cryptographyService.calculateFrequency(text);
        response.put("textFrequency", textFrequency);
        response.put("englishFrequency", cryptographyService.getEnglishLetterFrequencies());
        return response;
    }

    @PostMapping("/replace")
    public String replaceLetters(@RequestBody Map<String, Object> requestData) {
        String text = (String) requestData.get("text");
        Map<String, String> replacements = (Map<String, String>) requestData.get("replacements");
        return cryptographyService.replaceLetters(text, replacements);
    }
}
