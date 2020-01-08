package com.example.myapplication.effects.aux;

import android.util.Log;

public class Matrice {
    int dim;
    int[][] values;
    int inverse;

    public Matrice(int dim){
        this.dim = dim;
        values = new int[dim][dim];
        inverse = 0;
    }

    public Matrice(int dim, int value){
        this(dim);
        for(int i=0; i<dim; i++){
            for(int j=0; j<dim; j++){
                this.setValue(i,j,value);
            }
        }
        inverse = value*dim*dim;
    }

    public Matrice(int dim, int[] values){
        this(dim);
        for(int i=0; i<dim; i++){
            for(int j=0; j<dim; j++){
                this.values[i][j] = values[i+j*dim];
                inverse+=this.values[i][j];
            }
        }
    }
/*
    public static Matrice Gauss(int dim, double div){
        Matrice Gauss = new Matrice(dim);
        for(int i=0; i<dim; i++){
            for(int j=0; j<dim; j++){
                Gauss.values[i][j] = Math.exp(-(i*i+j*j)/(2*div*div));
            }
        }
        return Gauss;
    }
*/
    public int getDim(){
        return dim;
    }

    public void setValue(int i, int j, int value){
        inverse -= values[i][j];
        values[i][j] = value;
        inverse+= value;
    }

    public int getValue(int i, int j){
        return values[i][j];
    }

    public int getInverse(){
        int k = 0;
        for(int x=0; x<dim; x++) {
            for (int y = 0; y < dim; y++) {
                k += values[x][y];
            }
        }
        return k!=0?k:1;
    }
    public double weighted_average(Matrice m){
        if(m.dim != dim)
            return -1;

        double somme = 0;
        for(int x=0; x<dim; x++) {
            for (int y = 0; y < dim; y++) {
                somme = somme + m.values[x][y] * values[x][y];
            }
        }

        return inverse==0?somme:somme/inverse;
    }

    @Override
    public String toString(){
        String str = "";
        for(int i=0; i<dim; i++){
            for(int j=0; j<dim; j++){
                str += values[i][j]+" ";
            }
            str+= "\n";
        }

        return str;
    }

    public void show(){
        Log.i("Matrice",this.toString());
    }

}
