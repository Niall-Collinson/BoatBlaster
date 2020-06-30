package com.example.boatshooter;

/*
class to hold the variables which are submitted to the firebase database
 */
public class Name {

    //variables to be sent to the database
    String artistId;
    String playerName;
    int finalScore;

    /*
    constructor
     */
    public Name(){

    }

    /*
    initialise
     */
    public Name(String artistId, String playerName, int finalScore) {
        this.artistId = artistId;
        this.playerName = playerName;
        this.finalScore = finalScore;
    }

    /*
    id getter
     */
    public String getArtistId() {
        return artistId;
    }

    /*
    player name getter
     */
    public String getPlayerName() {
        return playerName;
    }

    /*
    final score getter
     */
    public int getFinalScore() {
        return finalScore;
    }
}
