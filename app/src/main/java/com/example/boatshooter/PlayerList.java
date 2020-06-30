package com.example.boatshooter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

/*
list to be displayed on game over screen
 */
public class PlayerList extends ArrayAdapter<Name> {

    //variable declaration
    private Activity context;
    private List<Name> playerList;

    public PlayerList(Activity context, List<Name> playerList){
        super(context, R.layout.list_layout, playerList);
        this.context = context;
        this.playerList = playerList;
    }

    /*
    gets data to be displayed in the listview
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        //the two types of data to be displayed
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewScore = (TextView) listViewItem.findViewById(R.id.textViewScore);

        //spawn object and get attributes
        Name name = playerList.get(position);
        textViewName.setText(name.getPlayerName());
        textViewScore.setText("" + name.getFinalScore());

        return listViewItem;
    }
}
