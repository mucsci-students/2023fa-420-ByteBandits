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
public static void main(String args[]) throws FileNotFoundException, InterruptedException{
    
    playerData saveFile = new playerData();
    String baseWord = getBaseWord(dictionaryFile());
    char reqLetter = getReqLetter(baseWord);
    List<String> acceptedWordList = acceptedWords(baseWord, reqLetter);
    
    intro();
    Scanner inputScanner = new Scanner(System.in);
    String input;
    //------------------------------------------------------//
    //LOGIC FOR COMMAND INPUT
    do
    {
        System.out.print("\u001B[33m" + ">" + "\u001B[0m"); 
        input = inputScanner.nextLine();
        switch (input.toLowerCase())
        {
            case "/newpuzzle":
                baseWord = getBaseWord(dictionaryFile());
                reqLetter = getReqLetter(baseWord);
                acceptedWordList = acceptedWords(baseWord, reqLetter);
                System.out.println("\u001B[33m" + "\nBuzzing for a new word..." + "\u001B[0m");
                Thread.sleep(500);
                System.out.println("\u001B[33m" + "Buzz..." + "\u001B[0m");
                Thread.sleep(500);
                System.out.println("\u001B[33m" + "Buzz...\n" + "\u001B[0m");
                Thread.sleep(500);
                
                shuffle(baseWord, reqLetter);
                guess(baseWord, acceptedWordList);
                System.out.println("Exited guessing phase. Enter a new command, or do /guess to guess again!");
                System.out.println();
                break;
            case "/basepuzzle":
                //insert base puzzle command call here
                break;
            case "/guess":
                guess(baseWord, acceptedWordList);
                break;
            case "/showpuzzle":
                display(baseWord, reqLetter);
                break;
            case "/foundwords":
                //insert found words command call here
                break;
            case "/shuffle":
                System.out.println("\u001B[33m" + "Shaking up the hive!" + "\u001B[0m");
                Thread.sleep(1000);
                System.out.println("\u001B[33m" + "Bzzzzzzzzzzz!" + "\u001B[0m");
                Thread.sleep(500);
                shuffle(baseWord, reqLetter);
                break;
            case "/savepuzzle":
                playerData.savePlayerData(baseWord, String.valueOf(reqLetter), 10, "cool");
                System.out.println("Game Status Saved!");
                break;
            case "/loadpuzzle":
                playerData.loadPlayerData();
                System.out.println("Game Status Loaded!");
                break;
            case "/showstatus":
                //playerRank(reqLetter);
                break;
            case "/help":
                help();
                System.out.println();
                break;
        }
    }
    while (!input.equalsIgnoreCase("exit"));
    inputScanner.close();
    System.out.println("Thanks for playing! :)");

    //------------------------------------------------------//

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
        String a = scanner.nextLine();
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
      "Help",
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
      "Displays help information",
      "Leave the application"
   };
   System.out.println();
   System.out.println("The WordyWasps game allows players to create words using 7 unique letters with a required letter. ");
   System.out.println("- Words must contain at least 4 letters");
   System.out.println("- Words must include the required letter");
   System.out.println("- Letters can be used more than once");
   System.out.println("");
   System.out.println("Command Line    |   Explanation");

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
    System.out.println("||   " + "\u001B[33m" + required + "\u001B[0m" + "   ||");
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
    System.out.println("\u001B[33m" + "\nBzz. Do /q when you're done guessing! Bzz." + "\u001B[0m");
    System.out.println();
    while(true){
        System.out.print("Guess a word: ");
        
        String validWord = guessedWord.nextLine();
        
        if(validWord.equals("/q")){
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

/*********************************************************/
/*********************************************************/  
  
/*
 * intro
 * param: N/A
 * returns: N/A
 * This function is used to display the intro to the player.
 */
public static void intro()
{
    // ANSI escape code for red color
    String yellowColor = "\u001B[33m";
    // ANSI escape code for resetting the color
    String resetColor = "\u001B[0m";

    System.out.println("Welcome to " + yellowColor + "WordyWasps - A Word Puzzle Game!" + resetColor);
    System.out.println("In WordyWasps, you'll be given a set of 7 letters and 1 required letter. Your goal is to create words from them.");
    System.out.println("Here are the " + yellowColor + "basic commands" + resetColor + " you can use to play the game:");

    System.out.println();
    System.out.println("     \" ,  ,");
    System.out.println("\u001B[33m        \", ,\u001B[0m");
    System.out.println("           \"\"     _---.    ..;%%%;, .");
    System.out.println("\u001B[33m             \"\" .\",  ,  .==% %%%%%%% ' .\u001B[0m");
    System.out.println("               \"\", %%%   =%% %%%%%%;  ; ;-_");
    System.out.println("\u001B[33m               %; %%%%%  .;%;%%%\"%p ---; _  '-_\u001B[0m");
    System.out.println("               %; %%%%% __;%%;p/; O        --_ \"-,");
    System.out.println("\u001B[33m                q; %%% /v \\;%p ;%%%%%;--__    \"'-__'-._\u001B[0m");
    System.out.println("                //\\\\\" // \\  % ;%%%%%%%;',/%\\_  __  \"'-_\\_");
    System.out.println("\u001B[33m                \\  / //   \\/   ;%% %; %;/\\%%%%;;;;\\    \"- _\\\u001B[0m");
    System.out.println("                   ,\"             %;  %%;  %%;;'  ';%       -\\-_");
    System.out.println("\u001B[33m              -=\\=\"             __%    %%;_ |;;    %%%\\          \\\u001B[0m");
    System.out.println("                              _/ _=      \\==_;,  %%%; % -_      /");
    System.out.println("\u001B[33m                             / /-          =%- ;%%%%; %%;  \"--__/\u001B[0m");
    System.out.println("                            //=             ==%-%%;  %; %");
    System.out.println("\u001B[33m                            /             _=_-  d  ;%; ;%;  :F_P:\u001B[0m");
    System.out.println("                            \\            =,-\"    d%%; ;%%;");
    System.out.println("                                        //        %  ;%%;");
    System.out.println("\u001B[33m                                       //          d%%%\"\u001B[0m");
    System.out.println("                                        \\           %%");
    System.out.println("                                                    V");
    System.out.println();


    System.out.println(yellowColor + "/newpuzzle" + resetColor + ": Start a new puzzle.");
    System.out.println(yellowColor + "/basepuzzle" + resetColor + ": Restart the current puzzle with the same set of letters.");
    System.out.println(yellowColor + "/showpuzzle" + resetColor + ": Display the current set of 7 letters.");
    System.out.println(yellowColor + "/foundwords" + resetColor + ": Show the words you've already found.");
    System.out.println(yellowColor + "/guess" + resetColor + ": Enter a word you think is valid.");
    System.out.println(yellowColor + "/shuffle" + resetColor + ": Shuffle the 7 letters to get a new arrangement.");
    System.out.println(yellowColor + "/savepuzzle" + resetColor + ": Save the current puzzle for later.");
    System.out.println(yellowColor + "/savecurrent" + resetColor + ": Save your progress in the current game.");
    System.out.println(yellowColor + "/loadpuzzle" + resetColor + ": Load a previously saved puzzle.");
    System.out.println(yellowColor + "/showstatus" + resetColor + ": Display your current game status.");
    System.out.println(yellowColor + "/help" + resetColor + ": Display help information.");
    System.out.println(yellowColor + "/exit" + resetColor + ": Quit the game.");
    System.out.println();

    System.out.println("Now that you know the commands, let's start playing! Have fun and find as many words as you can!");
    System.out.println();
}

}



