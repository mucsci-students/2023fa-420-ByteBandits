//Author: Jose De La Cruz, Logan Wasmer

/*********************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*********************************************************/

public class helpers {
    
/*
* isBetween
* param: int val, int min, int max
* returns: Boolean
* This function takes a val param, this param is the one that
* is being tested, the min and max param are the two conditions.
* True will be returned is val is between min and max false otherwise.
*/

public static Boolean isBetween(int val, int min, int max){
    return val >= min && val <= max;
}

/*********************************************************/
/*********************************************************/

/*
 * isUnique
 * param: String s
 * returns: Boolean 
 * This function receives a string and tests to
 * see if it has all unique letters and if it
 * does the method returns true, otherwise false.
 */

public static boolean isUnique(String s)
{
    for(int i = 0; i < s.length(); ++i)
    {
        for(int j = i+1; j < s.length(); ++j)
        {
            if(s.charAt(i) == s.charAt(j))
            {
                return false;
            }
        }
    }
    return true;
}

/*********************************************************/
/*********************************************************/

/*
 * sameChars
 * param: String baseWord, String dicString
 * returns: Boolean 
 * This function takes the base word and a string from the dictionary.
 * It then converts base word to a set of chars and checks if all chars
 * in dicString are in baseWord. If it finds so much as 1 that isnt in baseWord
 * it returns false. If the tests pass then it returns true.
 */

public static boolean sameChars(String baseWord, String dicString) {
    Set<Character> baseWordSet = new HashSet<>();
    for (char c : baseWord.toCharArray()) {
        baseWordSet.add(c);
    }

    for (char c : dicString.toCharArray()) {
        if (!baseWordSet.contains(c)) {
            return false; 
        }
    }

    return true; 
}

}