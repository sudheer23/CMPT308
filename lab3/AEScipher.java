
/**
 * File name:AEScipher.java 
 * Author: Sudheer Mandava 
 * Date: 3/28/2016 
 * Course name: MSCS630 
 * Description : This file contains the declaration of the AES abstract data
 * The Class AEScipher.java is where the Core computational logic
 * */

// References: http://csrc.nist.gov/publications/fips/fips197/fips-197.pdf

public class AEScipher {
    private static int Nb, Nk, Nr;
    private static byte[][] w;

    /**
     * S-box values are taken into 2-D array and is displayed in hexadecimal.
     */
    private static int[] sbox = { 0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F,
            0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76, 0xCA, 0x82,
            0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C,
            0xA4, 0x72, 0xC0, 0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC,
            0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15, 0x04, 0xC7, 0x23,
            0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27,
            0xB2, 0x75, 0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52,
            0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84, 0x53, 0xD1, 0x00, 0xED,
            0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58,
            0xCF, 0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9,
            0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8, 0x51, 0xA3, 0x40, 0x8F, 0x92,
            0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
            0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E,
            0x3D, 0x64, 0x5D, 0x19, 0x73, 0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A,
            0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB, 0xE0,
            0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62,
            0x91, 0x95, 0xE4, 0x79, 0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E,
            0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08, 0xBA, 0x78,
            0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B,
            0xBD, 0x8B, 0x8A, 0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E,
            0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E, 0xE1, 0xF8, 0x98,
            0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55,
            0x28, 0xDF, 0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41,
            0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16 };

    /**
     * Rcon[]:The round constant word array. This method is used to produce
     * round constant which is hexadecimal
     */
    private static int Rcon[] = { 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20,
            0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e,
            0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef,
            0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25,
            0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb,
            0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36,
            0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97,
            0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72,
            0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66,
            0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04,
            0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d,
            0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3,
            0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61,
            0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a,
            0x74, 0xe8, 0xcb, 0x8d, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40,
            0x80, 0x1b, 0x36, 0x6c, 0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc,
            0x63, 0xc6, 0x97, 0x35, 0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5,
            0x91, 0x39, 0x72, 0xe4, 0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a,
            0x94, 0x33, 0x66, 0xcc, 0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb, 0x8d,
            0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36, 0x6c,
            0xd8, 0xab, 0x4d, 0x9a, 0x2f, 0x5e, 0xbc, 0x63, 0xc6, 0x97, 0x35,
            0x6a, 0xd4, 0xb3, 0x7d, 0xfa, 0xef, 0xc5, 0x91, 0x39, 0x72, 0xe4,
            0xd3, 0xbd, 0x61, 0xc2, 0x9f, 0x25, 0x4a, 0x94, 0x33, 0x66, 0xcc,
            0x83, 0x1d, 0x3a, 0x74, 0xe8, 0xcb };

    /**
     * aesRound
     * 
     * This function takes input as 4*4 matrix and splits 
     * for every two bits.
     * @param "KeyHex" is the hexadecimal encryption key.
     * 
     */
    public static byte[][] aesRoundKeyHexs(byte[] KeyHex) {

        byte[][] w_matrix = new byte[Nb * (Nr + 1)][4];
        int i = 0;
        while (i < Nk) {
            w_matrix[i][0] = KeyHex[i * 4];
            w_matrix[i][1] = KeyHex[i * 4 + 1];
            w_matrix[i][2] = KeyHex[i * 4 + 2];
            w_matrix[i][3] = KeyHex[i * 4 + 3];
            i++;
        }
        i = Nk;
        while (i < Nb * (Nr + 1)) {
            byte[] innerWord = new byte[4];
            for (int k = 0; k < 4; k++)
                innerWord[k] = w_matrix[i - 1][k];
            if (i % Nk == 0) {
                innerWord = SubWord(RotWord(innerWord));
                innerWord[0] = (byte) (innerWord[0] ^ (Rcon[i / Nk] & 0xff));
            } else if (Nk > 6 && i % Nk == 4) {
                innerWord = SubWord(innerWord);
            }
            w_matrix[i] = XOR(w_matrix[i - Nk], innerWord);
            i++;
        }

        return w_matrix;
    }

