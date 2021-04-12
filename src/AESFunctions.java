import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class AESFunctions {
    public AESFunctions() {
    }

    /**
     * The function get input path and return byte array
     * @param filePath
     * @return byte array that represent the file
     */
    public static byte[] readFileAsBytes(String filePath){
        try {
            Path fileLocation = Paths.get(filePath);
            byte[] byteArr = Files.readAllBytes(fileLocation);
            return byteArr;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * given byte array, the function returns string array each cell represent hexadecimal number.
     * @param bytes
     * @return string array
     */
    public static String[] byteToHex(byte[]bytes){
        String[] strArr = new String[bytes.length];
        int index = 0;
        for(byte b:bytes){
            char[] hexDigits = new char[2];
            hexDigits[0] = Character.forDigit((b >> 4) & 0xF, 16);
            hexDigits[1] = Character.forDigit((b & 0xF), 16);
//            String s = String.format("%02X", b);
            strArr[index]= new String(hexDigits);
            index++;
        }
        return strArr;
    }

    public static byte[] hexToByte(String[] strArr){
        int index = 0;
        byte[] toRuturn = new byte[strArr.length];
        for(String s: strArr) {
            int firstDigit = toDigit(s.charAt(0));
            int secondDigit = toDigit(s.charAt(1));
            toRuturn[index] = (byte) ((firstDigit << 4) + secondDigit);
            index++;
        }
        return toRuturn;
    }
    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if (digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: " + hexChar);
        }
        return digit;
    }
    /**
     * given byte array, the function returns 2D string array(matrix)(4X4)
     * @param byteArr
     * @return
     */
    public static String[][] byteArrToMatrix(byte[] byteArr){
        String[] sArr = byteToHex(byteArr);
        int index = 0;
        String[][] matrix = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = sArr[index];
                index++;
            }
        }
        return matrix;
    }

    public static String[] matToStringArr(String[][] mat){
        String[] strArr = new String[16];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                strArr[index] = mat[i][j];
                index++;
            }
        }
        return strArr;
    }

    /**
     * The function get an string array and return matrix
     * @param mat
     * @return
     */
    public static String[][] stringArrToMat(String[] mat){
        String[][] matrix = new String[4][4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = mat[index];
                index++;
            }
        }
        return matrix;
    }
    /**
     * The function get string array that represent the file hex. 128bit=block(16Byte)
     * @param sa
     * @return
     */
    public static String[][] FileAsBlocks(String[] sa){
        int blocks = sa.length/16;
        String[][] strArr = new String[blocks][16]; //rows- num of blocks, col - the block arr
//        strArr = byteArrToMatrix(byteArr);

//        String[] seperateBlocks = new String[blocks];
        for (int i = 0; i < blocks; i++) {
           strArr[i] = Arrays.copyOfRange(sa, i * 16, i * 16 + 16);
        }
        return strArr;
    }

    /**
     * given two matrix the function return matrix which is the XOR result of them.
     * @param m1
     * @param m2
     * @return
     */
    public static String[][] XorMatrix(String[][] m1,String[][] m2){
        //check if needed matrix sizees validation
        String[][] result = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int value1= Integer.parseInt(m1[i][j],16);
                int value2= Integer.parseInt(m2[i][j],16);

                int xorRes= value1^value2;
                result[i][j] = Integer.toHexString(xorRes);
            }
        }
        return result;
    }

    /**
     * The function swap cells in the matrix
     * @param m
     * @return m(swap)
     */
    public static String[][] SwapIndx(String[][] m){
        int row = 0;
        while (row < 4) {
            for (int col = 0; col < 4; col++) {
                String temp = m[row][col];
                m[row][col] = m[col][row];
                m[col][row] = temp;
            }
            row++;
        }
        return m;
    }

    // To implement
    public static void writeToFile(String[] toWrite, String path){
        Path fileLocation = Paths.get(path);
        byte[] bytes = AESFunctions.hexToByte(toWrite);
        File file = new File(String.valueOf(fileLocation));
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                if(fos!=null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
