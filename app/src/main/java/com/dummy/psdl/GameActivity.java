package com.dummy.psdl;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    public GameView gameView;
    private MediaPlayer bckgrndmusic;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Play background music
        bckgrndmusic = MediaPlayer.create(GameActivity.this, R.raw.skywanderer);
        bckgrndmusic.setLooping(true);
        bckgrndmusic.start();

        // Get screen size of ur device
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Initialize game view with screen size
        FrameLayout game = new FrameLayout(this);
        LinearLayout gameWidgets = new LinearLayout(this);
        final ImageButton pause = new ImageButton(this);
        pause.setBackgroundResource(R.drawable.ic_pause_white_24dp);
        gameView = new GameView(this, size.x, size.y);

        // Add widgets and game view to the layout
        gameWidgets.addView(pause);
        game.addView(gameView);
        game.addView(gameWidgets);
        setContentView(game);

        // Pause and resume functionality
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    gameView.pause1();
                    pause.setBackgroundResource(R.drawable.ic_play_arrow_white_24dp);
                    flag = 1;
                } else if (flag == 1) {
                    gameView.resume();
                    pause.setBackgroundResource(R.drawable.ic_pause_white_24dp);
                    flag = 0;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        bckgrndmusic.release();
        gameView.pause1();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
