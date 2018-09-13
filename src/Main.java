
import java.util.Arrays;
import java.util.Collections;

public class Main {
    // main medthod dåra
    public static void main(String[] args) {

        ParseInput parse = new ParseInput();
        double[][] transistionMatrix = parse.transistionMatrix();
        double[][] emissionMatrix = parse.emissionMatrix();
        double[][] initialStateVector = parse.initialStateVector();
        int[] emissions = parse.getEmissions();

        /*double [][] first = (calcPropX(initialStateVector,transistionMatrix));
        printMatrix(calcPropX(first, emissionMatrix));*/

        //double[][] finalAlpha = observationLikelihood(transistionMatrix, emissionMatrix, emissions,initialStateVector, emissions.length-1);

        int[] path = stateLiklihood(transistionMatrix, emissionMatrix, emissions, initialStateVector, emissions.length-1);
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

    // Does nothing.
    private static double[][] calcPropX(double[][] A, double[][] B){

        return matrixMultiplication(A, B);
    }

    // Computes a matrix multiplication where A should have dimensions (x,y) and B (y,z)
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

    public static double sumAlpha(double[][] alpha){
        double res = 0;
        for (double i : alpha[0]) {
            res += i;
        }
        return res;

    }


    public static double[][] observationLikelihood(double[][] A, double[][] B, int[] obs, double[][] pi, int n){
        if (n == 0){
            return elementWiseMultiplication(pi,getColumn(B, obs[n]));
        }
        double[][] newAlpha = matrixMultiplication(observationLikelihood(A,B,obs,pi,n-1), A);
        return elementWiseMultiplication(newAlpha, getColumn(B,obs[n]));

    }

    public static double[][] getColumn(double[][] matrix, int n){
        double[][] colVector = new double[matrix.length][1];
        for (int i=0; i<matrix.length; i++){
            colVector[i][0] = matrix[i][n];
        }
        return colVector;
    }

    public static double[][] elementWiseMultiplication(double[][] a, double[][] b){
        double[][] resultVector = new double[1][a[0].length];
        for (int i = 0; i<a[0].length; i++){
            resultVector[0][i] = a[0][i] * b[i][0];
        }
        return resultVector;
    }

    public static double[][] elementWiseMultiplicationCol(double[][] a, double[][] b){
        double[][] resultVector = new double[a.length][1];
        for (int i = 0; i<a.length; i++){
            resultVector[i][0] = a[i][0] * b[i][0];
        }
        return resultVector;
    }

    public static int[] stateLiklihood(double[][] A, double[][] B, int[] obs, double[][] pi, int n){

        double[][] delta = new double[A.length][n+1];
        int[][] phi = new int[A.length][n+1];

        double[][] initialDeltaCol = elementWiseMultiplication(pi,getColumn(B, obs[0]));

        for (int i = 0; i<initialDeltaCol[0].length; i++) {
            delta[i][0] = initialDeltaCol[0][i];
        }


        for (int t = 1; t<n; t++){
            for (int s = 0; s < A.length; s++){
                double[][] res = (elementWiseMultiplicationCol(elementWiseMultiplicationCol(getColumn(delta, t-1), getColumn(A,s)), getColumn(B, obs[t])));
                delta[s][t] = Arrays.stream(res[0]).max().getAsDouble();
                double mini = 0.0;
                for (int index = 0; index < res.length; index++){
                    if(res[index][0]>mini){
                        mini = res[index][0];
                        phi[s][t] = index;
                    }
                }
            }
        }

        int finalState = phi[A.length-1][n];

        int[] path = new int[n+1];
        path[n] = finalState;
        for (int t = n-1; t>=0;t--){
            path[t] = phi[path[t+1]][t];
        }

        return path;
    }

}