package app.src.main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * ResourceFactory is an interface that provides method declarations
 * for obtaining resources, specifically dictionary and game data.
 * Any class implementing this interface should provide concrete implementations
 * for these methods.
 */
public interface ResourceFactory {

    /**
     * Retrieves a scanner instance for reading the dictionary resource.
     * 
     * @return A Scanner instance for reading the dictionary file.
     * @throws FileNotFoundException if the dictionary resource is not found.
     */
    Scanner getDictionaryResource() throws FileNotFoundException;

    /**
     * Retrieves a scanner instance for reading the game data resource.
     * 
     * @return A Scanner instance for reading the game data file.
     * @throws FileNotFoundException if the game data resource is not found.
     */
    Scanner getGameDataResource() throws FileNotFoundException;
}

/**
 * FileResourceFactory is a concrete implementation of the ResourceFactory
 * interface.
 * This class is responsible for creating Scanner instances for specific file
 * resources.
 */
class FileResourceFactory implements ResourceFactory {

    /**
     * Returns a Scanner instance that reads from the 7-letter-words file.
     * 
     * @return A Scanner instance for reading the 7-letter-words file.
     * @throws FileNotFoundException if the 7-letter-words.txt file is not found.
     */
    @Override
    public Scanner getDictionaryResource() throws FileNotFoundException {
        return new Scanner(new File("./src/main/resources/7-letter-words.txt"));
    }

    /**
     * Returns a Scanner instance that reads from the game data resource within the
     * CliGameModel class.
     * 
     * @return A Scanner instance for reading the game data resource.
     * @throws FileNotFoundException if the /4-15_Dictionary.txt resource is not
     *                               found within CliGameModel.
     */
    @Override
    public Scanner getGameDataResource() throws FileNotFoundException {
        return new Scanner(CliGameModel.class.getResourceAsStream("/4-15_Dictionary.txt"));
    }
}
