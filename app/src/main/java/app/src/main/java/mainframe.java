package app.src.main.java;
// Authors: Joshua Dawson, Logan Wasmer, Jose De La Cruz, Ilynd Rapant
/***************************************************************/
/***************************************************************/
//Imports
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DocumentFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
/***************************************************************/
/***************************************************************/

public class mainframe {
    protected JTextPane textPane;
    
    private int charCount = 0;

    Color darkYellow = new Color(204, 153, 0);
    //create a button object view 
    public class CustomButton extends JButton {
        private Color originalBackgroundColor;
        private boolean isLetterButton4;
      
        public CustomButton(String text, boolean isLetterButton4) {
            super(text);
            this.isLetterButton4 = isLetterButton4;
            initialize();
        }
        //initialize all buttons mixture of view and controller
        private void initialize(){
            originalBackgroundColor = isLetterButton4 ? new Color(0, 0, 0) : new Color(204, 153, 0);
    
            setOpaque(true);
            setFont(new Font("SansSerif", Font.BOLD, 24));
            setPreferredSize(new Dimension(280, 80));
            setBackground(originalBackgroundColor);
            setForeground(isLetterButton4 ? Color.BLACK : Color.BLACK);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            setUI(new BasicButtonUI());

    
            addMouseListener(new MouseAdapter(){
                @Override
                public void mouseEntered(MouseEvent e){
                    if(isLetterButton4){
                        setBorder(BorderFactory.createLineBorder(darkYellow, 4)); 
                    }else{
                      setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));  
                    }
                    
                }
    
                @Override
                public void mouseExited(MouseEvent e){
                    setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }
    
                @Override
                public void mousePressed(MouseEvent e){
                    if (isLetterButton4){
                        setBackground(new Color(255, 215, 0));
                        setForeground(Color.BLACK); 
                    }else{
                        setBackground(new Color(255, 215, 0)); 
                    }
                }
    
                @Override
                public void mouseReleased(MouseEvent e){
                    if (isLetterButton4){
                        setBackground(Color.BLACK);
                        setForeground(darkYellow); 
                    }else{
                        setBackground(new Color(204, 153, 0));
                        setForeground(Color.BLACK); 
                    }
                }
            });
        }
    }
    //model
    private playerData playerGameData = new playerData();
    
    playerData saveFile = new playerData();
    public static String baseWord = "       ";
    public static char reqLetter = master.getReqLetter(baseWord);
    public static String shuffleWord = baseWord;
    List<String> acceptedWordList;
    
    char[] bWLetters = baseWord.toCharArray();
    //view class creae mainfram secondframe
    private JFrame mainFrame;
    private JFrame secondFrame;
    private JDialog howToPlayDialog;
    private JDialog foundwords;
    final private Font mainFont = new Font("SansSerif", Font.BOLD, 18);
    final private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    /*************************************************************/
    /*******************BACKGORUND PANEL**************************/
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(Image image) {
            this.backgroundImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
    /**********************************************************/
    /**********************************************************/
    private void updateFoundWordsDialog() {
        if (foundwords == null) {
            foundwords = new JDialog(mainFrame, "FOUND WORD LIST", true);
            foundwords.setSize(400, 300);
            foundwords.setLocationRelativeTo(mainFrame);
            JTextArea foundWordsArea = new JTextArea();
            Color darkYellow = new Color(204, 153, 0);
            foundWordsArea.setBackground(darkYellow);
            foundWordsArea.setEditable(false);
            foundWordsArea.setWrapStyleWord(true);
            foundWordsArea.setLineWrap(true);
            foundWordsArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
            foundWordsArea.setForeground(Color.BLACK);
            if (master.foundWords.isEmpty()) {
                foundWordsArea.append("YOU HAVEN'T FOUND ANY WORDS");
            }
    
            for (String word : master.foundWords) {
                word = word.toUpperCase();
                foundWordsArea.append("- " + word + "\n");
            }
            foundwords.add(new JScrollPane(foundWordsArea));
        } else {
            JTextArea foundWordsArea = (JTextArea) ((JScrollPane) foundwords.getContentPane().getComponent(0)).getViewport().getView();
            foundWordsArea.setText("");
            if (master.foundWords.isEmpty()) {
                foundWordsArea.append("YOU HAVEN'T FOUND ANY WORDS");
            }
    
            for (String word : master.foundWords) {
                word = word.toUpperCase();
                foundWordsArea.append("- " + word + "\n");
            }
        }
    }
    public mainframe() {
        mainFrame = new JFrame("Welcome to Wordy Wasps");
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(screenSize);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon gifIcon = new ImageIcon("./src/main/resources/guicontent/bg1.jpg");

        Image gifImage = gifIcon.getImage();
        BackgroundPanel backgroundPanel = new BackgroundPanel(gifImage);
        backgroundPanel.setLayout(new BorderLayout());
    
        // Create a JPanel for the top section
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
    
        // Centered JLabel
        JLabel welcomeLabel = new JLabel("<html><font color='black'>W</font><font color='#FFD700'>O</font><font color='black'>R</font><font color='#FFD700'>D</font><font color='black'>Y</font><font color='#FFD700'>W</font><font color='black'>A</font><font color='#FFD700'>S</font><font color='black'>P</font><font color='#FFD700'>S</font></html>", JLabel.CENTER);
        Font titleFont = new Font("SansSerif", Font.BOLD, 72);
        welcomeLabel.setFont(titleFont);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
    
        backgroundPanel.add(topPanel, BorderLayout.NORTH);
    
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
    
        
        ImageIcon gifIcon1 = new ImageIcon("./src/main/resources/guicontent/finalWaspGif.gif");
        Image originalImage = gifIcon1.getImage();
        Image resizedImage = originalImage.getScaledInstance(650, 500, Image.SCALE_DEFAULT);
        ImageIcon resizedGifIcon = new ImageIcon(resizedImage);
        JLabel gifLabel1 = new JLabel(resizedGifIcon);

        centerPanel.add(gifLabel1, BorderLayout.CENTER);

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);
    
        // Create a panel for the buttons at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
    
        // "PLAY" button
        CustomButton playButton = new CustomButton("PLAY", false);
        playButton.setBackground(new Color(204, 153, 0));
        playButton.setOpaque(true); // Make the button opaque
        playButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        playButton.setPreferredSize(new Dimension(200, 60)); // Increase the width
    playButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showSecondScreen();
        }
    });
    
        // "GUI -> CLI" button
    //     CustomButton guiToCliButton = new CustomButton("GUI -> CLI", false);
    //     guiToCliButton.setBackground(new Color(204, 153, 0));
    //     guiToCliButton.setOpaque(true); // Make the button opaque
    //     guiToCliButton.setFont(new Font("SansSerif", Font.BOLD, 24));
    //     guiToCliButton.setPreferredSize(new Dimension(200, 60)); // Increase the width
    //     guiToCliButton.addActionListener(new ActionListener() {
    //     @Override
    //     public void actionPerformed(ActionEvent e) {
    //         mainFrame.dispose();
    //         try {
    //             master.main(new String[0]);
    //         } catch (Exception ex) {
    //             ex.printStackTrace();
    //         }
    //     }
    // });
    
        // Add both buttons to the button panel
        buttonPanel.add(playButton);
        //buttonPanel.add(guiToCliButton);
    
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);
    
        mainFrame.setContentPane(backgroundPanel);
        mainFrame.setVisible(true);
    }
    
    /************************************************************/
    /*********************SECOND SCREEN**************************/
    //Shows after player clicks PLAY
    private void showSecondScreen() {
        mainFrame.setVisible(false);
        
        // Create the second frame
        secondFrame = new JFrame("Wordy Wasps - Main Menu");
        secondFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        secondFrame.setSize(screenSize);
        secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        secondFrame.getContentPane().setBackground(new Color(255, 255, 153));

        // Create a panel for the buttons at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); 
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JPanel buttonPanel2 = new JPanel();
        buttonPanel.setOpaque(false); 
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel2.setBackground(new Color(255, 255, 153));

        // Create buttons for the second screen

        String bW1 = Character.toString(bWLetters[0]);
        String bW2 = Character.toString(bWLetters[1]);
        String bW3 = Character.toString(bWLetters[2]);
        String bW4 = Character.toString(bWLetters[3]);
        String bW5 = Character.toString(bWLetters[4]);
        String bW6 = Character.toString(bWLetters[5]);
        String bW7 = Character.toString(bWLetters[6]);

        CustomButton foundWordsButton = new CustomButton("FOUND WORDS", false);
        CustomButton shufflePuzzle = new CustomButton("SHUFFLE PUZZLE", false);
        CustomButton newPuzzleButton = new CustomButton("NEW PUZZLE", false);
        CustomButton newUserPuzzleButton = new CustomButton("CUSTOM PUZZLE", false);
        CustomButton savePuzzleButton = new CustomButton("SAVE PUZZLE", false);
        CustomButton loadPuzzleButton = new CustomButton("LOAD PUZZLE", false);
        CustomButton howToPlayButton = new CustomButton("HOW TO PLAY", false);
        CustomButton exitButton = new CustomButton("EXIT", false);
        CustomButton letterbutton1 = new CustomButton(bW1, false);
        CustomButton letterbutton2 = new CustomButton(bW2, false);
        CustomButton letterbutton3 = new CustomButton(bW3, false);
        CustomButton letterbutton4 = new CustomButton(bW4, true);
        CustomButton letterbutton5 = new CustomButton(bW5, false);
        CustomButton letterbutton6 = new CustomButton(bW6, false);
        CustomButton letterbutton7 = new CustomButton(bW7, false);

        letterbutton1.setEnabled(false);
        letterbutton2.setEnabled(false);
        letterbutton3.setEnabled(false);
        letterbutton4.setEnabled(false);
        letterbutton5.setEnabled(false);
        letterbutton6.setEnabled(false);
        letterbutton7.setEnabled(false);


        Color darkYellow = new Color(204, 153, 0);

        Color black = new Color(0,0,0);
        shufflePuzzle.setBackground(darkYellow);
        newPuzzleButton.setBackground(darkYellow); 
        newUserPuzzleButton.setBackground(darkYellow);
        savePuzzleButton.setBackground(darkYellow);
        loadPuzzleButton.setBackground(darkYellow); 
        howToPlayButton.setBackground(darkYellow); 
        foundWordsButton.setBackground(darkYellow);
        exitButton.setBackground(darkYellow);

        letterbutton1.setBackground(darkYellow);
        letterbutton2.setBackground(darkYellow);
        letterbutton3.setBackground(darkYellow);
        letterbutton4.setBackground(black);
        letterbutton5.setBackground(darkYellow);
        letterbutton6.setBackground(darkYellow);
        letterbutton7.setBackground(darkYellow);

        letterbutton1.setForeground(Color.BLACK);
        letterbutton2.setForeground(Color.BLACK);
        letterbutton3.setForeground(Color.BLACK);
        letterbutton4.setForeground(darkYellow);
        letterbutton5.setForeground(Color.BLACK);
        letterbutton6.setForeground(Color.BLACK);
        letterbutton7.setForeground(Color.BLACK);

        Dimension buttonSize = new Dimension(180, 50); 

        shufflePuzzle.setPreferredSize(buttonSize);
        newPuzzleButton.setPreferredSize(buttonSize);
        newUserPuzzleButton.setPreferredSize(buttonSize);
        savePuzzleButton.setPreferredSize(buttonSize);
        loadPuzzleButton.setPreferredSize(buttonSize);
        howToPlayButton.setPreferredSize(buttonSize);
        foundWordsButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 15);

        shufflePuzzle.setFont(buttonFont);
        newPuzzleButton.setFont(buttonFont);
        newUserPuzzleButton.setFont(buttonFont);
        savePuzzleButton.setFont(buttonFont);
        loadPuzzleButton.setFont(buttonFont);
        howToPlayButton.setFont(buttonFont);
        foundWordsButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        letterbutton1.setFont(buttonFont);
        letterbutton2.setFont(buttonFont);
        letterbutton3.setFont(buttonFont);
        letterbutton4.setFont(buttonFont);
        letterbutton5.setFont(buttonFont);
        letterbutton6.setFont(buttonFont);
        letterbutton7.setFont(buttonFont);

        letterbutton4.setForeground(darkYellow);

        StrategyNewPuzzle strategyNewPuzzle = new StrategyNewPuzzle(letterbutton1, letterbutton2, letterbutton3, letterbutton4, letterbutton5, letterbutton6, letterbutton7);

    /*********************************************************************/
    /**************************GUESSING TEXTBOX***************************/

    //border view

    Border goldBorder = BorderFactory.createLineBorder(darkYellow, 4);

   
    Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 4);

    
    Border compoundBorder = BorderFactory.createCompoundBorder(goldBorder, blackBorder);
