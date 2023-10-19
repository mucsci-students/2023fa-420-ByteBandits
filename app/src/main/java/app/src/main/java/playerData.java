package app.src.main.java;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class playerData {
    private String baseWord;
    private List<String> foundWords = new ArrayList<>();
    private int playerPoints;
    private String requiredLetter;
    private int maxPoints; 
    public void saveGameData(String baseWord, List<String> foundWords, int playerPoints, String requiredLetter, int maxPoints) {
        try (FileWriter fileWriter = new FileWriter("game_data.json")) {
            
            JSONObject gameData = new JSONObject();
            gameData.put("baseWord", baseWord);
            gameData.put("foundWords", foundWords);
            gameData.put("playerPoints", playerPoints);
            gameData.put("requiredLetter", requiredLetter);
            gameData.put("maxPoints", maxPoints);

            
            fileWriter.write(gameData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGameData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("game_data.json"))) {
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line);
            }
            JSONObject gameData = new JSONObject(jsonData.toString());

            // Populate the variables from the JSON object
            baseWord = gameData.getString("baseWord");

            foundWords = new ArrayList<>();
            JSONArray foundWordsArray = gameData.getJSONArray("foundWords");
            for (int i = 0; i < foundWordsArray.length(); ++i) {
                foundWords.add(foundWordsArray.getString(i));
            }

            playerPoints = gameData.getInt("playerPoints");
            requiredLetter = gameData.getString("requiredLetter");
            maxPoints = gameData.getInt("maxPoints"); // Retrieve max points
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public String getBaseWord() {
        return baseWord;
    }

    public List<String> getFoundWords() {
        return foundWords;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public String getRequiredLetter() {
        return requiredLetter;
    }

    public int getMaxPoints() {
        return maxPoints; 
    }

    //Setter Functions
    public void setBaseWord(String baseWord) {
        this.baseWord = baseWord;
    }

    public void setFoundWords(List<String> foundWords) {
        this.foundWords = foundWords;
    }

    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
    }

    public void setRequiredLetter(String requiredLetter) {
        this.requiredLetter = requiredLetter;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }
}
