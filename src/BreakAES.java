import java.util.Arrays;

public class BreakAES {
    private byte[][] message;
    private byte[][] cipherText;
    private byte[][] key1 = {{1,0,0,0},{0,1,0,0},{0,0,1,0},{0,0,0,1}};
    private byte[][] key2;

    public BreakAES(byte[][] message, byte[][] cipher) {
        this.message = message;
        this.cipherText= cipher;
//        String[] tempMat = new String[]{"1000010000100001"};
        this.key2 = new byte[4][4];
    }

    public byte[] breakAES(){

        this.key1 = AESFunctions.SwapIndx(this.key1);
        // M XOR C
        byte[][] resXOR = AESFunctions.XorMatrix(this.message, this.cipherText);
        this.key2 = AESFunctions.XorMatrix(this.key1, resXOR);
        byte[]k1 = AESFunctions.matToByteArr(this.key1);
        byte[]k2 =AESFunctions.matToByteArr(this.key2);
        byte[] result = new byte[k1.length + k2.length];
        int ind = 0;
        for (int i = 0; i < k1.length; i++) {
            result[ind] = k1[i];
            ind++;
        }
        for (int i = 0; i < k2.length; i++) {
            result[ind] = k2[i];
            ind++;
        }
        return result;
    }
}