//getting rid of caret blinking class paint view, controller noCaret 
    class NoCaret extends DefaultCaret {
        @Override
        public void paint(Graphics g) {
            
        }
    }
    //view
    JPanel panel = new JPanel(null);
    panel.setOpaque(false); 
    secondFrame.add(panel);
    
    JTextPane textPane = new JTextPane();
    
    textPane.setOpaque(false);
    textPane.setBorder(BorderFactory.createEmptyBorder());
    StyledDocument doc = textPane.getStyledDocument();
    SimpleAttributeSet center = new SimpleAttributeSet();
    StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
    doc.setParagraphAttributes(0, doc.getLength(), center, false);

    textPane.setEnabled(false);

    int textFieldWidth = 590;
    int textFieldHeight = 65;
    int maxCharacterCount = 15;
    
    Font textFieldFont = new Font("SansSerif", Font.BOLD, 39);
    textPane.setFont(textFieldFont);
    
    int xCenter = (secondFrame.getWidth() - textFieldWidth) / 2;
    int y = (secondFrame.getHeight() - textFieldHeight) / 9;
    
    textPane.setBounds(xCenter, y, textFieldWidth, textFieldHeight);
    panel.add(textPane);


    // Create JLabels view
JLabel outputLabel = new JLabel();
JLabel outputLabel2 = new JLabel();
JLabel outputLabel3 = new JLabel();
JLabel outputLabel4 = new JLabel();
JLabel outputLabel5 = new JLabel();
Font labelFont = new Font("Sans Serif", Font.BOLD, 21);
// Set the font for all labels with a larger font size
outputLabel.setFont(labelFont);
outputLabel2.setFont(labelFont);
outputLabel3.setFont(labelFont);
outputLabel4.setFont(labelFont);
outputLabel5.setFont(labelFont);

