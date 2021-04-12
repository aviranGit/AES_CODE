import java.io.File;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {
        manualTest();

        if (args[0].equals('e') || args[0].equals('d')){

            String keyPath= args[2];
            String inputPath= args[4];
            String outputPath = args[6];

            //mesage/cypher
            byte[] bytes =AESFunctions.readFileAsBytes(inputPath);
            String[] hex = AESFunctions.byteToHex(bytes);
            String[][] blocks = AESFunctions.FileAsBlocks(hex);

            // keys
            byte[]keys = AESFunctions.readFileAsBytes(keyPath);
            byte[]key1= new byte[keys.length/2];
            byte[]key2 =new byte[keys.length/2];
            key1 = Arrays.copyOfRange(keys, 0, (keys.length/2));
            key2 = Arrays.copyOfRange(keys,keys.length/2, keys.length);
            String[] key1_hex = AESFunctions.byteToHex(key1);
            String[] key2_hex = AESFunctions.byteToHex(key2);
            String[][] key1Splited = AESFunctions.FileAsBlocks(key1_hex);
            String[][] key2Splited = AESFunctions.FileAsBlocks(key2_hex);


            //encrype
            if(args[0].equals('e')){

                EncrypeAES encrypeAES = new EncrypeAES();
                for (int i = 0; i < blocks.length; i++) {
                    String[][] mat = AESFunctions.stringArrToMat(blocks[i]);
                    String[][]k1 = AESFunctions.stringArrToMat(key1Splited[i]);
                    String[][]k2 = AESFunctions.stringArrToMat(key2Splited[i]);
                    String[] encrypted = encrypeAES.encrypt(mat,k1,k2);
                    AESFunctions.writeToFile(encrypted,outputPath);
                }

            //decrype
            if(args[0].equals('d')){
                DecrypeAES decrypeAES = new DecrypeAES();
                for (int i = 0; i < blocks.length; i++) {
                    String[][] mat = AESFunctions.stringArrToMat(blocks[i]);
                    String[][]k1 = AESFunctions.stringArrToMat(key1Splited[i]);
                    String[][]k2 = AESFunctions.stringArrToMat(key2Splited[i]);
                    String[] decrype = decrypeAES.decrypt(mat,k1,k2);
                    AESFunctions.writeToFile(decrype,outputPath);
                }
            }}
        }

        if (args[0].equals('b')){
            String plainTextPath=  args[2];
            String cipherTextPath= args[4];
            String keysPath = args[6];

            //massage
            byte[] bytes1 =AESFunctions.readFileAsBytes(plainTextPath);
            String[] hex1 = AESFunctions.byteToHex(bytes1);
            String[][] massageBlocks = AESFunctions.FileAsBlocks(hex1);

            //cipher
            byte[] bytes2 =AESFunctions.readFileAsBytes(cipherTextPath);
            String[] hex2 = AESFunctions.byteToHex(bytes2);
            String[][] cipherBlocks = AESFunctions.FileAsBlocks(hex2);

            //break
            BreakAES breakAES = new BreakAES(massageBlocks,cipherBlocks);
            String[] result= breakAES.breakAES();
            AESFunctions.writeToFile(result, keysPath);

        }
    }

    public static void manualTest() {

        byte[] M = AESFunctions.readFileAsBytes("TestFiles/message_short");
        byte[] key_To_Enc = AESFunctions.readFileAsBytes("TestFiles/key_short");

        //mesage
        String[] hex = AESFunctions.byteToHex(M);
        String[][] blocks = AESFunctions.FileAsBlocks(hex);

        // keys
        byte[] key1 = new byte[key_To_Enc.length / 2];
        byte[] key2 = new byte[key_To_Enc.length / 2];
        key1 = Arrays.copyOfRange(key_To_Enc, 0, (key_To_Enc.length / 2));
        key2 = Arrays.copyOfRange(key_To_Enc, key_To_Enc.length / 2, key_To_Enc.length);
        String[] key1_hex = AESFunctions.byteToHex(key1);
        String[] key2_hex = AESFunctions.byteToHex(key2);
        String[][] key1Splited = AESFunctions.FileAsBlocks(key1_hex);
        String[][] key2Splited = AESFunctions.FileAsBlocks(key2_hex);

        EncrypeAES encrypeAES = new EncrypeAES();
        String[] encrypted = new String[16];
        byte[] byte_enc = new byte[16];
        for (int i = 0; i < blocks.length; i++) {
            String[][] mat = AESFunctions.stringArrToMat(blocks[i]);
            String[][] k1 = AESFunctions.stringArrToMat(key1Splited[i]);
            String[][] k2 = AESFunctions.stringArrToMat(key2Splited[i]);
            encrypted = encrypeAES.encrypt(mat, k1, k2);
            byte_enc = AESFunctions.hexToByte(encrypted);
            AESFunctions.writeToFile(encrypted, "TestFiles/newfile");
        }

        byte[] C = AESFunctions.readFileAsBytes("TestFiles/cipher_short");
        byte[] key_To_Dec = AESFunctions.readFileAsBytes("TestFiles/key_short");

        String[] hexDec = AESFunctions.byteToHex(C);
        String[][] blocks_to_dec = AESFunctions.FileAsBlocks(hexDec);

        // keys
        byte[] key1_to_c = new byte[key_To_Enc.length / 2];
        byte[] key2_to_c = new byte[key_To_Enc.length / 2];
        key1_to_c = Arrays.copyOfRange(key_To_Dec, 0, (key_To_Enc.length / 2));
        key2_to_c = Arrays.copyOfRange(key_To_Dec, key_To_Enc.length / 2, key_To_Enc.length);
        String[] key1_hex_c = AESFunctions.byteToHex(key1_to_c);
        String[] key2_hex_c = AESFunctions.byteToHex(key2_to_c);
        String[][] key1Splited_c = AESFunctions.FileAsBlocks(key1_hex_c);
        String[][] key2Splited_c = AESFunctions.FileAsBlocks(key2_hex_c);

        DecrypeAES decrypeAES = new DecrypeAES();
        for (int i = 0; i < blocks_to_dec.length; i++) {
            String[][] mat = AESFunctions.stringArrToMat(blocks[i]);
            String[][] k1 = AESFunctions.stringArrToMat(key1Splited_c[i]);
            String[][] k2 = AESFunctions.stringArrToMat(key2Splited_c[i]);
            String[] decrypted = decrypeAES.decrypt(mat, k1, k2);
            byte[] byte_dec = AESFunctions.hexToByte(decrypted);
            boolean test = checkIfEq(byte_enc, C);
            AESFunctions.writeToFile(decrypted, "TestFiles/newfile_c");
        }
    }
    private static boolean checkIfEq(byte[]m, byte[]c){
        if(m.length != c.length){return false;}
        for (int i = 0; i < m.length; i++) {
            if(m[i] != c[i]){
                return false;
            }
        }
        return true;
    }
}
