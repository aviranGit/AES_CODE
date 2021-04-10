public class Main {

    public static void main(String[] args) {
        manualTest();
        if (args[0].equals('e') || args[0].equals('d')){

            String keyPath= args[2];
            String inputPath= args[4];
            String outputPath = args[6];

            if(args[0].equals('e')){
                byte[] bytes = AESFuncrtions.readFileAsBytes(inputPath);

            }

        }
    }

    public static void manualTest(){
        byte[] M = AESFuncrtions.readFileAsBytes("TestFiles/message_long");
        byte[] k1 = AESFuncrtions.readFileAsBytes("TestFiles/key_long");
        String [] test = AESFuncrtions.byteToHex(M);
        String[][] mat = AESFuncrtions.byteArrToMatrix(M);
        String[][] Kmat = AESFuncrtions.byteArrToMatrix(k1);
        String[][] swap_mat = AESFuncrtions.SwapIndx(mat,2,1);
        String[][] splited = AESFuncrtions.FileAsBlocks(test);
        String[][] xorM = AESFuncrtions.XorMatrix(mat, Kmat);
    }
}
