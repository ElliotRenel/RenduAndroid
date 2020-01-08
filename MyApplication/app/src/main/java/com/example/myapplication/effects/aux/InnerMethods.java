package com.example.myapplication.effects.aux;

import android.graphics.Bitmap;
import android.graphics.Color;

public class InnerMethods {

    public static void rgb_to_hsv(int pixel,float[] hsv){
        float red_ = (float)Color.red(pixel)/(float)255;
        float blue_ = (float)Color.green(pixel)/(float)255;
        float green_ = (float)Color.blue(pixel)/(float)255;

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

        h = (360-h)%360;

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

    public static int hsv_to_rgb(float[] hsv){
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
        return Color.rgb(red,green,blue);

    }

    public static double[] rgb_to_v(int[] pixels){
        int size = pixels.length;
        double[] V= new double[size];
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

    public static int v_to_rgb(int old_rgb, double V){
        float[] hsv = new float[3];
        rgb_to_hsv(old_rgb,hsv);
        hsv[2] = (float)V;
        return hsv_to_rgb(hsv);
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

    public static void bitmapToHistHSV(Bitmap bmp,int[] hist){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);
        for(int i=0;i<size; i++){
            float[] hsv = new float[3];
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
