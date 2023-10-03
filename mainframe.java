// Authors: Joshua Dawson, Logan Wasmer, Jose De La Cruz, Ilynd Rapant
/***************************************************************/
/***************************************************************/
//Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/***************************************************************/
/***************************************************************/

public class mainframe {
    
    private JFrame mainFrame;
    private JFrame secondFrame;
    private JDialog howToPlayDialog;
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
                //Logic for "Load Puzzle" here
            }
        });
    /***********************************************************************/
    /**********************************************************************/

    /***********************************************************************/
    /*********************HOW TO PLAY BUTTON LOGIC**************************/
    
    howToPlayButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (howToPlayDialog == null) {
                // Create the dialog if it doesn't exist
                howToPlayDialog = new JDialog(mainFrame, "How To Play", true);
                howToPlayDialog.setSize(400, 300);
                howToPlayDialog.setLocationRelativeTo(mainFrame);
                JTextArea helpTextArea = new JTextArea();
                helpTextArea.setEditable(false);
                helpTextArea.setWrapStyleWord(true);
                helpTextArea.setLineWrap(true);
                helpTextArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
                helpTextArea.setForeground(Color.BLACK);
                helpTextArea.setText("Instructions:\n"
                        + "- Your goal is to create words using 7 unique letters with a required letter.\n"
                        + "- Words must contain at least 4 letters.\n"
                        + "- Words must include the required letter.\n"
                        + "- Letters can be used more than once.\n"
                        + "Commands:\n"
                        + "1. /newpuzzle: Generates a new puzzle with 7 unique letters and a required letter.\n"
                        + "2. /basepuzzle: Generates a new puzzle with a word of your choice using 7 unique letters and a required letter.\n"
                        + "3. /showpuzzle: Shows the current puzzle you are working on.\n"
                        + "4. /foundwords: Generates a list of words that you have found.\n"
                        + "5. /guess: Allows you to guess your words.\n"
                        + "6. /shuffle: Allows you to shuffle around the letters.\n"
                        + "7. /savepuzzle: Lets you save a blank puzzle.\n"
                        + "8. /savecurr: Lets you save a puzzle that may have been partially played.\n"
                        + "9. /loadpuzzle: The player can load a saved game.\n"
                        + "10. /showstatus : The player can see their rank and progress on a current puzzle.\n"
                        + "11. /exit : Leave the application."
                        
                );
                howToPlayDialog.add(new JScrollPane(helpTextArea));
            }

            // Show or hide the dialog when the button is clicked
            if (!howToPlayDialog.isVisible()) {
                howToPlayDialog.setVisible(true);
            } else {
                howToPlayDialog.setVisible(false);
            }
        }
    });

        //secondFrame.add(new JScrollPane(helpTextArea), BorderLayout.CENTER);

    /**********************************************************************/
    /**********************************************************************/

    /**********************************************************************/
    /*********************GUI->CLI BUTTON LOGIC****************************/
        guiToCliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Logic for "GUI->CLI" here
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
