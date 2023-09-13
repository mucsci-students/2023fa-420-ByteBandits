//Authors: Joshua Dawson

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

/*
 *PlayerData class
 *This class serves as the location where player data wilL be saved and loaded. 
 *NOTE: Char for required letter must be converted to string before calling this class for savefile to work. 
 */


public class playerData {
    private String chosenWord;
    private String reqLetterString;
    private int score;
    private String rank;
    private List<String> acceptedWordList = new ArrayList<>();

   /*
    * saveGameData
    * param: String chosenWord, String reqLetterString, int score, String rank
    * returns: N/A
    * This function saves game data into game_data.json. 
    */

    public void saveGameData(String chosenWord, String reqLetterString, int score, String rank, List<String> acceptedWordList) 
    {
        try (FileWriter fileWriter = new FileWriter("game_data.json"))
        {
            // Create a JSON object to hold the game data
            JSONObject gameData = new JSONObject();
            gameData.put("chosenWord", chosenWord);
            gameData.put("reqLetterString", reqLetterString);
            gameData.put("score", score);
            gameData.put("rank", rank);
            gameData.put("acceptedWordList", acceptedWordList);
    
            // Write the JSON object to the file
            fileWriter.write(gameData.toString());
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
/**********************************************************/
/**********************************************************/

    /*
    * loadGameData
    * param: N/A
    * returns: N/A
    * This function loads save data from game_data.json.
    */

    public void loadGameData() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader("game_data.json"))) 
        {
            StringBuilder jsonData = new StringBuilder();
            String line;
            reqLetterString = "";
            while ((line = reader.readLine()) != null) 
            {
                jsonData.append(line);
            }
            JSONObject gameData = new JSONObject(jsonData.toString());

            chosenWord = gameData.getString("chosenWord");
            reqLetterString = gameData.getString("reqLetterString");
            score = gameData.getInt("score");
            rank = gameData.getString("rank");
            JSONArray acceptedWordListArray = gameData.getJSONArray("acceptedWordList");
            for(int i = 0; i < acceptedWordListArray.length(); ++i)
            {
                acceptedWordList.add(acceptedWordListArray.getString(i));
            }
        } 
        catch (IOException | JSONException e)
        {
            // Handle any errors that may occur during file I/O or data parsing
            e.printStackTrace();
        }
    }

/**********************************************************/
/**********************************************************/

    /*
    * Getter Functions
    * param: N/A
    * returns: chosenWord, reqLetterString, score, rank
    * These functions serve to allow access to private types from master.java. 
    */

    public String getChosenWord() {
        return chosenWord;
    }

    public String getReqLetterString() {
        return reqLetterString;
    }

    public int getScore() {
        return score;
    }

    public String getRank() {
        return rank;
    }

    public List<String> getAcceptedWordList(){
        return acceptedWordList;
    }
}
