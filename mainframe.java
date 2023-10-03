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
import java.util.List;
import java.io.*;
import java.util.*;
/***************************************************************/
/***************************************************************/

public class mainframe {

    Color darkYellow = new Color(204, 153, 0);
    class CustomButton extends JButton {
        private Color originalBackgroundColor;
        private boolean isLetterButton4;
    
        public CustomButton(String text, boolean isLetterButton4) {
            super(text);
            this.isLetterButton4 = isLetterButton4;
            initialize();
        }
    
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

    private playerData playerGameData = new playerData();
    
    playerData saveFile = new playerData();
    String baseWord = "       ";
    char reqLetter = master.getReqLetter(baseWord);
    String shuffleWord = baseWord;
    List<String> acceptedWordList;
    char[] bWLetters = baseWord.toCharArray();
    
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

    public mainframe() {
        mainFrame = new JFrame("Welcome to Wordy Wasps");
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
        mainFrame.setSize(screenSize);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon gifIcon = new ImageIcon("guicontent/matrixGif.gif");

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
    
        // Panel for waspGif
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
    
        // Load the waspGif
        ImageIcon gifIcon1 = new ImageIcon("guicontent/finalWaspGif.gif");
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
        CustomButton guiToCliButton = new CustomButton("GUI -> CLI", false);
        guiToCliButton.setBackground(new Color(204, 153, 0));
        guiToCliButton.setOpaque(true); // Make the button opaque
        guiToCliButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        guiToCliButton.setPreferredSize(new Dimension(200, 60)); // Increase the width
        guiToCliButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.dispose();
            try {
                master.main(new String[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    });
    
        // Add both buttons to the button panel
        buttonPanel.add(playButton);
        buttonPanel.add(guiToCliButton);
    
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
        loadPuzzleButton.setPreferredSize(buttonSize);
        howToPlayButton.setPreferredSize(buttonSize);
        foundWordsButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 15);

        shufflePuzzle.setFont(buttonFont);
        newPuzzleButton.setFont(buttonFont);
        newUserPuzzleButton.setFont(buttonFont);
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




    /*********************************************************************/
    /**************************GUESSING TEXTBOX***************************/

   
    Border goldBorder = BorderFactory.createLineBorder(darkYellow, 4);

   
    Border blackBorder = BorderFactory.createLineBorder(Color.BLACK, 4);

    
    Border compoundBorder = BorderFactory.createCompoundBorder(goldBorder, blackBorder);

    class NoCaret extends DefaultCaret {
        @Override
        public void paint(Graphics g) {
            
        }
    }
    
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
    int y = (secondFrame.getHeight() - textFieldHeight) / 2 - 400;
    
    textPane.setBounds(xCenter, y, textFieldWidth, textFieldHeight);
    panel.add(textPane);
    
    textPane.setCaret(new NoCaret());
     DocumentFilter filter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                fb.insertString(offset, text.toUpperCase(), attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                fb.replace(offset, length, text.toUpperCase(), attrs);
            }
        };

        // Attach the filter to the Document of the JTextPane
        ((AbstractDocument) textPane.getDocument()).setDocumentFilter(filter);
    
        textPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char typedChar = e.getKeyChar();
                StyledDocument doc = textPane.getStyledDocument();
                if (doc.getLength() >= maxCharacterCount || Character.isSpaceChar(typedChar)) {
                    e.consume();
                }
            }
        });
    
    String defaultText = "";
    textPane.setText(defaultText);
    
    

    textPane.setBorder(compoundBorder);

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


    /**********************************************************************/
    /***********************SHUFFLE BUTTON LOGIC***************************/

        shufflePuzzle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                shuffleWord = master.shuffle(baseWord, reqLetter);

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
            
              }
        });   

    /**********************************************************************/
    /***********************NEW PUZZLE BUTTON LOGIC************************/

        newPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                master.foundWords = new ArrayList<>();
                
                shuffleWord = master.shuffle(baseWord, reqLetter);

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

                
                textPane.setEnabled(true);
            }
        });

    /**********************************************************************/
    /**********************************************************************/


    /*********************************************************************/
    /********************NEW USER PUZZLE BUTTON LOGIC*********************/

        newUserPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String baseSave = baseWord;
                char reqSave = reqLetter;

                String userWord = JOptionPane.showInputDialog(secondFrame, "Enter a base word for the puzzle:");

                if (userWord != null && !userWord.isEmpty() && userWord.length() == 7) {

                    if (!master.isUnique(userWord)){

                        JOptionPane.showMessageDialog(secondFrame, "Bzzt. Oops, all letters have to be unique! Bzz.");
                        baseWord = baseSave;
                        reqLetter = reqSave;
                        return;
                    }

                    reqLetter = master.getReqLetter(userWord);

                    userWord = userWord.toLowerCase();

                    try {
                        acceptedWordList = master.acceptedWords(userWord, reqLetter);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }

                    
                    if (!acceptedWordList.contains(userWord)){
                        
                        JOptionPane.showMessageDialog(secondFrame, "Buzz. Are you making stuff up now!  Make sure you type a valid word! Buzz.");
                        baseWord = baseSave;
                        reqLetter = reqSave;
                        
                        try {
                            acceptedWordList = master.acceptedWords(baseWord, reqLetter);
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }

                        return;
                    }
                    
                    baseWord = userWord.toUpperCase(); 
                    reqLetter = master.getReqLetter(baseWord);

                }else{
                    if(userWord.length() == 0){
                        return;
                    }

                    JOptionPane.showMessageDialog(secondFrame, "Bzzuh Bzzoh, word has to have 7 letters! Buzz.");
                    return;
                }


                master.totalPoints = 0;

                master.foundWords = new ArrayList<>();
                
                shuffleWord = master.shuffle(baseWord, reqLetter);
                
                // Letter change code goes here after letters are created
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

                
                textPane.setEnabled(true);
                
            }
        });

    /**********************************************************************/
    /**********************************************************************/

    /**********************************************************************/
    /*********************LOAD PUZZLE LOGIC********************************/
        loadPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerGameData.loadGameData(); // Load game data from the JSON file

                String baseWord = playerGameData.getBaseWord();
                List<String> foundWords = playerGameData.getFoundWords();
                int playerPoints = playerGameData.getPlayerPoints();
                String requiredLetter = playerGameData.getRequiredLetter();
                int maxPoints = playerGameData.getMaxPoints();

                // Create JLabel components to display the variables
                JLabel baseWordLabel = new JLabel("Base Word: " + baseWord);
                JLabel foundWordsLabel = new JLabel("Found Words: " + foundWords);
                JLabel playerPointsLabel = new JLabel("Player Points: " + playerPoints);
                JLabel requiredLetterLabel = new JLabel("Required Letter: " + requiredLetter);
                JLabel maxPointsLabel = new JLabel("Max Points: " + maxPoints);

                // Create a JPanel to hold the labels
                JPanel labelsPanel = new JPanel();
                labelsPanel.setLayout(new GridLayout(5, 1));
                labelsPanel.add(baseWordLabel);
                labelsPanel.add(foundWordsLabel);
                labelsPanel.add(playerPointsLabel);
                labelsPanel.add(requiredLetterLabel);
                labelsPanel.add(maxPointsLabel);

                // Set the background color for the labels panel
                labelsPanel.setBackground(new Color(204, 153, 0));

                // Remove all components from the mainFrame's content pane
                mainFrame.getContentPane().removeAll();

                // Add the labels panel to the mainFrame's content pane
                mainFrame.getContentPane().add(labelsPanel, BorderLayout.CENTER);

                // Repaint the mainFrame to update the display
                mainFrame.revalidate();
                mainFrame.repaint();
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
                currentText += button1Text;
                textPane.setText(currentText);
            }
            
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
                currentText += button2Text;
                textPane.setText(currentText);
            }
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
                currentText += button3Text;
                textPane.setText(currentText);
            }
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
                currentText += button4Text;
                textPane.setText(currentText);
            }
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
                currentText += button5Text;
                textPane.setText(currentText);
            }   
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
                currentText += button6Text;
                textPane.setText(currentText);
            }
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
                currentText += button7Text;
                textPane.setText(currentText);
            }
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
                        + "4. /guess: Allows you to guess your words.\n"
                        + "5. SHUFFLE PUZZLE: Allows you to shuffle around the letters.\n"
                        + "6. /savepuzzle: Lets you save a blank puzzle.\n"
                        + "7. /savecurr: Lets you save a puzzle that may have been partially played.\n"
                        + "8. /loadpuzzle: The player can load a saved game.\n"
                        + "9. /showstatus : The player can see their rank and progress on a current puzzle.\n"
                        + "10. EXIT : Leave the application."
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
        }
    });
    

    /***********************************************************************/
    /*********************FOUND WORD LIST LOGIC****************************/
        foundWordsButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
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
                if(master.foundWords.isEmpty()){
                    foundWordsArea.append("YOU HAVEN'T FOUND ANY WORDS");
                }
                for (String word : master.foundWords)
                {
                    foundWordsArea.append(word + "\n");
                }
                foundwords.add(new JScrollPane(foundWordsArea));
            }
            if (!foundwords.isVisible()) {
                foundwords.setVisible(true);
            } else {
                foundwords.setVisible(false);
            }
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

        // Add buttons to the button panel
        buttonPanel.add(shufflePuzzle);
        buttonPanel.add(newUserPuzzleButton);
        buttonPanel.add(newPuzzleButton);
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
    //IF YOU WANT TO ADD EXTRA SCREENS START HERE//

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                new mainframe();
                
            }
        });
    }
}