import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.*;

public class ECBEncode{

	public static void main(String[] args) throws IOException{
		
		int[] key = {10,12,13,14};
		ECB ecb = new ECB(key);
		int[] img = new int[2];
		
		FileInputStream imgIn = new FileInputStream("plain.bmp");
		FileOutputStream imgOut = new FileOutputStream("ecb_crypto.bmp");
		DataInputStream dataIn = new DataInputStream(imgIn);
		DataOutputStream dataOut = new DataOutputStream(imgOut);
		
		for(int i=0;i<10;i++){
			if(dataIn.available() > 0){
				img[0] = dataIn.readInt();
				img[1] = dataIn.readInt();
				dataOut.writeInt(img[0]);
				dataOut.writeInt(img[1]);
			}
		}
		
		int cipher[] = new int[2];
		boolean check = true;
		
		while(dataIn.available() > 0){
			try{
				img[0] = dataIn.readInt();
				check = true;
				img[1] = dataIn.readInt();
				cipher = ecb.encrypt(img);
				dataOut.writeInt(cipher[0]);
				dataOut.writeInt(cipher[1]);
				check = false;
			}catch(EOFException e){ 
				if(!check){
					dataOut.writeInt(img[0]);
					dataOut.writeInt(img[1]);
				}else							
					dataOut.writeInt(img[0]);
			}
		}
		dataIn.close();
		dataOut.close();
	}
}