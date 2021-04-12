public class EncrypeAES{
    private byte[][] message;
    private byte[][] key1;
    private byte[][] key2;

    public EncrypeAES() {

    }

    public EncrypeAES(byte[][] message, byte[][] key1, byte[][] key2) {
        this.message = message;
        this.key1 = key1;
        this.key2 = key2;
    }

    public byte[] encrypt(byte[][]m,byte[][]key1,byte[][]key2){
        //check if needed to verified variables
        m = AESFunctions.SwapIndx(m); //swap m

        byte [][] firstXor = AESFunctions.XorMatrix(m,key1); //m XOR key1
        byte[][] swap_firstXOR  = AESFunctions.SwapIndx(firstXor);
        byte[][] encryptMatrix = AESFunctions.XorMatrix(swap_firstXOR,key2); //resul XOR key2
        byte[] byteArr = AESFunctions.matToByteArr(encryptMatrix);
        return byteArr;
    }
}
