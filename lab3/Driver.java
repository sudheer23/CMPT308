
import java.io.FileNotFoundException;
import java.io.FileReader;
// This import statement imports BigInteger  
//BigInteger converts the String representation of a BigInteger in the specified radix into a BigInteger
import java.math.BigInteger;
import java.util.Scanner;

/**
 * File name:AEScipher.java 
 * Author: Sudheer Mandava 
 * Date: 3/28/2016 Course name:
 * MSCS630 
 * Description : This file contains the declaration of the AES abstract
 * data The Class AEScipher.java is where the Core computational logic
 **/
public class Driver {
    /**
     * main()
     * 
     * @param Array
     *            of arguments are taken into the String which is a BigInteger
     */
    public static void main(String[] args) {
        //Reads input key and text using Scanner class
        Scanner scr = new Scanner(System.in);
        String key = scr.nextLine();
        //Converting keyHex integer to byte array
        byte[] keyHex = new BigInteger(key, 16).toByteArray();
        String text = scr.nextLine();
        //Converting text to byte array
        byte[] pTextHex = new BigInteger(text, 16).toByteArray();
        //Calls the encrpyt() method in AEScipher class and sending pTextHex, 
        //keyHex as input parameters.
        byte[] out = AEScipher.encrypt(pTextHex, keyHex);
        for (int i = 0; i < out.length; i++)
            System.out.printf("%02X", out[i]);
    }

}
