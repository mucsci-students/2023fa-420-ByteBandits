package app.src.main.java;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class highScores {

    public static final String GAME_DATA_FILENAME = "high_scores.json";
    private static final int MAX_ENTRIES_PER_WORD = 10;
    private static boolean newHighScore = false;
    public static List<String> highScores;

    public static boolean saveHighScores(String baseWord, int totalPoints, String userId) {
        try {

            JSONObject allData;

            try (BufferedReader reader = new BufferedReader(new FileReader(GAME_DATA_FILENAME))) {
                String jsonData = reader.readLine();
                if (jsonData != null) {
                    allData = new JSONObject(jsonData);
                } else {
                    allData = new JSONObject();
                }
            } catch (IOException | org.json.JSONException ignored) {
                allData = new JSONObject();
            }

            if (userId == null) {
                return false;
            }

            if (totalPoints == 0) {
                System.out.println("\nYour score was not a high score :(");
                return false;
            }

            JSONObject gameData = new JSONObject();
            gameData.put("score", totalPoints);
            gameData.put("userId", userId);

            JSONArray scoresArray = allData.optJSONArray(baseWord);
            if (scoresArray == null) {
                scoresArray = new JSONArray();
            }

            if (scoresArray.length() >= MAX_ENTRIES_PER_WORD) {
                boolean replaced = replaceLowestScore(scoresArray, gameData);
                newHighScore = true;

                if (!replaced) {
                    System.out.println("\nYour score was not a high score :(");
                    newHighScore = false;
                }
            } else {
                scoresArray.put(gameData);
                newHighScore = true;
            }

            allData.put(baseWord, scoresArray);

            try (FileWriter fileWriter = new FileWriter(GAME_DATA_FILENAME)) {
                fileWriter.write(allData.toString());
                fileWriter.write("\n");
            }
            return newHighScore;
        } catch (IOException | org.json.JSONException e) {
            e.printStackTrace();
        }

        return newHighScore;
    }

    private static boolean replaceLowestScore(JSONArray scoresArray, JSONObject newScore) {
        int lowestScore = Integer.MAX_VALUE;
        int lowestScoreIndex = -1;

        for (int i = 0; i < scoresArray.length(); i++) {
            JSONObject scoreObj = scoresArray.getJSONObject(i);
            int score = scoreObj.getInt("score");

            if (score < lowestScore) {
                lowestScore = score;
                lowestScoreIndex = i;
            }
        }

        if (newScore.getInt("score") > lowestScore && lowestScoreIndex != -1) {
            scoresArray.put(lowestScoreIndex, newScore);
            return true;
        }

        return false;
    }

public static void displayEntriesForBaseWord(String baseWord) {
    try (BufferedReader reader = new BufferedReader(new FileReader(GAME_DATA_FILENAME))) {
        StringBuilder jsonData = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonData.append(line).append("\n");
        }

        JSONObject jsonObject = new JSONObject(jsonData.toString());

        String yellowColor = "\u001B[33m";
        String resetColor = "\u001B[0m";

        if (baseWord.equals("       ")) {
            return;
        }

        if (jsonObject.has(baseWord)) {
            JSONArray entries = jsonObject.getJSONArray(baseWord);
            List<JSONObject> entryList = new ArrayList<>();

            for (int i = 0; i < entries.length(); i++) {
                entryList.add(entries.getJSONObject(i));
            }

            Collections.sort(entryList, Comparator.comparingInt(o -> o.getInt("score")));
            Collections.reverse(entryList);

            System.out.println();
            System.out.printf("%18s\n", "HIGH SCORES");
            System.out.printf("%-10s%16s\n","Name:", "Score:");

            for (int i = 0; i < entryList.size(); i++) {
                JSONObject entry = entryList.get(i);
                String name = entry.getString("userId");
                int score = entry.getInt("score");
                System.out.printf("%d%-10s%12s%s\n", (i + 1), "." + name, score, " pts");
            }
        } else {
            System.out.println("NO HIGH SCORES FOR THIS WORD");
        }
    } catch (IOException | JSONException e) {
        e.printStackTrace();
    }
}

    public static boolean isHighScore(String baseWord, int totalPoints) {
        try (BufferedReader reader = new BufferedReader(new FileReader(GAME_DATA_FILENAME))) {
            StringBuilder jsonData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonData.append(line).append("\n");
            }

            JSONObject allData = new JSONObject(jsonData.toString());

            if (totalPoints == 0) {
                System.out.println("\nYour score was not a high score :(");
                return false;
            }

            if (allData.has(baseWord)) {
                JSONArray scoresArray = allData.getJSONArray(baseWord);

                for (int i = 0; i < scoresArray.length(); i++) {
                    JSONObject scoreObj = scoresArray.getJSONObject(i);
                    int score = scoreObj.getInt("score");

                    if (totalPoints > score || (totalPoints == score && scoresArray.length() != MAX_ENTRIES_PER_WORD)
                            || totalPoints < score) {
                        return true;
                    }
                }
                return false;
            } else {
                return true;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return false;
    }
}
