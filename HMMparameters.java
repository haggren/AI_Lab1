/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai18hmm;

/**
 *
 * @author hhaggren
 */
public class HMMparameters {
    
    double[][] transitionMatrix;
    double[][] emissionMatrix;
    double[] initialState;
    
    public HMMparameters(){
        
        
    }
    
    public void setTransition(double[][] newA){
        transitionMatrix = newA;
    }
    
    public void setEmission(double[][] newB){
        emissionMatrix = newB;
    }
    
    public void setInitialState(double[] newInit){
        initialState = newInit;
    }
    public double[][] getTransition(){
        return transitionMatrix;
    }
    
    public double[][] getEmission(){
        return emissionMatrix;
    }
}
