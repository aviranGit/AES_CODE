public class EncrypeAES{
    private String[][] message;
    private String[][] key1;
    private String[][] key2;

    public EncrypeAES() {

    }

    public EncrypeAES(String[][] message, String[][] key1, String[][] key2) {
        this.message = message;
        this.key1 = key1;
        this.key2 = key2;
    }

    public String[] encrypt(String[][]m,String[][]key1,String[][]key2){
        //check if needed to verified variables
        m = AESFunctions.SwapIndx(m); //swap m

        String [][] firstXor = AESFunctions.XorMatrix(m,key1); //m XOR key1
        String[][] encryptMatrix = AESFunctions.XorMatrix(firstXor,key2); //resul XOR key2
        String[] strArr = AESFunctions.matToStringArr(encryptMatrix);
        return strArr;
    }
}
