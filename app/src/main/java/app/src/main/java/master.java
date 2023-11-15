package app.src.main.java;
// Authors: Logan Wasmer, Jose De La Cruz, Ilynd Rapant, Joshua Dawson

/**********************************************************/
/**********************************************************/

//Imports
import java.io.*;
import java.util.*;


/**********************************************************/
/**********************************************************/

public class master extends helpers{
    public static int totalPoints = 0;

    public static String playerRank = "";

    public static List<String> foundWords = new ArrayList<>();

    public static String author;
    public static List<String> wordList; // TODO: WORK ON THESE LATER SPRINT 4
    
public static void cliMode() throws Exception{
    
    playerData saveFile = new playerData();
    String baseWord = "       ";
    char reqLetter = getReqLetter(baseWord);
    List<String> acceptedWordList = acceptedWords(baseWord, reqLetter);
    String shuffleWord = baseWord;
    
    
    intro();
    
    Scanner inputScanner = new Scanner(System.in);
    String input;
    
    do
    {
        System.out.print("\u001B[33m" + ">" + "\u001B[0m"); 
        input = inputScanner.nextLine();
        switch (input.toLowerCase())

        {
            case "/newpuzzle":
                totalPoints = 0;
                foundWords = new ArrayList<>();
                baseWord = getBaseWord(dictionaryFile());
                reqLetter = getReqLetter(baseWord);
                acceptedWordList = acceptedWords(baseWord, reqLetter);
                System.out.println("\u001B[33m" + "\nBuzzing for a new word..." + "\u001B[0m");
                Thread.sleep(500);
                System.out.println("\u001B[33m" + "Buzz..." + "\u001B[0m");
                Thread.sleep(500);
                System.out.println("\u001B[33m" + "Buzz...\n" + "\u001B[0m");
                Thread.sleep(500);
                
                shuffleWord = shuffle(baseWord, reqLetter);
                guess(baseWord, acceptedWordList, playerRank(baseWord, totalPoints, acceptedWordList));
                
                System.out.println();

                break;

            case "/basepuzzle":
            totalPoints = 0;
            playerRank = "";
            String yellowColor = "\u001B[33m";
            foundWords = new ArrayList<>();
            Scanner console = new Scanner(System.in);
            System.out.println("Please choose a baseword: ");
            baseWord = console.next();
            
            
            if (baseWord.length() != 7) 
            {
                baseWord = shuffleWord;
                System.out.println(yellowColor + "Bzzuh Bzzoh, word has to have 7 letters! Buzz.");

                break; 
            }

            reqLetter = getReqLetter(baseWord);
            acceptedWordList = acceptedWords(baseWord, reqLetter);

            System.out.println("\u001B[33m" + "\nBuzzing for a new word..." + "\u001B[0m");
            Thread.sleep(500);
            System.out.println("\u001B[33m" + "Buzz..." + "\u001B[0m");
            Thread.sleep(500);
            System.out.println("\u001B[33m" + "Buzz...\n" + "\u001B[0m");
            Thread.sleep(500);

            
            if (!acceptedWordList.contains(baseWord)) 
            {
                baseWord = shuffleWord;
                System.out.println(yellowColor + "Buzz. Are you making stuff up now!  Make sure you type a valid word! Buzz.");
                break; 
            }

            if (!isUnique(baseWord))
            {
                baseWord = shuffleWord;
                System.out.println(yellowColor + "Bzzt. Oops, all letters have to be unique! Bzz.");
                break; 
            }

            shuffleWord = shuffle(baseWord, reqLetter);
            guess(baseWord, acceptedWordList, playerRank(baseWord, totalPoints, acceptedWordList));
        
            System.out.println();

            break;

            case "/guess":
                if(baseWord.charAt(1) == ' ')
                {
                    System.out.println("\u001B[33m" + "\nYou haven't created a new puzzle! Do /loadpuzzle, /newpuzzle, or /basepuzzle to get one up! BUZZ!\n" + "\u001B[0m");

                    break;
                }

                guess(baseWord, acceptedWordList, playerRank(baseWord, totalPoints, acceptedWordList));

                break;

            case "/showpuzzle":
                if(baseWord.charAt(1) == ' ')
                {
                    System.out.println("\u001B[33m" + "\nYou haven't created a new puzzle! Do /loadpuzzle, /newpuzzle, or /basepuzzle to get one up! BUZZ!\n" + "\u001B[0m");

                    break;
                }

                display(shuffleWord, reqLetter);

                break;
            
            case "/foundwords":
                if(baseWord.charAt(1) == ' ')
                {
                    System.out.println("\u001B[33m" + "\nYou haven't created a new puzzle! Do /loadpuzzle, /newpuzzle, or /basepuzzle to get one up! BUZZ!\n" + "\u001B[0m");

                    break;
                }

                foundWordList();

                break;

            case "/shuffle":
                if(baseWord.charAt(1) == ' ')
                {
                    System.out.println("\u001B[33m" + "\nYou haven't created a new puzzle! Do /loadpuzzle, /newpuzzle, or /basepuzzle to get one up! BUZZ!\n" + "\u001B[0m");
                    break;
                }

                System.out.println("\u001B[33m" + "\nShaking up the hive!" + "\u001B[0m");
                Thread.sleep(1000);
                System.out.println("\u001B[33m" + "Bzzzzzzzzzzz!\n" + "\u001B[0m");
                Thread.sleep(500);

                shuffleWord = shuffle(baseWord, reqLetter);

                break;

            case "/savepuzzle":

                int possiblePoints = possiblePoints(baseWord, acceptedWordList);
                if(baseWord.charAt(1) == ' ')
                {
                    System.out.println("\u001B[33m" + "\nYou haven't created a new puzzle! Do /loadpuzzle, /newpuzzle, or /basepuzzle to get one up! BUZZ!\n" + "\u001B[0m");
                    break;
                }
                if(totalPoints != 0)
                {
                    System.out.println("\u001B[33m" + "\nBuzz. There's already progress on this puzzle! Please use /savecurr to save instead!\n" + "\u001B[0m");
                    break;
                }

                saveFile.saveGameData(CliGameModel.getSaveFileName(), baseWord, shuffleWord, foundWords, totalPoints, String.valueOf(reqLetter), possiblePoints);

                System.out.println("Game Status Saved!\n");

                CliGameModel.savePuzzle();


                break;

            case "/savecurr":
                
                if(baseWord.charAt(1) == ' ')
                {
                    System.out.println("\u001B[33m" + "\nYou haven't created a new puzzle! Do /loadpuzzle, /newpuzzle, or /basepuzzle to get one up! BUZZ!\n" + "\u001B[0m");
                    break;
                }
            
                saveFile.saveGameData(CliGameModel.getSaveFileName(), baseWord, shuffleWord, foundWords, totalPoints, String.valueOf(reqLetter), possiblePoints(baseWord, acceptedWordList));
                System.out.println("\nGame Status Saved!\n");

                CliGameModel.saveCurr();


                break;

            case "/loadpuzzle":

                if(baseWord == saveFile.getFormat() && totalPoints == saveFile.getPlayerPoints()){
                    System.out.println("\u001B[33m" + "\nThis puzzle is already loaded!\n" + "\u001B[0m");
                    break;
                }    

                saveFile.loadGameData(CliGameModel.getSaveFileName());
                
                baseWord = saveFile.getFormat();
                foundWords = saveFile.getFoundWords();
                totalPoints = saveFile.getPlayerPoints();
                reqLetter = saveFile.getRequiredLetter().charAt(0);
                possiblePoints = saveFile.getMaxPoints();

                
                
                acceptedWordList = acceptedWords(baseWord, reqLetter);
                shuffleWord = display(baseWord, reqLetter);

                System.out.println("\nTotal Points: " + "\u001B[33m" + totalPoints + "\u001B[0m");
                System.out.println("Rank: " + "\u001B[33m" + playerRank + "\u001B[0m" + "\n");
                System.out.println("Game Status Loaded!\n");


                CliGameModel.loadPuzzle();

                break;

            case "/showstatus":
                if(baseWord.charAt(1) == ' ')
                {
                    System.out.println("\u001B[33m" + "\nYou haven't created a new puzzle! Do /loadpuzzle, /newpuzzle, or /basepuzzle to get one up! BUZZ!\n" + "\u001B[0m");

                    break;
                }
                puzzleStatus(playerRank(baseWord, totalPoints, acceptedWordList));
                break;

            case "/help":
                help();
                System.out.println();

                break;
            default:
                if (input.equals("/exit")){
                    System.out.println("\u001B[33m" + "\nThanks for playing! :)" + "\u001B[0m");
                }
                else{
                   System.out.println("\u001B[33m" + "Invalid command! Type /help for a list of commands." + "\u001B[0m"); 
                }
                break;
        }
    }while (!input.equalsIgnoreCase("/exit"));

    inputScanner.close();

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

public static List<String> dictionaryFile() throws FileNotFoundException{
    Scanner scanner = new Scanner(new File("./src/main/resources/7-letter-words.txt"));
    List<String> sevenLetterWords = new ArrayList<>();

    while(scanner.hasNextLine()){
        String a = scanner.nextLine();

        if(isUnique(a)){
            sevenLetterWords.add(a);
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

public static String getBaseWord(List<String> dictionary) throws FileNotFoundException{
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
 * param: String baseWord, String userGuess
 * returns: int 
 * This function recieves two Strings as parameters,
 * the length of the string is then retrived. After 
 * the amount of points is determined by the length,
 * it will return that point value.
 */

public static int pointsPWord(String baseWord, String userGuess){
    int length = userGuess.length();
    int points = 0;

    switch(length){
        case 4:
        points = 1;
        System.out.println("\u001B[33m" + "Good! +1 pt\n" + "\u001B[0m");
        break;

        default:
        points = length;
        if (length == 5 || length == 6){
            System.out.println("\u001B[33m" + "Great! +" + points + " pts\n" + "\u001B[0m");
        }
        else{
            
            if (isPangram(baseWord, userGuess)){
                points += 7;

                System.out.println();
                System.out.println("\u001B[33m" + "Your guess was a PANGRAM!" + "\u001B[0m");
                System.out.println("\u001B[33m" + "BONUS +7!\n" + "\u001B[0m");
            }

            System.out.println("\u001B[33m" + "Awesome! +" + points + " pts\n" + "\u001B[0m");
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

public static String playerRank(String baseWord, int playerPoints, List<String> possiblewords){

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
    
    if (playerPoints < Math.round(goodStart)){
        playerRank = "Beginner";
    }
    if (isBetween(playerPoints, Math.round(goodStart), movingUp)){
        playerRank = "Good Start";
    }
    if (isBetween(playerPoints, Math.round(movingUp), good)){
        playerRank = "Moving Up";
    }
    if(isBetween(playerPoints, Math.round(good), solid)){
        playerRank = "Good";
    }
    if(isBetween(playerPoints, Math.round(solid), nice)){
        playerRank = "Solid";
    }
    if(isBetween(playerPoints, Math.round(nice), great)){
        playerRank = "Nice";
    }
    if(isBetween(playerPoints, Math.round(great), amazing)){
        playerRank = "great";
    }
    if(isBetween(playerPoints, Math.round(amazing), genuis)){
        playerRank = "Amazing";
    }
    if(isBetween(playerPoints, Math.round(genuis), queenBee)){
        playerRank = "Genius";
    }
    if(playerPoints == Math.round(queenBee) ){
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
    String yellowColor = "\u001B[33m";
    String [] commandLines = {
       "/newpuzzle",
       "/basepuzzle",
       "/showpuzzle",
       "/foundwords",
       "/guess",
       "/shuffle",
       "/savepuzzle",
       "/savecurr",
       "/loadpuzzle",
       "/showstatus",
       "/help",
       "/exit"
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
    System.out.println(yellowColor + "The WordyWasps game allows players to create words using 7 unique letters with a required letter. ");
    System.out.println("- Words must contain at least 4 letters");
    System.out.println("- Words must include the required letter");
    System.out.println("- Letters can be used more than once");
    System.out.println("");
    System.out.println(yellowColor + "Command Line    |   Explanation");
         System.out.println(yellowColor + "---------------------------------------");
         for (int i = 0; i < commandLines.length; i++) {
           System.out.printf("%-15s |   %s%n", commandLines[i], explanations[i]);
         }
 }
/*********************************************************/
/*********************************************************/

/*
 * display
 * param: String baseword, char required
 * returns: String
 * This function creates a cool display for the puzzle.
 */

public static String display(String baseword, char required)
{
    String result = removeChar(baseword, required);
    char[] charArray = result.toCharArray(); 

    System.out.println("   -----");
    System.out.print(" / ");
    int maxIndex = Math.min(charArray.length, 3);
    for (int i = 0; i < maxIndex; i++) {
        System.out.print(charArray[i] + " ");
    }

    System.out.print( "\\");
    System.out.println();
    System.out.println("||   " + "\u001B[33m" + required + "\u001B[0m" + "   ||");
    System.out.print(" \\ ");
    maxIndex = Math.min(charArray.length, 6);
    for (int i = 3; i < maxIndex; i++) {
        System.out.print(charArray[i] + " ");
    }

    System.out.println("/");
    System.out.println("   -----");

    return baseword;
}
    
/*********************************************************/
/*********************************************************/

/*
 * shuffle
 * param: String curr, char required
 * returns: String
 * This function shuffles the letters of a current puzzle.
 */
  
public static String shuffle (String curr, char required)
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

    return shuffled;
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

public static void guess(String baseWord, List<String> acceptedWords, String playerRank){
    
    Scanner guessedWord = new Scanner(System.in);
    System.out.println("\u001B[33m" + "\nBzz. Do /q when you're done guessing! Bzz." + "\u001B[0m");
    System.out.println();

    while(true){
        System.out.print("Guess a word: ");
        
        String validWord = guessedWord.nextLine();
        
        if(validWord.equals("/q")){
            System.out.println("\u001B[33m" + "\nExited guessing phase. Enter a new command, or do /guess to guess again!\n" + "\u001B[0m");
            break;
        }

        if(acceptedWords.contains(validWord) && foundWords.contains(validWord)){
            System.out.println("\u001B[33m" + "\nYou already guessed that word correctly, try another one!\n" + "\u001B[0m");
            
        }else if(acceptedWords.contains(validWord)){
            foundWords.add(validWord);
            totalPoints += pointsPWord(baseWord, validWord);
            playerRank = playerRank(baseWord, totalPoints, acceptedWords);
            System.out.println("Bzz. Do /q when you're done guessing! Bzz.");
            System.out.println("YOUR CURRENT RANK IS: " + "\u001B[33m" +  playerRank + "\u001B[0m");
            System.out.println("YOUR CURRENT POINTS ARE: " + "\u001B[33m" + totalPoints + "\u001B[0m");
            
            calculateRankDifference(playerRank, totalPoints, acceptedWords, baseWord);

        }else{
            System.out.println("\u001B[33m" + "\nNot a valid word, try again!\n" + "\u001B[0m");
        }
        
    }

}

/*********************************************************/
/*********************************************************/

/*
 * guessGUI
 * param: String guessedWord, String baseWord, List<String> acceptedWords, String playerRank 
 * returns: N/A
 * This function is invoked in the GUI after pressing "Enter".
 * When this happens a guess will be tested if it is valid and then
 * all the information is then updated for the GUI.
 */

public static void guessGUI(String guessedWord, String baseWord, List<String> acceptedWords, String playerRank){
    guessedWord = guessedWord.toLowerCase();
    if(acceptedWords.contains(guessedWord) && foundWords.contains(guessedWord)){
            //already guessed
    }else if(acceptedWords.contains(guessedWord)){
        
        foundWords.add(guessedWord);
        
        
        totalPoints += pointsPWord(baseWord, guessedWord);
        playerRank = playerRank(baseWord, totalPoints, acceptedWords);

        calculateRankDifference(playerRank, totalPoints, acceptedWords, baseWord);
    }
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

public static void puzzleStatus (String playerRank){

    String yellowColor = "\u001B[33m";
    String resetColor = "\u001B[0m";

    System.out.println();

    System.out.println("YOUR CURRENT RANK IS: " + yellowColor + playerRank + resetColor);
    System.out.println("YOUR CURRENT POINTS ARE: " + yellowColor + totalPoints + resetColor);

    System.out.println();

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

public static void foundWordList (){
    
    String yellowColor = "\u001B[33m";
    String resetColor = "\u001B[0m";

    System.out.println();
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
    
    System.out.println();
    System.out.println();

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
    String yellowColor = "\u001B[33m";
    
    String resetColor = "\u001B[0m";

    System.out.println("\nWelcome to " + yellowColor + "WordyWasps - A Word Puzzle Game!" + resetColor);
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


    System.out.println(yellowColor + "/newpuzzle" + resetColor + ":   Start a new puzzle.");
    System.out.println(yellowColor + "/basepuzzle" + resetColor + ":  Restart the current puzzle with the same set of letters.");
    System.out.println(yellowColor + "/showpuzzle" + resetColor + ":  Display the current set of 7 letters.");
    System.out.println(yellowColor + "/foundwords" + resetColor + ":  Show the words you've already found.");
    System.out.println(yellowColor + "/guess" + resetColor + ":       Enter a word you think is valid.");
    System.out.println(yellowColor + "/shuffle" + resetColor + ":     Shuffle the 7 letters to get a new arrangement.");
    System.out.println(yellowColor + "/savepuzzle" + resetColor + ":  Save the current puzzle for later without game status.");
    System.out.println(yellowColor + "/savecurr" + resetColor + ":    Save your progress in the current game.");
    System.out.println(yellowColor + "/loadpuzzle" + resetColor + ":  Load a previously saved puzzle.");
    System.out.println(yellowColor + "/showstatus" + resetColor + ":  Display your current game status.");
    System.out.println(yellowColor + "/help" + resetColor + ":        Display help information.");
    System.out.println(yellowColor + "/exit" + resetColor + ":        Quit the game.");
    System.out.println();

    System.out.println("Now that you know the commands, let's start playing! Have fun and find as many words as you can!");
    System.out.println();
}
}