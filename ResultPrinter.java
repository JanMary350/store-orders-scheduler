public class ResultPrinter {
    public static void printResult(String[][] result) {
        //prints the result
        for (int i = 0; i < result.length; i++) {
            if (result[i][0] != null)
                System.out.println(result[i][0] + " " + result[i][1] + " " + result[i][2]);

        }
    }


}
