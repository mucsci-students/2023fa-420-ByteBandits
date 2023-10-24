package app.src.main.java;
//Author: Jose De La Cruz, Logan Wasmer, Ilynd Rapant

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/*********************************************************/
/*********************************************************/

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Map;
import java.util.TreeMap;

/*********************************************************/
/*********************************************************/


public class helpers {
    public static String nextRank = "";
    public static int pointsRequired = 0;
    public static int difference = 0;
 
/*
* isBetween
* param: int val, int min, int max
* returns: Boolean
* This function takes a val param, this param is the one that
* is being tested, the min and max param are the two conditions.
* True will be returned is val is between min and max false otherwise.
*/


public static Boolean isBetween(int val, double min, double max){
 return val >= min && val < max;
}

/*********************************************************/
/*********************************************************/

/*
 * isUnique
 * param: String s
 * returns: Boolean 
 * This function receives a string and tests to
 * see if it has all unique letters and if it
 * does the method returns true, otherwise false.
 */

public static boolean isUnique(String s)
{
    for(int i = 0; i < s.length(); ++i)
    {
        for(int j = i+1; j < s.length(); ++j)
        {
            if(s.charAt(i) == s.charAt(j))
            {
                return false;
            }
        }
    }
    return true;
}

/*********************************************************/
/*********************************************************/

/*
 * getLongestWordLength
 * param: String baseWord, char reqLetter
 * returns: int
 * This function receives the baseword and reqLetter and
 * check is the word is an accepted word then keeps a count of the
 * longest accepted word. This int is then returned.
 */

