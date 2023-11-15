package app.src.main.java;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class playerData {
    private String baseWord;
    private List<String> foundWords = new ArrayList<>();
    private int playerPoints;
    private String requiredLetter;
    private int maxPoints;
    private String format;

    private static final String GAME_DATA_FILENAME = "game_data.json";

    public void saveGameData(String saveName, String baseWord, String format, List<String> foundWords, int playerPoints, String requiredLetter, int maxPoints) {
        try {
            JSONObject allData = new JSONObject();
            try (BufferedReader reader = new BufferedReader(new FileReader(GAME_DATA_FILENAME))) {
                allData = new JSONObject(reader.readLine());
            } catch (IOException | JSONException ignored) { }

            JSONObject gameData = new JSONObject();
            gameData.put("baseWord", baseWord);
            gameData.put("format", format);
            gameData.put("foundWords", foundWords);
            gameData.put("playerPoints", playerPoints);
            gameData.put("requiredLetter", requiredLetter);
            gameData.put("maxPoints", maxPoints);

            allData.put(saveName, gameData);
            
            try (FileWriter fileWriter = new FileWriter(GAME_DATA_FILENAME)) {
                fileWriter.write(allData.toString());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void loadGameData(String saveName) {
        try {
            JSONObject allData = new JSONObject();
            try (BufferedReader reader = new BufferedReader(new FileReader(GAME_DATA_FILENAME))) {
                allData = new JSONObject(reader.readLine());
            }

            if (!allData.has(saveName)) {
                throw new JSONException("Save not found: " + saveName);
            }

            JSONObject gameData = allData.getJSONObject(saveName);
            
            format = gameData.getString("format");
            baseWord = gameData.getString("baseWord");
            foundWords = new ArrayList<>();
            JSONArray foundWordsArray = gameData.getJSONArray("foundWords");
            for (int i = 0; i < foundWordsArray.length(); ++i) {
                foundWords.add(foundWordsArray.getString(i));
            }
            playerPoints = gameData.getInt("playerPoints");
            requiredLetter = gameData.getString("requiredLetter");
            maxPoints = gameData.getInt("maxPoints");

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllSaveNames() {
        List<String> saveNames = new ArrayList<>();
        try {
            JSONObject allData = new JSONObject();
            try (BufferedReader reader = new BufferedReader(new FileReader(GAME_DATA_FILENAME))) {
                allData = new JSONObject(reader.readLine());
            }
            saveNames.addAll(allData.keySet());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return saveNames;
    }

    public String getBaseWord() {
        return baseWord;
    }
    
    public String getFormat() {
        return format;
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
        this.format = baseWord;
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
