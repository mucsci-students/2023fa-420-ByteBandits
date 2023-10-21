package src.test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import app.src.main.java.helpers;
import app.src.main.java.master;

import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;

public class MasterTest {

    @Test
    public void testPointsPWord() {
        // Test with a word of length 4
        int points1 = master.pointsPWord("apple", "plum");
        assertEquals(1, points1);

        // Test with a word of length 5
        int points2 = master.pointsPWord("apple", "apple");
        assertEquals(5, points2);

        // Test with a word of length 6
        int points3 = master.pointsPWord("apple", "maples");
        assertEquals(6, points3);

        // Test with a pangram
        // int points4 = master.pointsPWord("pangram", "programming");
        // assertEquals(14, points4);

        // Test with a longer word
        // int points5 = master.pointsPWord("longword", "wordlonger");
        // assertEquals(9, points5);
    }

    @Test
    public void testPlayerRank() {
        // Test with different player points and ranks
        // String rank1 = master.playerRank("apple", 5, List.of("apple", "plum", "maple"));
        // assertEquals("Moving Up", rank1);

        // String rank2 = master.playerRank("apple", 15, List.of("apple", "plum", "maple"));
        // assertEquals("Great", rank2);

        // String rank3 = master.playerRank("apple", 35, List.of("apple", "plum", "maple"));
        // assertEquals("Genius", rank3);
    }

    @Test
    public void testAcceptedWords() throws FileNotFoundException {
        List<String> acceptedWords = master.acceptedWords("apple", 'a');

        // Check if "apple" is in the list
        assertTrue(acceptedWords.contains("apple"));

        // Check if words without 'a' are not in the list
        assertFalse(acceptedWords.contains("plum"));
        assertFalse(acceptedWords.contains("maple"));
    }
    @Test
    public void testIsUnique() {
        assertTrue(helpers.isUnique("abcdefg"));
        assertFalse(helpers.isUnique("hello"));
    }
    public void testRemoveChar() {
        String result = helpers.removeChar("hello", 'l');
        assertEquals("heo", result);

        result = helpers.removeChar("banana", 'a');
        assertEquals("bnn", result);

        result = helpers.removeChar("apple", 'x');
        assertEquals("apple", result);
    }

}
