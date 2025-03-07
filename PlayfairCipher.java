
package playfaircipher;
 import java.util.*;

public class PlayfairCipher {
 private char[][] matrix = new char[5][5];

    // Generate the Playfair Cipher matrix from the keyword
    public void generateMatrix(String keyword) {
        String key = prepareKey(keyword);
        Set<Character> seen = new HashSet<>();
        StringBuilder keyMatrix = new StringBuilder();

        for (char c : key.toCharArray()) {
            if (!seen.contains(c)) {
                keyMatrix.append(c);
                seen.add(c);
            }
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !seen.contains(c)) {  // 'J' is replaced with 'I'
                keyMatrix.append(c);
                seen.add(c);
            }
        }

        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = keyMatrix.charAt(index++);
            }
        }
    }

    // Prepare the key by removing non-alphabetic characters and replacing 'J' with 'I'
    private String prepareKey(String keyword) {
        return keyword.toUpperCase().replace("J", "I").replaceAll("[^A-Z]", "");
    }

    // Encrypt a plaintext using the Playfair cipher
    public String encrypt(String plaintext) {
        return processText(prepareKey(plaintext), true);
    }

    // Decrypt a ciphertext using the Playfair cipher
    public String decrypt(String ciphertext) {
        return processText(prepareKey(ciphertext), false);
    }

    // Process the text for encryption or decryption
    private String processText(String text, boolean encrypt) {
        StringBuilder processedText = new StringBuilder();
        List<String> pairs = createPairs(text);

        for (String pair : pairs) {
            int[] pos1 = findPosition(pair.charAt(0));
            int[] pos2 = findPosition(pair.charAt(1));

            if (pos1[0] == pos2[0]) { // Same row
                pos1[1] = (pos1[1] + (encrypt ? 1 : 4)) % 5;
                pos2[1] = (pos2[1] + (encrypt ? 1 : 4)) % 5;
            } else if (pos1[1] == pos2[1]) { // Same column
                pos1[0] = (pos1[0] + (encrypt ? 1 : 4)) % 5;
                pos2[0] = (pos2[0] + (encrypt ? 1 : 4)) % 5;
            } else { // Rectangle swap
                int temp = pos1[1];
                pos1[1] = pos2[1];
                pos2[1] = temp;
            }

            processedText.append(matrix[pos1[0]][pos1[1]])
                         .append(matrix[pos2[0]][pos2[1]]);
        }
        return processedText.toString();
    }

    // Create letter pairs from the input text
    private List<String> createPairs(String text) {
        List<String> pairs = new ArrayList<>();
        StringBuilder preparedText = new StringBuilder(text);
        for (int i = 0; i < preparedText.length(); i += 2) {
            if (i == preparedText.length() - 1 || preparedText.charAt(i) == preparedText.charAt(i + 1)) {
                preparedText.insert(i + 1, 'X');
            }
            pairs.add(preparedText.substring(i, i + 2));
        }
        return pairs;
    }

    // Find the position of a character in the matrix
    private int[] findPosition(char c) {
        if (c == 'J') c = 'I'; // Playfair replaces J with I
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // Display the 5x5 Playfair matrix
    public void displayMatrix() {
        System.out.println("Playfair Matrix:");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

   
    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
        PlayfairCipher cipher = new PlayfairCipher();

        System.out.print("Enter keyword for Playfair Cipher: ");
        String keyword = scanner.nextLine();
        cipher.generateMatrix(keyword);
        cipher.displayMatrix();

        System.out.print("Enter text to encrypt: ");
        String plaintext = scanner.nextLine();
        String encrypted = cipher.encrypt(plaintext);
        System.out.println("Encrypted: " + encrypted);

        System.out.print("Enter text to decrypt: ");
        String ciphertext = scanner.nextLine();
        System.out.println("Decrypted: " + cipher.decrypt(ciphertext));

        scanner.close();
        
    }
    
}
