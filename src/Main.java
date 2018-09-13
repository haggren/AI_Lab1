import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {

        ParseInput parse = new ParseInput();
        double[][] transistionMatrix = parse.transistionMatrix();
        double[][] emissionMatrix = parse.emissionMatrix();
        double[][] initialStateVector = parse.initialStateVector();

        double [][] first = (calcPropX(initialStateVector,transistionMatrix));
        printMatrix(calcPropX(first, emissionMatrix));
        parse.closeStream();

    }

    private static void printMatrix(double[][] matrix){
        System.out.print(matrix.length + " "  + matrix[0].length);
        for (int i=0; i<(matrix.length); i++){
          for (int j=0; j<(matrix[0].length); j++){
              /* Use the commented code to round the output to 1 decimal or change the scaling after your preference.*/
              /*Double truncatedDouble = BigDecimal.valueOf(matrix[i][j])
                      .setScale(1, RoundingMode.HALF_UP)
                      .doubleValue();*/
            System.out.print(" " + matrix[i][j]);
          }
          System.out.println();
      }
    }

    private static double[][] calcPropX(double[][] A, double[][] B){

        return matrixMultiplication(A, B);
    }

    private static double[][] matrixMultiplication(double[][] A, double[][] B) {
        int rows = A.length;
        int resCols = B[0].length;
        int cols = B.length;

        double[][] resultVector = new double[rows][resCols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < resCols; j++) {
                double sum = 0;
                for (int k = 0; k < cols; k++) {
                    resultVector[i][j] += B[k][j] * A[i][k];
                }
            }
        }
        return resultVector;
    }
}