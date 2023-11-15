package app.src.main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

import app.src.main.java.CliGameController;

public class CliGameModel extends helpers {
    public static int possiblePoints;
    
    private static String baseWord;
    
    private static String shuffleWord;
    
    private static int totalPoints;
    
    private static String playerRank;
    
    private static List<String> foundWords;
    
    private static char reqLetter;

    private static highScores saveHighScores;
    
    private static playerData saveFile;
    
    private static List<String> acceptedWordList;

    private static String saveFileName;

    public static Scanner console = new Scanner(System.in);

    private static String author = "";
    
    private static List<String> wordList;
    
    /**
     * @throws FileNotFoundException
     */
    public CliGameModel() throws FileNotFoundException
    {
    }
    
    //GETTERS
    public String getBaseWord()
    {
        return baseWord;
    }

    public static String getShuffleWord()
    {
        return shuffleWord;
    }

    public static char getReqLetter()
    {
        return reqLetter;
    }

    public static List<String> getFoundWords()
    {
        return foundWords;
    }

    public static List<String> getAcceptedWordList()
    {
        return acceptedWordList;
    }

    public static int getTotalPoints()
    {
        return totalPoints;
    }

    public static String getPlayerRank()
    {
        return playerRank;
    }

    public static playerData getSaveFile()
    {
        return saveFile;
    }

    public static String getSaveFileName() {
        return saveFileName;
    }

    public static String getAuthor(){
        return author;
    }

    //SETTERS
    public static void setPossiblePoints(int possiblePoints) {
        CliGameModel.possiblePoints = possiblePoints;
    }

    public static void setBaseWord(String baseWord) {
        CliGameModel.baseWord = baseWord;
    }

    public static void setShuffleWord(String shuffleWord) {
        CliGameModel.shuffleWord = shuffleWord;
    }

    public static void setTotalPoints(int totalPoints) {
        CliGameModel.totalPoints = totalPoints;
    }

    public static void setPlayerRank(String playerRank) {
        CliGameModel.playerRank = playerRank;
    }

    public static void setFoundWords(List<String> foundWords) {
        CliGameModel.foundWords = foundWords;
    }

    public static void setReqLetter(char reqLetter) {
        CliGameModel.reqLetter = reqLetter;
    }

    public static void setSaveFile(playerData saveFile) {
        CliGameModel.saveFile = saveFile;
    }

    public static void setAcceptedWordList(List<String> acceptedWordList) {
        CliGameModel.acceptedWordList = acceptedWordList;
    }

    public void setScanner(Scanner scanner) {
        this.console = scanner;
    }

    public String getUserInput() {
        return console.nextLine();
    }

    public static void setAuthor(String author) {
        CliGameModel.author = author;
    }
    /*
    * initGame
    * param: N/A
    * returns: N/A
    * This function initializes the start of a game.
    */
    public void initGame() throws FileNotFoundException {
       totalPoints = 0;
        playerRank = "";
        foundWords = new ArrayList<>();
        baseWord = "       ";
        shuffleWord = baseWord;
        reqLetter = getReqLetter1(baseWord);
        acceptedWordList = acceptedWords(baseWord, reqLetter);
        saveFile = new playerData();
        saveHighScores = new highScores();
    }

    /**
     * newPuzzle
     * param: N/A
     * returns: N/A
     * This function creates a new puzzle.
     * @throws FileNotFoundException
     * @throws InterruptedException
     * 
     */
    public static void newPuzzle() throws FileNotFoundException, InterruptedException
    {
        totalPoints = 0;
        foundWords = new ArrayList<>();
        baseWord = getBaseWord1(dictionaryFile());
        reqLetter = getReqLetter1(baseWord);
        acceptedWordList = acceptedWords(baseWord, reqLetter);
        CliGameView.newPuzzlePrint();
        shuffleWord = shuffle(baseWord, reqLetter);
        guess(baseWord, acceptedWordList, playerRank(baseWord, totalPoints, acceptedWordList));
    }

    /**
     * basePuzzle
     * param: N/A
     * returns: N/A
     * Function that lets a player create a new puzzle.
     * @throws FileNotFoundException
     * @throws InterruptedException
     * 
     */
    public void basePuzzle() throws FileNotFoundException, InterruptedException
    {
        totalPoints = 0;
        playerRank = "";
        foundWords = new ArrayList<>();
        
        CliGameView.basePuzzleChooseMessage();
        baseWord = console.next();
    
        if (baseWord.length() != 7) {
            baseWord = shuffleWord;
            CliGameView.sevenMessage();
            return;
        } 
            reqLetter = getReqLetter1(baseWord);
            acceptedWordList = acceptedWords(baseWord, reqLetter);
            CliGameView.newPuzzlePrint();
            if (!acceptedWordList.contains(baseWord)) {
                baseWord = shuffleWord;
                CliGameView.makeUpMessage();
                return;
            }
            if (!isUnique(baseWord)) {
                baseWord = shuffleWord;
                CliGameView.uniqueMessage();
                return;
            }
            shuffleWord = shuffle(baseWord, reqLetter);
            console.nextLine();
            guess(baseWord, acceptedWordList, playerRank(baseWord, totalPoints, acceptedWordList));
    }

