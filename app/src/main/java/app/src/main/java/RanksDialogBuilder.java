package app.src.main.java;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RanksDialogBuilder {
    private JDialog ranksDialog;
    private JFrame parentFrame;
    private String dialogTitle;
    private String[] rankNames;
    private ArrayList<String> acceptedWordList;
    private String baseWord;

    public RanksDialogBuilder(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public RanksDialogBuilder setTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
        return this;
    }

    public RanksDialogBuilder setRankNames(String[] rankNames) {
        this.rankNames = rankNames;
        return this;
    }

    public RanksDialogBuilder setAcceptedWordList(List<String> acceptedWordList2) {
        this.acceptedWordList = (ArrayList<String>) acceptedWordList2;
        return this;
    }

    public RanksDialogBuilder setBaseWord(String baseWord) {
        this.baseWord = baseWord;
        return this;
    }

    public JDialog build() {
        ranksDialog = new JDialog(parentFrame, dialogTitle, true);
        ranksDialog.setSize(400, 300);
        ranksDialog.setLocationRelativeTo(parentFrame);
        JTextArea ranksArea = new JTextArea();
        Color darkYellow = new Color(204, 153, 0);
        ranksArea.setBackground(darkYellow);
        ranksArea.setEditable(false);
        ranksArea.setWrapStyleWord(true);
        ranksArea.setLineWrap(true);
        ranksArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        ranksArea.setForeground(Color.BLACK);

        if (rankNames != null) {
            for (String rankName : rankNames) {
                helpers.calculateRankDifference(rankName, 0, acceptedWordList, baseWord.toLowerCase());
                if (helpers.pointsRequired != 0) {
                    ranksArea.append("Rank: " + rankName + "\nPoints needed: " + helpers.pointsRequired + "\n\n");
                }
            }
        }

        ranksDialog.add(new JScrollPane(ranksArea));
        return ranksDialog;
    }
}
