package app.src.main.java;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import app.src.main.java.CliGameModel;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

public class CliGameView {

    private static CliGameModel model;
    private static Scanner scanner;

    public CliGameView(CliGameModel model) {
        this.model = model;
        scanner = new Scanner(System.in);
    }

    /*
     * intro
     * param: N/A
     * returns: N/A
     * This function is used to display the intro to the player.
     */
    public static void displayIntro() {
        String yellowColor = "\u001B[33m";

        String resetColor = "\u001B[0m";

        System.out.println("\nWelcome to " + yellowColor + "WordyWasps - A Word Puzzle Game!" + resetColor);
        System.out.println(
                "In WordyWasps, you'll be given a set of 7 letters and 1 required letter. Your goal is to create words from them.");
        System.out.println(
                "Here are the " + yellowColor + "basic commands" + resetColor + " you can use to play the game:");

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

        System.out.println(yellowColor + "/newpuzzle" + resetColor + ":      Start a new puzzle.");
        System.out.println(yellowColor + "/basepuzzle" + resetColor
                + ":     Restart the current puzzle with the same set of letters.");
        System.out.println(yellowColor + "/viewpuzzle" + resetColor + ":     Display the current set of 7 letters.");
        System.out.println(yellowColor + "/foundwords" + resetColor + ":     Show the words you've already found.");
        System.out.println(yellowColor + "/guess" + resetColor + ":          Enter a word you think is valid.");
        System.out.println(
                yellowColor + "/shuffle" + resetColor + ":        Shuffle the 7 letters to get a new arrangement.");
        System.out.println(yellowColor + "/cleansave" + resetColor
                + ":      Save the current puzzle for later without game status.");
        System.out.println(yellowColor + "/advancedsave" + resetColor + ":   Save your progress in the current game.");
        System.out.println(yellowColor + "/loadpuzzle" + resetColor + ":     Load a previously saved puzzle.");
        System.out.println(yellowColor + "/observestatus" + resetColor + ":  Display your current game status.");
        System.out.println(yellowColor + "/help" + resetColor + ":           Display help information.");
        System.out.println(yellowColor + "/exit" + resetColor + ":           Quit the game.");
        System.out.println();
        System.out.println(yellowColor + "You can also use tab completion!" + resetColor + "\nSimply press the "
                + yellowColor + "'TAB'" + resetColor + " key after typing in the start of a command\nthen press "
                + yellowColor + "'ENTER'" + resetColor
                + " and it will know what command you want to use!\nKeep in mind you can always use" + yellowColor
                + " /help " + resetColor + "at anytime to view all the commands.");
        System.out.println();
        System.out.println(yellowColor
                + "Now that you know the commands, let's start playing! Have fun and find as many words as you can!"
                + resetColor);
        System.out.println();
    }

    /*
     * getUserInput
     * param: N/A
     * returns: N/A
     * This function is used to get a word from user for guessing.
     */
    public static void getUserInput() {
        System.out.print("Enter a word: ");
    }

    /*
     * newPuzzlePrint
     * param: N/A
     * returns: N/A
     * This is what is shown when a player starts a puzzle.
     */
    public static void newPuzzlePrint() throws InterruptedException {
        System.out.println("\u001B[33m" + "\nBuzzing for a new word..." + "\u001B[0m");
        Thread.sleep(500);
        System.out.println("\u001B[33m" + "Buzz..." + "\u001B[0m");
        Thread.sleep(500);
        System.out.println("\u001B[33m" + "Buzz...\n" + "\u001B[0m");
        Thread.sleep(500);
    }

    /*
     * printPuzzleError
     * param: N/A
     * returns: N/A
     * This function makes the player create a puzzle before doing any other
     * commands.
     */
    public static void printPuzzleError() {
        System.out.println("\u001B[33m"
                + "\nYou haven't created a new puzzle! Do /loadpuzzle, /newpuzzle, or /basepuzzle to get one up! BUZZ!\n"
                + "\u001B[0m");
    }

    /*
     * displayGuessIntro
     * param: N/A
     * returns: N/A
     * This function is showing user how to exit out of guess.
     */
    public static void displayGuessIntro() {
        System.out.println("\u001B[33m" + "\nBzz. Do /q when you're done guessing! Bzz." + "\u001B[0m");
        System.out.println();
    }

    /*
     * displayDuplicate
     * param: N/A
     * returns: N/A
     * This function tells the user if they already guessed a word.
     */
    public static void displayDuplicate() {
        System.out
                .println("\u001B[33m" + "\nYou already guessed that word correctly, try another one!\n" + "\u001B[0m");
    }

