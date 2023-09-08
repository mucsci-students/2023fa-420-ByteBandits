// Authors: Logan Wasmer, Jose De La Cruz, Ilynd Rapant, Joshua Dawson

/**********************************************************/
/**********************************************************/

//Imports
import java.io.*;
import java.util.*;
import java.util.Random;

/**********************************************************/
/**********************************************************/

public class master extends helpers{
public static void main(String args[]) throws FileNotFoundException{
    playerData saveFile = new playerData();
    String baseWord = getBaseWord(dictionaryFile());
    char reqLetter = getReqLetter(baseWord);
}

/**********************************************************/
/**********************************************************/

/*
 * dictionaryFile
 * param: N/A
 * returns: String[] 
 * This function scans the given dictionary file
 * of seven letter words and creates a string array
 * of all the words on the file.
 */

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

/*
 * getBaseWord
 * param: String[] dictionary
 * returns: String 
 * This function receives the string array from
 * dictionaryFile and randomly chooses a word
 * from the array using indexing. This word
 * is then returned and acts as our base word.
 */

public static String getBaseWord(String[] dictionary){
    Random rand = new Random();
    int upperBound = 25250;
    int randomInt = rand.nextInt(upperBound);
    
    return dictionary[randomInt];
}

/**********************************************************/
/**********************************************************/

/*
 * getReqLetters
 * param: String baseWord
 * returns: char
 * This function receives the string from 
 * getBaseWord and randomly chooses a character
 * from the base word given using indexing. 
 * This character is returned and acts as 
 * our required character.
 */

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
 * the amount of points is determined be the length
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
 * Rank
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
  
 /*
 * help
 * param: nothing
 * returns: String
 * This function tells the player the rules of the game and shows
 * a table of all the command lines the player can use and what 
 * each command line does. 
 */
  
public static void help()
{

   String [] commandLines = {
      "New Puzzle",
      "Base Puzzle",
      "Show Puzzle",
      "Found Words",
      "Guess",
      "Shuffle",
      "Save Puzzle",
      "Save Current",
      "Load Puzzle",
      "Show Status",
      "Exit"
   };
   String [] explanations = {
      "Generates a new puzzle with 7 unique letters and a required letter",
      "Generates a new puzzle with a word of the player's choice with 7 unique letters and a required letter",
      "Shows the current puzzle the player is working on",
      "Generates a list of words that the player has found ",
      "Allows the player to guess their words",
      "Allows the player to shuffle around the words",
      "Lets the player save a blank puzzle",
      "Lets the players save a puzzle that may have been partially played",
      "The player can load a saved game",
      "The player can see their rank and progress on a current puzzle",
      "Leave the application"
   };
   System.out.println("The Spelling Bee game allows players to create words using 7 unique letters with a required letter. ");
   System.out.println("- Words must contain at least 4 letters");
   System.out.println("- Words must include the required letter");
   System.out.println("- Letters can be used more than once");
   System.out.println("");
   System.out.println("Command Line   |   Explanation");

        System.out.println("---------------------------------------");
        for (int i = 0; i < commandLines.length; i++) {
          System.out.printf("%-15s |   %s%n", commandLines[i], explanations[i]);
        }
}
  
/*********************************************************/
/*********************************************************/

/*
 * exit
 * param: nothing
 * returns: exit status code
 * This function forcibly exits out of the game
 * when the player invokes it. 
 */

public static void exit()
{
   System.exit(0);
}

/*********************************************************/
/*********************************************************/

/*
 * display
 * param: String baseword
 * param: char required
 * returns: nothiing
 * This function creates a cool display for the puzzle
 */

public static void display(String baseword, char required)
{
    //Remove the required character from the baseword
    String result = removeChar(baseword, required);
    
    
    //Convert the result (shuffled characters without the required word)
    char[] charArray = result.toCharArray(); 

    //Display the characters in a specific format
    System.out.println("   -----");
    System.out.print(" / ");
    for (int i = 0; i < 3; i++) {
        System.out.print(charArray[i] + " ");
    }
    System.out.print( "\\");
    System.out.println();
    System.out.println("||   " + required + "   ||");
    System.out.print(" \\ ");
    for (int i = 3; i < 6; i++) {
        System.out.print(charArray[i] + " ");
    }
    System.out.println("/");
    System.out.println("   -----");
}

/*********************************************************/
/*********************************************************/
 /*
 * removeChar
 * param: String current
 * param: char remove
 * returns: String
 * This is a helper function for display.  It gets rid of the 
 * required letter from the baseword.
 */

 private static String removeChar(String current, char remove)
 {
     //Create a StringBuilder to construct the new string without the required letter
     StringBuilder builder = new StringBuilder();

     //Iterate through each character in the input string
     for (char c : current.toCharArray())
     {
         //Check if the current character is not equal to the character to be removed
         if (c != remove)
         {
             //Append the character to the StringBuilder if it's not the character to  be removed
             builder.append(c);
         }
     }
     //Convert the StringBuilder to a string and return the updated string
     return builder.toString();
 }
    
/*********************************************************/
/*********************************************************/

/*
 * shuffle
 * param: String curr
 * param: char required
 * returns: nothing
 * This function shuffles the letters of a current puzzle 
 */
  
public static void shuffle (String curr, char required)
{
    //Convert the input string to a character array
    char[] charArray = curr.toCharArray();

    //Create a random number generator
    Random rand = new Random();

    //Loop through the character array for shuffling
    for (int i = charArray.length - 1; i > 0; i--)
    {
        //Generate a random index between 0 and i
        int j = rand.nextInt(i + 1);

        //Swap the characters at positions i and j
        char temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
    }
    //Convert the shuffled character array back to a string 
    String shuffled = new String(charArray);
    
    //This will display the shuffled word in the format we like
    //display (shuffled, required);
    //commenting it out until I have display merged with master
}
  
/*********************************************************/
/*********************************************************/
  
/* 
* getCurrent
* param: String baseword
* param: char required
* returns: a nice display of current puzzle
* This function gets the current word of the puzzle and 
* displays it in a nice way 
*/
  
public static void getCurrent(String baseword, char required)
{
    display (baseword, required);
}
  
}



