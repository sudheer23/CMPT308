




import java.util.Scanner;

public class Driver {
	   private static String[][] originalKey = new String[4][4];

	public static void main(String args[])
	{
		 System.out.println("Enter the key");
		 @SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
	     String input = sc.nextLine();
	    
	       int i = 0;
		    for (int col = 0; col <4; col++) {
		      for (int row = 0; row <4; row = row + 1) {
		        originalKey[row][col] = input.substring(i, i + 2);
		        i = i + 2;
		      }
		    }
	     Aescipher as = new Aescipher();
	     as.generateRoundKeys(originalKey);
	     
	     
	 
	}
	
	
}
