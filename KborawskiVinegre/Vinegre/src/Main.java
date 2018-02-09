import java.io.*;
import java.util.Scanner;

public class Main{

    public void prepare(){
        File orig = new File("orig.txt");
        File plain = new File("plain.txt");
        String linetext = "", all = "";
        StringBuilder sB = new StringBuilder(all);
        try{
            try{
                try{
                    Reader reader = new InputStreamReader(new FileInputStream(orig),"ASCII");
                    BufferedReader in = new BufferedReader(reader);
                    Writer writer = new OutputStreamWriter(new FileOutputStream(plain), "UTF-8");
                    BufferedWriter out = new BufferedWriter(writer);
                    linetext = in.readLine();
                    while(linetext!=null){
                        linetext = linetext.replaceAll("[,.!?:;'0123456789 ]", "");
                        linetext = linetext.toLowerCase();
                        sB.append(linetext);
                        linetext = in.readLine();
                    }
                    all = sB.toString();
                    out.write(all);
                    in.close();
                    out.close();
                } catch (UnsupportedEncodingException e) {}
            } catch (FileNotFoundException e) {}
        } catch (IOException e) {}
    }

    public static void main(String[] args) throws FileNotFoundException {
        String[] x = args;
        Main start = new Main();

        switch (x[0]) {

            case "-p":
                start.prepare();
                break;

            case "-e":
            	VinegreCipher.encrypt();
                break;

            case "-d":
            	VinegreCipher.decrypt();
                break;

            case "-k":
            	VinegreCipher.crypt();
                break;
        }
    }
}