    /*
     * displayGuessInstructions
     * param: N/A
     * returns: N/A
     * This function is used to prompt user for word.
     */
    public static void displayGuessInstructions() {
        System.out.print("Guess a word or stop guessing with '/q': ");
    }

    /*
     * displayGuessExit
     * param: N/A
     * returns: N/A
     * This function is used to let player know they are done guessing.
     */
    public static void displayGuessExit() {
        System.out.println("\u001B[33m" + "\nExited guessing phase. Enter a new command, or do /guess to guess again!\n"
                + "\u001B[0m");
    }

    /*
     * displayGuessResult
     * param: N/A
     * returns: N/A
     * This function shows the player their status while they are playing.
     */
    public static void displayGuessResult(String playerRank, int totalPoints) {
        System.out.println("YOUR CURRENT RANK IS: " + "\u001B[33m" + playerRank + "\u001B[0m");
        System.out.println("YOUR CURRENT POINTS ARE: " + "\u001B[33m" + totalPoints + "\u001B[0m");
    }

    /*
     * displayInvalidWord
     * param: N/A
     * returns: N/A
     * This function tells the player that their word was invalid.
     */
    public static void displayInvalidWord() {
        System.out.println("\u001B[33m" + "\nNot a valid word, try again!\n" + "\u001B[0m");
    }

    /*
     * display
     * param: char[] charArray, char required
     * returns: N/A
     * This function displays the look of the puzzle.
     */
    public static void display(char[] charArray, char required) {
        System.out.println("   -----");
        System.out.print(" / ");
        int maxIndex = Math.min(charArray.length, 3);
        for (int i = 0; i < maxIndex; i++) {
            System.out.print(charArray[i] + " ");
        }

        System.out.print("\\");
        System.out.println();
        System.out.println("||   " + "\u001B[33m" + required + "\u001B[0m" + "   ||");
        System.out.print(" \\ ");
        maxIndex = Math.min(charArray.length, 6);
        for (int i = 3; i < maxIndex; i++) {
            System.out.print(charArray[i] + " ");
        }

        System.out.println("/");
        System.out.println("   -----");

    }

    /*
     * foundWordList
     * param: N/A
     * returns: N/A
     * This function is used to display the rank and player points
     * of the pllayer during the current session.
     */
    public static void foundWordList(List<String> foundWords) {

        String yellowColor = "\u001B[33m";
        String resetColor = "\u001B[0m";

        System.out.println();
        System.out.printf("%-2sFOUND WORD LIST%n", "");

        for (int i = 0; i <= 18; i++) {
            System.out.print(yellowColor + "*" + resetColor);
        }

        System.out.println();
        foundWords.sort(Comparator.naturalOrder());

        for (int j = 0; j < foundWords.size(); j++) {
            System.out.printf(yellowColor + "* " + resetColor + "%-16s" + yellowColor + "*%n", foundWords.get(j));
        }

        for (int k = 0; k <= 18; k++) {
            System.out.print(yellowColor + "*" + resetColor);
        }

        System.out.println();
        System.out.println();
    }

    /*
     * displayMessagShuffle
     * param: N/A
     * returns: N/A
     * This function shows the user that the puzzle is being shuffled.
     * 
     * @throws InterruptedException
     */
    public static void displaMessageShuffle() throws InterruptedException {
        System.out.println("\u001B[33m" + "\nShaking up the hive!" + "\u001B[0m");
        Thread.sleep(1000);
        System.out.println("\u001B[33m" + "Bzzzzzzzzzzz!\n" + "\u001B[0m");
        Thread.sleep(500);
    }

    /*
     * createPuzzleMessage
     * param: N/A
     * returns: N/A
     * This function lets player know that they need to use /savecurr.
     */
    public static void createPuzzleMessage() {
        System.out.println("\u001B[33m"
                + "\nBuzz. There's already progress on this puzzle! Please use /savecurr to save instead!\n"
                + "\u001B[0m");
        return;
    }

    /*
     * successfulSaveMessage
     * param: N/A
     * returns: N/A
     * This function lets the player know their puzzle was saved.
     */
    public static void successfulSaveMessage() {
        System.out.println("Game Status Saved!\n");
    }

    /*
     * duplicateLoadMessage
     * param: N/A
     * returns: N/A
     * This function lets player know that their puzzle has already been loaded.
     */
    public static void duplicateLoadMessage() {
        System.out.println("\u001B[33m" + "\nThis puzzle is already loaded!\n" + "\u001B[0m");
        return;
    }

