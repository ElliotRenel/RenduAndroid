package com.example.myapplication.effects.aux;

import android.util.Log;

public class Matrice {
    int dim;
    int[][] values;

    public Matrice(int dim){
        this.dim = dim;
        values = new int[dim][dim];
    }

    public Matrice(int dim, int value){
        this(dim);
        for(int i=0; i<dim; i++){
            for(int j=0; j<dim; j++){
                this.setValue(i,j,value);
            }
        }
    }

    public Matrice(int dim, int[] values){
        this(dim);
        for(int i=0; i<dim; i++){
            for(int j=0; j<dim; j++){
                this.values[i][j] = values[i+j*dim];
            }
        }
    }

    public int getDim(){
        return dim;
    }

    public void setValue(int i, int j, int value){
        values[i][j] = value;
    }

    public int getValue(int i, int j){
        return values[i][j];
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
