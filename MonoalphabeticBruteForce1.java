
package monoalphabeticbruteforce1;

  import java.util.*;
public class MonoalphabeticBruteForce1 {
 private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Function to decrypt using a given key
    public static String decrypt(String cipherText, String key) {
        StringBuilder decryptedText = new StringBuilder();
        cipherText = cipherText.toUpperCase();
        
        for (char c : cipherText.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = key.indexOf(c);
                decryptedText.append(ALPHABET.charAt(index));
            } else {
                decryptedText.append(c);
            }
        }
        return decryptedText.toString();
    }

    // Generate and test key permutations (limited for feasibility)
    public static void bruteForce(String cipherText) {
        List<String> possibleKeys = generateSampleKeys();
        for (String key : possibleKeys) {
            System.out.println("Key: " + key + " -> " + decrypt(cipherText, key));
        }
    }

    // Generate a sample set of key permutations (not full 26!)
    private static List<String> generateSampleKeys() {
        List<String> keys = new ArrayList<>();
        char[] letters = ALPHABET.toCharArray();
        for (int i = 0; i < 5; i++) { // Generate 5 random key permutations
            shuffleArray(letters);
            keys.add(new String(letters));
        }
        return keys;
    }

    // Shuffle array to create a new key
    private static void shuffleArray(char[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            char temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
    
    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
        System.out.print("Enter encrypted text: ");
        String cipherText = scanner.nextLine();
        scanner.close();

        bruteForce(cipherText);
        
    }
    
}
