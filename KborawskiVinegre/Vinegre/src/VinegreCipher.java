import java.io.*;
import java.util.Scanner;

public class VinegreCipher {

    public static int maxKeyLen = 20;

    static void encrypt() throws FileNotFoundException{
        String key;
        File file = new File("key.txt");
        Scanner in = new Scanner(file);
        key = in.nextLine();
        in.close();
        String res = "", text;
        File src = new File("plain.txt");
        File dest = new File("crypto.txt");
        Scanner entry = new Scanner(src);
        text = entry.nextLine();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'a' || c > 'z') continue;
            res += (char)((c + key.charAt(j) - 2 * 'a') % 26 + 'a');
            j = ++j % key.length();
        }
        PrintWriter out = new PrintWriter(dest);
        out.println(res);
        out.close();
    }

    static void decrypt() throws FileNotFoundException{
        String key;
        File file = new File("key.txt");
        Scanner in = new Scanner(file);
        key = in.nextLine();
        in.close();
        String res = "", text;
        File src = new File("crypto.txt");
        File dest = new File("decrypt.txt");
        Scanner entry = new Scanner(src);
        text = entry.nextLine();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'a' || c > 'z') continue;
            res += (char)((c - key.charAt(j) + 26) % 26 + 'a');
            j = ++j % key.length();
        }
        PrintWriter out = new PrintWriter(dest);
        out.println(res);
        out.close();
    }

    static void crypt() throws FileNotFoundException{
        String res = "", text;
        File src = new File("crypto.txt");
        File dest = new File("decrypt.txt");
        Scanner entry = new Scanner(src);
        text = entry.nextLine();
        res = cryptanalysis(text, maxKeyLen, 'a', 'z');
        PrintWriter out = new PrintWriter(dest);
        out.println(res);
        out.close();
        getKey();
    }

    static void getKey() throws FileNotFoundException{
        String key;
        File file = new File("decrypt.txt");
        Scanner in = new Scanner(file);
        key = in.nextLine();
        in.close();
        String res = "", text;
        File src = new File("crypto.txt");
        File dest = new File("crypto-key.txt");
        Scanner entry = new Scanner(src);
        text = entry.nextLine();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'a' || c > 'z') continue;
            res += (char)((c - key.charAt(j) + 26) % 26 + 'a');
            j = ++j % key.length();
        }
        String keyfinal = findValidKey(res);
        PrintWriter out = new PrintWriter(dest);
        out.println(keyfinal);
        out.close();
    }

    static String findValidKey(String keydup) throws FileNotFoundException{
        String valid;
        File file = new File("crypto.txt");
        Scanner in = new Scanner(file);
        valid = in.nextLine();
        in.close();

        for(int i = 1; i <= maxKeyLen; i++){
            String str = keydup.substring(0, i);

            if((keyCheck(str)).equals(valid)){
                return str;
            }
        }
        return "";
    }

    static String keyCheck(String pass) throws FileNotFoundException{
        String res = "", text;
        File src = new File("decrypt.txt");
        Scanner entry = new Scanner(src);
        text = entry.nextLine();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'a' || c > 'z') continue;
            res += (char)((c + pass.charAt(j) - 2 * 'a') % 26 + 'a');
            j = ++j % pass.length();
        }
        return res;
    }

    private static double[] frequencies = { 8.167, 1.492, 2.782, 4.253, 12.702, 2.228,
            2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507,
            1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150,
            1.974, 0.074 };

    private static double score(String data){
        int[] letters = new int[26];
        for(char c : data.toLowerCase().toCharArray()) letters[c - 'a']++;

        double sumDSquared = 0.0;
        for(int j = 0; j < frequencies.length; j++) sumDSquared += Math.pow((100.0 * letters[j] / data.length() - frequencies[j]), 2);
        return sumDSquared;
    }

    public static String min(String a, String b){
        return a == null || (b != null && score(a) > score(b)) ? b : a;
    }

    static String caesarDecrypt(String msg, char minChar, char maxChar){
        String best = null;

        for(int i = minChar; i <= maxChar; i++) best = min(best, vigenereHelper(msg, "" + (char) (i), minChar, maxChar));

        return best;
    }

    static String vigenereHelper(String data, String password, char minChar, char maxChar){
        StringBuilder out = new StringBuilder();

        for(int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            int pass = password.charAt(i % password.length()) - minChar, range = maxChar - minChar + 1;
            out.append((c >= minChar && c <= maxChar) ? (char) (minChar + ((c - minChar + pass) % range + range) % range) : c);
        }

        return out.toString();
    }

    static StringBuilder[] splitInterleaves(String input, int length){
        StringBuilder[] inter = new StringBuilder[length];

        for(int i = 0; i < inter.length; i++) inter[i] = new StringBuilder();
        for(int j = 0; j < input.length(); j++) inter[j % length].append(input.charAt(j));

        return inter;
    }

    static String cryptanalysis(String input, int maxLen, char minChar, char maxChar){
        String best = null;

        for(int keylen = 1; keylen <= maxLen; keylen++) {
            StringBuilder[] inter = splitInterleaves(input, keylen);
            String[] decryptedInterleaves = new String[keylen];

            for(int j = 0; j < keylen; j++) decryptedInterleaves[j] = caesarDecrypt(inter[j].toString(), minChar, maxChar);

            StringBuilder combined = new StringBuilder();

            for(int x = 0; x < input.length(); x++) combined.append(decryptedInterleaves[x % keylen].charAt(x / keylen));

            best = min(best, combined.toString());
        }
        return best;
    }

}