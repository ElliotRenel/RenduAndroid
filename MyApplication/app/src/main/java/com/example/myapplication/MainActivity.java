package com.example.myapplication;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.effects.*;

public class MainActivity extends AppCompatActivity {
    Bitmap bit0,bit;
    Bitmap[] bits;
    ImageView iv;
    TextView tv, t_sk;
    SeekBar sk;
    int color_slide, current_bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Deb","create"+System.getProperty("user.dir"));

        // Displaying Image
        iv = findViewById(R.id.imgView);

        bits = new Bitmap[4];
        bits[0] = BitmapFactory.decodeResource(getResources(),R.drawable.squirel);
        bits[1] = BitmapFactory.decodeResource(getResources(),R.drawable.conv_grey);
        bits[2] = BitmapFactory.decodeResource(getResources(),R.drawable.color_forest);
        bits[3] = BitmapFactory.decodeResource(getResources(),R.drawable.color);
        current_bit = 0;
        bit0 = bits[0].copy(bits[0].getConfig(),false);
        bit = bit0.copy(bit0.getConfig(),true);
        iv.setImageBitmap(bit);

        tv = findViewById(R.id.size_t);
        String w = Integer.toString(bit.getWidth());
        String h = Integer.toString(bit.getHeight());
        tv.setText(w+"x"+h);

        sk = findViewById(R.id.seekBar);
        t_sk = findViewById(R.id.seek_value);
        color_slide = sk.getProgress();
        t_sk.setText("Progress: " + color_slide);

        sk.setOnSeekBarChangeListener(seekBarKeepColor);
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
                ColorEffects.toGray(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Contrast

        //Equal Color
        Button contrastec = findViewById(R.id.contrastec_b);
        contrastec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contrast.contrastEqualColor(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Equal Grey
        Button contrasteg = findViewById(R.id.contrasteg_b);
        contrasteg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contrast.contrastEqualGrey(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Convolution
        Button simple_blur= findViewById(R.id.simpleblur_b);
        simple_blur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KernelConvolution.simpleBlur(bit);
                iv.setImageBitmap(bit);
            }
        });

        Button gauss_blur = findViewById(R.id.gauss_b);
        gauss_blur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KernelConvolution.simpleGaussian(bit);
                iv.setImageBitmap(bit);
            }
        });

        Button laplace_gaussian = findViewById(R.id.laplace_b);
        laplace_gaussian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KernelConvolution.LaplacianGaussian(bit);
                iv.setImageBitmap(bit);
            }
        });


        //Change picture buttons

        Button but2 = findViewById(R.id.switch_b);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_bit = (current_bit+1)%4;
                bit0 = bits[current_bit].copy(bits[current_bit].getConfig(),false);
                bit = bit0.copy(bit0.getConfig(),true);
                iv.setImageBitmap(bit);
                String w = Integer.toString(bit.getWidth());
                String h = Integer.toString(bit.getHeight());
                tv.setText(w+"x"+h);
            }
        });
    }
        // SeekBar for keep color (update the color kept when
        SeekBar.OnSeekBarChangeListener seekBarKeepColor = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            t_sk.setText("Progress: " + progress);
            color_slide = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
            bit = bit0.copy(bit0.getConfig(),true);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
            ColorEffects.keepColor(bit,color_slide,50);
            iv.setImageBitmap(bit);
        }
    };


    // SeekBar for keep color (update the color kept when
    SeekBar.OnSeekBarChangeListener seekBarColorize = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            t_sk.setText("Progress: " + progress);
            color_slide = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
            bit = bit0.copy(bit0.getConfig(),true);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
            ColorEffects.keepColor(bit,color_slide,50);
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
