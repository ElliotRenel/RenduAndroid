package com.example.myapplication.effects;

import android.graphics.Bitmap;

import com.example.myapplication.effects.aux.InnerMethods;
import com.example.myapplication.effects.aux.Matrice;

public class KernelConvolution {

    /**
     * Apply a mask to a pixel
     * @param mask the mask
     * @param pixels array of V pixel value of the image
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param w the width of the image
     * @return the new value V for the pixel
     */
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
        return somme/inverse;
    }

    /**
     * Apply a convolution with the given mask on the given image
     * @param bmp the bitmap image
     * @param mask the mask
     */
    private static void convolution(Bitmap bmp, Matrice mask){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] rgb_pixels = new int[size];
        bmp.getPixels(rgb_pixels ,0,w,0,0,w,h);
        double[] hsv_pixels = InnerMethods.rgb_to_v(rgb_pixels);

        int dim = mask.getDim()/2;

        for(int i=dim; i<w-dim; i++){
            for(int j=dim; j<h-dim;j++){
                rgb_pixels[i+j*w] = InnerMethods.v_to_rgb(rgb_pixels[i+j*w],applyMask(mask,hsv_pixels,i,j,w));
            }
        }

        bmp.setPixels(rgb_pixels,0,w,0,0,w,h);

    }

    /**
     * Apply a simple average convolution on given image
     * @param bmp the bitmap image
     */
    public static void simpleBlur(Bitmap bmp){
        Matrice simpleMask = new Matrice(7 ,1);

        convolution(bmp,simpleMask);
    }

    /**
     * Apply a gaussian mask convolution on given image (gaussian blur)
     * @param bmp the bitmap image
     */
    public static void Gaussian(Bitmap bmp){
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

    /**
     * Apply a LaplaceGaussian mask convolution on given image (Laplace edge detection)
     * @param bmp the bitmap image
     */
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


}
