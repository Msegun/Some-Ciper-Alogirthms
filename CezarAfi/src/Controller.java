import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Controller {

	char command = 'e';
    char code = 'c';
	
    String key;
    
    Kodowanie kce;
    
    Analiza kae;
    
    HashMap<String,String> params = new HashMap<>();
    
    private boolean getCommands(String[] args) {
        String[] param;
        for(String str : args){
            param = str.split("=");
            if(param.length == 2){
                params.put(param[0], param[1]);
            }else{
                if(param[0].equals("-c")){
                    code = 'c';
                }
                if(param[0].equals("-a")){
                    code = 'a';
                }
                if(param[0].equals("-e")){
                    command = 'e';
                }
                if(param[0].equals("-d")){
                    command = 'd';
                }
                if(param[0].equals("-j")){
                    command = 'j';
                }
                if(param[0].equals("-k")){
                    command = 'k';
                }
            }
        }
        
        setDefaults();
        
        return true;
    }
    
    private void setDefaults() {
        if(params.get("plainFile") == null){
            params.put("plainFile", "plain.txt");
        }
        if(params.get("cryptoFile") == null){
            params.put("cryptoFile", "crypto.txt");
        }
        if(params.get("decryptFile") == null){
            params.put("decryptFile", "decrypt.txt");
        }
        if(params.get("keyFile") == null){
           params.put("keyFile", "key.txt");
        }
        if(params.get("extraFile") == null){
           params.put("extraFile", "extra.txt");
        }
        if(params.get("analyzeFile") == null){
           params.put("analyzeFile", "analyze.txt");
        }
    }
    
    private String get(String name, String _default) {
        String ret = params.get(name);
        if(ret == null){
            ret = _default;
            if(name.equals("key")){
                ret = Help.readFileLine(get("keyFile", "key.txt"));
                params.put("key", ret);
            }
        }
        return ret;
    }
    
    private void prepareEnv() throws FileNotFoundException {
        String a;
        String b;
        String key = null;
        String analyze = null;
        String times = null;
        File file = new File("key.txt");
        Scanner in = new Scanner(file);
        key = in.nextLine();
        
        switch(code){
            case 'c': //Szyfr Cezara
            	b = Character.toString(key.charAt(0));//get("b", "1");
                kce = new SzyfrowanieCezar(Integer.parseInt(b));
                          
                kae = new KryptoanalizaCezar(
                        get("cryptoFile", "crypto.txt"), 
                        get("decryptFile", "decrypt.txt"),
                        get("extraFile", "extra.txt"));
            break;
            case 'a': //Szyfr Afiniczny
                a = Character.toString(key.charAt(2));//get("a", "2");
                b = Character.toString(key.charAt(0));//get("b", "1");
                kce = new SzyfrowanieAfiniczny(Integer.parseInt(a), Integer.parseInt(b));
                
                kae = new KryptoanalizaAfiniczny(
                        get("cryptoFile", "crypto.txt"), 
                        get("decryptFile", "decrypt.txt"),
                        get("extraFile", "extra.txt"));
            break;
            default :
                throw new IllegalStateException("Error");
        }
    }
    
    private void launchCommand() throws FileNotFoundException {
        prepareEnv();
        
        switch(command){
            case 'e'://Kodowanie
                kce.encodingMode(true);
                kce.src = get("plainFile", "plain.txt");
                kce.dest = get("cryptoFile", "crypto.txt");
                new Thread(kce).start();
                break;
            case 'd'://Dekodowanie
                kce.encodingMode(false);
                kce.dest = get("decryptFile", "decrypt.txt");
                kce.src = get("cryptoFile", "crypto.txt");
                new Thread(kce).start();
                break;
            case 'j'://Kryptoanaliza z tekstem pomocniczym
                kae.hintMode(true);
                new Thread(kae).start();
                break;
            case 'k'://Kryptoanaliza bez tekstu pomocniczego
                kae.hintMode(false);
                new Thread(kae).start();
                break;
            default:
                throw new IllegalStateException("Error");
        }
    }
    
	public Controller(String[] args) throws FileNotFoundException{
        
        if(!getCommands(args)){
            System.out.println("Argument Error");
            System.exit(1);
        }
        
        launchCommand();
        
        System.out.println("Zakończono");
    }
}
