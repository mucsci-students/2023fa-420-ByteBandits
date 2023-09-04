// Authors: Logan Wasmer, Jose De La Cruz, 

/**********************************************************/
/**********************************************************/

//Imports
import java.io.*;
import java.util.*;

/**********************************************************/
/**********************************************************/

public class master extends helpers{

public static void main(String args[]) throws FileNotFoundException{ 
    String baseWord = getBaseWord(dictionaryFile());
    char reqLetter = getReqLetter(baseWord);
    
}

/**********************************************************/
/**********************************************************/

public static String[] dictionaryFile() throws FileNotFoundException{
    Scanner scanner = new Scanner(new File("7-letter-words.txt"));
    String[] sevenLetterWords = new String[25251];
    int i = 0;
    while(scanner.hasNext()){
        sevenLetterWords[i] = scanner.nextLine();
        i++;
    } 
    return sevenLetterWords;
}

/**********************************************************/
/**********************************************************/

public static String getBaseWord(String[] dictionary){
    Random rand = new Random();
    int upperBound = 25250;
    int randomInt = rand.nextInt(upperBound);
    
    return dictionary[randomInt];
}

/**********************************************************/
/**********************************************************/

public static char getReqLetter(String baseWord){
    Random rand = new Random();
    int upperBound = 7;
    int randomInt = rand.nextInt(upperBound);

    return baseWord.charAt(randomInt);
}

/**********************************************************/
/**********************************************************/

/*
 * pointsPWord
 * param: String userGuess
 * returns: int 
 * This function recieves a String as a parameter,
 * the length of the string is then retrived. After 
 * the amount of pointes is determined be the length
 * it will return that point value.
 */

private static int pointsPWord(String userGuess){
    int length = userGuess.length();

    int points = 0;

    switch(length){
        case 4:
        points = 2;
        break;
        case 5:
        points = 5;
        break;
        case 6:
        points = 7;
        break;
        case 7:
        points = 9;
        break;
        default:
        points = 10;
        break;
    }
    
    return points;
}

/*********************************************************/
/*********************************************************/

/*
 * playerRank
 * param: int playerPoints
 * returns: String
 * This function receives an int as a parameter, the int value
 * is used to determine the rank of the player. 
 */

private static String playerRank(int playerPoints){

    String playerRank = "";

    if (isBetween(playerPoints, 0, 99)){
        playerRank += "Novice";
    }
    if (isBetween(playerPoints, 100, 199)){
        playerRank += "Amateur";
    }
    if (isBetween(playerPoints, 200, 299)){
        playerRank += "Expert";
    }
    if(isBetween(playerPoints, 300, 399)){
        playerRank += "Master";
    }
    if(isBetween(playerPoints, 400, 499)){
        playerRank += "Legend";
    }
    if(playerPoints >= 500){
        playerRank += "Immortal";
    }

    return playerRank;
}

/*********************************************************/
/*********************************************************/










































}