//Authors: Joshua Dawson, Ilynd Rapant, Logan Wasmer, Jose De La Cruz

package test.src;

import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import app.src.main.java.CliGameModel;
import app.src.main.java.CliGameView;
import app.src.main.java.highScores;
// Removed unused imports
import app.src.main.java.playerData;

public class wwModelTest {
    private CliGameModel game;
    private static playerData mockSaveFile;
    private highScores mockHighScores;
    
    @Before
    public void setUp() throws FileNotFoundException {
        game = new CliGameModel();
        mockSaveFile = mock(playerData.class);

        // Stubbing the mock to return specific values when methods are called
        when(mockSaveFile.getBaseWord()).thenReturn("jackpot");
        when(mockSaveFile.getFoundWords()).thenReturn(Arrays.asList("jack", "jackpot"));
        when(mockSaveFile.getPlayerPoints()).thenReturn(100);
        when(mockSaveFile.getRequiredLetter()).thenReturn("k");
        when(mockSaveFile.getMaxPoints()).thenReturn(200);

        // Assign the mock to the static field in CliGameModel
        CliGameModel.setSaveFile(mockSaveFile);
        mockHighScores = mock(highScores.class);
        game.setHighScores(mockHighScores);
    }

    @Test
    public void testInitGame() throws Exception {
        game.initGame();

        assertEquals("Total points should be initialized to 0", 0, CliGameModel.getTotalPoints());
        assertEquals("There shouldn't be a base word until the user requests one.","       ", game.getBaseWord());
        assertEquals("There shouldn't be a shuffled version of the base word initially.", "       ", game.getShuffleWord());
        assertEquals("Player rank should be initialized to an empty string.", "", CliGameModel.getPlayerRank());
        assertTrue("List of accepted words should be empty at the start.", CliGameModel.getAcceptedWordList().isEmpty());
        assertTrue("List of found words should be empty at the start.", CliGameModel.getFoundWords().isEmpty());
    }

/**********************************************************************/
/**********************************************************************/

/** 
 @Test
    public void testSavePuzzle() {
        // Setup
        CliGameModel.setTotalPoints(0);
        CliGameModel.setAcceptedWordList(Arrays.asList("jack", "jackpot"));
        CliGameModel.setBaseWord("jackpot");

        CliGameModel.setShuffleWord("jackpot"); 
        CliGameModel.setReqLetter('k'); 
        // Action
        CliGameModel.savePuzzle();

        // Assert
        // Verify the saveGameData method was called on mockSaveFile with the expected arguments
        verify(mockSaveFile).saveGameData(anyString(), eq("jackpot"), eq(Arrays.asList("jack", "jackpot")), eq(0), eq("k"), anyInt());
    }

    @Test
    public void testSaveCurr() {
        // Action
        CliGameModel.saveCurr();

        // Assert
        // Verify the saveGameData method was called on mockSaveFile with the expected arguments
        verify(mockSaveFile).saveGameData(anyString(), anyString(), anyList(), anyInt(), eq("k"), anyInt());
        
        
    }
*/
/**********************************************************************/
/**********************************************************************/
// @Test
// public void testLoadPuzzle() throws FileNotFoundException {
//     CliGameModel mockModel = mock(CliGameModel.class);
//     playerData mockSaveFile = mock(playerData.class);
//     when(mockSaveFile.getBaseWord()).thenReturn("jackpot");
//     when(mockSaveFile.getPlayerPoints()).thenReturn(100);
//     when(mockSaveFile.getFoundWords()).thenReturn(Arrays.asList("jack", "jackpot"));
//     when(mockSaveFile.getRequiredLetter()).thenReturn("k");
//     when(mockSaveFile.getMaxPoints()).thenReturn(200);

//     // Setting up the mock for CliGameModel
//     CliGameModel.setSaveFile(mockSaveFile);
//     CliGameModel.setBaseWord("jackpot");
//     CliGameModel.setTotalPoints(50);

//     // Action
//     CliGameModel.loadPuzzle();

//     // Assertions based on the mock's behavior
//     assertEquals("jackpot", mockModel.getBaseWord());
//     assertEquals(Arrays.asList("jack", "jackpot"), CliGameModel.getFoundWords());
//     assertEquals(100, CliGameModel.getTotalPoints());
//     assertEquals('k', CliGameModel.getReqLetter());
//     assertEquals(200, mockModel.getPossiblePoints());
// }

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
//Tests for Setters
    @Test
    public void testSetPossiblePoints() {
        CliGameModel.setPossiblePoints(100);
        assertEquals(100, CliGameModel.possiblePoints);
    }

    @Test
    public void testSetShuffleWord() {
        CliGameModel.setShuffleWord("word");
        assertEquals("word", CliGameModel.getShuffleWord());
    }

    @Test
    public void testSetTotalPoints() {
        CliGameModel.setTotalPoints(500);
        assertEquals(500, CliGameModel.getTotalPoints());
    }

    @Test
    public void testSetPlayerRank() {
        CliGameModel.setPlayerRank("Beginner");
        assertEquals("Beginner", CliGameModel.getPlayerRank());
    }

    @Test
    public void testSetFoundWords() {
        List<String> words = Arrays.asList("one", "two", "three");
        CliGameModel.setFoundWords(words);
        assertEquals(words, CliGameModel.getFoundWords());
    }

