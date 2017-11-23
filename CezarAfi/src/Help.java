import java.io.*;
import java.util.BitSet;
import java.util.Random;

public class Help {
	
	public static void p(String _str) {
        System.out.print(_str);
    }
	
	public static int moduloInverse(int number, int modulo) {
        int u = 1;
        int w = number;
        int x = 0;
        int z = modulo;

        while (w != 0) {
            if (w < z) {
                int temp;
                temp = u;
                u = x;
                x = temp;

                temp = w;
                w = z;
                z = temp;
            }
            int q = w / z;
            u = u - q * x;
            w = w - q * z;
        }

        if (z != 1) {
            System.out.println("Error");
            return number;
        }

        if (x < 0) {
            x = x + modulo;
        }
        return x;
    }
	
	public static int NWD(int a, int b) {
        int c;
        while (b != 0) {
            c = a % b;
            a = b;
            b = c;
        }
        return a;
    }
	
	public static String readFileLine(String file) {
        try {
            String strLine;
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                    new DataInputStream(
                    new FileInputStream(file))));
            strLine = br.readLine();
            br.close();
            return strLine;
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
            return "";
        }
    }
	
	static void writeLineToFile(String src, String string) {
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(
                        new File(src),
                    true));
            bw.write(string);
            bw.newLine();
            bw.close();
        }
        catch(Exception ex){
            System.err.println(ex);
            System.out.println("Error");
        }
    }
	
	
	
}
