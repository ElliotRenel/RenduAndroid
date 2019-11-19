package com.example.myapplication;


import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.Math;

public class MainActivity extends AppCompatActivity {
    Bitmap bit0,bit,bit_b,bit_g,bit_c,bit_t;
    ImageView iv;
    TextView tv, t_sk;
    SeekBar sk;
    int color_slide;

    private void rgb_to_hsv(float red, float green, float blue, float[] hsv) {
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

    private int hsv_to_rgb(float hsv[]){
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

    private void bitmapToHistGray(Bitmap bmp, int[] hist){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);
        for(int i=0; i<size; i++)
            hist[Color.red(pixels[i])]++;
    }

    private void bitmapToHistColor(Bitmap bmp, int[] hist, char color){
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

    private void histToC(int[] hist, int[] C, int size){
        C[0] = hist[0];
        for(int i=1; i<size;i++){
            C[i] = C[i-1] + hist[i];
        }
    }
/*
    private  void  toGrayRS(Bitmap  bmp) {
        RenderScript rs = RenderScript.create(this);

        Allocation input = Allocation.createFromBitmap(rs , bmp);
        Allocation  output= Allocation.createTyped(rs , input.getType ());

        ScriptC_gray grayScript = new  ScriptC_gray(rs);

        grayScript.forEach_toGray(input , output);

        output.copyTo(bmp);

        input.destroy (); output.destroy ();
        grayScript.destroy (); rs.destroy ();
    }
*/
    void toGray2(Bitmap bmp){
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

    void contrastLinearGrey(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);

        int[] hist = new int[256];
        bitmapToHistGray(bmp,hist);

        int min=0, max=255;

        for(int i=0; i<256; i++) {
            if (hist[i] != 0) {
                min = i;
                break;
            }
        }
        for(int i=min+1; i<256;i++){
            if(hist[i]!=0)
                max=i;
        }

        for(int i=0; i<size; i++){
            long r = (long)255*((long)Color.red(pixels[i])-(long)min)/(long)(max-min);
            long g = (long)255*((long)Color.green(pixels[i])-(long)min)/(long)(max-min);
            long b = (long)255*((long)Color.blue(pixels[i])-(long)min)/(long)(max-min);
            pixels[i] = Color.argb(Color.alpha(pixels[i]),(int)r,(int)g,(int)b);
        }
        bmp.setPixels(pixels,0,w,0,0,w,h);
    }

    void contrastLinearColor(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);

        int[] hist_r = new int[256];
        int[] hist_g = new int[256];
        int[] hist_b = new int[256];
        bitmapToHistColor(bmp,hist_r,'r');
        bitmapToHistColor(bmp,hist_g,'g');
        bitmapToHistColor(bmp,hist_b,'b');

        int min_r=0, max_r=255;
        int min_g=0, max_g=255;
        int min_b=0, max_b=255;
        boolean br = true, bg = true, bb = true;

        for(int i=0; i<256; i++) {
            if (hist_r[i] != 0 && br) {
                min_r = i;
                br=false;
            }
            if (hist_g[i] != 0 && bg) {
                min_g = i;
                bg=false;
            }
            if (hist_b[i] != 0 && bb) {
                min_b = i;
                bb=false;
            }
        }

        int min = Math.min(min_r,Math.min(min_b,min_g));

        for(int i=min+1; i<256;i++){
            if(hist_r[i]!=0)
                max_r=i;
            if(hist_g[i]!=0)
                max_g=i;
            if(hist_b[i]!=0)
                max_b=i;
        }

        for(int i=0; i<size; i++){
            long r = (long)255*((long)Color.red(pixels[i])-(long)min_r)/(long)(max_r-min_r);
            long g = (long)255*((long)Color.green(pixels[i])-(long)min_g)/(long)(max_g-min_g);
            long b = (long)255*((long)Color.blue(pixels[i])-(long)min_b)/(long)(max_b-min_b);
            pixels[i] = Color.argb(Color.alpha(pixels[i]),(int)r,(int)g,(int)b);
        }
        bmp.setPixels(pixels,0,w,0,0,w,h);
    }

    void contrastEqualGrey(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);

        int[] hist = new int[256];
        bitmapToHistGray(bmp,hist);

        int[] C = new int[256];
        histToC(hist,C,256);

        for(int i =0; i<size; i++){
            long r = (C[Color.red(pixels[i])]*(long)255)/(long)size;
            long g = (C[Color.green(pixels[i])]*(long)255)/(long)size;
            long b = (C[Color.blue(pixels[i])]*(long)255)/(long)size;
            pixels[i] = Color.argb(Color.alpha(pixels[i]),(int)r,(int)g,(int)b);
        }
        bmp.setPixels(pixels,0,w,0,0,w,h);
    }

    void contrastEqualColor(Bitmap bmp){
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);

        int[] hist_r = new int[256];
        int[] hist_g = new int[256];
        int[] hist_b = new int[256];
        bitmapToHistColor(bmp,hist_r,'r');
        bitmapToHistColor(bmp,hist_g,'g');
        bitmapToHistColor(bmp,hist_b,'b');

        int[] C_r = new int[256];
        int[] C_g = new int[256];
        int[] C_b = new int[256];
        histToC(hist_r,C_r,256);
        histToC(hist_g,C_g,256);
        histToC(hist_b,C_b,256);

