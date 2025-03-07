
package monoalphabeticfrequencyanalysis;
import java.util.*;

public class MonoalphabeticFrequencyAnalysis {
 private static final String ENGLISH_FREQ_ORDER = "ETAOINSHRDLCUMWFGYPBVKJXQZ";

    // Function to count letter frequencies in the given text
    public static Map<Character, Integer> countFrequencies(String text) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toUpperCase().toCharArray()) {
            if (Character.isLetter(c)) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }
        return frequencyMap;
    }

    // Function to perform frequency analysis and approximate decryption
    public static String performFrequencyAnalysis(String text) {
        // Step 1: Count character frequencies in the ciphertext
        Map<Character, Integer> frequencyMap = countFrequencies(text);

        // Step 2: Sort characters by frequency in descending order
        List<Map.Entry<Character, Integer>> sortedFreq = new ArrayList<>(frequencyMap.entrySet());
        sortedFreq.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Step 3: Create a substitution mapping
        Map<Character, Character> substitutionMap = new HashMap<>();
        for (int i = 0; i < sortedFreq.size() && i < ENGLISH_FREQ_ORDER.length(); i++) {
            substitutionMap.put(sortedFreq.get(i).getKey(), ENGLISH_FREQ_ORDER.charAt(i));
        }

        // Step 4: Apply the substitution to decrypt the text
        StringBuilder decryptedText = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            decryptedText.append(substitutionMap.getOrDefault(c, c)); // Replace if mapped, otherwise keep original
        }

        return decryptedText.toString();
    }

    
    public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in);

        System.out.print("Enter encrypted text for cryptanalysis: ");
        String encryptedText = scanner.nextLine();

        String decryptedText = performFrequencyAnalysis(encryptedText);

        System.out.println("Decrypted (approximation): " + decryptedText);
        scanner.close();

        
    }
    
}
