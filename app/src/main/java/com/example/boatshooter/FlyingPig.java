package com.example.boatshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
flying pig object
 */
public class FlyingPig extends Boat {

    //bitmap to hold png files
    Bitmap[] boat = new Bitmap[3];

    /*
    constructor with png files and resets position
     */
    public FlyingPig(Context context) {
        super(context);
        boat[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.flying_pig1);
        boat[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.flying_pig2);
        boat[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.flying_pig3);
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
    resets position and declares velocity
     */
    @Override
    public void resetPosition() {
        boatX = -(200+random.nextInt(1500));
        boatY = random.nextInt(400);
        velocity = 20;
    }
}