        for(int i =0; i<size; i++){
            long r = (C_r[Color.red(pixels[i])]*(long)255)/(long)size;
            long g = (C_g[Color.green(pixels[i])]*(long)255)/(long)size;
            long b = (C_b[Color.blue(pixels[i])]*(long)255)/(long)size;
            pixels[i] = Color.argb(Color.alpha(pixels[i]),(int)r,(int)g,(int)b);
        }
        bmp.setPixels(pixels,0,w,0,0,w,h);
    }

    void keepColor(Bitmap bmp, int color, int range){
        color = (color%360);
        int w = bmp.getWidth();
        int h = bmp.getHeight();
        int size = w*h;
        int[] pixels = new int[size];
        bmp.getPixels(pixels,0,w,0,0,w,h);
        float hsv[] = new float[3];
        int cmin = (color - range + 360)%360, cmax = (color+range)%360;
        Log.i("Color",Integer.toString(cmin)+" "+Integer.toString(cmax));
        boolean discontinue = cmax<cmin?true:false;
        for(int i=0; i<size; i++){
            rgb_to_hsv(Color.red(pixels[i]),Color.green(pixels[i]),Color.blue(pixels[i]),hsv);//Color.colorToHSV(pixels[i],hsv);
            boolean inRange = discontinue?hsv[0]<cmax || hsv[0]>cmin:hsv[0]<cmax && hsv[0]>cmin;
            if(!inRange) {
                hsv[1] = 0;
                //hsv[2] = 100;
            }
            pixels[i] = hsv_to_rgb(hsv);

        }
        bmp.setPixels(pixels,0,w,0,0,w,h);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Deb","create"+System.getProperty("user.dir"));

        // Displaying Image
        iv = findViewById(R.id.imgView);
        bit_b = BitmapFactory.decodeResource(getResources(),R.drawable.color_beach);
        bit_g = BitmapFactory.decodeResource(getResources(),R.drawable.graydune);
        bit_t = BitmapFactory.decodeResource(getResources(),R.drawable.cute_dog);
        bit_c = BitmapFactory.decodeResource(getResources(),R.drawable.color);
        bit0 = bit_b.copy(bit_b.getConfig(),false);
        bit = bit0.copy(bit0.getConfig(),true);
        iv.setImageBitmap(bit);

        tv = (TextView) findViewById(R.id.size_t);
        String w = Integer.toString(bit.getWidth());
        String h = Integer.toString(bit.getHeight());
        tv.setText(w+"x"+h);

        sk = findViewById(R.id.seekBar);
        t_sk = findViewById(R.id.seek_value);
        color_slide = sk.getProgress();
        t_sk.setText("Progress: " + color_slide);

        sk.setOnSeekBarChangeListener(seekBarChangeListener);
        //Picture modification buttons

        //Reset
        Button reset = findViewById(R.id.reset_b);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageBitmap(bit0);
                bit = bit0.copy(bit0.getConfig(),true);
            }
        });


        //Gray Scale
        Button gray = findViewById(R.id.gray_b);
        gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGray2(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Keep color
        Button keep = findViewById(R.id.keep_b);
        keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keepColor(bit,color_slide,50);
                iv.setImageBitmap(bit);
            }
        });

        //Contrast
        //Equal Color
        Button contrastec = findViewById(R.id.contrastec_b);
        contrastec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contrastEqualColor(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Equal Grey
        Button contrasteg = findViewById(R.id.contrasteg_b);
        contrasteg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contrastEqualGrey(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Linear Grey
        Button contrastlg = findViewById(R.id.contrastlg_b);
        contrastlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contrastLinearGrey(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Linear Color
        Button contrastlc = findViewById(R.id.contrastlc_b);
        contrastlc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contrastLinearColor(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Change picture buttons

        Button but2 = findViewById(R.id.beach_b);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit0 = bit_b.copy(bit_b.getConfig(),false);
                bit = bit0.copy(bit0.getConfig(),true);
                iv.setImageBitmap(bit);
                String w = Integer.toString(bit.getWidth());
                String h = Integer.toString(bit.getHeight());
                tv.setText(w+"x"+h);
            }
        });

        Button but3 = findViewById(R.id.wheel_b);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit0 = bit_c.copy(bit_c.getConfig(),false);
                bit = bit0.copy(bit0.getConfig(),true);
                iv.setImageBitmap(bit);
                String w = Integer.toString(bit.getWidth());
                String h = Integer.toString(bit.getHeight());
                tv.setText(w+"x"+h);
            }
        });

        Button but4 = findViewById(R.id.dune_b);
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit0 = bit_g.copy(bit_g.getConfig(),false);
                bit = bit0.copy(bit0.getConfig(),true);
                iv.setImageBitmap(bit);
                String w = Integer.toString(bit.getWidth());
                String h = Integer.toString(bit.getHeight());
                tv.setText(w+"x"+h);
            }
        });

        Button but5 = findViewById(R.id.dog_b);
        but5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit0 = bit_t.copy(bit_t.getConfig(),false);
                bit = bit0.copy(bit0.getConfig(),true);
                iv.setImageBitmap(bit);
                String w = Integer.toString(bit.getWidth());
                String h = Integer.toString(bit.getHeight());
                tv.setText(w+"x"+h);
            }
        });
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            t_sk.setText("Progress: " + progress);
            color_slide = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };


    protected void onStart() {
        super.onStart();
        Log.i("Deb","start");
    }

    protected void onResume(){
        super.onResume();
        Log.i("Deb","resume");
    }

    protected void onPause(){
        super.onPause();
        Log.i("Deb","pause");
    }

    protected void onStop(){
        super.onStop();
        Log.i("Deb","stop");
    }

    protected void onRestart(){
        super.onRestart();
        Log.i("Deb","restart");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i("Deb","destroy");
    }


}
