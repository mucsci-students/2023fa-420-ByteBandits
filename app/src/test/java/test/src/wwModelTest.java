//Authors: Joshua Dawson, Ilynd Rapant, Logan Wasmer, Jose De La Cruz

package test.src;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import app.src.main.java.CliGameModel;
// Removed unused imports

public class wwModelTest {
    private CliGameModel game;

    @Before
    public void setUp() throws FileNotFoundException {
        game = new CliGameModel();
    }

    @Test
    public void testInitGame() throws Exception {
        game.initGame();

        assertEquals("Total points should be initialized to 0", 0, game.getTotalPoints());
        assertEquals("There shouldn't be a base word until the user requests one.","       ", game.getBaseWord());
        assertEquals("There shouldn't be a shuffled version of the base word initially.", "       ", game.getShuffleWord());
        assertEquals("Player rank should be initialized to an empty string.", "", game.getPlayerRank());
        assertTrue("List of accepted words should be empty at the start.", CliGameModel.getAcceptedWordList().isEmpty());
        assertTrue("List of found words should be empty at the start.", game.getFoundWords().isEmpty());
    }

/**********************************************************************/
/**********************************************************************/
//Tests for newPuzzle

    /**
    * This test verifies that the newPuzzle() method correctly initializes
    * the game state, specifically ensuring that total points are set to zero
    * and the list of found words is empty.
    */
    @Test
    public void testNewPuzzle_Initialization() throws FileNotFoundException, InterruptedException {
        String mockInput = "jackpot\n/q\n";
        ByteArrayInputStream in = new ByteArrayInputStream(mockInput.getBytes());
        Scanner mockScanner = new Scanner(in);

        game.setScanner(mockScanner);  
        CliGameModel.newPuzzle();

        assertEquals(0, game.getTotalPoints());
        assertTrue(game.getFoundWords().isEmpty());
    }      
    
    /**
    * This test confirms that the newPuzzle() method sets a valid base word
    * for the puzzle. The base word should not be null, must have a length 
    * of 7, and should consist of unique characters.
    */
    /** 
    @Test
    public void testNewPuzzle_BaseWordSet() throws FileNotFoundException, InterruptedException {
        CliGameModel.newPuzzle();
        assertEquals("Base word should be set to a string of seven spaces.", "       ", game.getBaseWord());
        assertEquals("Base word should have a length of 7.", 7, game.getBaseWord().length());
        assertTrue("Base word should consist of unique characters.", isUniqueCharacters(game.getBaseWord()));  
        //TODO: FIX THIS TEST      
    }
    */

    /**
    * This test checks that the newPuzzle() method correctly sets a required
    * letter for the game. This required letter must be present in the base word.
    */
    @Test
    public void testNewPuzzle_RequiredLetterSet() throws FileNotFoundException, InterruptedException {
        String mockInput = "jackpot\n/q\n";  
        ByteArrayInputStream in = new ByteArrayInputStream(mockInput.getBytes());
        CliGameModel.console = new Scanner(in);

        CliGameModel.newPuzzle();

        

        char reqLetter = CliGameModel.getReqLetter1("jackpot");
        assertTrue("jackpot".contains(String.valueOf(reqLetter)));
        CliGameModel.console = new Scanner(System.in);
    }

    /**
    * This test ensures that the newPuzzle() method generates a list of accepted words
    * for the given puzzle. The list should not be empty, and every word in the list
    * must contain the required letter.
    */
    /** 
    @Test
    public void testNewPuzzle_AcceptedWordsSet() throws FileNotFoundException, InterruptedException {
    // Mocking the input for console
    String mockInput = "jackpot\n/q\n";  // added /q to exit the guess loop
    ByteArrayInputStream in = new ByteArrayInputStream(mockInput.getBytes());
    CliGameModel.console = new Scanner(in);
    
    // Call newPuzzle to set up the base word, required letter, and accepted word list
    CliGameModel.newPuzzle();
    
    // Retrieve the accepted word list and required letter that were set in newPuzzle
    List<String> acceptedWordList = CliGameModel.getAcceptedWordList();  
    char reqLetter = CliGameModel.getReqLetter1("jackpot");  

    assertFalse(acceptedWordList.isEmpty());

    for(String word : acceptedWordList) {
        assertTrue(word.contains(String.valueOf(reqLetter)));
    }
    

    // Reset the scanner
    CliGameModel.console = new Scanner(System.in);
}
    TODO: FIX THIS TEST
    */
/**********************************************************************/
/**********************************************************************/
//Tests for basePuzzle

    /**
    * Test to verify that if the user inputs a valid 7-letter word for baseWord, 
    * the baseWord in the game model should be set to that word.
    */
    @Test
    public void testBasePuzzleForCorrectWordLength() throws FileNotFoundException, InterruptedException {
        CliGameModel mockGame = mock(CliGameModel.class);
        when(mockGame.getUserInput()).thenReturn("example");
    
        mockGame.basePuzzle();
    
        verify(mockGame, times(1));
        CliGameModel.setBaseWord("example");
    }

    /**
    * Test to verify that if the user inputs a word that is not 7 letters in length, 
    * the baseWord in the game model should not be set to that word.
    */
    @Test
    public void testBasePuzzleForIncorrectWordLength() throws FileNotFoundException, InterruptedException {
        // Using a mock scanner to simulate user input for baseWord
        game.setScanner(new Scanner("examples"));

        game.basePuzzle();
    
        assertNotEquals("Base word should not be 'examples' due to length", "examples", game.getBaseWord());
    }   

