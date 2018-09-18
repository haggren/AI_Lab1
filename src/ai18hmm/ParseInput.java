/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai18hmm;

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