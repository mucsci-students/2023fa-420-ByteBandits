package app.src.main.java;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException; // For handling IOException if needed


public class CliGameController {
    private CliGameModel model;
    private CliGameView view;


    public CliGameController(CliGameModel model, CliGameView view )
    {
        this.model = model;
        this.view = view;
    }
    public void startGame() throws FileNotFoundException, InterruptedException
    {
        model.initGame();
        view.displayIntro();
        

        Scanner inputScanner = new Scanner(System.in);
        String input;

        do {
            System.out.print("\u001B[33m" + ">" + "\u001B[0m");
            input = inputScanner.nextLine();
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
                    if(error == true)
                    {
                        break;
                    }
                    model.guess(model.getBaseWord(), model.getAcceptedWordList(), model.playerRank(model.getBaseWord(),model.getTotaPoints() ,model.getAcceptedWordList()));
                    break;
                case "/showpuzzle":
                    error = model.errorFirst();
                    if(error == true)
                    {
                        break;
                    }
                    model.display(model.getShuffleWord(), model.getReqLetter());
                    break;
                case "/foundwords":
                    error = model.errorFirst();
                    if(error == true)
                    {
                        break;
                    }
                    view.foundWordList(model.getFoundWords());
                    break;
                case "/shuffle":
                    error = model.errorFirst();
                    if(error == true)
                    {
                        break;
                    }
                    CliGameView.displaMessageShuffle();
                    model.shuffle(model.getBaseWord(), model.getReqLetter());
                    break;
                case "/savepuzzle":
                    error = model.errorFirst();
                    if(error == true)
                    {
                        break;
                    }
                    model.savePuzzle();
                    break;
                case "/savecurr":
                    error = model.errorFirst();
                    if(error == true)
                    {
                        break;
                    }
                    model.saveCurr();
                    break;
                case "/loadpuzzle":
                    model.loadPuzzle();
                    break;
                case "/showstatus":
                    error = model.errorFirst();
                    if(error == true)
                    {
                        break;
                    }
                    view.puzzleStatus(model.playerRank(model.getBaseWord(), model.getTotaPoints(), model.getAcceptedWordList()), model.getTotaPoints());
                    break;
                
                case "/help":
                    view.help();
                    System.out.println();
                    break;
                default: 
                    if (input.equals("/exit"))
                    {
                        view.exitMessage();
                    }
                    else{
                        view.invalidCommandMessage();
                    }
                    break;
            }
        }while (!input.equalsIgnoreCase("/exit"));
        inputScanner.close();
    }
}