    /**
    * errorFirst
    * param: N/A
    * returns: N/A
    * This function checks to see if the player has created a puzzle.
    */
    public boolean errorFirst()
    {
        if(baseWord.charAt(1) == ' ')
        {
            CliGameView.printPuzzleError();
            return true;
        }
        return false;
    }

    /**
    * guess
    * param: String baseWord, List<String> acceptedWords, String playerRank
    * returns: N/A
    * This function lets the player guess words for a puzzle.
    */
    public static void guess(String baseWord, List<String> acceptedWords, String playerRank) {
        CliGameView.displayGuessIntro();
        while (true) {
            CliGameView.displayGuessInstructions();
            String validWord = console.nextLine();
    
            if (validWord.equals("/q")) {
                CliGameView.displayGuessExit();
                break;
            }
    
            if (acceptedWordList.contains(validWord) && foundWords.contains(validWord)) {
                CliGameView.displayDuplicate();
            }else if (acceptedWords.contains(validWord)){
                foundWords.add(validWord);
                totalPoints += pointsPWord(baseWord, validWord);
                playerRank = playerRank(baseWord, totalPoints, acceptedWords);
                CliGameView.displayGuessResult(playerRank, totalPoints);
                calculateRankDifference(playerRank, totalPoints, acceptedWords, baseWord);
            } else {
                CliGameView.displayInvalidWord();
            }
        }
    }

    /**
    * display
    * param: String baseword, char required
    * returns: N/A
    * This function gets the string ready for it to be displayed to the user.
    */
    public static String display(String baseword, char required)
    {
        String result = removeChar(baseword, required);
        char[] charArray = result.toCharArray();
        CliGameView.display(charArray, required);
        return baseWord;
    }