    @Test
    public void testSetReqLetter() {
        CliGameModel.setReqLetter('a');
        assertEquals('a', CliGameModel.getReqLetter());
    }

    @Test
    public void testSetSaveFile() {
        playerData data = new playerData(); 
        CliGameModel.setSaveFile(data);
        assertEquals(data, CliGameModel.getSaveFile());
    }

    @Test
    public void testSetAcceptedWordList() {
        List<String> acceptedWords = Arrays.asList("apple", "banana", "cherry");
        CliGameModel.setAcceptedWordList(acceptedWords);
        assertEquals(acceptedWords, CliGameModel.getAcceptedWordList());
    }

/**********************************************************************/
/**********************************************************************/
//Tests for Getters

@Test
public void testGetReqLetter() {
    char expectedReqLetter = 'k'; // Or whatever value you know it should be.
    CliGameModel.setReqLetter(expectedReqLetter); // Assuming you have a setter method. If not, you might need to set this through some other mechanism, like a constructor or another method in CliGameModel.
    
    char actualReqLetter = CliGameModel.getReqLetter();

    assertEquals("ReqLetter should match the set value", expectedReqLetter, actualReqLetter);
}

@Test
public void testGetSaveFileName() {
    String expectedFileName = "save1"; 
    CliGameModel.setSaveFileName(expectedFileName); 

    String actualFileName = CliGameModel.getSaveFileName();

    assertEquals("FileName should match the set value", expectedFileName, actualFileName);
}
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
    
        mockGame.getUserInput();
    
        verify(mockGame, times(1)).getUserInput();
        CliGameModel.setBaseWord("example");
    }

    @Test
    public void testGetUserInput() {
        String simulatedInput = "jackpot";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);

        CliGameModel cliGameModel = new CliGameModel(scanner);

        String userInput = cliGameModel.getUserInput();

        assertEquals(simulatedInput, userInput);
    }

    @Test
    public void testSetAuthor() {
        String testAuthor = "John Doe";
        CliGameModel.setAuthor(testAuthor);

        assertEquals(testAuthor, CliGameModel.getAuthor());
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
//Tests for PointsPWord
    @Test
    public void testPointsPWord() {
        //char requiredLetter = 'a';

        // Tests using "jackpot" as the base word
        int points = CliGameModel.pointsPWord("jackpot", "jack");
        assertEquals("A word of length 4 containing the required letter should give 1 point.", 1, points);
        
        points = CliGameModel.pointsPWord("jackpot", "j");
        assertEquals("A word of length 1 containing the required letter should give 0 points.", 0, points);
   
        points = CliGameModel.pointsPWord("jackpot", "pack");
        assertEquals("A word of length 4 containing the required letter should give 1 point.", 1, points);
        
        points = CliGameModel.pointsPWord("jackpot", "jackp");
        assertEquals("A word of length 6 containing the required letter should give 5 points.", 5, points);

        points = CliGameModel.pointsPWord("jackpot", "jackpo");
        assertEquals("A word of length 6 containing the required letter should give 6 points.", 6, points);

        points = CliGameModel.pointsPWord("jackpot", "jackpot");
        assertEquals("A pangram (all letters) containing the required letter should give 14 points.", 14, points); // 7 for word length + 7 bonus for pangram
}

/**********************************************************************/
/**********************************************************************/
//Tests for DicitonaryFile

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

/**********************************************************************/
/***********************************************************************/
//Tests for playerRank
 @Test
    public void testPlayerRank() throws FileNotFoundException {
        String baseWord = "jackpot";
        char reqLetter = 'j';
        List<String> possibleWords = CliGameModel.acceptedWords(baseWord, reqLetter);
        int maxPoints = CliGameModel.possiblePoints(baseWord, possibleWords);
        
        // Test for Beginner rank
        assertEquals("Beginner", CliGameModel.playerRank(baseWord, 0, possibleWords));
    
        // Test for Good Start rank
        assertEquals("Good Start", CliGameModel.playerRank(baseWord, (int) Math.round(0.02 * maxPoints), possibleWords));
    
        // Test for Moving Up rank
        assertEquals("Moving Up", CliGameModel.playerRank(baseWord, (int) Math.round(0.05 * maxPoints), possibleWords));
    
        // Test for Good rank
        assertEquals("Good", CliGameModel.playerRank(baseWord, (int) Math.round(0.08 * maxPoints), possibleWords));
    
        // Test for Solid rank
        assertEquals("Solid", CliGameModel.playerRank(baseWord, (int) Math.round(0.15 * maxPoints), possibleWords));
    
        // Test for Nice rank
        assertEquals("Nice", CliGameModel.playerRank(baseWord, (int) Math.round(0.25 * maxPoints), possibleWords));
    
        // Test for Great rank
        assertEquals("Great", CliGameModel.playerRank(baseWord, (int) Math.round(0.40 * maxPoints), possibleWords));
    
        // Test for Amazing rank
        assertEquals("Amazing", CliGameModel.playerRank(baseWord, (int) Math.round(0.50 * maxPoints), possibleWords));
    
        // Test for Genius rank
        assertEquals("Genius", CliGameModel.playerRank(baseWord, (int) Math.round(0.70 * maxPoints), possibleWords));
    
        // Test for Queen Bee rank
        assertEquals("Queen Bee", CliGameModel.playerRank(baseWord, maxPoints, possibleWords));
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