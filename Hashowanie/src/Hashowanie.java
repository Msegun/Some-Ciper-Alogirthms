import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Hashowanie {
	
	private static void getDiff() throws FileNotFoundException, UnsupportedEncodingException {
		
		String personalmd5;
		String personal_md5;
		String personalsha1;
		String personal_sha1;
		String personalsha224;
		String personal_sha224;
		String personalsha256;
		String personal_sha256;
		String personalsha384;
		String personal_sha384;
		String personalsha512;
		String personal_sha512;
		
		int res;
		int perc;
		
		File file = new File("hash.txt");
        Scanner in = new Scanner(file);
        
        personalmd5 = in.nextLine();
        personal_md5 = in.nextLine();
        personalsha1 = in.nextLine();
        personal_sha1 = in.nextLine();
        personalsha224 = in.nextLine();
        personal_sha224 = in.nextLine();
        personalsha256 = in.nextLine();
        personal_sha256 = in.nextLine();
        personalsha384 = in.nextLine();
        personal_sha384 = in.nextLine();
        personalsha512 = in.nextLine();
        personal_sha512 = in.nextLine();
        
        in.close();
        
        PrintWriter out = new PrintWriter("diff.txt");
        
        out.println("personal.txt | md5sum");
        out.println("personal_.txt | md5sum");
        out.println(personalmd5);
        out.println(personal_md5);
        res = getResult(personalmd5, personal_md5);
        perc = res * 100 / (personalmd5.length() * 4);
        out.println("Liczba bledow to " + res + ", czyli " + perc + "% ze 128");
        out.println();
        
        out.println("personal.txt | sha1sum");
        out.println("personal_.txt | sha1sum");
        out.println(personalsha1);
        out.println(personal_sha1);
        res = getResult(personalsha1, personal_sha1);
        perc = res * 100 / (personalsha1.length() * 4);
        out.println("Liczba bledow to " + res + ", czyli " + perc + "% ze 160");
        out.println();
        
        out.println("personal.txt | sha224sum");
        out.println("personal_.txt | sha224sum");
        out.println(personalsha224);
        out.println(personal_sha224);
        res = getResult(personalsha224, personal_sha224);
        perc = res * 100 / (personalsha224.length() * 4);
        out.println("Liczba bledow to " + res + ", czyli " + perc + "% z 224");
        out.println();
        
        out.println("personal.txt | sha256sum");
        out.println("personal_.txt | sha256sum");
        out.println(personalsha256);
        out.println(personal_sha256);
        res = getResult(personalsha256, personal_sha256);
        perc = res * 100 / (personalsha256.length() * 4);
        out.println("Liczba bledow to " + res + ", czyli " + perc + "% z 256");
        out.println();
        
        out.println("personal.txt | sha384sum");
        out.println("personal_.txt | sha384sum");
        out.println(personalsha384);
        out.println(personal_sha384);
        res = getResult(personalsha384, personal_sha384);
        perc = res * 100 / (personalsha384.length() * 4);
        out.println("Liczba bledow to " + res + ", czyli " + perc + "% z 384");
        out.println();
        
        out.println("personal.txt | sha512sum");
        out.println("personal_.txt | sha512sum");
        out.println(personalsha512);
        out.println(personal_sha512);
        res = getResult(personalsha512, personal_sha512);
        perc = res * 100 / (personalsha512.length() * 4);
        out.println("Liczba bledow to " + res + ", czyli " + perc + "% z 512");
        
        out.close();
	}
	
	public static int hashComp(char orig, char dash) throws FileNotFoundException, UnsupportedEncodingException {
		
		int x = ((int) orig) % 16;
		int y = ((int) dash) % 16;
		int p = 0;
		int diff = 0;
		
		int bin1[] = {0, 0, 0, 0, 0, 0, 0, 0};
		int bin2[] = {0, 0, 0, 0, 0, 0, 0, 0};
		
		while (x != 0) {
			bin1[p] = x % 2;
	        x/=2;
	        p++;
		}
		
		p = 0;
		
		while (y != 0) {
			bin1[p] = y % 2;
	        y/=2;
	        p++;
		}
		
		for (int i = 0; i < 8; i++){
	        if(bin1[i]!=bin2[i])
	            diff++;
	    }
		
		return diff;
	}

	public static int getResult(String orig, String dash) throws FileNotFoundException, UnsupportedEncodingException {
		
		int result = 0;
		
		for (int i = 0; i < orig.length(); i++) {
			result += hashComp(orig.charAt(i), dash.charAt(i));
		}
		
		return result;
	}
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		getDiff();
	}
}