    /**
     * SubWord 
     * 
     * This function takes 4 byte input and uses the Substitution
     * S-box and applies corresponding values in the place of 4 bytes
     * and replaces it with S-Box values
     * 
     * 
     * @param "a" is an input byte 
     * @return Word are the substituted S-Box values
     *         
     */
    private static byte[] SubWord(byte[] a) {
        byte[] word = new byte[a.length];
        for (int i = 0; i < word.length; i++)
            word[i] = (byte) (sbox[a[i] & 0x000000ff] & 0xff);
        return word;
    }

    /**
     * RotWord()
     * This function takes 4 byte input and performs cyclic shift.
     * 
     * @param w_new is a 4 byte input array 
     * @return irritavive is the 4 byte output array 
     */
    private static byte[] RotWord(byte[] w_new) {
        byte[] irritative = new byte[w_new.length];
        irritative[0] = w_new[1];
        irritative[1] = w_new[2];
        irritative[2] = w_new[3];
        irritative[3] = w_new[0];
        return irritative;
    }

    /**
     * XOR
     * This function performs the XOR operation by taking two inputs
     * 
     * @param a is first value
     * @param b is second value
     * @return Value is the result of XOR operation
     */
    private static byte[] XOR(byte[] a, byte[] b) {
        byte[] value = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            value[i] = (byte) (a[i] ^ b[i]);
        }
        return value;
    }
    /**
     * encryptBloc 
     * This function takes an input 4 byte array and performs aesNibbleSub(),
     * aesShiftRow(),aesMixColumn() and aesStateXOR() on it.
     * @param block is the 4 byte input array
     * @return tmp is the output which is cipher text
     */
    private static byte[] encryptBloc(byte[] block) {
        byte[] tmp = new byte[block.length];
        byte[][] state = new byte[4][4];
        for (int i = 0; i < block.length; i++)
            state[i / 4][i % 4] = block[i % 4 * 4 + i / 4];
        state = aesStateXOR(state, w, 0);
        for (int round = 1; round < Nr; round++) {
            state = aesNibbleSub(state);
            state = aesShiftRow(state);
            state = aesMixColumn(state);
            state = aesStateXOR(state, w, round);
        }
        state = aesNibbleSub(state);
        state = aesShiftRow(state);
        state = aesStateXOR(state, w, Nr);
        for (int i = 0; i < tmp.length; i++)
            tmp[i % 4 * 4 + i / 4] = state[i / 4][i % 4];
        return tmp;
    }
    /**
     * encrypt
     * This method takes input text and key value and performs encryption using 
     * encryptBlock() method
     * @param text is the input text
     * @param key is the key value to perform encryption 
     * @return tmp is the cipher text byte block
     */
    public static byte[] encrypt(byte[] text, byte[] key) {
        Nb = 4;
        Nk = key.length / 4;
        Nr = Nk + 6;
        byte[] tmp = new byte[text.length];
        byte[] bloc = new byte[16];
        w = aesRoundKeyHexs(key);
        int i;
        for (i = 0; i < text.length; i++) {
            if (i > 0 && i % 16 == 0) {
                bloc = encryptBloc(bloc);
                System.arraycopy(bloc, 0, tmp, i - 16, bloc.length);
            }
            if (i < text.length)
                bloc[i % 16] = text[i];
        }
        if (bloc.length == 16) {
            bloc = encryptBloc(bloc);
            System.arraycopy(bloc, 0, tmp, i - 16, bloc.length);
        }
        return tmp;
    }

    /**
     * aesStateXor
     * 
     * This method performs XOR operation using plaintext,key and round 
     * values
     * @param sHex is the input plaintext
     * @param keyHex is the hexadecimal key value
     * @param round is the round number
     * @return output is the result of XOR operation.
     */
    public static byte[][] aesStateXOR(byte[][] sHex, byte[][] keyHex, int round) {
        byte[][] output = new byte[sHex.length][sHex[0].length];
        for (int i = 0; i < Nb; i++) {
            for (int j = 0; j < 4; j++)
                output[j][i] = (byte) (sHex[j][i] ^ keyHex[round * Nb + i][j]);
        }
        return output;
    }

    /**
     * aesNibbleSub
     * 
     * This function takes an input state matrix and performs nibble
     * substitution on it.
     * @param inStateHex input state byte array of length 4*4
     * @return the 4*4 byte array after nibble substitution as output
     */
    public static byte[][] aesNibbleSub(byte[][] inStateHex) {
        byte[] step1 = toSingleDimention(inStateHex);
        byte[] step2 = SubWord(step1);
        return toBiDimention(step2, 4, 4);
    }

    /**
     * aesShiftRow
     * 
     * This method takes inStateHex as input 4*4 matrix on which
     * we are going to perform shift operation on a row.
     * 
     * @param inStateHex is the input 4*4 matrix 
     * @return the output matrix after shifting a row
     */
    public static byte[][] aesShiftRow(byte[][] inStateHex) {
        byte[] local = new byte[4];
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < Nb; j++)
                local[j] = inStateHex[i][(j + i) % Nb];
            for (int k = 0; k < Nb; k++)
                inStateHex[i][k] = local[k];
        }
        return inStateHex;
    }

    /**
     * aesMixColumn
     * 
     * This method takes input matrix and performs mix columns 
     * by making use of multiplier() method and performs XOR on it.
     * @param input is the 4*4 input matrix
     * @return output is the 4*4 matrix
     */
    public static byte[][] aesMixColumn(byte[][] input) {
        int[] local = new int[4];
        byte b2 = (byte) 0x02;
        byte b3 = (byte) 0x03;
        for (int c = 0; c < 4; c++) {
            local[0] = multiplier(b2, input[0][c])
                    ^ multiplier(b3, input[1][c]) ^ input[2][c] ^ input[3][c];
            local[1] = input[0][c] ^ multiplier(b2, input[1][c])
                    ^ multiplier(b3, input[2][c]) ^ input[3][c];
            local[2] = input[0][c] ^ input[1][c] ^ multiplier(b2, input[2][c])
                    ^ multiplier(b3, input[3][c]);
            local[3] = multiplier(b3, input[0][c]) ^ input[1][c] ^ input[2][c]
                    ^ multiplier(b2, input[3][c]);

            for (int i = 0; i < 4; i++)
                input[i][c] = (byte) (local[i]);
        }

        return input;
    }

    /**
     * multiplier
     * 
     * This method takes two input values and performs shifting operation
     * based on the bytes 
     * @param first is the first input value
     * @param second is the second input value
     * @return the Value after performing the shifting
     */
    public static byte multiplier(byte first, byte second) {
        byte value = 0, local;
        while (first != 0) {
            if ((first & 1) != 0)
                value = (byte) (value ^ second);
            local = (byte) (second & 0x80);
            second = (byte) (second << 1);
            if (local != 0)
                second = (byte) (second ^ 0x1b);
            first = (byte) ((first & 0xff) >> 1);
        }
        return value;
    }
    /*
     * toBiDimention
     * This function converts byte input array to the 2Dimentional matrix
     * @param input is the input 4 byte array
     * @param rows is the row value
     * @param cols is the column value
     * @return is the converted 2D matrix
     */
    private static byte[][] toBiDimention(final byte[] input, final int rows,
            final int cols) {
        if (input.length != (rows * cols))
            throw new IllegalArgumentException("Invalid  array  length");
        byte[][] convert = new byte[rows][cols];
        for (int i = 0; i < rows; i++)
            System.arraycopy(input, (i * cols), convert[i], 0, cols);
        return convert;
    }
    /*
     * toSingleDimention
     * This method performs 2Dimensional to single dimensional byte array
     * @param input is the input 2Dimensional matrix
     * @return convert is the converted single dimensional array
     * 
     */
    private static byte[] toSingleDimention(final byte[][] input) {
        int rows = input.length, cols = input[0].length;
        byte[] convert = new byte[(rows * cols)];
        for (int i = 0; i < rows; i++)
            System.arraycopy(input[i], 0, convert, (i * cols), cols);
        return convert;
    }
    /*
     * genMatrix
     * 
     * This function takes hexString as input and performs split using 
     * substring() method.
     * @param hexString is the input hexString
     * @return matrix is the matrix after split
     */
    public static String[][] genMatrix(String hexString) {
        String[][] matrix = new String[4][4];
        int col = 0;
        for (int i = 0; i < (hexString.length() - 1); i = i + 8) {
            int row = 0;
            for (int j = i; j < (i + 8); j = j + 2) {
                matrix[row][col] = hexString.substring(j, (j + 2));
                ++row;
            }
            ++col;
        }
        return matrix;
    }

}
