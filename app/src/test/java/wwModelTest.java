//Authors: Joshua Dawson, Ilynd Rapant, Logan Wasmer, Jose De La Cruz

package test.src;

import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import app.src.main.java.CliGameModel;
// Removed unused imports

public class wwModelTest {
    private CliGameModel game;

    @Before
    public void setUp() throws FileNotFoundException {
        CliGameModel game = new CliGameModel();
    }

    @Test
    public void testInitGame() throws Exception {
        game.initGame();

        assertEquals("Total points should be initialized to 0", 0, game.getTotalPoints());
        assertEquals("Player rank should be initialized to an empty string.", "", game.getPlayerRank());
        assertTrue("Found words list should be empty.", game.getFoundWords().isEmpty());
        // Add similar assertions for other initial values...
    }

    @Test
    public void testShuffleWord() {
        String baseWord = "example";
        char reqLetter = 'e';

        String shuffled = game.shuffle(baseWord, reqLetter);

        assertNotEquals("Shuffled word should be different from the base word.", baseWord, shuffled);
        assertTrue("Shuffled word should contain all characters from the base word.", containsAllChars(baseWord, shuffled));
    }

    @Test
    public void testErrorFirst() {
        assertFalse("Should return false since a game is initialized and baseWord shouldn't have spaces.", game.errorFirst());

        // Add logic to make the second character in baseWord a space, and then test if it returns true
    }

    @Test
    public void testPointsPWord() {
        int points = game.pointsPWord("example", "exam");
        assertEquals("A word of length 4 should give 1 point.", 1, points);

        // Test other word lengths and special cases like Pangrams...
    }

    @Test
    public void testDictionaryFile() throws Exception {
        assertNotNull("Should successfully load the dictionary file.", game.dictionaryFile());
    }

    // ... Add more tests for other methods ...

    private boolean containsAllChars(String baseWord, String shuffled) {
        for (char c : baseWord.toCharArray()) {
            if (!shuffled.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }
}
