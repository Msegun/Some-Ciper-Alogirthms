import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Helper{

	protected static int DELTA = 0x9e3779b9;		
	protected static int ROUNDS = 32;
	protected int sum;
	protected int[] key; 			

	public Helper(){
		key = null;
	}

	public Helper(int[] keyAdd) throws FileNotFoundException{
		File file = new File("key.txt");
		if (file.exists()) {
			Scanner in = new Scanner(file);
		    String keycode;
		    keycode = in.nextLine();
			key = new int[4];
			key[0] = keycode.charAt(0);
			key[1] = keycode.charAt(1);
			key[2] = keycode.charAt(2);
			key[3] = keycode.charAt(3);
			in.close();
		}
		else {
			key = new int[4];
			key[0] = keyAdd[0];
			key[1] = keyAdd[1];
			key[2] = keyAdd[2];
			key[3] = keyAdd[3];
		}
	}
	
	public void addKey(int[] key){
		if(key.length < 4)
			System.out.println("Key is less than 128 bits");
		else if(key.length > 4)
			System.out.println("Key is more than 128 bits");
		else
			this.key = key;			
	}
}