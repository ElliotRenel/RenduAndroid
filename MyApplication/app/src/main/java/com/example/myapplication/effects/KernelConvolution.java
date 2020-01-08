package com.example.myapplication.effects;

import android.graphics.Bitmap;

import com.example.myapplication.effects.aux.InnerMethods;
import com.example.myapplication.effects.aux.Matrice;

public class KernelConvolution {

    private static double applyMask (Matrice mask, double[] pixels, int x, int y, int w){
        int dim = mask.getDim();
        int dim2 = dim/2;

        double somme = 0;
        double inverse =0;

        for(int i=x-dim2; i<x+dim2+1; i++) {
            for (int j = y - dim2; j < y + dim2 + 1; j++) {
                somme += pixels[j * w + i] * (double) mask.getValue(i - (x - dim2), j - (y - dim2));
                inverse += (double) mask.getValue(i - (x - dim2), j - (y - dim2));
            }
        }
        if(inverse==0){
            return InnerMethods.mapTo0_1(somme,-1,1);
        }
        return (somme/(inverse));
    }

    private static void convolution(Bitmap bmp, Matrice mask){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] rgb_pixels = new int[size];
        bmp.getPixels(rgb_pixels ,0,w,0,0,w,h);
        double[] pixels = InnerMethods.rgb_to_v(rgb_pixels);
        double[] newpixels = pixels.clone();

        int dim = mask.getDim()/2;

        for(int i=dim; i<w-dim; i++){
            for(int j=dim; j<h-dim;j++){
                newpixels[i+j*w] = applyMask(mask,pixels,i,j,w);
            }
        }

        bmp.setPixels(InnerMethods.v_to_rgb(rgb_pixels,newpixels),0,w,0,0,w,h);

    }

    public static void simpleGaussian(Bitmap bmp){
        int[] tmp = {
                1, 4, 6, 4, 1,
                4, 16 ,24, 16 ,4,
                6, 24, 36, 24, 6,
                4, 16, 24, 16, 4,
                1, 4, 6, 4, 1
        };
        Matrice gauss = new Matrice(5,tmp);

        convolution(bmp,gauss);
    }

    public static void LaplacianGaussian(Bitmap bmp){
        int[] mask = {
                0, 0,-1, 0, 0,
                0,-1,-2,-1, 0,
                -1,-2,16,-2,-1,
                0,-1,-2,-1, 0,
                0, 0,-1, 0, 0
        };
        Matrice custom = new Matrice(5,mask);

        convolution(bmp,custom);
    }

    public static void simpleBlur(Bitmap bmp){
        Matrice simpleMask = new Matrice(7 ,1);

        convolution(bmp,simpleMask);
    }


}
