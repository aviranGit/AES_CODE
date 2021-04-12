import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args[0].equals('e') || args[0].equals('d')) {

            String keyPath = args[2];
            String inputPath = args[4];
            String outputPath = args[6];

            //mesage/cypher
            byte[] bytes = AESFunctions.readFileAsBytes(inputPath);
//            String[] hex = AESFunctions.byteToHex(bytes);
            byte[][] blocks = AESFunctions.FileAsBlocks(bytes);

            // keys
            byte[] keys = AESFunctions.readFileAsBytes(keyPath);
            byte[] key1 = new byte[keys.length / 2];
            byte[] key2 = new byte[keys.length / 2];
            key1 = Arrays.copyOfRange(keys, 0, (keys.length / 2));
            key2 = Arrays.copyOfRange(keys, keys.length / 2, keys.length);
//            String[] key1_hex = AESFunctions.byteToHex(key1);
//            String[] key2_hex = AESFunctions.byteToHex(key2);
//            byte[][] key1Splited = AESFunctions.FileAsBlocks(key1);
//            byte[][] key2Splited = AESFunctions.FileAsBlocks(key2);


            //encrype
            if (args[0].equals('e')) {

                EncrypeAES encrypeAES = new EncrypeAES();
                if (blocks.length > 1) {
                    byte[] allblocks = new byte[16 * blocks.length];
                    for (int i = 0; i < blocks.length; i++) {
                        byte[][] mat = AESFunctions.byteArrToMatrix(blocks[i]);
                        byte[][] k1 = AESFunctions.byteArrToMatrix(key1);
                        byte[][] k2 = AESFunctions.byteArrToMatrix(key2);
                        byte[] encrypted = encrypeAES.encrypt(mat, k1, k2);
                        int index = 0;
                        for (int j = i * 16; j < i * 16 + 16; j++) {
                            allblocks[j] = encrypted[index];
                            index++;
                        }
                    }
                    AESFunctions.writeToFile(allblocks, outputPath);
                } else { // only one block
                    byte[][] mat = AESFunctions.byteArrToMatrix(blocks[0]);
                    byte[][] k1 = AESFunctions.byteArrToMatrix(key1);
                    byte[][] k2 = AESFunctions.byteArrToMatrix(key2);
                    byte[] encrypted = encrypeAES.encrypt(mat, k1, k2);
                    AESFunctions.writeToFile(encrypted, outputPath);
                }
            }
            //decrype
            if (args[0].equals('d')) {
                DecrypeAES decrypeAES = new DecrypeAES();
                if (blocks.length > 1) {
                    byte[] allblocks = new byte[16 * blocks.length];
                    for (int i = 0; i < blocks.length; i++) {
                        byte[][] mat = AESFunctions.byteArrToMatrix(blocks[i]);
                        byte[][] k1 = AESFunctions.byteArrToMatrix(key1);
                        byte[][] k2 = AESFunctions.byteArrToMatrix(key2);
                        byte[] decrypte = decrypeAES.decrypt(mat, k1, k2);
                        int index = 0;
                        for (int j = i * 16; j < i * 16 + 16; j++) {
                            allblocks[j] = decrypte[index];
                            index++;
                        }
                    }
                    AESFunctions.writeToFile(allblocks, outputPath);
                } else { // only one block
                    byte[][] mat = AESFunctions.byteArrToMatrix(blocks[0]);
                    byte[][] k1 = AESFunctions.byteArrToMatrix(key1);
                    byte[][] k2 = AESFunctions.byteArrToMatrix(key2);
                    byte[] decrypte = decrypeAES.decrypt(mat, k1, k2);
                    AESFunctions.writeToFile(decrypte, outputPath);
                }
            }
        }
        if (args[0].equals('b')) {
            String plainTextPath = args[2];
            String cipherTextPath = args[4];
            String outputPath = args[6];

            //massage
            byte[] M = AESFunctions.readFileAsBytes(plainTextPath);
//            String[] hex_M = AESFunctions.byteToHex(M);
            byte[][] massageBlocks = AESFunctions.FileAsBlocks(M);

            //cipher
            byte[] C = AESFunctions.readFileAsBytes(cipherTextPath);
//            String[] hex_C = AESFunctions.byteToHex(C);
            byte[][] cipherBlocks = AESFunctions.FileAsBlocks(C);

            //break
            byte[][] c_mat = AESFunctions.byteArrToMatrix(cipherBlocks[0]);
            byte[][] m_mat = AESFunctions.byteArrToMatrix(massageBlocks[0]);
            BreakAES breakAES = new BreakAES(m_mat, c_mat);
            byte[] keyBreak = breakAES.breakAES();
            AESFunctions.writeToFile(keyBreak, outputPath);

//            for (int i = 0; i < massageBlocks.length; i++) {
//                byte[] result= breakAES.breakAES();
//                AESFunctions.writeToFile(result, outputPath);
//            }
        }
    }
}

