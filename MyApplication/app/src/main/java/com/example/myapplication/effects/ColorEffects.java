package com.example.myapplication.effects;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.myapplication.effects.aux.InnerMethods;

public class ColorEffects {

    public static void toGray(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);

        for(int i=0;i<size;i++){
            int y = (int)(i/w);
            int x = i - (y*(w));
            int p = pixels[i];
            int val = (int)(Color.red(p)*0.3)+(int)(Color.green(p)*0.59)+(int)(Color.blue(p)*0.11);
            pixels[i] = Color.rgb(val,val,val);
        }
        bmp.setPixels(pixels,0,w,0,0,w,h);
    }

    public static void keepColor(Bitmap bmp, int color, int range){
        color = (color%360);
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);
        float hsv[] = new float[3];
        int cmin = (color - range + 360)%360, cmax = (color+range)%360;
        boolean discontinue = cmax<cmin?true:false;
        for(int i=0; i<size; i++){
            InnerMethods.rgb_to_hsv(Color.red(pixels[i]),Color.green(pixels[i]),Color.blue(pixels[i]),hsv);//Color.colorToHSV(pixels[i],hsv);
            boolean inRange = discontinue?hsv[0]<cmax || hsv[0]>cmin:hsv[0]<cmax && hsv[0]>cmin;
            if(!inRange) {
                hsv[1] = 0;
                //hsv[2] = 100;
            }
            pixels[i] = InnerMethods.hsv_to_rgb(hsv);

        }
        bmp.setPixels(pixels,0,w,0,0,w,h);
    }

    public  static void dontChange(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);

        for(int i=0; i<size; i++){
            float hsv[]= new float[3];
            InnerMethods.rgb_to_hsv(pixels[i],hsv);
            pixels[i] = InnerMethods.hsv_to_rgb(hsv);
        }

        bmp.setPixels(pixels,0,w,0,0,w,h);
    }
}