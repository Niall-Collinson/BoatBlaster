package com.example.boatshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
creates canonball object
 */
public class CanonBall {

    //declares x, y coordinates
    int x, y;

    //declares velocity of canonball
    int cVelocity;

    //holds bitmap of canonball
    Bitmap canonBall;

    /*
    constructor and creates fireball, defines variables
     */
    public CanonBall(Context context){
    canonBall = BitmapFactory.decodeResource(context.getResources(), R.drawable.fireball);
    x = GameView.dWidth/2 - getCanonBallWidth()/2;
    y = GameView.dHeight - GameView.canonHeight - getCanonBallHeight()/2;
    cVelocity = 50;
    }

    /*
    width getter
     */
    public int getCanonBallWidth(){
        return canonBall.getWidth();
    }

    /*
    height getter
     */
    public int getCanonBallHeight(){
        return canonBall.getHeight();
    }

}
