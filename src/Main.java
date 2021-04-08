public class Main {

    public static void main(String[] args) {

        if (args[0].equals('e') || args[0].equals('d')){

            String keyPath= args[2];
            String inputPath= args[4];
            String outputPath = args[6];

            if(args[0].equals('e')){
                byte[] bytes = AESFuncrtions.readFileAsBytes(inputPath);

            }

        }
    }


}
