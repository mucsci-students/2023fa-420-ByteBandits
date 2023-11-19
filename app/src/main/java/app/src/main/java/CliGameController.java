package app.src.main.java;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class CliGameController {
    private static CliGameController instance;
    private CliGameView view;
    private LineReader lineReader;
    private CliGameModel model;

    public CliGameController(CliGameModel model, CliGameView view) {
        this.model = model;
        this.view = view;
    }
    public static CliGameController getInstance(CliGameModel model, CliGameView view) {
        if (instance == null) {
            instance = new CliGameController(model, view);
        }
        return instance;
    }

    public void startGame() throws FileNotFoundException, InterruptedException {
        System.out.print("\u001B[2J\u001B[H");
        System.setProperty("org.jline.terminal.dumb", "true");

        model.initGame();
        view.displayIntro();
   
        try {
            Terminal terminal = TerminalBuilder.builder().dumb(true).system(true).build();
            lineReader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(new CliGameCompleter()) 
                    .build();

            while (true) {
                System.out.print("\u001B[33m" + ">" + "\u001B[0m");
                String input = lineReader.readLine();
                
                input = input.trim();
                processInput(input);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processInput(String input) throws FileNotFoundException, InterruptedException{
        
        switch (input.toLowerCase()) {
            case "/newpuzzle":
                model.newPuzzle();
                System.out.println();
                break;
            case "/basepuzzle":
                model.basePuzzle();
                System.out.println();
                break;
            case "/guess":
                boolean error = model.errorFirst();
                if (error) {
                    break;
                }
                model.guess(model.getBaseWord(), model.getAcceptedWordList(), model.playerRank(model.getBaseWord(), model.getTotalPoints(), model.getAcceptedWordList()));
                break;
            case "/viewpuzzle":
                error = model.errorFirst();
                if (error) {
                    break;
                }
                model.display(model.getShuffleWord(), model.getReqLetter());
                break;
            case "/foundwords":
                error = model.errorFirst();
                if (error) {
                    break;
                }
                view.foundWordList(model.getFoundWords());
                break;
            case "/shuffle":
                error = model.errorFirst();
                if (error) {
                    break;
                }
                CliGameView.displaMessageShuffle();
                model.shuffle(model.getBaseWord(), model.getReqLetter());
                break;
                case "/cleansave":
                error = model.errorFirst();
                if (error) {
                    break;
                }
                try {
                    model.savePuzzle();
                    System.out.println("Puzzle saved successfully.");
                } catch (Exception e) {
                    System.err.println("An error occurred while saving the puzzle: " + e.getMessage());
                }
                break;
            
            case "/advancedsave":
                error = model.errorFirst();
                if (error) {
                    break;
                }
                try {
                    model.saveCurr();
                    System.out.println("Current game state saved successfully.");
                } catch (Exception e) {
                    System.err.println("An error occurred while saving the current game state: " + e.getMessage());
                }
                break;
            case "/loadpuzzle":
                model.loadPuzzle();
                break;
            case "/observestatus":
                error = model.errorFirst();
                if (error) {
                    break;
                }
                view.puzzleStatus(model.playerRank(model.getBaseWord(), model.getTotalPoints(), model.getAcceptedWordList()), model.getTotalPoints());
                break;
            case "/topscores":
                error = model.errorFirst();
                if (error) {
                    break;
                }
                highScores.displayEntriesForBaseWord(model.getBaseWord());
                break;
            case "/help":
                view.help();
                System.out.println();
                break;
            case "/exit":
                System.out.println(model.getBaseWord());
                model.highScore(model.getBaseWord(), model.getTotalPoints(), "");
                highScores.displayEntriesForBaseWord(model.getBaseWord());
                System.exit(0);
                break;
            case "/matrixhints":
                error = model.errorFirst();
                if (error) {
                    break;
                }
                view.showHints();
                break;
            default:
                view.invalidCommandMessage();
                break;
        }
    }
}
