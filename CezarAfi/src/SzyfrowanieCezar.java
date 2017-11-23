
public class SzyfrowanieCezar extends Kodowanie{
	
	int rot = 1;
    
    public SzyfrowanieCezar(int _rot){
       rot = _rot;
    }
    
    @Override
    public Character encode(Character ch) {
        return (char)((ch.charValue() + rot) % alp);
    }
    
    @Override
    public Character decode(Character ch) {
        int znak = ch.charValue();
        
        znak -= rot;
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
