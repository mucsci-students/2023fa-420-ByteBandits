// Authors: Logan Wasmer, Jose De La Cruz, Ilynd Rapant, Joshua Dawson

/**********************************************************/
/**********************************************************/

//Imports
import java.io.*;
import java.util.*;


/**********************************************************/
/**********************************************************/

public class master extends helpers{
    static int totalPoints = 0;
public static void main(String args[]) throws FileNotFoundException{
    
    playerData saveFile = new playerData();
    String baseWord = "planets";
    char reqLetter = 'n';   
    List<String> acceptedWordList = acceptedWords(baseWord, reqLetter);

}

/**********************************************************/
/**********************************************************/

/*
 * dictionaryFile
 * param: N/A
 * returns: List<String> 
 * This function scans the given dictionary file
 * of seven letter words and creates an arraylist of strings
 * of all the words on the file that have all unique letters.
 */

private static List<String> dictionaryFile() throws FileNotFoundException{
    Scanner scanner = new Scanner(new File("7-letter-words.txt"));
    List<String> sevenLetterWords = new ArrayList<>();
    int i = 0;
    while(scanner.hasNextLine()){
        var a = scanner.nextLine();
        if(isUnique(a)){
            sevenLetterWords.add(a);
            i++;
        }
    } 
    return sevenLetterWords;
}

/**********************************************************/
/**********************************************************/

/*
 * getBaseWord
 * param: List<String> dictionary
 * returns: String 
 * This function receives the string array from
 * dictionaryFile and randomly chooses a word
 * from the array using indexing. This word
 * is then returned and acts as our base word.
 */

private static String getBaseWord(List<String> dictionary) throws FileNotFoundException{
    Random rand = new Random();
    int upperBound = dictionaryFile().size();
    int randomInt = rand.nextInt(upperBound);
    
    return dictionary.get(randomInt);
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

private static char getReqLetter(String baseWord){
    Random rand = new Random();
    int upperBound = 7;
    int randomInt = rand.nextInt(upperBound);

    return baseWord.charAt(randomInt);
}

/**********************************************************/
/**********************************************************/

/*
 * pointsPWord
 * param: String baseWord, String userGuess
 * returns: int 
 * This function recieves two Strings as parameters,
 * the length of the string is then retrived. After 
 * the amount of points is determined by the length,
 * it will return that point value.
 */

private static int pointsPWord(String baseWord, String userGuess){
    int length = userGuess.length();

    int points = 0;

    switch(length){
        case 4:
        points = 1;
        System.out.println("Good! +1 pt");
        break;
        default:
        points = length;
        if (length == 5 || length == 6){

            System.out.println("Great! +" + points + " pts");
        }
        else{
            
            if (sameChars(baseWord, userGuess)){
                points += 7;
            }

            System.out.println("Awesome! +" + points + " pts");
        }
        break;
    }

    return points;
}

/*********************************************************/
/*********************************************************/

/*
 * playerRank
 * param: String baseWord, int playerPoints, List<String> possibleWords
 * returns: String
 * This function receives an int as a parameter, the int value
 * is used to determine the rank of the player. 
 */

private static String playerRank(String baseWord, int playerPoints, List<String> possiblewords){

    int posPoints = possiblePoints(baseWord,possiblewords);

    double goodStart = 0.02 * posPoints;
    double movingUp = 0.05 * posPoints;
    double good = 0.08 * posPoints;
    double solid = 0.15 * posPoints;
    double nice = 0.25 * posPoints;
    double great = 0.40 * posPoints;
    double amazing = 0.50 * posPoints;
    double genuis = 0.70 * posPoints;
    double queenBee = 1 * posPoints;
    
    String playerRank = "";

    if (playerPoints < goodStart){
        playerRank = "Beginner";
    }
    if (isBetween(playerPoints, goodStart, movingUp)){
        playerRank = "Good Start";
    }
    if (isBetween(playerPoints, movingUp, good)){
        playerRank = "Moving Up";
    }
    if(isBetween(playerPoints, good, solid)){
        playerRank = "Good";
    }
    if(isBetween(playerPoints, solid, nice)){
        playerRank = "Solid";
    }
    if(isBetween(playerPoints, nice, great)){
        playerRank = "Nice";
    }
    if(isBetween(playerPoints, great, amazing)){
        playerRank = "great";
    }
    if(isBetween(playerPoints, amazing, genuis)){
        playerRank = "Amazing";
    }
    if(isBetween(playerPoints, genuis, queenBee)){
        playerRank = "Genius";
    }
    if(playerPoints == queenBee ){
        playerRank = "Queen Bee";
    }

    return playerRank;
}

/*********************************************************/
/*********************************************************/
  
 /*
 * help
 * param: N/A
 * returns: String
 * This function tells the player the rules of the game and shows
 * a table of all the command lines the player can use and what 
 * each command line does. 
 */
  
private static void help()
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
 * display
 * param: String baseword, char required
 * returns: nothing
 * This function creates a cool display for the puzzle.
 */

private static void display(String baseword, char required)
{
    String result = removeChar(baseword, required);
    
    char[] charArray = result.toCharArray(); 

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
 * shuffle
 * param: String curr, char required
 * returns: N/A
 * This function shuffles the letters of a current puzzle.
 */
  
private static void shuffle (String curr, char required)
{

    char[] charArray = curr.toCharArray();

    Random rand = new Random();

    for (int i = charArray.length - 1; i > 0; i--)
    {
        int j = rand.nextInt(i + 1);

        char temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
    }

    String shuffled = new String(charArray);

    display (shuffled, required);
    
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

private static List<String> acceptedWords(String baseWord, char reqLetter) throws FileNotFoundException{
    Scanner scanner = new Scanner(new File("4-15_Dictionary.txt"));
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

/*********************************************************/
/*********************************************************/

/*
 * guess
 * param: String baseWord, acceptedWords 
 * returns: int
 * This function is invoked via a command from the user.
 * When a user is in this function, they will keep making
 * guesses until they invoke to stop guessing.
 * The total points are then returned from this
 * session of guesses.
 */

private static List<String> guess(String baseWord, List<String> acceptedWords){
    List<String> foundWords = new ArrayList<>();
    
    Scanner guessedWord = new Scanner(System.in);
    
    while(true){
        System.out.print("Guess a word: ");
        
        String validWord = guessedWord.nextLine();
        
        if(validWord.equals("/quit")){
            guessedWord.close();
            break;
        }

        if(acceptedWords.contains(validWord)){
            
            foundWords.add(validWord);
            totalPoints += pointsPWord(baseWord, validWord);
        }else{
            System.out.println("\nNot a valid word, try again!\n");
        }
        
    }
    
    return foundWords;

}

/*********************************************************/
/*********************************************************/

/*
 * puzzleStatus
 * param: String playerRank
 * returns: N/A
 * This function is used to display the rank and player points
 * of the pllayer during the current session.
 */

private static void puzzleStatus (String playerRank){

    System.out.println("YOUR CURRENT RANK: " + playerRank);
    System.out.println("YOUR CURRENT POINTS: " + totalPoints);

}

/*********************************************************/
/*********************************************************/

/*
 * foundWordList
 * param: N/A
 * returns: N/A
 * This function is used to display the rank and player points
 * of the pllayer during the current session.
 */

private static void foundWordList (){
    
    String yellowColor = "\u001B[33m";

    String resetColor = "\u001B[0m";

    System.out.printf("%-2sFOUND WORD LIST%n", ""); 
    
    for (int i = 0; i <= 18; i++){
        System.out.print(yellowColor + "*" + resetColor);
    }

    System.out.println();

    for(int j = 0; j < foundWords.size(); j++){
        System.out.printf(yellowColor + "* " + resetColor + "%-16s" + yellowColor + "*%n", foundWords.get(j));
    }

     for (int k = 0; k <= 18; k++){
        System.out.print(yellowColor + "*" + resetColor);
    }

}

}



