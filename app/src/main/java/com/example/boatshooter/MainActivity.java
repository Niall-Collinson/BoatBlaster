package com.example.boatshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/*
initial page
 */
public class MainActivity extends Activity {

    /*
    create page
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    start game button
     */
    public void startGame(View v){
    Log.i("ImageButton","clicked");
    Intent intent = new Intent(this,StartGame.class);
    startActivity(intent);
    finish();
    }
}
