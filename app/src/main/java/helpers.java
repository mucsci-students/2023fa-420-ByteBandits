package app.src.main.java;
//Author: Jose De La Cruz, Logan Wasmer, Ilynd Rapant

/*********************************************************/
/*********************************************************/

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * isPangram
 * param: String baseWord, String userGuess
 * returns: Boolean 
 * This function takes the base word and a string from the user's
 * guess and tests if it is a pangram or not using baseword as the 
 * letters to check it against. Hashsets are used to split the
 * characters up and compare them.
 */

public static boolean isPangram(String baseWord, String userGuess) {
    Set<Character> baseWordChars = new HashSet<>();
    for (char c : baseWord.toCharArray()) {
        if (Character.isAlphabetic(c)) {
            baseWordChars.add(Character.toLowerCase(c)); // Convert to lowercase for case-insensitive comparison
        }
    }

    for (char c : userGuess.toCharArray()) {
        if (Character.isAlphabetic(c)) {
            if (!baseWordChars.contains(Character.toLowerCase(c))) {
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

}




    



