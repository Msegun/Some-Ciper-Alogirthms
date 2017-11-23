import java.io.*;

abstract public class Kodowanie implements Runnable {
	protected String src;
    protected String dest;
    protected String keyFile;
    protected int alp = 127;
    protected boolean isEncoding = true;
    protected String key;
    protected StringBuilder sb = new StringBuilder();
    
    public void encode(String src, String dest) {
        try{
            FileInputStream fstream = new FileInputStream(src);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(
                        new File(dest),
                    true)
            );
            
            String encoded;
            while ((strLine = br.readLine()) != null)   {
                encoded = encode(strLine);
                bw.write(encoded);
                bw.write("\n");
            }
            br.close();
            in.close();
            fstream.close();
            
            bw.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public String encode(String text) {
        sb = new StringBuilder();
        Character encoded;
        for(int i=0; i<text.length(); i++){
            encoded = encode(text.charAt(i));
            sb.append(encoded);
        }
        return sb.toString();
    }
    
    public Character encode(Character ch) {
        return ch;
    }
    
    public void decode(String src, String dest) {
        try{
            FileInputStream fstream = new FileInputStream(src);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            
            String[] words;
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(
                        new File(dest),
                    true)
            );
            
            String decoded;
            while ((strLine = br.readLine()) != null)   {
                decoded = decode(strLine);
                bw.write(decoded);
                bw.write("\n");
            }
            br.close();
            in.close();
            fstream.close();
            
            bw.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    public String decode(String text) {
        sb = new StringBuilder();
        Character decoded;
        for(int i=0; i<text.length(); i++){
            decoded = decode(text.charAt(i));
            sb.append(decoded);
        }
        return sb.toString();
    }
    
    public Character decode(Character ch) {
        return ch;
    }
    public void encodingMode(boolean _enc) {
        isEncoding = _enc;
    }
    
    public void direction(String _src, String _dest){
        src = _src;
        dest = _dest;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Error");
    }
}
