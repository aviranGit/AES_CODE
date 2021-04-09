import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class AESFuncrtions {
    public AESFuncrtions() {
    }

    /**
     * The function get input path and return byte array
     * @param filePath
     * @return
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

    public static String[] byteToHex(byte[]bytes){
        String[] strArr = new String[bytes.length];
        int index = 0;
        for(byte b:bytes){
            String s = String.format("%02X", b);
            strArr[index]= s;
            index++;
        }
        return strArr;
    }
    
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

    public String[][] XorMatrix(String[][] m1,String[][] m2){
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

    public static String[][] SwapIndx(String[][] m, int row, int col){
        String temp= m[row][col];
        m[row][col] = m[col][row];
        m[col][row] = temp;
        return m;
    }

}
