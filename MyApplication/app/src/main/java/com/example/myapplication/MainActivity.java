package com.example.myapplication;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.effects.*;

public class MainActivity extends AppCompatActivity {
    Bitmap bit0,bit;
    Bitmap[] bits;
    ImageView iv;
    TextView tv, t_sk_k,t_sk_c;
    SeekBar sk_k,sk_c;
    int color_kept, color_colorize, current_bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Deb","create"+System.getProperty("user.dir"));

        // Loading and displaying Image
        iv = findViewById(R.id.imgView);

        bits = new Bitmap[7];
        bits[0] = BitmapFactory.decodeResource(getResources(),R.drawable.color_forest);
        bits[1] = BitmapFactory.decodeResource(getResources(),R.drawable.squirel);
        bits[2] = BitmapFactory.decodeResource(getResources(),R.drawable.cute_dog);
        bits[3] = BitmapFactory.decodeResource(getResources(),R.drawable.color_bird);
        bits[4] = BitmapFactory.decodeResource(getResources(),R.drawable.conv_grey);
        bits[5] = BitmapFactory.decodeResource(getResources(),R.drawable.gray_lady);
        bits[6] = BitmapFactory.decodeResource(getResources(),R.drawable.color);
        current_bit = 0;
        bit0 = bits[current_bit].copy(bits[current_bit].getConfig(),false);
        bit = bit0.copy(bit0.getConfig(),true);
        iv.setImageBitmap(bit);

        tv = findViewById(R.id.size_t);
        String w = Integer.toString(bit.getWidth());
        String h = Integer.toString(bit.getHeight());
        tv.setText(w+"x"+h);

        //Config for Keep Color seek bar
        sk_k = findViewById(R.id.seekBar_keep);
        t_sk_k = findViewById(R.id.seek_value_k);
        color_kept = sk_k.getProgress();
        t_sk_k.setText(color_kept+"°");
        sk_k.setOnSeekBarChangeListener(seekBarKeepColor);

        //Config for Colorize seek bar
        sk_c = findViewById(R.id.seekBar_colorize);
        t_sk_c = findViewById(R.id.seek_value_c);
        color_colorize = sk_c.getProgress();
        t_sk_c.setText(color_colorize+"°");
        sk_c.setOnSeekBarChangeListener(seekBarColorize);


        //Picture modification buttons

        //Reset
        findViewById(R.id.reset_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv.setImageBitmap(bit0);
                bit = bit0.copy(bit0.getConfig(),true);
            }
        });


        //Gray Scale
        findViewById(R.id.gray_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorEffects.toGray(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Contrast

        //Equal
        findViewById(R.id.contrast_equal_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contrast.contrastEqualColor(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Equal Grey
        findViewById(R.id.contrast_linear_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contrast.contrastLinearColor(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Convolution

        //Simple Blur
        findViewById(R.id.simpleblur_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KernelConvolution.simpleBlur(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Gaussian Blur
        findViewById(R.id.gauss_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KernelConvolution.simpleGaussian(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Laplacian and Gaussian effect
        findViewById(R.id.laplace_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KernelConvolution.LaplacianGaussian(bit);
                iv.setImageBitmap(bit);
            }
        });


        //Change picture button

        findViewById(R.id.switch_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_bit = (current_bit+1)%bits.length;
                bit0 = bits[current_bit].copy(bits[current_bit].getConfig(),false);
                bit = bit0.copy(bit0.getConfig(),true);
                iv.setImageBitmap(bit);
                String w = Integer.toString(bit.getWidth());
                String h = Integer.toString(bit.getHeight());
                tv.setText(w+"x"+h);
            }
        });
    }
        // SeekBar for keep color (update the color kept when releasing scroll)
        SeekBar.OnSeekBarChangeListener seekBarKeepColor = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            t_sk_k.setText(progress+"°");
            color_kept = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
            bit = bit0.copy(bit0.getConfig(),true);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
            ColorEffects.keepColor(bit, color_kept,50);
            iv.setImageBitmap(bit);
        }
    };


    // SeekBar for colorize (update the color used when releasing scroll)
    SeekBar.OnSeekBarChangeListener seekBarColorize = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            t_sk_c.setText(progress+"°");
            color_kept = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
            bit = bit0.copy(bit0.getConfig(),true);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
            ColorEffects.colorize(bit, color_kept,20);
            iv.setImageBitmap(bit);
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
