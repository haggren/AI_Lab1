
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

    public static double[][] insertColInMatrix(double[][] col, double[][]matrix, int colIndex){
        for (int index = 0; index<col.length;index++){
            matrix[index][colIndex] = col[index][0];
        }
        return matrix;
    }
    
    public static double[][] alphaPass(double[][] A, double[][] B, int[] obs, double[][] pi, int n, double [][] alphaMatrix){
        if (n == 0){
            double[][] firstAlpha =  elementWiseMultiplication(pi,getColumn(B, obs[n]));
            alphaMatrix = insertColInMatrix(firstAlpha, alphaMatrix, n);
            return alphaMatrix;
        }
        double[][] newAlpha = matrixMultiplication(alphaPass(A,B,obs,pi,n-1, alphaMatrix), A);
        double [][] alphaCol =  elementWiseMultiplication(newAlpha, getColumn(B,obs[n]));
        alphaMatrix = insertColInMatrix(alphaCol, alphaMatrix, n);
        return alphaMatrix;
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

    public static double[][] scalarMultiplication(double scalar, double[][] column){
        double[][] resultVector = new double[column.length][1];
        for (int i = 0; i<column.length; i++){
            resultVector[i][0] = scalar * column[i][0];
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


        for (int t = 1; t<=n; t++){
            for (int s = 0; s < A.length; s++){
                double[][] deltaAlpha =(elementWiseMultiplicationCol(getColumn(delta, t-1), getColumn(A,s)));
                //double [][] res = elementWiseMultiplicationCol(deltaAlpha, getColumn(B, obs[t]));
                double maxOfDeltaAlpha = 0.0;
                for (int index = 0; index < deltaAlpha.length; index++){
                    double potentiallyBigger = deltaAlpha[index][0];
                    if (maxOfDeltaAlpha<potentiallyBigger){
                        maxOfDeltaAlpha = potentiallyBigger;
                        phi[s][t] = index;
                    }
                }

                delta[s][t] = maxOfDeltaAlpha * B[s][obs[t]];

                /*double [][] res = scalarMultiplication(maxOfDeltaAlpha, getColumn(B, obs[t]));
                for (int index = 0; index<res.length; index++){
                    delta[index][t] = res[index][0];
                } */

               /*double mini = 0.0;
                for (int index = 0; index < deltaAlpha.length; index++){
                    if(deltaAlpha[index][0]>mini){
                        mini = deltaAlpha[index][0];
                        phi[s][t] = index;
                    }
                }*/
            }
        }

        int finalState = 0;
        double minVal = 0.0;
        for (int index = 0 ; index <delta.length; index++){
            if (delta[index][n] > minVal) {
                finalState = index;
            }
        }

        int[] path = new int[n+1];
        path[n] = finalState;
        for (int t = n-1; t>=0;t--){
            path[t] = phi[path[t+1]][t+1];
        }

        return path;
    }

    public static double[][] betaPass(double[][] A, double[][] B, int[] obs, int n, double[][] betaMatrix){
        if (n == obs.length-1){
            double[][] finalCol = new double[A.length][1];
            for (int i = 0; i<finalCol.length; i++){
                finalCol[i][0] = 1.0;
            }
            

        }


        return null;
    }

}