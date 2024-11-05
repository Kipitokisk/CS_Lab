import java.util.Scanner;

public class VigenereCipher {

    private static final String ROMANIAN_ALPHABET = "AĂÂBCDEFGHIÎJKLMNOPQRSȘTȚUVWXYZ";

    public static String normalizeText(String text) {
        StringBuilder normalized = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            if (ROMANIAN_ALPHABET.indexOf(c) != -1) {
                normalized.append(c);
            }
        }
        return normalized.toString();
    }

    public static String encrypt(String text, String key) {
        text = normalizeText(text);
        key = normalizeText(key);
        StringBuilder result = new StringBuilder();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int shift = ROMANIAN_ALPHABET.indexOf(key.charAt(j % key.length()));
            int originalPos = ROMANIAN_ALPHABET.indexOf(c);
            int newPos = (originalPos + shift) % ROMANIAN_ALPHABET.length();
            result.append(ROMANIAN_ALPHABET.charAt(newPos));
            j++;
        }

        return result.toString();
    }

    public static String decrypt(String text, String key) {
        text = normalizeText(text);
        key = normalizeText(key);
        StringBuilder result = new StringBuilder();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int shift = ROMANIAN_ALPHABET.indexOf(key.charAt(j % key.length()));
            int originalPos = ROMANIAN_ALPHABET.indexOf(c);
            int newPos = (originalPos - shift + ROMANIAN_ALPHABET.length()) % ROMANIAN_ALPHABET.length();
            result.append(ROMANIAN_ALPHABET.charAt(newPos));
            j++;
        }

        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to Encrypt(1) or Decrypt(2)? ");
        String choice = scanner.nextLine().trim().toUpperCase();

        System.out.print("Enter the text: ");
        String text = scanner.nextLine();

        System.out.print("Enter the key (minimum 7 characters): ");
        String key = scanner.nextLine();

        if (key.length() < 7) {
            System.out.println("Key must be at least 7 characters long.");
            return;
        }

        if (choice.equals("1")) {
            String encryptedText = encrypt(text, key);
            System.out.println("Encrypted text: " + encryptedText);
        } else {
            String decryptedText = decrypt(text, key);
            System.out.println("Decrypted text: " + decryptedText);
        }

        scanner.close();
    }
}