// Set other properties and positions for your labels (as you've already done)
outputLabel.setBounds(xCenter, y + textFieldHeight + 20, textFieldWidth, 30);
outputLabel.setHorizontalAlignment(SwingConstants.CENTER);

outputLabel2.setBounds(xCenter, y + textFieldHeight + 40, textFieldWidth, 30);
outputLabel2.setHorizontalAlignment(SwingConstants.CENTER);

outputLabel3.setBounds(xCenter, y + textFieldHeight + 60, textFieldWidth, 30);
outputLabel3.setHorizontalAlignment(SwingConstants.CENTER);

outputLabel4.setBounds(xCenter, y + textFieldHeight + 80, textFieldWidth, 30);
outputLabel4.setHorizontalAlignment(SwingConstants.CENTER);

outputLabel5.setBounds(xCenter, y + textFieldHeight + 100, textFieldWidth, 30);
outputLabel5.setHorizontalAlignment(SwingConstants.CENTER);
panel.add(outputLabel);
panel.add(outputLabel2);
panel.add(outputLabel3);
panel.add(outputLabel4);
panel.add(outputLabel5);

//calls class and helps get rid of Caret
    textPane.setCaret(new NoCaret());
     DocumentFilter filter = new DocumentFilter() {
        //being all capital letters in textbox no matter what view
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                fb.insertString(offset, text.toUpperCase(), attr);
            }
            //replacing any lowercase characters into uppercase view
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                fb.replace(offset, length, text.toUpperCase(), attrs);
            }
        };
        //setting 2 methods to always be running so it is always uppercase view
        ((AbstractDocument) textPane.getDocument()).setDocumentFilter(filter);
        
        //typing controller, maybe updating view and model
        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char typedChar = e.getKeyChar();
                char[] tester = baseWord.toCharArray();
                //ensuring you arent typing words that are not in baseword, controller
                boolean contained = false;
        
                for (char c : tester) {
                    if (Character.toUpperCase(c) == Character.toUpperCase(typedChar)) {
                        contained = true;
                        break;
                    }
                }
        
                if (!Character.isAlphabetic(typedChar) || !contained) {
                    e.consume(); 
                } else if (charCount >= maxCharacterCount) {
                    e.consume(); //doesnt let you type any numbers or symbols, making sure doesnt go over letter limit controller
                } else {
                    charCount++;
                }
            }
            //making sure when a key is pressed
            @Override
            public void keyPressed(KeyEvent e) {
                textPane.requestFocusInWindow();
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) { //backspace charcount goes down controller
            
                    charCount = Math.max(0, charCount - 1);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {//enter submit word controller 
                    
                    String enteredWord = textPane.getText().trim();
                    enteredWord = enteredWord.toLowerCase();
                    
                    e.consume();
                    textPane.setText("");
                    charCount = 0;
                    int initialSize = master.foundWords.size();

                    baseWord = baseWord.toLowerCase();
        
                    if (master.foundWords.contains(enteredWord)) {
                        outputLabel.setText("You already guessed this word correctly. Try again!");
                        
                    } else {
                        enteredWord = enteredWord.toUpperCase();
                        master.guessGUI(enteredWord, baseWord, acceptedWordList, master.playerRank(baseWord, master.totalPoints, acceptedWordList));
                        if (master.foundWords.size() > initialSize) {
                            if(master.isPangram(enteredWord, baseWord)){
                                String enteredWordText = "<font color='#CC9900'>" + enteredWord + "</font> is a valid word, and a <font color='#CC9900'>PANGRAM</font>... Well Done!";
                                outputLabel.setText("<html>" + enteredWordText + "</html>");
                            }else{
                                String enteredWordText = "<font color='#CC9900'>" + enteredWord + "</font> is a valid word!";
                                outputLabel.setText("<html>" + enteredWordText + "</html>");
                            }
                            String playerRank = master.playerRank(baseWord, master.totalPoints, acceptedWordList);
                            String playerrank = playerRank; 
                            String labelText = "Your current rank is: <font color=#CC9900>" + playerrank + "</font>";
                            outputLabel2.setText("<html>" + labelText + "</html>"); 
                            String labelText1 = "Total points:   <font color='#CC9900'>" + master.totalPoints + "</font>";
                            outputLabel3.setText("<html>" + labelText1 + "</html>");
                            helpers.calculateRankDifference(playerRank, master.totalPoints, acceptedWordList, baseWord);
                            String nextRankText = "Total points needed for next rank   <font color='#CC9900'>" + helpers.nextRank + "</font>: ";
                            String pointsRequiredText = "<font color='#CC9900'>" + helpers.pointsRequired + "</font>";
                            outputLabel4.setText("<html>" + nextRankText + pointsRequiredText + "</html>");
                            String differenceText = "Points needed to reach next rank: <font color='#CC9900'>" + helpers.difference + "</font>";
                            outputLabel5.setText("<html>" + differenceText + "</html>");

                        } else {
                            outputLabel.setText("Invalid word, try again!");

                        }
                        enteredWord = enteredWord.toLowerCase();
                    }
                }//end
                baseWord = baseWord.toUpperCase();
            }

        });
        //after endter is pressed makes it so that you can instantly guess letters again, sets textbox empty controller and view
    textPane.requestFocusInWindow();
    String defaultText = "";
    textPane.setText(defaultText);
    
    

    textPane.setBorder(compoundBorder);
    //controller and view    
    textPane.addFocusListener(new FocusAdapter() {
        @Override
        public void focusGained(FocusEvent e) {
            if (textPane.getText().equals(defaultText)) {
                textPane.setText("");
            }
        }
    
        @Override
        public void focusLost(FocusEvent e) {
            if (textPane.getText().isEmpty()) {
                textPane.setText(defaultText);
            }
        }
    });
    
    secondFrame.setVisible(true);

    textPane.requestFocusInWindow();

    /**********************************************************************/
    /***********************SHUFFLE BUTTON LOGIC***************************/

        shufflePuzzle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shuffleWord = master.shuffle(shuffleWord, reqLetter);
                System.out.println(reqLetter);
                baseWord = shuffleWord;
                String noReqLetter = master.removeChar(baseWord, reqLetter);
                char[] bWLetters = noReqLetter.toCharArray();

                String bW1 = Character.toString(bWLetters[0]);
                String bW2 = Character.toString(bWLetters[1]);
                String bW3 = Character.toString(bWLetters[2]);
                String bW4 = Character.toString(reqLetter);
                String bW5 = Character.toString(bWLetters[3]);
                String bW6 = Character.toString(bWLetters[4]);
                String bW7 = Character.toString(bWLetters[5]);

                letterbutton1.setText(bW1.toUpperCase());
                letterbutton2.setText(bW2.toUpperCase());
                letterbutton3.setText(bW3.toUpperCase());
                letterbutton4.setText(bW4.toUpperCase());
                letterbutton5.setText(bW5.toUpperCase());
                letterbutton6.setText(bW6.toUpperCase());
                letterbutton7.setText(bW7.toUpperCase());

                textPane.requestFocusInWindow();
            
              }
        });   

    /**********************************************************************/
    /***********************NEW PUZZLE BUTTON LOGIC************************/

        newPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                outputLabel.setText("");
                outputLabel2.setText("");
                outputLabel3.setText("");
                outputLabel4.setText("");
                outputLabel5.setText("");
                master.totalPoints = 0;
                try {
                    baseWord = master.getBaseWord(master.dictionaryFile());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                reqLetter = master.getReqLetter(baseWord);
                try {
                    acceptedWordList = master.acceptedWords(baseWord, reqLetter);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                strategyNewPuzzle.execute(mainframe.this);
                
                textPane.setEnabled(true);

                textPane.requestFocusInWindow();
            }
        });

    /**********************************************************************/
    /**********************************************************************/


    /*********************************************************************/
    /********************NEW USER PUZZLE BUTTON LOGIC*********************/

        newUserPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String userWord = JOptionPane.showInputDialog(secondFrame, "Enter a base word for the puzzle:");
                userWord = userWord.toLowerCase();
                if(userWord.contains(" ")){
                    JOptionPane.showMessageDialog(secondFrame, "Bzzt. Make sure there are no spaces in your word! Bzz.");
                    return;
                }

                if (userWord != null && !userWord.isEmpty() && userWord.length() == 7) {

                    if (!master.isUnique(userWord)){

                        JOptionPane.showMessageDialog(secondFrame, "Bzzt. Oops, all letters have to be unique! Bzz.");
                        return;
                    }

                    baseWord = userWord;
                    reqLetter = master.getReqLetter(baseWord);

                    

                    try {
                        acceptedWordList = master.acceptedWords(baseWord, reqLetter);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }

                    
                    if (!acceptedWordList.contains(baseWord)){
                        
                        JOptionPane.showMessageDialog(secondFrame, "Buzz. Are you making stuff up now!  Make sure you type a valid word! Buzz.");
                        return;
        
                    }
                    
                    outputLabel.setText("");
                    outputLabel2.setText("");
                    outputLabel3.setText("");
                    outputLabel4.setText("");
                    outputLabel5.setText("");
                    

                }else{
                    if(userWord.length() == 0){
                        return;
                    }

                    JOptionPane.showMessageDialog(secondFrame, "Bzzuh Bzzoh, word has to have 7 letters! Buzz.");
                    return;
                }

                strategyNewPuzzle.execute(mainframe.this);
                
                textPane.setEnabled(true);

                textPane.requestFocusInWindow();
                
            }
        });
    /**********************************************************************/
    /**********************************************************************/

    /**********************************************************************/
    /*********************SAVE PUZZLE LOGIC********************************/
        savePuzzleButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String saveFileName = JOptionPane.showInputDialog("Enter a name for your saved game:");
                CliGameModel.setSaveFileName(saveFileName);
                if (saveFileName != null && !saveFileName.trim().isEmpty())
                {
                    try
                    {
                        List<String> possibleWords = master.acceptedWords(baseWord, reqLetter);
                        int maxPoints = helpers.possiblePoints(baseWord, possibleWords);
                        // Call the saveGameData method with the appropriate parameters
                        playerGameData.saveGameData(saveFileName, baseWord, master.foundWords, master.totalPoints, "" + reqLetter, maxPoints);
                    }
                    catch (FileNotFoundException e1)
                    {
                        System.err.println("File not found " + e1.getMessage());
                    }
                }

                else 
                {
                    JOptionPane.showMessageDialog(null, "You did not provide a valid save name. Game was not saved.");
                }
            }
        }
        );

    /**********************************************************************/
    /**********************************************************************/

    /**********************************************************************/
    /*********************LOAD PUZZLE LOGIC********************************/
        loadPuzzleButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {

                outputLabel.setText("");
                outputLabel2.setText("");
                outputLabel3.setText("");
                outputLabel4.setText("");
                outputLabel5.setText("");

                //fetch list of all saved game names
                List<String> saveNames = playerGameData.getAllSaveNames();

                //remove some irrelevant keys that are not save game names
                saveNames.remove("maxPoints");
                saveNames.remove("playerPoints");
                saveNames.remove("requiredLetter");
                saveNames.remove("baseWord");
                saveNames.remove("foundWords");

                // Show the names in a JOptionPane
                String chosenSave = (String) JOptionPane.showInputDialog(
                null, 
                "Select a saved game:", 
                "Load Game",
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                saveNames.toArray(), 
                saveNames.isEmpty() ? null : saveNames.get(0));

                if (chosenSave == null) 
                {
                    // User canceled or closed the dialog
                    return;
                }

                playerGameData.loadGameData(chosenSave); // Load selected game data 
                // Load game variables from playerGameData
                baseWord = playerGameData.getBaseWord();
                shuffleWord = playerGameData.getBaseWord();
                List<String> foundWords = playerGameData.getFoundWords();
                CliGameModel.setTotalPoints(playerGameData.getPlayerPoints());
                reqLetter = playerGameData.getRequiredLetter().charAt(0);
                int possiblePoints = playerGameData.getMaxPoints();
                CliGameModel.setSaveFileName(chosenSave);
                baseWord = baseWord.toLowerCase();
                
                try {
                    acceptedWordList = master.acceptedWords(baseWord, reqLetter);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }

                master.foundWords = foundWords;

                String noReqLetter = master.removeChar(baseWord, reqLetter);

                char[] bWLetters = noReqLetter.toCharArray();
                if (bWLetters.length != 6) {
                    // Handle error - loaded baseWord is not of expected length
                    System.out.println("Error: Loaded baseWord has incorrect length");
                    return;
                }

                // Update letters on the buttons
                letterbutton1.setText(Character.toString(bWLetters[0]).toUpperCase());
                letterbutton2.setText(Character.toString(bWLetters[1]).toUpperCase());
                letterbutton3.setText(Character.toString(bWLetters[2]).toUpperCase());
                letterbutton4.setText(Character.toString(reqLetter).toUpperCase()); // 4th button is the required one
                letterbutton5.setText(Character.toString(bWLetters[3]).toUpperCase());
                letterbutton6.setText(Character.toString(bWLetters[4]).toUpperCase());
                letterbutton7.setText(Character.toString(bWLetters[5]).toUpperCase());
                
                

                letterbutton1.setEnabled(true);
                letterbutton2.setEnabled(true);
                letterbutton3.setEnabled(true);
                letterbutton4.setEnabled(true);
                letterbutton5.setEnabled(true);
                letterbutton6.setEnabled(true);
                letterbutton7.setEnabled(true);

                
                textPane.setEnabled(true);
                
                textPane.requestFocusInWindow();
            }
            
        });
    /***********************************************************************/
    /**********************************************************************/

    secondFrame.add(letterbutton1);
    secondFrame.add(letterbutton2);
    secondFrame.add(letterbutton3);
    secondFrame.add(letterbutton4);
    secondFrame.add(letterbutton5);
    secondFrame.add(letterbutton6);
    secondFrame.add(letterbutton7);

    /***********************LETTER BUTTONS***********************************/
    
    letterbutton1.setPreferredSize(new Dimension(80, 80)); 
    letterbutton1.setFont(new Font("SansSerif", Font.BOLD, 24)); 
    letterbutton1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = textPane.getText();
            String button1Text = letterbutton1.getText();
            if(currentText.length() < maxCharacterCount){
                charCount++;
                currentText += button1Text;
                textPane.setText(currentText);
            }
            textPane.requestFocusInWindow();
        }
    });

    letterbutton2.setPreferredSize(new Dimension(80, 80)); 
    letterbutton2.setFont(new Font("SansSerif", Font.BOLD, 24)); 
    letterbutton2.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = textPane.getText();
            String button2Text = letterbutton2.getText();
            if(currentText.length() < maxCharacterCount){
                charCount++;
                currentText += button2Text;
                textPane.setText(currentText);
            }
            textPane.requestFocusInWindow();
        }
    });

    letterbutton3.setPreferredSize(new Dimension(80, 80)); 
    letterbutton3.setFont(new Font("SansSerif", Font.BOLD, 24)); 
    letterbutton3.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = textPane.getText();
            String button3Text = letterbutton3.getText();
            if(currentText.length() < maxCharacterCount){
                charCount++;
                currentText += button3Text;
                textPane.setText(currentText);
            }
            textPane.requestFocusInWindow();
        }
    });

    letterbutton4.setPreferredSize(new Dimension(80, 80)); 
    letterbutton4.setFont(new Font("SansSerif", Font.BOLD, 24)); 
    letterbutton4.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = textPane.getText();
            String button4Text = letterbutton4.getText();
            if(currentText.length() < maxCharacterCount){
                charCount++;
                currentText += button4Text;
                textPane.setText(currentText);
            }
            textPane.requestFocusInWindow();
        }
    });

    letterbutton5.setPreferredSize(new Dimension(80, 80)); 
    letterbutton5.setFont(new Font("SansSerif", Font.BOLD, 24)); 
    letterbutton5.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = textPane.getText();
            String button5Text = letterbutton5.getText();
            if(currentText.length() < maxCharacterCount){
                charCount++;
                currentText += button5Text;
                textPane.setText(currentText);
            }   
            textPane.requestFocusInWindow();
        }
    });

    letterbutton6.setPreferredSize(new Dimension(80, 80)); 
    letterbutton6.setFont(new Font("SansSerif", Font.BOLD, 24)); 
    letterbutton6.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = textPane.getText();
            String button6Text = letterbutton6.getText();
            if(currentText.length() < maxCharacterCount){
                charCount++;
                currentText += button6Text;
                textPane.setText(currentText);
            }
            textPane.requestFocusInWindow();
        }
    });

    letterbutton7.setPreferredSize(new Dimension(80, 80)); 
    letterbutton7.setFont(new Font("SansSerif", Font.BOLD, 24)); 
    letterbutton7.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentText = textPane.getText();
            String button7Text = letterbutton7.getText();
            if(currentText.length() < maxCharacterCount){
                charCount++;
                currentText += button7Text;
                textPane.setText(currentText);
            }
            textPane.requestFocusInWindow();
        }
    });

    /***********************************************************************/
    /*********************HOW TO PLAY BUTTON LOGIC**************************/

    howToPlayButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (howToPlayDialog == null) {
                howToPlayDialog = new JDialog(mainFrame, "How To Play", true);
                howToPlayDialog.setSize(400, 300);
                howToPlayDialog.setLocationRelativeTo(mainFrame);
                
                JTextArea helpTextArea = new JTextArea();
                helpTextArea.setEditable(false);
                Color darkYellow = new Color(204, 153, 0);
                helpTextArea.setBackground(darkYellow);
                helpTextArea.setWrapStyleWord(true);
                helpTextArea.setLineWrap(true);
                helpTextArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
                helpTextArea.setForeground(Color.BLACK);
                helpTextArea.setText("Instructions:\n"
                + "- Your goal is to create words using 7 unique letters with a required letter.\n"
                + "- Words must contain at least 4 letters.\n"
                + "- Words must include the required letter.\n"
                + "- Letters can be used more than once.\n"
                + "Buttons:\n"
                + "1. NEW PUZZLE: Generates a new puzzle with 7 unique letters and a required letter.\n"
                + "2. CUSTOM PUZZLE: Generates a new puzzle with a word of your choice using 7 unique letters and a required letter.\n"
                + "3. FOUND WORDS: Generates a list of words that you have found.\n"
                + "4. PRESS ENTER: Allows you to guess your words.\n"
                + "5. SHUFFLE PUZZLE: Allows you to shuffle around the letters.\n"
                + "6. SAVE PUZZLE: Lets you save a blank puzzle.\n"
                + "7. LOAD PUZZLE: The player can load a saved game.\n"
                + "8. PRESS ENTER : The player can see their rank and progress on a current puzzle.\n"
                + "9. EXIT : Leave the application."
        );

                // Wrap the text area in a JScrollPane
                JScrollPane scrollPane = new JScrollPane(helpTextArea);
                scrollPane.setPreferredSize(new Dimension(380, 250)); // Set the preferred size of the scroll pane
    
                // Set the scroll pane as the content pane of the dialog
                howToPlayDialog.setContentPane(scrollPane);
            }
    
            if (!howToPlayDialog.isVisible()) {
                howToPlayDialog.setVisible(true);
            } else {
                howToPlayDialog.setVisible(false);
            }
            textPane.requestFocusInWindow();
        }
    });
    

    /***********************************************************************/
    /*********************FOUND WORD LIST LOGIC****************************/


    
       foundWordsButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        updateFoundWordsDialog(); // Call the method to update the dialog
        if (!foundwords.isVisible()) {
            foundwords.setVisible(true);
        } else {
            foundwords.setVisible(false);
        }
        textPane.requestFocusInWindow();
    }
    });

    

    /**********************************************************************/
    /************************EXIT BUTTON LOGIC*****************************/
    
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    /**********************************************************************/
    /**********************************************************************/




    /**********************************************************************/
    /**********************************************************************/  

        // Add buttons to the button panel view
        buttonPanel.add(shufflePuzzle);
        buttonPanel.add(newUserPuzzleButton);
        buttonPanel.add(newPuzzleButton);
        buttonPanel.add(savePuzzleButton);
        buttonPanel.add(loadPuzzleButton);
        buttonPanel.add(howToPlayButton);
        buttonPanel.add(foundWordsButton);
        buttonPanel.add(exitButton);

        buttonPanel2.add(letterbutton1);
        buttonPanel2.add(letterbutton2);
        buttonPanel2.add(letterbutton3);
        buttonPanel2.add(letterbutton4);
        buttonPanel2.add(letterbutton5);
        buttonPanel2.add(letterbutton6);
        buttonPanel2.add(letterbutton7);

        secondFrame.add(buttonPanel, BorderLayout.SOUTH);
        secondFrame.add(buttonPanel2, BorderLayout.CENTER);
        secondFrame.setVisible(true);
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length > 0 && args[0].equals("--cli")) {
            CliGameModel model = new CliGameModel();
            CliGameView view = new CliGameView(model);
            CliGameController controller = new CliGameController(model, view);

            try {
                controller.startGame();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new mainframe();
                }
            });
        }
    }
    
}