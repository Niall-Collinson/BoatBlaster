package com.example.boatshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
explosion object
 */
public class Explosion {

    //holds png files
    Bitmap explosion[] = new Bitmap[24];

    //declares explosion frame
    int explosionFrame;

    //coordinates variables
    int explosionx, explosiony;

    /*
    constructor holds png files
     */
    public Explosion(Context context)
    {
        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion1);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion2);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion3);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion4);
        explosion[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion5);
        explosion[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion6);
        explosion[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion7);

    }

    /*
    explosion getter
     */
    public Bitmap getExplosion(int explosionFrame){
        return explosion[explosionFrame];
    }

    /*
    width getter
     */
    public int getExplosionWidth(){
        return explosion[0].getWidth();
    }

    /*
    height getter
     */
    public int getExplosionHeight(){
        return explosion[0].getHeight();
    }
}
