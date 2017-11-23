import java.io.*;

public class KryptoanalizaAfiniczny extends Analiza{
	
	public KryptoanalizaAfiniczny(String _src, String _dest, String _extra){
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
        
            //Badanie przesuniec (ax+b mod alp)
            for(int a = 1; a<alp; a++){
                for(int b = 0; b<alp; b++){
                    br = new BufferedReader(new InputStreamReader(
                        new DataInputStream(
                                new FileInputStream(src)
                            )
                        )
                    );
                    bw.write("### a="+a+" b="+b+"\n");
                
            
                    String decoded;
                    while ((strLine = br.readLine()) != null)   {
                        int inverse = Help.moduloInverse(a, alp);
                        decoded = breakWord(strLine, a, b, inverse);
                        bw.write(decoded);
                        bw.write("\n");
                    }
                    br.close();
                }
            }       
            bw.close();
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
    }
	
	private String breakWord(String word, int a, int b, int inverse) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<word.length(); i++){
            char ch = word.charAt(i);
            if(ch < b){
                ch = (char)(alp - (b - ch));
            }else{
                ch = (char)(ch - b);
            }
            ch = (char)Math.floor(ch * inverse); 
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
            
            int a=0;
            int b=0;
            
            //Pierwsza litera w pliku extra odpowiada pierwszej literze pliku przed zakodowaniem
            char firstFromExtra = (char)brExtra.read();
            char secondFromExtra = (char)brExtra.read();
            brExtra.close();
            
            //Wczytaj pierwsza litere z plku zakodowanego
            char firstFromCrypto = (char)br.read();
            char secondFromCrypto = (char)br.read();
            br.close();
            
            if(secondFromExtra - firstFromExtra == 0){
                if(secondFromCrypto - firstFromCrypto == 0){
                    a = 1;
                    b = 0;
                }else{
                    throw new RuntimeException(
                        "Error");
                }
            }
            else{
                a = (secondFromCrypto - firstFromCrypto)/(secondFromExtra - firstFromExtra);
                b = firstFromCrypto - a*firstFromExtra;
            }
            //Obliczanie shifta
            if(a < 0){
                a = alp + a;
            }
            
            if(b < 0){
                b = alp + b;
            }
            
            SzyfrowanieAfiniczny af = new SzyfrowanieAfiniczny(a, b);
            BufferedWriter writer = new BufferedWriter(new FileWriter("key-new.txt"));
            writer.write(Integer.toString(b) + " " + Integer.toString(a));
            writer.close();
            af.decode(src, dest);
        }
        catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }
}