    /**
    * Test to verify that if the user inputs a word with repeating characters, 
    * the baseWord in the game model should not be set to that word.
    */
    @Test
    public void testBasePuzzleForUniqueCharacters() throws FileNotFoundException, InterruptedException {
        // Using a mock scanner to simulate user input for baseWord
        game.setScanner(new Scanner("letters"));

        game.basePuzzle();
    
        assertNotEquals("Base word should not be 'letters' due to repeating characters", "letters", game.getBaseWord());
    }

    /**
     * Test to verify that if the user inputs a word that is not in the accepted words list, 
     * the baseWord in the game model should not be set to that word.
     */
    @Test
    public void testBasePuzzleForNotInAcceptedWords() throws FileNotFoundException, InterruptedException {
        // Assume 'notaword' is not in the accepted words list
        // Using a mock scanner to simulate user input for baseWord
        game.setScanner(new Scanner("notaword"));

        game.basePuzzle();
    
        assertNotEquals("Base word should not be 'notaword' since it's not in accepted words", "notaword", game.getBaseWord());
    }

/**********************************************************************/
/**********************************************************************/
//Tests for errorFirst

    @Test
    public void testErrorFirst() {
        assertFalse("Should return false since a game is initialized and baseWord shouldn't have spaces.", game.errorFirst());

        // Introducing error: Making the second character of baseWord a space.
        String currentBaseWord = game.getBaseWord();
        if (currentBaseWord.length() >= 2) {
            String erroneousBaseWord = currentBaseWord.charAt(0) + " " + currentBaseWord.substring(2);
            CliGameModel.setBaseWord(erroneousBaseWord);
        }
        assertTrue("Should return true since the second character in baseWord is a space.", game.errorFirst());
    }

/**********************************************************************/
/**********************************************************************/
//Tests for guess
//TODO: Get mockito to run mock inputs.

/**********************************************************************/
/**********************************************************************/
//Tests for shuffle

    @Test
    public void testShuffleWord() {
        String baseWord = "jackpot";
        char reqLetter = 'a';

        String shuffled = CliGameModel.shuffle(baseWord, reqLetter);

        //Base Test
        assertNotEquals("Shuffled word should be different from the base word.", baseWord, shuffled);
        assertTrue("Shuffled word should contain all characters from the base word.", containsAllChars(baseWord, shuffled));
        
        //Multiple Calls Test
        String secondShuffle = CliGameModel.shuffle(baseWord, reqLetter);
        assertNotEquals("Consecutive shuffles may produce different results.", shuffled, secondShuffle);

        // Required Letter Presence
        assertTrue("Shuffled word should contain the required letter.", shuffled.indexOf(reqLetter) != -1);

        //Length Consistency
        assertEquals("Shuffled word should have the same length as the base word.", baseWord.length(), shuffled.length());

        //No Extra Characters
        assertTrue("Shuffled word should not contain any characters not in the base word.", containsOnlyCharsFrom(baseWord, shuffled));
    }

/**********************************************************************/
/**********************************************************************/

    @Test
    public void testPointsPWord() {
        //char requiredLetter = 'a';

        // Tests using "jackpot" as the base word
        int points = CliGameModel.pointsPWord("jackpot", "jack");
        assertEquals("A word of length 4 containing the required letter should give 1 point.", 1, points);
    
   

        points = CliGameModel.pointsPWord("jackpot", "pack");
        assertEquals("A word of length 4 containing the required letter should give 1 point.", 1, points);
    
        points = CliGameModel.pointsPWord("jackpot", "jackpo");
        assertEquals("A word of length 6 containing the required letter should give 6 points.", 6, points);

        points = CliGameModel.pointsPWord("jackpot", "jackpot");
        assertEquals("A pangram (all letters) containing the required letter should give 14 points.", 14, points); // 7 for word length + 7 bonus for pangram


}

    @Test
    public void testDictionaryFile() throws Exception {
    List<String> dictionary = CliGameModel.dictionaryFile();

    // Ensure the dictionary file is loaded
    assertNotNull("Should successfully load the dictionary file.", dictionary);

    // Ensure the dictionary file is non-empty
    assertFalse("Dictionary should not be empty.", dictionary.isEmpty());

    // Check for the presence of valid 7-letter English words
    assertTrue("Dictionary should contain the word 'jackpot'.", dictionary.contains("jackpot"));

    // Test for unique characters in 7-letter words
    for(String word : dictionary) {
        if(word.length() == 7) {
            assertTrue("All 7-letter words in the dictionary should have unique characters. Found a word with repeating characters: " + word, 
                       word.chars().distinct().count() == 7);
        }
    }

    // Check for the absence of invalid words
    assertFalse("Dictionary should not contain the word 'example' since it has repeating letters.", dictionary.contains("example"));
    assertFalse("Dictionary should not contain the word 'aaa'.", dictionary.contains("aaa"));
    assertFalse("Dictionary should not contain non-English words like 'abcdxyz'.", dictionary.contains("abcdxyz"));
}


//***************************************************************************//
//**********************HELPER FUNCTIONS*************************************//

    //Helper function to check if one string contains all characters of another string.
    private boolean containsAllChars(String base, String check) {
        for (char c : base.toCharArray()) {
            if (!check.contains(Character.toString(c))) {
                return false;
        }
    }
    return true;
    }

    //Helper function to check if one string contains only characters from another string
    private boolean containsOnlyCharsFrom(String base, String check) {
        for (char c : check.toCharArray()) {
            if (!base.contains(Character.toString(c))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUniqueCharacters(String str) {
        // If the string has more than 128 characters (ASCII set), return false.
        if (str.length() > 128) return false;
    
        boolean[] char_set = new boolean[128];
        for (int i = 0; i < str.length(); i++) {
            int val = str.charAt(i);
            if (char_set[val]) return false; // Already found this char in string.
            char_set[val] = true;
        }
        return true;
    }
    
}