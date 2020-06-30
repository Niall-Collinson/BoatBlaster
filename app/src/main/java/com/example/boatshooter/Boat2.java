package com.example.boatshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
creates second boat object
 */
public class Boat2 extends Boat {

    //bitmap for second boat to store png files
    Bitmap[] boat = new Bitmap[5];

    /*
    png files and resets position
     */
    public Boat2(Context context) {
        super(context);
        boat[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.scary_pirate_ship1);
        boat[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.scary_pirate_ship2);
        boat[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.scary_pirate_ship3);
        boat[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.scary_pirate_ship4);
        boat[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.scary_pirate_ship5);
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
    resets the boat as it crosses the screen
    changes velocity as level increases
     */
    @Override
    public void resetPosition() {
        boatX = -(200+random.nextInt(1500));
        boatY = random.nextInt(400);
        if (GameView.level == 1){
            velocity = 8 + random.nextInt(10);
        } else if (GameView.level == 2){
            velocity = 12 + random.nextInt(10);
        } else if (GameView.level == 3){
            velocity = 15 + random.nextInt(10);
        }
    }
}
