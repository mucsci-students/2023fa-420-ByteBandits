// Authors: Joshua Dawson, Logan Wasmer, Jose De La Cruz, Ilynd Rapant
/***************************************************************/
/***************************************************************/
//Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/***************************************************************/
/***************************************************************/

public class mainframe {
    private playerData playerGameData = new playerData();
    private JFrame mainFrame;
    private JFrame secondFrame;
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
        mainFrame.setSize(screenSize); 
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon gifIcon = new ImageIcon("C:\\Users\\17176\\git repository\\2023fa-420-ByteBandits\\guicontent\\matrixGif.gif");
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

        //Panel for waspGif
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        // Load the waspGif
        ImageIcon gifIcon1 = new ImageIcon("C:\\Users\\17176\\git repository\\2023fa-420-ByteBandits\\guicontent\\waspGif2.gif");
        JLabel gifLabel1 = new JLabel(gifIcon1);
        centerPanel.add(gifLabel1, BorderLayout.CENTER);

        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        //"PLAY" button
        JButton playButton = new JButton("PLAY");
        playButton.setBackground(new Color(204, 153, 0));
        playButton.setOpaque(true); // Make the button opaque
        playButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        playButton.setPreferredSize(new Dimension(200, 60));
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSecondScreen();
            }
        });
        backgroundPanel.add(playButton, BorderLayout.SOUTH);

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
        secondFrame.setSize(screenSize);
        secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        secondFrame.getContentPane().setBackground(new Color(255, 255, 153));

        // Create a panel for the buttons at the bottom
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); 
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Create buttons for the second screen
        JButton newPuzzleButton = new JButton("NEW PUZZLE");
        JButton loadPuzzleButton = new JButton("LOAD PUZZLE");
        JButton howToPlayButton = new JButton("HOW TO PLAY");
        JButton guiToCliButton = new JButton("GUI -> CLI");
        JButton exitButton = new JButton("EXIT");

        Color darkYellow = new Color(204, 153, 0);
        newPuzzleButton.setBackground(darkYellow); 
        loadPuzzleButton.setBackground(darkYellow); 
        howToPlayButton.setBackground(darkYellow); 
        guiToCliButton.setBackground(darkYellow); 
        exitButton.setBackground(darkYellow);

        Dimension buttonSize = new Dimension(280, 80); 
        newPuzzleButton.setPreferredSize(buttonSize);
        loadPuzzleButton.setPreferredSize(buttonSize);
        howToPlayButton.setPreferredSize(buttonSize);
        guiToCliButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 24);
        newPuzzleButton.setFont(buttonFont);
        loadPuzzleButton.setFont(buttonFont);
        howToPlayButton.setFont(buttonFont);
        guiToCliButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

    /**********************************************************************/
    /***********************NEW PUZZLE BUTTON LOGIC************************/

        newPuzzleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Logic for "New Puzzle" here
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

    /**********************************************************************/
    /**********************************************************************/

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
        buttonPanel.add(newPuzzleButton);
        buttonPanel.add(loadPuzzleButton);
        buttonPanel.add(howToPlayButton);
        buttonPanel.add(guiToCliButton);
        buttonPanel.add(exitButton);

        secondFrame.add(buttonPanel, BorderLayout.SOUTH);
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
