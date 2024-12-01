package com.dummy.psdl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Boom {
    //bitmap object
    private Bitmap bitmap;
    //coordinate variables
    private int x;
    private int y;
    //constructor
    public Boom(Context context) {
        //getting boom image from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.boom);

        x = -250;
        y = -250;
    }
    //setters for x and y to make it visible at the place of collision
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    //getters
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