    /**
    * shuffle
    * param: String curr, char required
    * returns: String
    * This function shuffles a string.
    */
    public static String shuffle(String curr, char required)
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
            display(shuffled, required);
            shuffleWord = shuffled;
            return shuffled;
    }

    /**
    * setSaveFileName
    * param: String name
    * returns: N/A
    * This function sets the filename for saving a puzzle.
    */
    public static void setSaveFileName(String name) {
        saveFileName = name;
    }

    /**
    * savePuzzle
    * param: N/A
    * returns: N/A
    * This function saves a puzzle that has no progress.
     * @throws Exception
    */
    public static void savePuzzle() throws Exception
    {
        possiblePoints = possiblePoints(baseWord, acceptedWordList);
        if (totalPoints != 0) {
            CliGameView.createPuzzleMessage();
            return;
        }


        //Ask the user to input save filename
        while (true) {
            System.out.print("Input your save file name (max 32 characters): ");
            saveFileName = console.nextLine().trim(); // Read input and remove leading/trailing whitespace
        
            if (saveFileName.isEmpty()) {
                System.out.println("Save file name cannot be empty. Please try again.");
            } else if (saveFileName.length() > 32) {
                System.out.println("Save file name too long. Please limit to 32 characters.");
            }
            else{
                break;
            }
        }

        //Ask the user to input a name:
        while (author.isEmpty() || author.length() > 16) {
            System.out.print("Input your username (max 16 characters): ");
            author = console.nextLine().trim(); // Read input and remove leading/trailing whitespace
        
            if (author.isEmpty()) {
                System.out.println("Username cannot be empty. Please try again.");
            } else if (author.length() > 16) {
                System.out.println("Username too long. Please limit to 16 characters.");
            }
        }


        // Ask the user if they want to encrypt the save
        boolean encrypt = false;
        System.out.print("Do you want to encrypt your save? (y/n): ");
        while (true) {
            String encryptChoice = console.nextLine().trim().toLowerCase();
            if (encryptChoice.equals("y")) {
                encrypt = true;
                break;
            } else if (encryptChoice.equals("n")) {
                break;
            } else {
                System.out.print("Invalid input. Please enter 'y' for yes or 'n' for no: ");
            }
        }

        saveFile.saveGameData(getSaveFileName(), baseWord, shuffleWord, foundWords, totalPoints, String.valueOf(reqLetter), possiblePoints, author, wordList, encrypt);
        CliGameView.successfulSaveMessage();
    }

      public static void highScore(String baseWord, int totalPoints, String userId) {
    
        if (highScores.isHighScore(baseWord, totalPoints)) {
            CliGameView.highScore();
            CliGameView.enterUserId();
            userId = console.next();
    
            highScores.saveHighScores(baseWord, totalPoints, userId);
    
            CliGameView.exitMessage();
        } else {
            CliGameView.exitMessage();
        }
    }

    /**
    * saveCurr
    * param: N/A
    * returns: N/A
    * This function saves a puzzle that has progress.
     * @throws Exception
    */
    public static void saveCurr() throws Exception
    {
   //Ask the user to input save filename
        while (true) {
            System.out.print("Input your save file name (max 32 characters): ");
            saveFileName = console.nextLine().trim(); // Read input and remove leading/trailing whitespace
        
            if (saveFileName.isEmpty()) {
                System.out.println("Save file name cannot be empty. Please try again.");
            } else if (saveFileName.length() > 32) {
                System.out.println("Save file name too long. Please limit to 32 characters.");
            }
            else{
                break;
            }
        }

        //Ask the user to input a name:
        while (author.isEmpty() || author.length() > 16) {
            System.out.print("Input your username (max 16 characters): ");
            author = console.nextLine().trim(); // Read input and remove leading/trailing whitespace
        
            if (author.isEmpty()) {
                System.out.println("Username cannot be empty. Please try again.");
            } else if (author.length() > 16) {
                System.out.println("Username too long. Please limit to 16 characters.");
            }
        }


        // Ask the user if they want to encrypt the save
        boolean encrypt = false;
        System.out.print("Do you want to encrypt your save? (y/n): ");
        while (true) {
            String encryptChoice = console.nextLine().trim().toLowerCase();
            if (encryptChoice.equals("y")) {
                encrypt = true;
                break;
            } else if (encryptChoice.equals("n")) {
                break;
            } else {
                System.out.print("Invalid input. Please enter 'y' for yes or 'n' for no: ");
            }
        }
        saveFile.saveGameData(getSaveFileName(), baseWord, shuffleWord, foundWords, totalPoints, String.valueOf(reqLetter), possiblePoints, author, wordList, encrypt);
        CliGameView.successfulSaveMessage();
    }


    /**
    * loadPuzzle
    * param: N/A
    * returns: N/A
    * This function loads up a puzzle from the past.
    */
    public static void loadPuzzle() throws FileNotFoundException
    {
        if(baseWord == saveFile.getFormat() && totalPoints == saveFile.getPlayerPoints()){
            CliGameView.duplicateLoadMessage();
        }
        saveFile.loadGameData(saveFileName);
                
        baseWord = saveFile.getFormat();
        foundWords = saveFile.getFoundWords();
        totalPoints = saveFile.getPlayerPoints();
        reqLetter = saveFile.getRequiredLetter().charAt(0);
        possiblePoints = saveFile.getMaxPoints();

                
                
        acceptedWordList = acceptedWords(baseWord, reqLetter);
        shuffleWord = display(baseWord, reqLetter);

        CliGameView.succesfulLoadMessage(totalPoints, playerRank(baseWord, totalPoints, acceptedWordList));
    }

    /**
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
            playerRank = "Great";
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

    /**
    * dictionaryFile
    * param: N/A
    * returns: List<String> 
    * This function scans the given dictionary file
    * of seven letter words and creates an arraylist of strings
    * of all the words on the file that have all unique letters.
    */
    public static List<String> dictionaryFile() throws FileNotFoundException{
        ResourceFactory resourceFactory = new FileResourceFactory(); //Access Factory Design Pattern
        Scanner oldConsole = console;
        console = resourceFactory.getDictionaryResource();
        List<String> sevenLetterWords = new ArrayList<>();

        while(console.hasNextLine()){
            String a = console.nextLine();

            if(isUnique(a)){
                sevenLetterWords.add(a);
            }
        } 

        console = oldConsole;
        return sevenLetterWords;
    }

    /**
    * getBaseWord1
    * param: List<String> dictionary
    * returns: String 
    * This function receives the string array from
    * dictionaryFile and randomly chooses a word
    * from the array using indexing. This word
    * is then returned and acts as our base word.
    */
    public static String getBaseWord1(List<String> dictionary) throws FileNotFoundException{
        Random rand = new Random();
        int upperBound = dictionaryFile().size();
        int randomInt = rand.nextInt(upperBound);
    
        return dictionary.get(randomInt);
    }

    /**
    * getReqLetters
    * param: String baseWord
    * returns: char
    * This function receives the string from 
    * getBaseWord and randomly chooses a character
    * from the base word given using indexing. 
    * This character is returned and acts as 
    * our required character.
    */
    public static char getReqLetter1(String baseWord){
        Random rand = new Random();
        int upperBound = 7;
        int randomInt = rand.nextInt(upperBound);

        return baseWord.charAt(randomInt);
    }

    /**
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

    /**
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
        ResourceFactory resourceFactory = new FileResourceFactory(); //Access Factory Design Pattern
        Scanner oldConsole= console;

        console = resourceFactory.getGameDataResource();

        List<String> acceptedWordList = new ArrayList<>();
        String reqLetter2 = Character.toString(reqLetter);
    
        String sNL = console.nextLine();
        while(console.hasNextLine()){
            if(sameChars(baseWord, sNL) && sNL.contains(reqLetter2)){
                acceptedWordList.add(sNL);
            }

            sNL = console.nextLine();  
        }
        console = oldConsole;
        return acceptedWordList;
    }
}

