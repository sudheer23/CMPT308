//*@author sudheer mandava


public class Aescipher {

	

	  private static final String[][] SBOX = {
	    {"63", "7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76"},
	    {"CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C", "A4", "72", "C0"},
	    {"B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15"},
	    {"04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75"},
	    {"09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84"},
	    {"53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF"},
	    {"D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8"},
	    {"51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2"},
	    {"CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73"},
	    {"60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB"},
	    {"E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79"},
	    {"E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08"},
	    {"BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A"},
	    {"70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E"},
	    {"E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF"},
	    {"8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16"}};

	  private static final String[][] RCON = {
	    {"8D", "01", "02", "04", "08", "10", "20", "40", "80", "1B", "36", "6C", "D8", "AB", "4D", "9A"},
	    {"2F", "5E", "BC", "63", "C6", "97", "35", "6A", "D4", "B3", "7D", "FA", "EF", "C5", "91", "39"},
	    {"72", "E4", "D3", "BD", "61", "C2", "9F", "25", "4A", "94", "33", "66", "CC", "83", "1D", "3A"},
	    {"74", "E8", "CB", "8D", "01", "02", "04", "08", "10", "20", "40", "80", "1B", "36", "6C", "D8"},
	    {"AB", "4D", "9A", "2F", "5E", "BC", "63", "C6", "97", "35", "6A", "D4", "B3", "7D", "FA", "EF"},
	    {"C5", "91", "39", "72", "E4", "D3", "BD", "61", "C2", "9F", "25", "4A", "94", "33", "66", "CC"},
	    {"83", "1D", "3A", "74", "E8", "CB", "8D", "01", "02", "04", "08", "10", "20", "40", "80", "1B"},
	    {"36", "6C", "D8", "AB", "4D", "9A", "2F", "5E", "BC", "63", "C6", "97", "35", "6A", "D4", "B3"},
	    {"7D", "FA", "EF", "C5", "91", "39", "72", "E4", "D3", "BD", "61", "C2", "9F", "25", "4A", "94"},
	    {"33", "66", "CC", "83", "1D", "3A", "74", "E8", "CB", "8D", "01", "02", "04", "08", "10", "20"},
	    {"40", "80", "1B", "36", "6C", "D8", "AB", "4D", "9A", "2F", "5E", "BC", "63", "C6", "97", "35"},
	    {"6A", "D4", "B3", "7D", "FA", "EF", "C5", "91", "39", "72", "E4", "D3", "BD", "61", "C2", "9F"},
	    {"25", "4A", "94", "33", "66", "CC", "83", "1D", "3A", "74", "E8", "CB", "8D", "01", "02", "04"},
	    {"08", "10", "20", "40", "80", "1B", "36", "6C", "D8", "AB", "4D", "9A", "2F", "5E", "BC", "63"},
	    {"C6", "97", "35", "6A", "D4", "B3", "7D", "FA", "EF", "C5", "91", "39", "72", "E4", "D3", "BD"},
	    {"61", "C2", "9F", "25", "4A", "94", "33", "66", "CC", "83", "1D", "3A", "74", "E8", "CB", "8D"}};


	
	  public static String[][] FullMatrix = new String[4][44];

	  public void generateRoundKeys(String[][] originalKey) {


	   

	    // take original key and make coloums for full matrix
	    for (int row = 0; row <4; row++) {
	      for (int col = 0; col <4; col++) {
	        FullMatrix[row][col] = originalKey[row][col];
	      }
	    }
	    //creating columns for Dupilicate matrix
	    String[][] Duplicatefullmatrix = null;
	    for (int col = 4; col < 44; col++) {
	      /* if it not divide with 4 xor column with index*/
	      if (col % 4 != 0) {
	        for (int row = 0; row <4; row++) {
	          FullMatrix[row][col] = performXOR(FullMatrix[row][col - 4], FullMatrix[row][col - 1]);
	        }
	      } else {
	        /* if the index of column is divisible by 4,change column t rows */
	        Duplicatefullmatrix = new String[1][4];

	        Duplicatefullmatrix[0][0] = FullMatrix[0][col - 1];
	        Duplicatefullmatrix[0][1] = FullMatrix[1][col - 1];
	        Duplicatefullmatrix[0][2] = FullMatrix[2][col - 1];
	        Duplicatefullmatrix[0][3] = FullMatrix[3][col - 1];

	        // Shift to the left of column Duplicate matrix
	        Duplicatefullmatrix[0][0] = FullMatrix[1][col - 1];
	        Duplicatefullmatrix[0][1] = FullMatrix[2][col - 1];
	        Duplicatefullmatrix[0][2] = FullMatrix[3][col - 1];
	        Duplicatefullmatrix[0][3] = FullMatrix[0][col - 1];

	        // change the four bytes Duplicate matrix  using an S-box function
	        for (int m = 0; m < 1; m++) {
	          for (int n = 0; n < 4; n++) {
	            Duplicatefullmatrix[m][n] = aesSbox(Duplicatefullmatrix[m][n]);
	          }
	        }

	        // get RConvalues to do XOR 
	       
	        int numRound = col / 4;
	     
	        Duplicatefullmatrix[0][0] = performXOR(RCON[0][numRound], Duplicatefullmatrix[0][0]);

	        // final XOR
	        for (int row = 0; row <4; row++) {
	          FullMatrix[row][col] = performXOR(FullMatrix[row][col - 4], Duplicatefullmatrix[0][row]);
	        }
	      }
	    }
	    //Print Round Keys
	    int Rounds = 1;
	    int column = 0;
	    while (Rounds <= 11) {
	      for (int k = 0; k <4; k++, column++) {
	        for (int row = 0; row <4; row++) {
	          System.out.print(FullMatrix[row][column]);
	        }
	      }
	      System.out.println();
	      Rounds++;
	    }
	    System.out.println("");
	  }

	  public static String aesSbox(String in) {
	    int x = Integer.parseInt(in.split("")[0], 16);
	    int y = Integer.parseInt(in.split("")[1], 16);
	    String output = SBOX[x][y];
	    return output;
	  }

	  public static String performXOR(String one, String two) {
	    int value1 = Integer.parseInt(one, 16);
	    int value2 = Integer.parseInt(two, 16);
	    int XORvalue = value1 ^ value2;
	    String result = Integer.toHexString(XORvalue);
	    if (result.length() == 1) {
	      result = "0" + result;
	    }
	    return result;
	  }
	        
	}



