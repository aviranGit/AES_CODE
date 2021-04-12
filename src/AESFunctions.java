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
    public AESFunctions() { }

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
     * given byte array, the function returns 2D string array(matrix)(4X4)
     * @param byteArr
     * @return
     */
    public static byte[][] byteArrToMatrix(byte[] byteArr){
//        String[] sArr = byteToHex(byteArr);
        int index = 0;
        byte[][] matrix = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = byteArr[index];
                index++;
            }
        }
        return matrix;
    }

    public static byte[] matToByteArr(byte[][] mat){
        byte[] byteArr = new byte[16];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                byteArr[index] = mat[i][j];
                index++;
            }
        }
        return byteArr;
    }


    /**
     * The function get string array that represent the file hex. 128bit=block(16Byte)
     * @param sa
     * @return
     */
    public static byte[][] FileAsBlocks(byte[] sa){
        int blocks = sa.length/16;
        byte[][] byteArr = new byte[blocks][16]; //rows- num of blocks, col - the block arr
//        strArr = byteArrToMatrix(byteArr);

//        String[] seperateBlocks = new String[blocks];
        for (int i = 0; i < blocks; i++) {
           byteArr[i] = Arrays.copyOfRange(sa, i * 16, i * 16 + 16);
        }
        return byteArr;
    }

    /**
     * given two matrix the function return matrix which is the XOR result of them.
     * @param m1
     * @param m2
     * @return
     */
    public static byte[][] XorMatrix(byte[][] m1,byte[][] m2){
        //check if needed matrix sizees validation
        byte[][] result = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
//                int value1= Integer.parseInt(m1[i][j],16);
//                int value2= Integer.parseInt(m2[i][j],16);
                byte value1 = m1[i][j];
                byte value2 = m2[i][j];
                byte xorRes= (byte) (value1^value2);
                result[i][j] = xorRes;
            }
        }
        return result;
    }

    /**
     * The function swap cells in the matrix
     * @param m
     * @return m(swap)
     */
    public static byte[][] SwapIndx(byte[][] m){

        int row = 0;
        int col = 4-row;
        for (int j = 0; j < 4 ; j++) {
            for (int i = row; i < col; i++) {
                byte temp = m[row][i];
                m[row][i] = m[i][row];
                m[i][row] = temp;
            }
            row++;
        }
        return m;
    }

    // To implement
    public static void writeToFile(byte[] toWrite, String path){
        Path fileLocation = Paths.get(path);
//        byte[] bytes = AESFunctions.hexToByte(toWrite);
        File file = new File(String.valueOf(fileLocation));
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            fos.write(toWrite);
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
