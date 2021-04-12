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

    public String[] decrypt(String[][] cypher, String[][]key1, String[][]key2){
        String[][] firstXor = AESFunctions.XorMatrix(cypher,key2);
        String[][] res = AESFunctions.XorMatrix(firstXor, key1);
        res = AESFunctions.SwapIndx(res);
        String[] strArr = AESFunctions.matToStringArr(res);
        return strArr;
    }

}

