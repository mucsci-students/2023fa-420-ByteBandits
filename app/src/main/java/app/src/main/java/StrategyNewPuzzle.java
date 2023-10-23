package app.src.main.java;

import java.util.ArrayList;

import app.src.main.java.mainframe.CustomButton;

public class StrategyNewPuzzle{
    private CustomButton letterbutton1;
    private CustomButton letterbutton2;
    private CustomButton letterbutton3;
    private CustomButton letterbutton4;
    private CustomButton letterbutton5;
    private CustomButton letterbutton6;
    private CustomButton letterbutton7;

    public StrategyNewPuzzle(CustomButton letterbutton1, CustomButton letterbutton2, CustomButton letterbutton3, CustomButton letterbutton4, CustomButton letterbutton5, CustomButton letterbutton6, CustomButton letterbutton7) {
        this.letterbutton1 = letterbutton1;
        this.letterbutton2 = letterbutton2;
        this.letterbutton3 = letterbutton3;
        this.letterbutton4 = letterbutton4;
        this.letterbutton5 = letterbutton5;
        this.letterbutton6 = letterbutton6;
        this.letterbutton7 = letterbutton7;
    }

    public void execute(mainframe mainFrame)
    {
        master.totalPoints = 0;

        master.foundWords = new ArrayList<>();
                
        mainframe.shuffleWord = master.shuffle(mainframe.baseWord, mainframe.reqLetter);
                
        // Letter change code goes here after letters are created
        mainframe.baseWord = mainframe.shuffleWord;

        String noReqLetter = master.removeChar(mainframe.baseWord, mainframe.reqLetter);

        char[] bWLetters = noReqLetter.toCharArray();

        String bW1 = "";
        String bW2 = "";
        String bW3 = "";
        String bW4 = "";
        String bW5 = "";
        String bW6 = "";
        String bW7 = "";

        bW1 = Character.toString(bWLetters[0]);
        bW2 = Character.toString(bWLetters[1]);
        bW3 = Character.toString(bWLetters[2]);
        bW4 = Character.toString(mainframe.reqLetter);
        bW5 = Character.toString(bWLetters[3]);
        bW6 = Character.toString(bWLetters[4]);
        bW7 = Character.toString(bWLetters[5]);

        // Letter change code goes here after letters are created
        letterbutton1.setText(bW1.toUpperCase());
        letterbutton2.setText(bW2.toUpperCase());
        letterbutton3.setText(bW3.toUpperCase());
        letterbutton4.setText(bW4.toUpperCase());
        letterbutton5.setText(bW5.toUpperCase());
        letterbutton6.setText(bW6.toUpperCase());
        letterbutton7.setText(bW7.toUpperCase());

        
        letterbutton1.setEnabled(true);
        letterbutton2.setEnabled(true);
        letterbutton3.setEnabled(true);
        letterbutton4.setEnabled(true);
        letterbutton5.setEnabled(true);
        letterbutton6.setEnabled(true);
        letterbutton7.setEnabled(true);
    }
    
}
