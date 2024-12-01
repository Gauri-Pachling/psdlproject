package com.dummy.psdl;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Highscore extends AppCompatActivity {
    private TextView text;
    private ImageButton highsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);



        text = findViewById(R.id.textView2);
        text.setText(String.valueOf(GameView.getScore()));

        highsc = findViewById(R.id.imageButton);
        DBHelper db = new DBHelper(getApplicationContext());
        Integer value = GameView.getScore();
        if (value != null) {
            db.insertScore(value);
        }

        highsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent("com.dummy.psdl.MainHighScore");
                startActivity(intent1);
            }
        });
    }




}
