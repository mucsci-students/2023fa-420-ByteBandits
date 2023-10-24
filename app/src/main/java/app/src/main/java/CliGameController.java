package app.src.main.java;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;

import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

public class CliGameController {
    private CliGameModel model;
    private CliGameView view;
    private LineReader lineReader;

    public CliGameController(CliGameModel model, CliGameView view) {
        this.model = model;
        this.view = view;
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
                model.savePuzzle();
                break;
            case "/advancedsave":
                error = model.errorFirst();
                if (error) {
                    break;
                }
                model.saveCurr();
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
            case "/help":
                view.help();
                System.out.println();
                break;
            case "/exit":
                view.exitMessage();
                System.exit(0);
                break;
            default:
                view.invalidCommandMessage();
                break;
        }
    }
}