 public static String dynamicHints(String baseWord, char reqLetter) throws FileNotFoundException {
    List<String> acceptedWordList = acceptedWords(baseWord, reqLetter);
    int longestWordLength = getLongestWordLength(acceptedWordList);
    String[][] hintsMatrix = getDynamicMatrix(acceptedWordList, longestWordLength, baseWord, reqLetter);

    baseWord = baseWord.toUpperCase();
    char[] baseWordChars = baseWord.toCharArray();
    baseWord = baseWord.toLowerCase();

    int totalWords = getTotalWords(baseWord, acceptedWordList);
    int possiblePoints = possiblePoints(baseWord, acceptedWordList);
    int[] totalPangrams = getTotalPangrams(baseWord, acceptedWordList);

    boolean isBingoPuzzle = isBingoPuzzle(hintsMatrix);
    String bingo = "";
    if(isBingoPuzzle){
       bingo = ", BINGO";
    }

    StringBuilder matrixStr = new StringBuilder();
    StringBuilder wordWithBold = new StringBuilder();

    char upperReq = Character.toUpperCase(reqLetter);
    for (char letter : baseWordChars) {
        if (letter == upperReq) {
            wordWithBold.append("<b>").append(letter).append("&nbsp;</b> ");
        } else {
            wordWithBold.append(letter).append("&nbsp;&nbsp;");
        }
    }

    matrixStr.append("<b style=\" font-family: 'Trebuchet MS', sans-serif; font-size: 20px;\">Wordy Wasps Grid</b><br><br><br>");
    matrixStr.append("<span style=\" font-family: Garamond, serif; font-size: 16px;\">Center letter is in <b>bold</b>.</span><br><br>");
    matrixStr.append("<span style=\" font-family: Garamond, serif; font-size: 18px;\">" + wordWithBold + "</span><br><br>");

    matrixStr.append("<span style=\" font-family: Garamond, serif; font-size: 16px;\">WORDS: " + totalWords + ", POINTS: " + possiblePoints + ", PANGRAMS: " + totalPangrams[0] + " (" + totalPangrams[1] + " Perfect)" + bingo + "</span><br><br><br><br>");

    matrixStr.append("<table style=\"border-collapse: collapse;\">"); // Start the table with collapsed borders


    matrixStr.append("<tr>");
    matrixStr.append("<td style=\"font-family: Garamond, serif; font-size: 19px; border: none; padding: 10px;\"></td>"); // Empty first element
    for (int j = 4; j <= longestWordLength; j++) {
        matrixStr.append("<td style=\"font-family: Garamond, serif; font-size: 19px; border: none; padding: 10px;\">");
        matrixStr.append("<b>").append(j).append("</b>");
        matrixStr.append("</td>");
    }

    matrixStr.append("&nbsp;&nbsp;&nbsp;<b style=\" font-size: 14px;\">&#8721</b>");

    int index = 0;
    for (int i = 0; i < hintsMatrix.length; i++) {
        matrixStr.append("<tr>");
        
        if (index < 7) {
            matrixStr.append("<b style=\" font-family: Garamond, serif; font-size: 16px;\">");
            matrixStr.append(baseWordChars[index]).append(": ");
            matrixStr.append("</b>&nbsp;&nbsp;");
            index++;
        } else {
            matrixStr.append("<b style=\" font-size: 12px;\">&#8721 : &nbsp;&nbsp;</b>");
        }
        
        for (int j = 0; j < hintsMatrix[i].length; j++) {
            String cellData = hintsMatrix[i][j];
            if (i == hintsMatrix.length - 1 || j == hintsMatrix[i].length - 1) { 
                matrixStr.append("<td style=\"font-family: Garamond, serif; font-size: 19px; border: none; padding: 10px;\"><b>").append(cellData).append("</b></td>"); // Make the last row and column bold
            } else if (cellData.startsWith("<b>")) {
                matrixStr.append("<td style=\"font-family: Garamond, serif; font-size: 19px; border: none; padding: 10px;\">").append(cellData).append("</td>"); // Keep the existing bold cells
            } else {
                matrixStr.append("<td style=\"font-family: Garamond, serif; font-size: 19px; border: none; padding: 10px;\">").append(cellData).append("</td>"); // For other cells
            }
        }
        matrixStr.append("</tr>"); 
    }

    matrixStr.append("</table><br>" + getDynamicTwoLetterList(acceptedWordList, baseWord));


    return matrixStr.toString();
}



/*********************************************************/
/*********************************************************/

/*
 * getDynamicMatrix
 * param: String baseWord, char reqLetter
 * returns: String[][]
 * This function creates the matrix of numbers representing
 * the amount of words in the accepted word list based on the
 * letter it starts with as well as the count of how
 * many letters in that word including the sums total.
 */

public static String[][] getDynamicMatrix(List<String> acceptedWordList, int longestWordLength, String baseWord, char reqLetter) {
    int numRows = baseWord.length();
    int numCols = longestWordLength - 3;

    String[][] matrix = new String[numRows + 1][numCols + 1];
    char[] baseWordLetters = baseWord.toCharArray();

    matrix[0][0] = "<b>4</b>";
    for (int j = 1; j <= numCols; j++) {
        matrix[0][j] = "<b>" + (j + 3) + "</b>";
    }

    int[] rowSums = new int[numRows];
    int[] colSums = new int[numCols];

    for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
            int count = 0;
            for (String word : acceptedWordList) {
                if (word.startsWith(String.valueOf(baseWordLetters[i])) && word.length() == j + 4) {
                    count++;
                }
            }
            matrix[i][j] = (count == 0) ? "-" : String.valueOf(count);

            rowSums[i] += count;
            colSums[j] += count;
        }
    }

    for (int i = 0; i < numRows; i++) {
        matrix[i][numCols] = String.valueOf(rowSums[i]);
    }

    for (int j = 0; j < numCols; j++) {
        matrix[numRows][j] = String.valueOf(colSums[j]);
    }

    int totalSum = 0;
    for (int i = 0; i < numRows; i++) {
        totalSum += rowSums[i];
    }
    matrix[numRows][numCols] = "<b><i>" + String.valueOf(totalSum) + "</i></b>";

    return matrix;
}



/*********************************************************/
/*********************************************************/

