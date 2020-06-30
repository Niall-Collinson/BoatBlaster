package com.example.boatshooter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

/*
game over screen
 */
public class GameOver extends Activity {

    //holds current score and best score on the device
    TextView tvScore, tvPersonalBest;

    //variable for the users name
    EditText editTextName;

    //button to add the score and name to the database
    Button buttonAddScore;

    //reference to connect to the database
    DatabaseReference databaseReference;

    //listview object which holds the connectiion
    ListView listViewPlayers;

    //holds list of the names from the firebase server
    List<Name> nameList;

    /*
    constructor to create page
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //lays out content to be displayed
        setContentView(R.layout.game_over);
        int score = getIntent().getExtras().getInt("score");

        //preferences
        SharedPreferences pref = getSharedPreferences("MyPref", 0);

        //users score added on screen
        int scoreSP = pref.getInt("scoreSP", 0);

        /*
        if the current score is higher than the current high score stored on the device and then
        if greater the new high score is updated
         */
        SharedPreferences.Editor editor = pref.edit();
        if(score > scoreSP){
            scoreSP = score;
            editor.putInt("scoreSP", scoreSP);
            editor.commit();
        }

        //scores from the local device printed on screen
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvPersonalBest = (TextView) findViewById(R.id.tvPersonalBest);
        tvScore.setText(""+score);
        tvPersonalBest.setText(""+scoreSP);

        //getting reference from firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("Name");

        //allows user to enter name
        editTextName = (EditText) findViewById(R.id.editTextName);

        //allows user to submit score
        buttonAddScore = (Button) findViewById(R.id.buttonAddScore);

        //namelist declared and a listview created
        nameList = new ArrayList<>();
        listViewPlayers = (ListView) findViewById(R.id.listViewPlayers);

        //listener for the button
        buttonAddScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            addName();
            }
        });

    }

    /*
    connects to the database to get names and scores
     */
    @Override
    protected void onStart() {
        super.onStart();

        //event listner added
        databaseReference.addValueEventListener(new ValueEventListener() {
            /*
            when data is changed to add players name and score to the database
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //clears namelist so it doesnt submit names which arent the users
                nameList.clear();

                //advance for loop to get the values from the Name class and add them to the list
                for(DataSnapshot playerSnapshot : dataSnapshot.getChildren()){
                    Name name = playerSnapshot.getValue(Name.class);
                    nameList.add(name);
                }

                //sends the data from the database to be displayed in the listView on screen
                PlayerList adapter = new PlayerList(GameOver.this, nameList);
                listViewPlayers.setAdapter(adapter);

            }

            /*
            empty
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*
    adding a name to the database
     */
    private void addName(){
        String name = editTextName.getText().toString().trim();
        int finalScore = getIntent().getExtras().getInt("score");

        if(!TextUtils.isEmpty(name)){
            String id = databaseReference.push().getKey();
            Name addName = new Name(id, name, finalScore);
            databaseReference.child(id).setValue(addName);

        }
    }

/*
restart button to restart the game
 */
    public void restart(View view){
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /*
    exit button to exit the game
     */
    public void exit(View view){
        finish();
    }
}
