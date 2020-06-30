package com.example.boatshooter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;
import android.os.Handler;

/*
main gameview for the game
 */
public class GameView extends View {

    //variables declared
    Bitmap background, canon;
    Rect rect;
    static int dWidth, dHeight;
    ArrayList<Boat> boats, boats2, crabs, flying_pigs;
    ArrayList<CanonBall> canonBalls;
    ArrayList<Explosion> explosions;
    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLIS = 30;
    static int canonWidth, canonHeight;
    Context context;
    int count = 0;
    SoundPool sp;
    int fire = 0, point = 0;
    Paint scorePaint, healthPaint, levelPaint;
    final int TEXT_SIZE = 60;
    int health = 10;
    Bitmap background_2, background_3, background_random;
    static int level = 1;
    static int pig_Counter = 0;

    /*
    main view for the game declaring objects and arrays as well setting out the screen
     */
    public GameView(Context context) {
        super(context);
        this.context = context;

        //declaring backgrounds and canon objects
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background_1);
        canon = BitmapFactory.decodeResource(getResources(), R.drawable.canon);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        //width and height of display
        dWidth = size.x;
        dHeight = size.y;
        rect = new Rect(0, 0, dWidth, dHeight);

        //objects in arrays
        boats = new ArrayList<>();
        boats2 = new ArrayList<>();
        canonBalls = new ArrayList<>();
        explosions = new ArrayList<>();
        crabs = new ArrayList<>();
        flying_pigs = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Boat boat = new Boat(context);
            boats.add(boat);
            Boat2 boat2 = new Boat2(context);
            boats2.add(boat2);
            Crab crab = new Crab(context);
            crabs.add(crab);
            FlyingPig flyingPig = new FlyingPig(context);
            flying_pigs.add(flyingPig);
        }
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        canonWidth = canon.getWidth();
        canonHeight = canon.getHeight();
        sp = new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        fire = sp.load(context, R.raw.fire, 1);
        point = sp.load(context, R.raw.point,1);
        scorePaint = new Paint();
        scorePaint.setColor(Color.BLACK);
        scorePaint.setTextSize(TEXT_SIZE);
        scorePaint.setTextAlign(Paint.Align.LEFT);
        healthPaint = new Paint();
        healthPaint.setColor(Color.GREEN);
        levelPaint = new Paint();
        levelPaint.setColor(Color.RED);
        levelPaint.setTextSize(TEXT_SIZE);
        levelPaint.setTextAlign(Paint.Align.CENTER);
        background_2 = BitmapFactory.decodeResource(getResources(), R.drawable.background_2);
        background_3 = BitmapFactory.decodeResource(getResources(), R.drawable.background_3);
        background_random = BitmapFactory.decodeResource(getResources(), R.drawable.background_random);
        level = 1;
    }


    /*
    on draw
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //if healthbar empty go to game over screen
        if(health == 0){
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("score", (count));
            context.startActivity(intent);
            ((Activity) context).finish();
        }

        //move up the level as the count moves up
        if (count > 10 && count < 21){
            level = 2;
        } else if (count >= 21){
            level = 3;
        }

        //change background as level changes
        if (level == 1){
            canvas.drawBitmap(background, null, rect, null);
        } else if (level == 2){
            canvas.drawBitmap(background_2, null, rect, null);
        } else if(level == 3){
            canvas.drawBitmap(background_3, null, rect, null);
        } else if(level == 1000){
            canvas.drawBitmap(background_random, null, rect, null);
        }

        /*
        animate object and reset the object position, if health gone then go to game over screen
        */
        for (int i = 0; i < boats.size(); i++) {
            if (level != 1000) {
                canvas.drawBitmap(boats.get(i).getBitmap(), boats.get(i).boatX, boats.get(i).boatY, null);
                boats.get(i).boatFrame++;
                if (boats.get(i).boatFrame > 24) {
                    boats.get(i).boatFrame = 0;
                }
                boats.get(i).boatX -= boats.get(i).velocity;
                if (boats.get(i).boatX < -boats.get(i).getWidth()) {
                    boats.get(i).resetPosition();
                    health--;
                    if (health == 0) {
                        Intent intent = new Intent(context, GameOver.class);
                        intent.putExtra("score", (count));
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                }
            }

            /*
            animate object and reset the object position, if health gone then go to game over screen
            */
            if (level != 1000) {
                canvas.drawBitmap(boats2.get(i).getBitmap(), boats2.get(i).boatX, boats2.get(i).boatY, null);
                boats2.get(i).boatFrame++;
                if (boats2.get(i).boatFrame > 4) {
                    boats2.get(i).boatFrame = 0;
                }
                boats2.get(i).boatX += boats2.get(i).velocity;
                if (boats2.get(i).boatX > (dWidth + boats2.get(i).getWidth())) {
                    boats2.get(i).resetPosition();
                    health--;
                    if (health == 0) {
                        Intent intent = new Intent(context, GameOver.class);
                        intent.putExtra("score", (count));
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                }
            }

            /*
            animate object and reset the object position, if health gone then go to game over screen
            crabs only appear in level 1
            */
            if (level == 1) {
                canvas.drawBitmap(crabs.get(i).getBitmap(), crabs.get(i).boatX, crabs.get(i).boatY, null);
                crabs.get(i).boatFrame++;
                if (crabs.get(i).boatFrame > 3) {
                    crabs.get(i).boatFrame = 0;
                }
                crabs.get(i).boatX += crabs.get(i).velocity;
                if (crabs.get(i).boatX > (dWidth + crabs.get(i).getWidth())) {
                    crabs.get(i).resetPosition();
                }
            }

            /*
            animate object and reset the object position, if health gone then go to game over screen
            flying pigs only exist in bonus level and do no damage to the player
            */
            if (level == 1000){
                canvas.drawBitmap(flying_pigs.get(i).getBitmap(), flying_pigs.get(i).boatX,
                        flying_pigs.get(i).boatY, null);
                flying_pigs.get(i).boatFrame++;
                if (flying_pigs.get(i).boatFrame > 2) {
                    flying_pigs.get(i).boatFrame = 0;
                }
                flying_pigs.get(i).boatX += flying_pigs.get(i).velocity;
                if (flying_pigs.get(i).boatX > (dWidth + flying_pigs.get(i).getWidth())) {
                    flying_pigs.get(i).resetPosition();
                }
            }


        }


        /*
        iterates through canonballs
         */
        for (int i = 0; i < canonBalls.size(); i++) {

            /*
            collision detection for canonballs and object
            if collided increase score, reset object position, get explosion and play sound
             */
            if (canonBalls.get(i).y > -canonBalls.get(i).getCanonBallHeight()) {
                canonBalls.get(i).y -= canonBalls.get(i).cVelocity;
                canvas.drawBitmap(canonBalls.get(i).canonBall, canonBalls.get(i).x, canonBalls.get(i).y, null);

                /*
                if collided then
                 */
                if (canonBalls.get(i).x >= boats.get(0).boatX && (canonBalls.get(i).x + canonBalls.get(i).getCanonBallWidth())
                        <= (boats.get(0).boatX + boats.get(0).getWidth()) && canonBalls.get(i).y >= boats.get(0).boatY &&
                        canonBalls.get(i).y <= (boats.get(0).boatY + boats.get(0).getHeight())) {

                    /*
                    get explosion, reset the position of the object,
                    increase count and remove canonball
                     */
                    Explosion explosion = new Explosion(context);
                    explosion.explosionx = boats.get(0).boatX + boats.get(0).getWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosiony = boats.get(0).boatY + boats.get(0).getHeight()/2 - explosion.getExplosionHeight()/2;
                    explosions.add(explosion);
                    boats.get(0).resetPosition();
                    count++;
                    canonBalls.remove(i);

                    //sound to play if colided
                    if(point != 0){
                        //sp.play(point,1,1,0,0,1);
                    }
                } else if (canonBalls.get(i).x >= boats.get(1).boatX && (canonBalls.get(i).x + canonBalls.get(i).getCanonBallWidth())
                        <= (boats.get(1).boatX + boats.get(1).getWidth()) && canonBalls.get(i).y >= boats.get(1).boatY &&
                        canonBalls.get(i).y <= (boats.get(1).boatY + boats.get(1).getHeight())) {
                    Explosion explosion = new Explosion(context);
                    explosion.explosionx = boats.get(1).boatX + boats.get(1).getWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosiony = boats.get(1).boatY + boats.get(1).getHeight()/2 - explosion.getExplosionHeight()/2;
                    explosions.add(explosion);
                    boats.get(1).resetPosition();
                    count++;
                    canonBalls.remove(i);
                    if(point != 0){
                        //sp.play(point,1,1,0,0,1);
                    }

                } else if (canonBalls.get(i).x >= boats2.get(0).boatX && (canonBalls.get(i).x + canonBalls.get(i).getCanonBallWidth())
                        <= (boats2.get(0).boatX + boats2.get(0).getWidth()) && canonBalls.get(i).y >= boats2.get(0).boatY &&
                        canonBalls.get(i).y <= (boats2.get(0).boatY + boats2.get(0).getHeight())) {
                    Explosion explosion = new Explosion(context);
                    explosion.explosionx = boats2.get(0).boatX + boats2.get(0).getWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosiony = boats2.get(0).boatY + boats2.get(0).getHeight()/2 - explosion.getExplosionHeight()/2;
                    explosions.add(explosion);
                    boats2.get(0).resetPosition();
                    count++;
                    canonBalls.remove(i);
                    if(point != 0){
                        //sp.play(point,1,1,0,0,1);
                    }

                } else if (canonBalls.get(i).x >= boats2.get(1).boatX && (canonBalls.get(i).x + canonBalls.get(i).getCanonBallWidth())
                        <= (boats2.get(1).boatX + boats2.get(1).getWidth()) && canonBalls.get(i).y >= boats2.get(1).boatY &&
                        canonBalls.get(i).y <= (boats2.get(1).boatY + boats2.get(1).getHeight())) {
                    Explosion explosion = new Explosion(context);
                    explosion.explosionx = boats2.get(1).boatX + boats2.get(1).getWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosiony = boats2.get(1).boatY + boats2.get(1).getHeight()/2 - explosion.getExplosionHeight()/2;
                    explosions.add(explosion);
                    boats2.get(1).resetPosition();
                    count++;
                    canonBalls.remove(i);
                    if(point != 0){
                        //sp.play(point,1,1,0,0,1);
                    }
                } else if (canonBalls.get(i).x >= crabs.get(0).boatX && (canonBalls.get(i).x + canonBalls.get(i).getCanonBallWidth())
                        <= (crabs.get(0).boatX + crabs.get(0).getWidth()) && canonBalls.get(i).y >= crabs.get(0).boatY &&
                        canonBalls.get(i).y <= (crabs.get(0).boatY + crabs.get(0).getHeight())) {
                    Explosion explosion = new Explosion(context);
                    explosion.explosionx = crabs.get(0).boatX + crabs.get(0).getWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosiony = crabs.get(0).boatY + crabs.get(0).getHeight()/2 - explosion.getExplosionHeight()/2;
                    explosions.add(explosion);
                    crabs.get(0).resetPosition();
                    level = 1000;
                    canonBalls.remove(i);
                    if(point != 0){
                        //sp.play(point,1,1,0,0,1);
                    }

                } else if (canonBalls.get(i).x >= crabs.get(1).boatX && (canonBalls.get(i).x + canonBalls.get(i).getCanonBallWidth())
                        <= (crabs.get(1).boatX + crabs.get(1).getWidth()) && canonBalls.get(i).y >= crabs.get(1).boatY &&
                        canonBalls.get(i).y <= (crabs.get(1).boatY + crabs.get(1).getHeight())) {
                    Explosion explosion = new Explosion(context);
                    explosion.explosionx = crabs.get(1).boatX + crabs.get(1).getWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosiony = crabs.get(1).boatY + crabs.get(1).getHeight()/2 - explosion.getExplosionHeight()/2;
                    explosions.add(explosion);
                    crabs.get(1).resetPosition();
                    level = 1000;
                    canonBalls.remove(i);
                    if(point != 0){
                        //sp.play(point,1,1,0,0,1);
                    }
                } else if (canonBalls.get(i).x >= flying_pigs.get(0).boatX && (canonBalls.get(i).x + canonBalls.get(i).getCanonBallWidth())
                        <= (flying_pigs.get(0).boatX + flying_pigs.get(0).getWidth()) && canonBalls.get(i).y >= flying_pigs.get(0).boatY &&
                        canonBalls.get(i).y <= (flying_pigs.get(0).boatY + flying_pigs.get(0).getHeight())) {
                    Explosion explosion = new Explosion(context);
                    explosion.explosionx = flying_pigs.get(0).boatX + flying_pigs.get(0).getWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosiony = flying_pigs.get(0).boatY + flying_pigs.get(0).getHeight()/2 - explosion.getExplosionHeight()/2;
                    explosions.add(explosion);
                    flying_pigs.get(0).resetPosition();
                    pig_Counter++;
                    if (pig_Counter == 5){
                        level=2;
                    }

                    canonBalls.remove(i);
                    if(point != 0){
                        //sp.play(point,1,1,0,0,1);
                    }

                } else if (canonBalls.get(i).x >= flying_pigs.get(1).boatX && (canonBalls.get(i).x + canonBalls.get(i).getCanonBallWidth())
                        <= (flying_pigs.get(1).boatX + flying_pigs.get(1).getWidth()) && canonBalls.get(i).y >= flying_pigs.get(1).boatY &&
                        canonBalls.get(i).y <= (flying_pigs.get(1).boatY + flying_pigs.get(1).getHeight())) {
                    Explosion explosion = new Explosion(context);
                    explosion.explosionx = flying_pigs.get(1).boatX + flying_pigs.get(1).getWidth()/2 - explosion.getExplosionWidth()/2;
                    explosion.explosiony = flying_pigs.get(1).boatY + flying_pigs.get(1).getHeight()/2 - explosion.getExplosionHeight()/2;
                    explosions.add(explosion);
                    flying_pigs.get(1).resetPosition();
                    pig_Counter++;
                    if (pig_Counter == 5){
                        level=2;
                    }
                    canonBalls.remove(i);
                    if(point != 0){
                        //sp.play(point,1,1,0,0,1);
                    }
                }
                } else {
                //if nothing hit then remove canonball
                    canonBalls.remove(i);
                }
            }


        /*
        explosion animation
         */
        for(int j=0; j<explosions.size(); j++){
            canvas.drawBitmap(explosions.get(j).getExplosion(explosions.get(j).explosionFrame), explosions.get(j).explosionx,
                    explosions.get(j).explosiony, null);
            explosions.get(j).explosionFrame++;
            if(explosions.get(j).explosionFrame > 6){
                explosions.remove(j);
            }
        }
        //draw cannon, score, level counter and health bar
            canvas.drawBitmap(canon, (dWidth / 2 - canonWidth / 2), dHeight - canonHeight, null);
            canvas.drawText("Score: " + (count), 0, TEXT_SIZE, scorePaint);
            canvas.drawText("Level: " + level, dWidth - 325, TEXT_SIZE, levelPaint);
            canvas.drawRect(dWidth - 110, 10, dWidth - 110 + 10*health, TEXT_SIZE, healthPaint);
            handler.postDelayed(runnable, UPDATE_MILLIS);
        }

        /*
        fire the cannon if touched
         */
        @Override
        public boolean onTouchEvent (MotionEvent event){
            //get tocuh event
            float touchX = event.getX();
            float touchY = event.getY();
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                if (touchX >= (dWidth / 2 - canonWidth / 2) && touchX <= (dWidth / 2 + canonWidth / 2) && touchY >= (dHeight - canonHeight)) {
                    Log.i("aklsdnfladsnf", "asjdkfkljasndf");

                    //canonballs fired at once is max 3
                    if (canonBalls.size() < 3) {
                        CanonBall c = new CanonBall(context);
                        canonBalls.add(c);

                        //play music
                        if(fire != 0){
                            //sp.play(fire,1,1,0,0,1);
                        }
                    }
                }
            }
            return true;
        }

    }


