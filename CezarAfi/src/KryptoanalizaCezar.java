import java.io.*;
import java.io.FileWriter;

public class KryptoanalizaCezar extends Analiza{

	public KryptoanalizaCezar(String _src, String _dest, String _extra){
        src = _src;
        dest = _dest;
        extra = _extra;
    }
	
	@Override
    public void run() {
        if(hasHint){
            //Kryptoanaliza z tekstem pomocniczym
            breakWithHint();
        }else{
            //Kryptoanaliza bez tekstu pomocniczego
            breakWithoutHint();
        }
    }
    
    public void breakWithoutHint(){
  
        try{
            BufferedReader br = null;
            
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(
                        new File(dest),
                    true)
            );
            
            String strLine;
        
            //Badanie przesuniec
            for(int i = 0; i<alp; i++){   
                br = new BufferedReader(new InputStreamReader(
                    new DataInputStream(
                            new FileInputStream(src)
                        )
                    )
                );
                bw.write("### b="+i+"\n");
                
                String[] words;
            
                String decoded;
                while ((strLine = br.readLine()) != null)   {
                    decoded = breakWord(strLine, i);
                    bw.write(decoded);
                    bw.write("\n");
                }
            }
            br.close();
            bw.close();
        }
        catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    private String breakWord(String word, int shift) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<word.length(); i++){
            char ch = word.charAt(i);
            if(ch < shift){
                ch = (char)(alp - (shift - ch));
            }else{
                ch = (char)(ch - shift);
            }
            ch = (char)(ch % alp);
            sb.append(ch);
        }
        return sb.toString();
    }
    
        public void breakWithHint(){
        try{
            BufferedReader br = new BufferedReader(
                new InputStreamReader(
                    new DataInputStream(
                        new FileInputStream(src)
                    )
                )
            );
            
            BufferedReader brExtra = new BufferedReader(
                new InputStreamReader(
                    new DataInputStream(
                        new FileInputStream(extra)
                    )
                )
            );
            
            //Pierwsza litera w pliku extra odpowiada pierwszej literze pliku przed zakodowaniem
            char letterFromExtra = (char)brExtra.read();
            brExtra.close();
            
            //Wczytaj pierwsza litere z plku zakodowanego
            char letterFromCrypto = (char)br.read();
            br.close();
            
            //Obliczanie shifta
            int shift = letterFromCrypto - letterFromExtra;
            if(shift < 0){
                shift = alp + shift;
            }
            
            SzyfrowanieCezar c = new SzyfrowanieCezar((char)shift);
            BufferedWriter writer = new BufferedWriter(new FileWriter("key-new.txt"));
            writer.write(Integer.toString(shift));
            writer.close();
            c.decode(src, dest);
        }
        catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
	
	
}
