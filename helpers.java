//Author: Jose De La Cruz 


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
    
}
