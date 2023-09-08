//Authors: Joshua Dawson

import java.util.prefs.Preferences;

/*
 *PlayerData class
 *This class serves as the location where player data wilL be saved and loaded. 
 *NOTE: Char for required letter must be converted to string before calling this class for savefile to work. 
 */

public class playerData {

   /*
    * savePlayerData
    * param: String chosenWord, String reqLetterString, int score, String rank
    * returns: N/A
    * This function uses preferences to store player data. This information is assigned to 
    * a String to be found in the load state later. 
    */

    public static void savePlayerData(String chosenWord, String reqLetterString, int score, String rank){
        Preferences prefs = Preferences.userNodeForPackage(playerData.class);
        prefs.put("ChosenWord", chosenWord);
        prefs.put("ReqLetter", reqLetterString);
        prefs.putInt("Score", score);
        prefs.put("Rank", rank);
    }
    
/**********************************************************/
/**********************************************************/

    /*
    * loadPlayerData
    * param: N/A
    * returns: N/A
    * This function uses preferences to load player data. This information is gathered from the associated
    * names from the savePlayerData method. 
    */

    public static void loadPlayerData(){
        Preferences prefs = Preferences.userNodeForPackage(playerData.class);
        String chosenWord = prefs.get("ChosenWord", "EMPTY");
        String reqLetterString = prefs.get("ReqLetter", "EMPTY");
        int score = prefs.getInt("Score", 0);
        String rank = prefs.get("Rank", "EMPTY");
    }
}
