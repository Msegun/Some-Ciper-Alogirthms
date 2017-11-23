
public class SzyfrowanieAfiniczny extends Kodowanie{
	
	int a = 1;
    int b = 0;
    
    public SzyfrowanieAfiniczny(int _a, int _b){
       a = _a;
       b = _b;
       if(Help.NWD(a,26) > 1){
           System.out.println("Error");
       }
    }
    
    @Override
    public Character encode(Character ch) {
        return (char)((a * ch.charValue() + b) % alp);
    }
    
    @Override
    public Character decode(Character ch) {
        int znak = ch.charValue();
        
        znak -= b;
        znak = znak * Help.moduloInverse(a, alp);
        if(znak >= 0){
            znak %= alp; 
        }else{
            while(znak <= -alp){
                znak += alp;
            }
            znak = alp +znak;
        }
        
        return (char)znak;
    }
    
    @Override
    public void run() {
        if(isEncoding){
            encode(src, dest);
        }else{
            decode(src,dest);
        }
    }
}
