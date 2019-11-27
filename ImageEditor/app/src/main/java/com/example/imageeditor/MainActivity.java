package com.example.imageeditor;

import com.example.imageeditor.effects.Effects;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Bitmap bit0, bit, bit_b, bit_g, bit_c, bit_t;
    ImageView iv;
    TextView tv, t_sk;
    SeekBar sk;
    int color_slide;
    Effects effects = new Effects();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Deb", "create" + System.getProperty("user.dir"));

        // Displaying Image
        iv = findViewById(R.id.imgView);
        bit_b = BitmapFactory.decodeResource(getResources(), R.drawable.color_beach);
        bit_g = BitmapFactory.decodeResource(getResources(), R.drawable.graydune);
        bit_t = BitmapFactory.decodeResource(getResources(), R.drawable.cute_dog);
        bit_c = BitmapFactory.decodeResource(getResources(), R.drawable.color);
        bit0 = bit_b.copy(bit_b.getConfig(), false);
        bit = bit0.copy(bit0.getConfig(), true);
        iv.setImageBitmap(bit);

        tv = (TextView) findViewById(R.id.size_t);
        String w = Integer.toString(bit.getWidth());
        String h = Integer.toString(bit.getHeight());
        tv.setText(w + "x" + h);

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
                bit = bit0.copy(bit0.getConfig(), true);
            }
        });


        //Gray Scale
        Button gray = findViewById(R.id.gray_b);
        gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.toGray2(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Keep color
        Button keep = findViewById(R.id.keep_b);
        keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.keepColor(bit, color_slide, 50);
                iv.setImageBitmap(bit);
            }
        });

        //Contrast
        //Equal Color
        Button contrastec = findViewById(R.id.contrastec_b);
        contrastec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.contrastEqualColor(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Equal Grey
        Button contrasteg = findViewById(R.id.contrasteg_b);
        contrasteg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.contrastEqualGrey(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Linear Grey
        Button contrastlg = findViewById(R.id.contrastlg_b);
        contrastlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.contrastLinearGrey(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Linear Color
        Button contrastlc = findViewById(R.id.contrastlc_b);
        contrastlc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                effects.contrastLinearColor(bit);
                iv.setImageBitmap(bit);
            }
        });

        //Change picture buttons

        Button but2 = findViewById(R.id.beach_b);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit0 = bit_b.copy(bit_b.getConfig(), false);
                bit = bit0.copy(bit0.getConfig(), true);
                iv.setImageBitmap(bit);
                String w = Integer.toString(bit.getWidth());
                String h = Integer.toString(bit.getHeight());
                tv.setText(w + "x" + h);
            }
        });

        Button but3 = findViewById(R.id.wheel_b);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit0 = bit_c.copy(bit_c.getConfig(), false);
                bit = bit0.copy(bit0.getConfig(), true);
                iv.setImageBitmap(bit);
                String w = Integer.toString(bit.getWidth());
                String h = Integer.toString(bit.getHeight());
                tv.setText(w + "x" + h);
            }
        });

        Button but4 = findViewById(R.id.dune_b);
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit0 = bit_g.copy(bit_g.getConfig(), false);
                bit = bit0.copy(bit0.getConfig(), true);
                iv.setImageBitmap(bit);
                String w = Integer.toString(bit.getWidth());
                String h = Integer.toString(bit.getHeight());
                tv.setText(w + "x" + h);
            }
        });

        Button but5 = findViewById(R.id.dog_b);
        but5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bit0 = bit_t.copy(bit_t.getConfig(), false);
                bit = bit0.copy(bit0.getConfig(), true);
                iv.setImageBitmap(bit);
                String w = Integer.toString(bit.getWidth());
                String h = Integer.toString(bit.getHeight());
                tv.setText(w + "x" + h);
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
}

