package com.dummy.psdl;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class MainHighScore extends AppCompatActivity {

    ListView listView;
    ImageButton exit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_high_score);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        listView = findViewById(R.id.listView);
        exit1 = findViewById(R.id.imageButton2);

        DBHelper db = new DBHelper(getApplicationContext());
        List<Integer> list = db.getAllScores();

        if (list != null) {
            ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(this, R.layout.score_list, list);
            listView.setAdapter(dataAdapter);
        } else {
            Toast.makeText(MainHighScore.this, "No scores enlisted.", Toast.LENGTH_SHORT).show();
        }

        // Exit button functionality
        exit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity(); // Exit the app completely
                System.exit(0);  // Exit process
            }
        });
    }

}
