/**
 * Created by gustavkjellberg on 2018-09-05.
 */

import java.io.*;

public class ParseInput {
    Kattio io = new Kattio(System.in, System.out);

    public ParseInput(){

    }

    public void closeStream(){
        io.close();
    }
    public double[][] transistionMatrix(){
        return createMatrix();
    }

    public double[][] emissionMatrix(){
        return createMatrix();
    }

    public double[][] initialStateVector(){
        return createMatrix();
    }

    private double[][] createMatrix(){
        int rows = io.getInt();
        int cols = io.getInt();
        double[][] matrix = new double[rows][cols];
        for (int i=0; i<rows;i++){
            for (int j=0; j < cols; j++){
                matrix[i][j] = io.getDouble();
            }
        }
        return matrix;
    }

    public int[] getEmissions(){
        int nr = io.getInt();

        int[] emission = new int[nr];

        for (int i = 0; i<nr; i++){
            emission[i] = io.getInt();
        }
        return emission;
    }
}
