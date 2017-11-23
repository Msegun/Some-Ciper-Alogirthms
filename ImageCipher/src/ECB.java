import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ECB extends Helper{

	public ECB(){
		this.key = null;
	}

	public ECB(int[] keyAdd) throws FileNotFoundException{
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

	public int[] encrypt(int[] plainText){
		if(key == null){
			System.out.println("Blad klucza.");
			System.exit(0);
		}
		
		int left = plainText[0];	
		int right = plainText[1];
		
		sum = 0;

		for(int i=0; i<32;i++){
			sum += DELTA;
			left += ((right << 4) + key[0]) ^ (right+sum) ^ ((right >> 5) + key[1]);
			right += ((left << 4) + key[2]) ^ (left+sum) ^ ((left >> 5) + key[3]);
		}
		
		int block[] = new int[2];
		block[0] = left;
		block[1] = right;

		return block;
	}
}