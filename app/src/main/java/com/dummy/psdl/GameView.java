

package com.dummy.psdl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    volatile boolean playing;
    private int k = 0;
    private Thread gameThread = null;
    private Player player;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private ArrayList<Star> stars = new ArrayList<>();
    private Enemy[] enemies;
    private static int enemyCount = 3;
    private Boom boom;
    private static int score = 0;
    private SoundPool sound;
    private int whoop;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        resetGameState(context, screenX, screenY);  // Initialize the game components
    }

    // Reset game state and initialize game objects
    public void resetGameState(Context context, int screenX, int screenY) {
        player = new Player(context, screenX, screenY);
        surfaceHolder = getHolder();
        paint = new Paint();
        stars.clear();  // Clear the stars list
        for (int i = 0; i < 100; i++) { // 100 stars
            stars.add(new Star(screenX, screenY));
        }
        enemies = new Enemy[enemyCount];
        for (int i = 0; i < enemyCount; i++) {
            enemies[i] = new Enemy(context, screenX, screenY);
        }
        boom = new Boom(context);
        sound = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        whoop = sound.load(context, R.raw.whoosh, 1);
        score = 0;  // Reset score when restarting the game
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        Random generator = new Random();
        player.update();
        boom.setX(-250);
        boom.setY(-250);
        for (Star s : stars) {
            s.update(player.getSpeed());
        }
        for (int i = 0; i < enemyCount; i++) {
            enemies[i].update(player.getSpeed());
            k = generator.nextInt(300);
            if (Rect.intersects(player.getDetectCollision(), enemies[i].getDetectCollision())) {
                boom.setX(enemies[i].getX());
                boom.setY(enemies[i].getY());
                sound.play(whoop, 1f, 1f, 0, 0, 1.5f);
                enemies[i].setXY(1000, k);
                score += 5;
            } else if (!Rect.intersects(player.getDetectCollision(), enemies[i].getDetectCollision()) && Enemy.ecount == 5) {
                Intent intent = new Intent().setClass(getContext(), Highscore.class);
                ((Activity) getContext()).startActivityForResult(intent, 0);
            }
        }
    }

    public static int getScore() {
        return score;
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);
            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(enemies[i].getBitmap(), enemies[i].getX(), enemies[i].getY(), paint);
            }
            canvas.drawBitmap(boom.getBitmap(), boom.getX(), boom.getY(), paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause1() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }
        return true;
    }

    public static void resetScore() {
        score = 0;
    }
}
