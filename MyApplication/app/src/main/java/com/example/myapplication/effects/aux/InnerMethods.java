package com.example.myapplication.effects.aux;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

public class InnerMethods {

    public static void rgb_to_hsv(float red, float green, float blue, float[] hsv) {
        float red_ = red/255;
        float blue_ = blue/255;
        float green_ = green/255;

        float cmax = Math.max(red_, blue_);
        cmax = Math.max(cmax, green_);
        float cmin= Math.min(red_, blue_);
        cmin = Math.min(cmin, green_);

        float delta = cmax - cmin;

        float h;


        if(delta == 0){
            h = 0;
        }else{
            if(cmax == red_) {
                h = ((60 * (((green_ - blue_)/delta)))+360)%360;
            }else if(cmax == green_) {
                h = (60 *(((blue_ - red_)/delta) ))+120;
            }else {
                h = 60 *(((red_ - green_)/delta))+240;
            }
        }

        float s;

        if(cmax == 0) {
            s = 0;
        }else {
            s = 1 -(cmin/cmax);
        }

        float v = cmax;

        hsv[0] = h;
        hsv[1] = s;
        hsv[2] = v;
    }

    public static void rgb_to_hsv(int pixel,float[] hsv){
        rgb_to_hsv(Color.red(pixel),Color.green(pixel),Color.blue(pixel),hsv);
    }

    public static float[][] rgb_to_hsv(int pixels[]){
        float result[][] = new float[pixels.length][3];
        for(int i=0; i<pixels.length; i++){
            rgb_to_hsv(pixels[i], result[i]);
        }
        return result;
    }

    public static int[] hsv_to_rgb(float hsv[][]){
        int size = hsv.length;
        int result[] = new int[size];
        for(int i=0; i<size; i++){
            result[i] = hsv_to_rgb(hsv[i]);
        }
        return result;
    }


    public static int hsv_to_rgb(float hsv[]){
        float t = (int) (hsv[0]/60)%6;
        float f = (hsv[0]/60)- t;
        float l = hsv[2] *(1 - hsv[1]);
        float m = hsv[2] * (1- f*hsv[1]);
        float n = hsv[2] * (1-(1-f) * hsv[1]);


        float red =0;
        float green=0;
        float blue=0;

        if(t ==0){
            red = hsv[2];
            green = n;
            blue = l;
        }else if(t==1){
            red = m;
            green = hsv[2];
            blue = l;
        }else if(t == 2){
            red = l;
            green = hsv[2];
            blue = n;
        }else if(t == 3){
            red = l;
            green = m;
            blue = hsv[2];
        }else if(t == 4){
            red = n;
            green = l;
            blue = hsv[2];
        }else if(t == 5){
            red = hsv[2];
            green = l;
            blue = m;
        }
        int rgb = Color.rgb(red,green,blue);
        //Log.v("hsv", "r " + red + " g " + green + " b  " + blue);

        return rgb;

    }

    public static double[] rgb_to_v(int[] pixels, int size){
        double V[] = new double[size];
        for(int i=0; i<size; i++){

            double red_ = (double)Color.red(pixels[i])/(double)255;
            double blue_ = (double)Color.blue(pixels[i])/(double)255;
            double green_ = (double)Color.green(pixels[i])/(double)255;

            double cmax = Math.max(red_, blue_);
            cmax = Math.max(cmax, green_);
            V[i] = cmax;
        }
        return V;
    }

    public static int[] v_to_rgb(int[] old_rgb, double[] V){
        int[] new_rgb = new int[old_rgb.length];
        for(int i=0; i<old_rgb.length; i++){
            float hsv[] = new float[3];
            rgb_to_hsv(old_rgb[i],hsv);
            hsv[2] = (float)V[i];
            new_rgb[i] = hsv_to_rgb(hsv);
        }
        return new_rgb;
    }

    public static void bitmapToHistGray(Bitmap bmp, int[] hist){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);
        for(int i=0; i<size; i++)
            hist[Color.red(pixels[i])]++;
    }

    public static void bitmapToHistColor(Bitmap bmp, int[] hist, char color){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);

        switch (color){
            case 'r':
                for(int i=0; i<size; i++)
                    hist[Color.red(pixels[i])]++;
                break;
            case 'g':
                for(int i=0; i<size; i++)
                    hist[Color.green(pixels[i])]++;
                break;
            case 'b':
                for(int i=0; i<size; i++)
                    hist[Color.blue(pixels[i])]++;
                break;
        }
    }

    public static void bitmapToHistHSV(Bitmap bmp,int[] hist){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);
        for(int i=0;i<size; i++){
            float hsv[] = new float[3];
            rgb_to_hsv(pixels[i],hsv);
            hist[(int)(hsv[2]*100)]++;
        }
    }

    public static void histToCumul(int[] hist, int[] C, int size){
        C[0] = hist[0];
        for(int i=1; i<size;i++){
            C[i] = C[i-1] + hist[i];
        }
    }

    public static double mapTo0_1(double value, double min, double max){
        return (value-min)/Math.abs(max-min);
    }

    public static float mapColor(float H, float targetMin, float targetMax){
        float length = Math.abs(targetMax+360-targetMin)%360;

        return ((length/360)*H + targetMin)%360;
    }

}