/*
 * getDynamicTwoLetterList
 * param: String baseWord, char reqLetter
 * returns: String
 * This function looks at every possible accepted word 
 * and gets the first two letters of each. It categorizes them 
 * and counts their appearences in the acceptedWords List.
 */

public static String getDynamicTwoLetterList(List<String> acceptedWords, String baseWord) {
    Map<String, Integer> pairCountMap = new HashMap<>();

    for (String str : acceptedWords) {
        if (str.length() >= 2) {
            String pair = str.substring(0, 2);
            pairCountMap.put(pair, pairCountMap.getOrDefault(pair, 0) + 1);
        }
    }

    StringBuilder html = new StringBuilder();
    html.append("<b style=\" font-family: Garamond, serif; font-size: 20px;\">Two letter list:<br></b>");
    char prevBaseChar = 0; 
    for (int i = 0; i < baseWord.length(); i++) {
        char baseChar = baseWord.charAt(i);
        if (prevBaseChar != baseChar) {
            html.append("<br>");
        }

        for (Map.Entry<String, Integer> entry : pairCountMap.entrySet()) {
            if (entry.getKey().charAt(0) == baseChar) {
                html.append("<span style=\" font-family: Garamond, serif; font-size: 18px;\">" + entry.getKey().toUpperCase()).append("-").append(entry.getValue()).append("&nbsp;&nbsp;</span>");
            }
        }
        prevBaseChar = baseChar;
    }

    return html.toString();
}


/*********************************************************/
/*********************************************************/

/*
 * findReqLetter
 * param: String baseWord, char reqLetter
 * returns: int
 * This function finds and returns the position of the reqLetter
 * in the baseword. Returns -1 if the letter given is not in 
 * baseword.
 */

public static int findReqLetter(String baseWord, char reqLetter){
    char[] baseWordChars = baseWord.toCharArray();
    for(int i = 0; i < 7; i++){
        if (baseWordChars[i] == reqLetter){
            return i;
        }
    }
    return -1;
}

/*********************************************************/
/*********************************************************/

/*
 * getTotalWords
 * param: String baseWord, List<String> acceptedWords
 * returns: int
 * This function simply looks at the acceptedWordList of all
 * possible correct words in a puzzle and counts how many words
 * there are.
 */

public static int getTotalWords(String baseWord, List<String> acceptedWords){
    int count = 0;
    for(String s : acceptedWords){
        count++;
    }
    return count;
}

/*********************************************************/
/*********************************************************/

/*
 * getTotalPangrams
 * param: String baseWord, List<String> acceptedWords
 * returns: int[] containing int[0]: totalPangrams and int[1]: totalPerfect pangrams.
 * This function simply looks at the acceptedWordList of all
 * possible correct words in a puzzle and counts how many pangrams
 * there are.
 */

public static int[] getTotalPangrams(String baseWord, List<String> acceptedWords){
    int[] count = {0, 0};
    
    for(String s : acceptedWords){
        if(isPangram(s, baseWord)){
            count[0] = count[0] + 1;
        }
        if(isPerfectPangram(s, baseWord)){
            count[1] = count[1] + 1;
        }
    }
    return count;
}

/*********************************************************/
/*********************************************************/

/*
 * getLongestWordLength
 * param: String baseWord, char reqLetter
 * returns: int
 * This function receives the baseword and reqLetter and
 * check is the word is an accepted word then keeps a count of the
 * longest accepted word. This int is then returned.
 */

public static int getLongestWordLength(List<String> acceptedWordList){
    String lastString = acceptedWordList.get(acceptedWordList.size() - 1);

    return lastString.length();
}

/*********************************************************/
/*********************************************************/

