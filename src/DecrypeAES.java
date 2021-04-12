public class DecrypeAES{
    private String[][] cypherText;
    private String[][] key1;
    private String[][] key2;


    public DecrypeAES() {
    }

    public DecrypeAES(String[][] message, String[][] key1, String[][] key2) {
        this.cypherText = message;
        this.key1 = key1;
        this.key2 = key2;
    }

    public byte[] decrypt(byte[][] cipher, byte[][]key1, byte[][]key2){

        byte[][] firstXor = AESFunctions.XorMatrix(cipher,key2);
        byte[][] swap_firstXOR = AESFunctions.SwapIndx(firstXor);
        byte[][] res = AESFunctions.XorMatrix(swap_firstXOR, key1);
        res = AESFunctions.SwapIndx(res);
        byte[] byteArr = AESFunctions.matToByteArr(res);
        return byteArr;
    }

}

