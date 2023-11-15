package app.src.main.java;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class playerData {
    private String baseWord;
    private List<String> foundWords = new ArrayList<>();
    private int playerPoints;
    private String requiredLetter;
    private int maxPoints;
    private List<String> wordList = new ArrayList<>();
    private String author; 

    private static final String GAME_DATA_FILENAME = "game_data.json";
    private static final SecretKey SECRET_KEY = new SecretKeySpec("zbUe3kVDRm5aZeKO".getBytes(), "AES");

    private String encrypt(String data) throws Exception {
        //System.out.println("Debug: Starting encryption");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        }

    private String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(original);
    }

    public void saveGameData(String saveName, String baseWord, List<String> foundWords, int playerPoints, String requiredLetter, int maxPoints, String author, List<String> wordList, boolean encrypt) throws Exception {
        JSONObject allData;
        File file = new File(GAME_DATA_FILENAME);
    
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String content = reader.readLine();
                allData = content != null && !content.isEmpty() ? new JSONObject(content) : new JSONObject();
            } catch (IOException | JSONException e) {
                allData = new JSONObject(); // In case of an error, start with an empty JSONObject
            }
        } else {
            allData = new JSONObject(); // Start with an empty JSONObject if the file doesn't exist
        }

            JSONObject gameData = new JSONObject();
            gameData.put("baseWord", baseWord);
            gameData.put("foundWords", foundWords);
            gameData.put("playerPoints", playerPoints);
            gameData.put("requiredLetter", requiredLetter);
            gameData.put("maxPoints", maxPoints);
            gameData.put("author", author); 
            gameData.put("wordList", new JSONArray(wordList)); 
            //System.out.println("Debug: gameData content before saving - " + gameData.toString());

            if (encrypt) {
                try {
                    String encryptedData = encrypt(gameData.toString());
                    allData.put(saveName, encryptedData);
                } catch (Exception e) {
                    e.printStackTrace(); // Print the full stack trace
                }
            } else {
                allData.put(saveName, gameData);
            }
            
            try (FileWriter fileWriter = new FileWriter(GAME_DATA_FILENAME)) {
                //System.out.println("Debug: allData content being written to file - " + allData.toString());
                fileWriter.write(allData.toString());
            }
        } 


    public void loadGameData(String saveName) {
        try {
            JSONObject allData = new JSONObject();
            try (BufferedReader reader = new BufferedReader(new FileReader(GAME_DATA_FILENAME))) {
                String line = reader.readLine();
                if (line != null) {
                    allData = new JSONObject(line);
                }
            }
    
            if (!allData.has(saveName)) {
                throw new JSONException("Save not found: " + saveName);
            }
    
            Object gameDataObj = allData.get(saveName);
    
            if (gameDataObj instanceof String) {
                // Data is encrypted, decrypt it
                String decryptedData = decrypt((String) gameDataObj);
                gameDataObj = new JSONObject(decryptedData);
            }
    
            if (gameDataObj instanceof JSONObject) {
                JSONObject gameData = (JSONObject) gameDataObj;
                baseWord = gameData.getString("baseWord");
                foundWords = new ArrayList<>();
                JSONArray foundWordsArray = gameData.getJSONArray("foundWords");
                for (int i = 0; i < foundWordsArray.length(); ++i) {
                    foundWords.add(foundWordsArray.getString(i));
                }
                playerPoints = gameData.getInt("playerPoints");
                requiredLetter = gameData.getString("requiredLetter");
                maxPoints = gameData.getInt("maxPoints");
            }
    
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error during decryption: " + e.getMessage());
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

    public String getAuthor(){
        return author;
    }

    public List<String> getWordList(){
        return wordList;
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

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setWordList(List<String> wordList){
        this.wordList = wordList;
    }
}