/*
 * isBingoPuzzle
 * param: String[][] matrix
 * returns: boolean
 * This function receives the matrix of word counts. It then will check every row 
 * of the last column exept for the first and last row (as they are headings and sums).
 * It checks those numbers and if any of them is <1 then it is not a BINGO puzzle and returns false,
 * otherwise it is and returns true.
 */

 public static boolean isBingoPuzzle(String[][] matrix) {
    int lastColumnIndex = matrix[0].length - 1;

    for (int i = 1; i < matrix.length - 1; i++) {
        String value = matrix[i][lastColumnIndex];
        try {
            int numericValue = Integer.parseInt(value);
            if (numericValue < 1) {
                return false; 
            }
        } catch (NumberFormatException e) {
            return false; 
        }
    }

    return true; 
}

/*********************************************************/
/*********************************************************/

/*
 * isPangram
 * param: String baseWord, String userGuess
 * returns: Boolean 
 * This function takes the base word and a string from the user's
 * guess and tests if it is a pangram or not using baseword as the 
 * letters to check it against. Hashsets are used to split the
 * characters up and compare them.
 */

public static boolean isPangram(String userGuess, String baseWord) {
    Set<Character> userGuessChars = new HashSet<>();
    for (char c : userGuess.toCharArray()) {
        if (Character.isAlphabetic(c)) {
            userGuessChars.add(Character.toLowerCase(c)); 
        }
    }

    for (char c : baseWord.toCharArray()) {
        if (Character.isAlphabetic(c)) {
            if (!userGuessChars.contains(Character.toLowerCase(c))) {
                return false; 
            }
        }
    }

    return true;
}

/*********************************************************/
/*********************************************************/

/*
 * isPerfectPangram
 * param: String userGuess, String baseWord
 * returns: Boolean 
 * This function takes the base word and a string from the user's
 * guess and tests if it is a perfect pangram or not using baseword as the 
 * letters to check it against. Hashsets are used to split the
 * characters up and compare them.
 */

public static boolean isPerfectPangram(String userGuess, String baseWord) {
    if(userGuess.length() != baseWord.length()){
        return false;
    }

    Set<Character> userGuessChars = new HashSet<>();
    for (char c : userGuess.toCharArray()) {
        if (Character.isAlphabetic(c)) {
            userGuessChars.add(Character.toLowerCase(c)); 
        }
    }

    for (char c : baseWord.toCharArray()) {
        if (Character.isAlphabetic(c)) {
            if (!userGuessChars.contains(Character.toLowerCase(c))) {
                return false; 
            }
        }
    }

    return true;
}

/*********************************************************/
/*********************************************************/

/*
 * sameChars
 * param: String baseWord, String dicString
 * returns: Boolean 
 * This function takes the base word and a string from the dictionary.
 * It then converts base word to a set of chars and checks if all chars
 * in dicString are in baseWord. If it finds so much as 1 that isnt in baseWord
 * it returns false. If the tests all pass then it returns true.
 */

public static boolean sameChars(String baseWord, String dicString) {
    Set<Character> baseWordSet = new HashSet<>();
    for (char c : baseWord.toCharArray()) {
        baseWordSet.add(c);
    }

    for (char c : dicString.toCharArray()) {
        if (!baseWordSet.contains(c)) {
            return false; 
        }
    }

    return true; 
}

/*********************************************************/
/*********************************************************/

/*
 * possiblePoints
 * param: String baseWord, List<String> possibleWords
 * returns: int
 * This helper function is used to determine the possible
 * amount of points based on the base-word. The function has
 * special cases for four-letter words and pangrams.
 */

 public static int possiblePoints(String baseWord, List<String> possibleWords){
    
    int posPoints = 0;
    
    for (int i = 0; i < possibleWords.size(); i++){
        
        if(possibleWords.get(i).length() == 4){
            posPoints += 1;
        }
        else{
            posPoints += possibleWords.get(i).length();
            if (posPoints >= 7 && sameChars(baseWord, possibleWords.get(i))){
                posPoints += 7;
            }
        }
    }
    
    return posPoints;
}

