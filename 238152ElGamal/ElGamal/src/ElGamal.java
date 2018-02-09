import javafx.util.converter.BigIntegerStringConverter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ElGamal {
    public static void main(String[] args) {

        if (args.length > 1 || args.length == 0) {
            showError();
            return;
        }

        if (args[0].equals("-g")) {
            String output = "1665997633093155705263923663680487185948531888850484859473375695734301776192932338784530163"
                    + "\n" + "170057347237941209366519667629336535698946063913573988287540019819022183488419112350737049";

            writeToFile("elgamal.txt", output);
        }

        if (args[0].equals("-k")) {
            List<BigInteger> nums = fileToBigInt("elgamal.txt", 2);

            BigInteger p = nums.get(0);
            BigInteger g = nums.get(1);

            BigInteger b = randomBigInt(BigInteger.ONE, p.subtract(BigInteger.ONE));
            BigInteger beta = g.modPow(b, p);

            String publicString = p.toString() + "\n" + g.toString() + "\n" + beta.toString();
            writeToFile("public.txt", publicString);
            String privateString = p.toString() + "\n" + g.toString() + "\n" + b.toString();
            writeToFile("private.txt", privateString);
        }

        if (args[0].equals("-e")) {
            List<BigInteger> publicKey = fileToBigInt("public.txt", 3);

            List<BigInteger> plain = fileToBigInt("plain.txt", 1);

            BigInteger p = publicKey.get(0);
            BigInteger g = publicKey.get(1);
            BigInteger beta = publicKey.get(2);

            BigInteger m = plain.get(0);

            if (m.compareTo(p) > 0) {
                System.out.println("The value from file " + "plain.txt" + " is higher than prime number in " + "public.txt");
                return;
            }

            BigInteger k = randomBigInt(BigInteger.valueOf(2L), p.subtract(BigInteger.ONE));
            while (k.gcd(p).compareTo(BigInteger.ONE) != 0) {
                k = randomBigInt(BigInteger.valueOf(2L), p.subtract(BigInteger.ONE));
            }

            BigInteger r = g.modPow(k, p);
            BigInteger t = (m.multiply(beta.modPow(k, p))).mod(p);

            String crypto = r.toString() + "\n" + t.toString();
            writeToFile("crypto.txt", crypto);
        }

        if (args[0].equals("-d")) {
            List<BigInteger> privateKey = fileToBigInt("private.txt", 3);

            List<BigInteger> crypto = fileToBigInt("crypto.txt", 2);

            BigInteger p = privateKey.get(0);
            //BigInteger g = privateKey.get(1);
            BigInteger b = privateKey.get(2);

            BigInteger r = crypto.get(0);
            BigInteger t = crypto.get(1);

            BigInteger mod = r.modPow(b, p);
            BigInteger m = (mod.modInverse(p).multiply(t)).mod(p);

            String decrypt = m.toString();
            writeToFile("decrypt.txt", decrypt);
        }

        if (args[0].equals("-s")) {
            List<BigInteger> privateKey = fileToBigInt("private.txt", 3);

            List<BigInteger> message = fileToBigInt("message.txt", 1);

            BigInteger p = privateKey.get(0);
            BigInteger g = privateKey.get(1);
            BigInteger b = privateKey.get(2);

            BigInteger m = message.get(0);

            if (m.compareTo(p) > 0) {
                System.out.println("The value from " + "message.txt" + " is higher than prime number in " + "public.txt");
                return;
            }

            BigInteger k = randomBigInt(BigInteger.valueOf(2L), p.subtract(BigInteger.ONE));
            while (k.gcd(p.subtract(BigInteger.ONE)).compareTo(BigInteger.ONE) != 0) {
                k = randomBigInt(BigInteger.valueOf(2L), p.subtract(BigInteger.ONE));
            }

            BigInteger r = g.modPow(k, p);
            BigInteger x = ((m.subtract(b.multiply(r))).multiply(k.modInverse(p.subtract(BigInteger.ONE)))).mod(p.subtract(BigInteger.ONE));

            String signature = r.toString() + "\n" + x.toString();
            writeToFile("signature.txt", signature);
        }

        if (args[0].equals("-v")) {
            List<BigInteger> publicKey = fileToBigInt("public.txt", 3);

            List<BigInteger> message = fileToBigInt("message.txt", 1);

            List<BigInteger> signature = fileToBigInt("signature.txt", 2);

            BigInteger p = publicKey.get(0);
            BigInteger g = publicKey.get(1);
            BigInteger beta = publicKey.get(2);

            BigInteger m = message.get(0);

            BigInteger r = signature.get(0);
            BigInteger x = signature.get(1);

            BigInteger a = g.modPow(m, p);
            BigInteger b = (r.modPow(x, p).multiply(beta.modPow(r, p))).mod(p);

            if (a.compareTo(b) == 0) {
                String correct = "Signature is correct! ( ͡° ͜ʖ ͡°)";
                System.out.println(correct);
                writeToFile("verify.txt", correct);
            } else {
                String incorrect = "Signature is not correct! >:P";
                System.out.println(incorrect);
                writeToFile("verify.txt", incorrect);
            }
        }
    }

    private static void showError() {
        System.out.println("Argument error");
    }

    private static void checkResult(String filename, List<BigInteger> checkList, int numCount) {
        if (checkList.size() != numCount) {
            System.out.println("File " + filename + " does not contain " + numCount + " numbers.");
            System.exit(0);
        }
    }

    public static BigInteger randomBigInt(BigInteger min, BigInteger max) {
        Random rnd = new Random();
        do {
            BigInteger i = new BigInteger(max.bitLength(), rnd);
            if (i.compareTo(max) <= 0) {

                if (i.compareTo(min) > 0) {
                    return i;
                } else {
                    randomBigInt(min, max);
                }
            }
        } while (true);
    }

    private static String readFile(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename))).trim();
        } catch (IOException e) {
            throw new RuntimeException("File not found");
        }
    }

    private static List<BigInteger> fileToBigInt(String filename, int numCount) {
        String fileString = readFile(filename);
        List<BigInteger> nums = stringToDigit(fileString);
        checkResult(filename, nums, numCount);

        return nums;
    }

    private static List<BigInteger> stringToDigit(String string) {
        String stringNums = "";

        for (char c : string.toCharArray()) {
            if (Character.isDigit(c)) {
                stringNums += c;
            } else {
                stringNums += " ";
            }
        }

        List<BigInteger> bigIntList = new ArrayList<>();
        BigIntegerStringConverter bigIntegerStringConverter = new BigIntegerStringConverter();

        for (String liczbaString : stringNums.trim().replaceAll(" +", " ").split(" ")) {
            bigIntList.add(bigIntegerStringConverter.fromString(liczbaString));
        }

        return bigIntList;
    }

    private static void writeToFile(String filename, String text) {
        try {
            Files.write(Paths.get(filename), text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
