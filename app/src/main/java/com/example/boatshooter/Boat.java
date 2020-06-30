package com.example.boatshooter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;


/*
class for creating a boat object
 */
public class Boat {

    /*
    declaring boat bitmap for png files
     */
    Bitmap boat[] = new Bitmap[25];

    //creating x, y coordinates, velocity and a frame to hold the png files
    int boatX, boatY, velocity, boatFrame;

    //creates random object
    Random random;

/*
boat object
 */
    public Boat(Context context) {
        boat[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship1);
        boat[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship2);
        boat[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship3);
        boat[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship4);
        boat[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship5);
        boat[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship6);
        boat[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship7);
        boat[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship8);
        boat[8] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship9);
        boat[9] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship10);
        boat[10] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship11);
        boat[11] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship12);
        boat[12] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship13);
        boat[13] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship14);
        boat[14] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship15);
        boat[15] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship16);
        boat[16] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship17);
        boat[17] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship18);
        boat[18] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship19);
        boat[19] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship20);
        boat[20] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship21);
        boat[21] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship22);
        boat[22] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship23);
        boat[23] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship24);
        boat[24] = BitmapFactory.decodeResource(context.getResources(),R.drawable.pirate_ship25);
        random = new Random();
       resetPosition();
    }

    /*
    returns bitmap
     */
    public Bitmap getBitmap(){
        return boat[boatFrame];
    }

    /*
    returns width
     */
    public int getWidth(){
        return boat[0].getWidth();
    }

    /*
    returns height
     */
    public int getHeight(){
        return boat[0].getHeight();
    }

    /*
    resets the position once it has crossed the screen
    changes the velocity depending on the level
     */
    public void resetPosition(){
        boatX = GameView.dWidth + random.nextInt(1200);
        boatY = random.nextInt(300);
        if (GameView.level == 1){
            velocity = 8 + random.nextInt(10);
        } else if (GameView.level == 2){
            velocity = 12 + random.nextInt(10);
        } else if (GameView.level == 3){
            velocity = 15 + random.nextInt(10);
        }
        boatFrame = 0;
    }
}
