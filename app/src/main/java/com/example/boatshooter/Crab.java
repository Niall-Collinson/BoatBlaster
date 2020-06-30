package com.example.boatshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
creates crab object extending boat object
 */
public class Crab extends Boat {

    //bitmap to hold png files
    Bitmap[] boat = new Bitmap[4];

    /*
    constructor for png files and resets the position
     */
    public Crab(Context context) {
        super(context);
        boat[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.crab1);
        boat[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.crab2);
        boat[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.crab3);
        boat[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.crab4);
        resetPosition();
    }

    /*
    bitmap getter
     */
    @Override
    public Bitmap getBitmap() {
        return boat[boatFrame];
    }

    /*
    width getter
     */
    @Override
    public int getWidth() {
        return boat[0].getWidth();
    }

    /*
    height getter
     */
    @Override
    public int getHeight() {
        return boat[0].getHeight();
    }

    /*
    resets position and defines velocity
     */
    @Override
    public void resetPosition() {
       boatX = -(200+random.nextInt(1500));
       boatY = random.nextInt(400);
       velocity = 5;
    }
}
