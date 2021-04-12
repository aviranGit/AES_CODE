public class BreakAES {
    private String[][] message;
    private String[][] cipherText;
    private String[][] key1;
    private String[][] key2;

    public BreakAES(String[][] message, String[][] cipher) {
        this.message = message;
        this.cipherText= cipher;
        String[] tempMat = new String[]{"1000010000100001"};
        this.key1 =AESFunctions.stringArrToMat(tempMat);
        this.key2 = null;
    }

    public String[] breakAES(){

        this.key1 = AESFunctions.SwapIndx(this.key1);
        // M XOR C
        String[][] resXOR = AESFunctions.XorMatrix(this.message, this.cipherText);
        this.key2 = AESFunctions.XorMatrix(this.key1, resXOR);
        String[]k1 = AESFunctions.matToStringArr(this.key1);
        String[]k2 =AESFunctions.matToStringArr(this.key2);
        String[] result = new String[k1.length + k2.length];
        return result;
    }
}
