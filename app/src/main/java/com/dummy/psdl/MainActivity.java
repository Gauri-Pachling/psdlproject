package com.dummy.psdl;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton playnow, exit, sound, info;
    MediaPlayer bckgrnd;
    TextView tv;
    private int flag1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bckgrnd = MediaPlayer.create(MainActivity.this, R.raw.dualdragon);
        bckgrnd.setLooping(true);
        bckgrnd.start();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        // Set up the Toolbar

        tv = findViewById(R.id.textView3);
        try {
            tv.setVisibility(View.GONE);
        } catch (Exception e) {
        }
        info1();

        sound = findViewById(R.id.sound);
        sound.setBackgroundResource(R.drawable.ic_volume_up_white_24dp);
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag1 == 0) {
                    bckgrnd.pause();
                    sound.setBackgroundResource(R.drawable.ic_volume_off_white_24dp);
                    flag1 = 1;
                } else if (flag1 == 1) {
                    bckgrnd.start();
                    sound.setBackgroundResource(R.drawable.ic_volume_up_white_24dp);
                    flag1 = 0;
                }
            }
        });

        playnow = findViewById(R.id.buttonPlay);
        exit = findViewById(R.id.buttonScore);

        playnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.dummy.psdl.GameActivity");
                startActivity(intent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity(); //works on Android 4.1 or higher
                System.exit(0);
            }
        });
    }

    // Show or hide the info TextView
    void info1() {
        info = findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv.getVisibility() == TextView.GONE)
                    tv.setVisibility(View.VISIBLE);
                else
                    tv.setVisibility(View.GONE);
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        bckgrnd.release();
    }


}