    /*
     * succesfulLoadMessage
     * param: int totalPoints, String playerRank
     * returns: N/A
     * This function is used to show loaded puzzles progress.
     */
    public static void succesfulLoadMessage(int totalPoints, String playerRank) {
        System.out.println("\nTotal Points: " + "\u001B[33m" + totalPoints + "\u001B[0m");
        System.out.println("Rank: " + "\u001B[33m" + playerRank + "\u001B[0m" + "\n");
        System.out.println("Game Status Loaded!\n");
    }

    /*
     * puzzleStatus
     * param: String playerRank
     * returns: N/A
     * This function is used to display the rank and player points
     * of the pllayer during the current session.
     */
    public static void puzzleStatus(String playerRank, int totalPoints) {

        String yellowColor = "\u001B[33m";
        String resetColor = "\u001B[0m";

        System.out.println();

        System.out.println("YOUR CURRENT RANK IS: " + yellowColor + playerRank + resetColor);
        System.out.println("YOUR CURRENT POINTS ARE: " + yellowColor + totalPoints + resetColor);

        System.out.println();
    }

    /*
     * help
     * param: N/A
     * returns: String
     * This function tells the player the rules of the game and shows
     * a table of all the command lines the player can use and what
     * each command line does.
     */
    public static void help() {
        String yellowColor = "\u001B[33m";
        String[] commandLines = {
                "/newpuzzle",
                "/basepuzzle",
                "/viewpuzzle",
                "/foundwords",
                "/guess",
                "/shuffle",
                "/cleansave",
                "/advancedsave",
                "/loadpuzzle",
                "/observestatus",
                "/help",
                "/exit"
        };
        String[] explanations = {
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
        System.out.println(yellowColor
                + "The WordyWasps game allows players to create words using 7 unique letters with a required letter. "
                + "\u001B[0m");
        System.out.println("- Words must contain at least 4 letters");
        System.out.println("- Words must include the required letter");
        System.out.println("- Letters can be used more than once");
        System.out.println("");
        System.out.println(yellowColor + "Command Line    |   Explanation" + "\\u001B[0m");
        System.out.println(yellowColor + "---------------------------------------");
        for (int i = 0; i < commandLines.length; i++) {
            System.out.printf("%-15s |   %s%n", commandLines[i], explanations[i]);
        }
        System.out.println("\u001B[0m");
    }

    /*
     * exitMessage
     * param: N/A
     * returns: N/A
     * This function lets the player know they have exited the game.
     */
    public static void exitMessage() {
        System.out.println("\u001B[33m" + "\nThanks for playing! :)" + "\u001B[0m");
    }

    /*
     * invalidCommandMessage
     * param: N/A
     * returns: N/A
     * This function lets the player know they did an invalid command.
     */
    public static void invalidCommandMessage() {
        System.out.println("\u001B[33m" + "Invalid command! Type /help for a list of commands." + "\u001B[0m");
    }

    /*
     * basePuzzleChooseMessage
     * param: N/A
     * returns: N/A
     * This function shows the prompt for choosing a baseword.
     */
    public static void basePuzzleChooseMessage() {
        System.out.println("Please choose a baseword: ");
    }

    /*
     * highScore()
     * param: N/A
     * returns: N/A
     * This function shows the user that their score was high score.
     */
    public static void highScore() {
        System.out.println("Your score was a " + "\u001B[33m" + "high score!" + "\u001B[0m");
    }

    /*
     * enterUserId()
     * param: N/A
     * returns: N/A
     * This function shows the prompt for entering name for high scores.
     */
    public static void enterUserId() {
        System.out.println("Enter " + "\u001B[33m" + "your first name " + "\u001B[0m" + "to join the " + "\u001B[33m"
                + "leaderboard!" + "\u001B[0m" + ": ");
    }

    /*
     * sevenMessage
     * param: N/A
     * returns: N/A
     * This function lets the player know they need to make a word with 7 letters.
     */
    public static void sevenMessage() {
        String yellowColor = "\u001B[33m";
        System.out.println(yellowColor + "Bzzuh Bzzoh, word has to have 7 letters! Buzz.");
    }

    /*
     * makeUpMessage
     * param: N/A
     * returns: N/A
     * This function lets the player know they need a valid word for basepuzzle.
     */
    public static void makeUpMessage() {
        String yellowColor = "\u001B[33m";
        System.out.println(yellowColor + "Buzz. Are you making stuff up now!  Make sure you type a valid word! Buzz.");
    }

    /*
     * uniqueMessage
     * param: N/A
     * returns: N/A
     * This function lets the player know they need 7 unique letters for basepuzzle.
     */
    public static void uniqueMessage() {
        String yellowColor = "\u001B[33m";
        System.out.println(yellowColor + "Bzzt. Oops, all letters have to be unique! Bzz.");
    }
}