/*********************************************************/
/*********************************************************/

 /*
 * removeChar
 * param: String current, char remove
 * returns: String
 * This is a helper function for display. It gets rid of the 
 * required letter from the baseword.
 */

 public static String removeChar(String current, char remove)
 {
     StringBuilder builder = new StringBuilder();

     for (char c : current.toCharArray())
     {
         if (c != remove)
         {
             builder.append(c);
         }
     }
     return builder.toString();
 }

/*********************************************************/
/*********************************************************/

 /*
 * calculateRankDifference
 * param: String currentRank, int playerPoints, List<String> possibleWords, String baseword
 * returns: N/A
 * This is a helper function for guess. It calculates and shows
 * the differences between the current and next rank.
 */
 
 public static void calculateRankDifference(String currentRank, int playerPoints, List<String> possibleWords, String baseWord) {
    nextRank = "";
   pointsRequired = 0;

    int currentRankIndex = Arrays.asList("Beginner", "Good Start", "Moving Up", "Good", "Solid", "Nice", "Great", "Amazing", "Genius", "Queen Bee").indexOf(currentRank);

    if (currentRankIndex >= 0) {
        switch (currentRankIndex) {
            case 0:
                nextRank = "Good Start";
                pointsRequired = (int) (0.02 * possiblePoints(baseWord, possibleWords));
                break;
            case 1:
                nextRank = "Moving Up";
                pointsRequired = (int) (0.05 * possiblePoints(baseWord, possibleWords));
                break;
            case 2:
                nextRank = "Good";
                pointsRequired = (int) (0.08 * possiblePoints(baseWord, possibleWords));
                break;
            case 3:
                nextRank = "Solid";
                pointsRequired = (int) (0.15 * possiblePoints(baseWord, possibleWords));
                break;
            case 4:
                nextRank = "Nice";
                pointsRequired = (int) (0.25 * possiblePoints(baseWord, possibleWords));
                break;
            case 5:
                nextRank = "Great";
                pointsRequired = (int) (0.40 * possiblePoints(baseWord, possibleWords));
                break;
            case 6:
                nextRank = "Amazing";
                pointsRequired = (int) (0.50 * possiblePoints(baseWord, possibleWords));
                break;
            case 7:
                nextRank = "Genius";
                pointsRequired = (int) (0.70 * possiblePoints(baseWord, possibleWords));
                break;
            case 8:
                nextRank = "Queen Bee";
                pointsRequired = (int) (1.0 * possiblePoints(baseWord, possibleWords));
                break;
            default:
                System.out.println("Invalid current rank");
                return;
        }

        difference = pointsRequired - playerPoints;

        System.out.println("TOTAL POINTS NEEDED FOR NEXT RANK " + "\u001B[33m" + nextRank + "\u001B[0m" + ": " + "\u001B[33m" + pointsRequired + "\u001B[0m");
        System.out.println("POINTS NEEDED TO REACH NEXT RANK: " + "\u001B[33m" + difference + "\u001B[0m\n");
    } else {
        System.out.println("Invalid current rank");
    }
}
/*********************************************************/
/*********************************************************/

/*
 * acceptedWords
 * param: String baseWord, char reqLetter
 * returns: List<String>
 * This function scans through each string in the dictionary
 * and makes sure each char in that string is also included 
 * in baseWord as well as making sure it inlcudes the reqLetter.
 * It then adds any string that passes the test into the List<String>
 * and then finally returns it. 
 */
public static List<String> acceptedWords(String baseWord, char reqLetter) throws FileNotFoundException{
    Scanner scanner = new Scanner(new File("./src/main/resources/4-15_Dictionary.txt"));
    List<String> acceptedWordList = new ArrayList<>();
    String reqLetter2 = Character.toString(reqLetter);
    
    String sNL = scanner.nextLine();
    while(scanner.hasNextLine()){
        if(sameChars(baseWord, sNL) && sNL.contains(reqLetter2)){
            acceptedWordList.add(sNL);
        }

        sNL = scanner.nextLine();  
    }

    return acceptedWordList;
}

}




    




