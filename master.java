// Authors: Logan Wasmer, Jose De La Cruz, 

/**********************************************************/
/**********************************************************/

//Imports
import java.io.*;
import java.util.*;

/**********************************************************/
/**********************************************************/

public class master{

public static void main(String args[]) throws FileNotFoundException{ 
    String baseWord = getBaseWord(dictionaryFile());
    char reqLetter = getReqLetter(baseWord);
    
}

/**********************************************************/
/**********************************************************/

public static String[] dictionaryFile() throws FileNotFoundException{
    Scanner scanner = new Scanner(new File("7-letter-words.txt"));
    String[] sevenLetterWords = new String[25251];
    int i = 0;
    while(scanner.hasNext()){
        sevenLetterWords[i] = scanner.nextLine();
        i++;
    } 
    return sevenLetterWords;
}

/**********************************************************/
/**********************************************************/

public static String getBaseWord(String[] dictionary){
    Random rand = new Random();
    int upperBound = 25250;
    int randomInt = rand.nextInt(upperBound);
    
    return dictionary[randomInt];
}

/**********************************************************/
/**********************************************************/

public static char getReqLetter(String baseWord){
    Random rand = new Random();
    int upperBound = 7;
    int randomInt = rand.nextInt(upperBound);

    return baseWord.charAt(randomInt);
}

/**********************************************************/
/**********************************************************/































































































































}






