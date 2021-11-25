import java.util.*;
import java.lang.*;

/**
 * Print out set 70 of 3 numbers x, y, z satisfying following rules:
 * x2 + y2 = 1 + z3
 * z <x <y
 * x, y, z has no common factors
 *
 * @author Minh Tran
 */
public class Threes{

    public static void main (String[] args) {

        firstExercise();

        secondExercise();

    }
    
    /**
     * firstExercise method-Create the set by incrementing x
     */
    private static void firstExercise () {
        int count = 1;

        long x = 3;
        List<Long> lsY = new ArrayList<Long>();


        while (count <= 70){
            if(!lsY.contains(x)){

                for (long z = 1; z<x; z+=2){
                    if (checkCommonDivisor(z, x)){
                        if (getY(x, z) > 0){
                            long y = getY(x, z);
                            lsY.add(y);
                            System.out.println(count + " " + x + " " + y + " "+ z);
                            count++;
                            break;
                        }
                    }
                }
            }
                x+=2;
        }

        lsY.clear();
    }

    /**
     * secondExercise method-Create the set by incrementing z
     */
    private static void secondExercise() {
        int count = 1;

        long z = 1;
        long x = 3;
        
        while (count <= 70){
            Boolean found = false;
                while(!found){
                    
                    // Break point to stop iterate through unnecessary
                    // as y can't be smaller than x or be unreal
                    if (getY(x, z) == -1) break;

                    if(getY(x, z) >0){
                        long y = getY(x, z); 
                        if (checkCommonDivisor(z, x)){
                                System.out.println(count + " " + x + " " + y + " " + z);
                                count++;

                                //second check to stop loop as soon as count is updated over 70
                                if (count > 70){
                                    found = true;
                                }
                        }
                    }
                      
                    x+=2;
                }
                z+=2;
                x=z;
        } 
       
    }

    /**
     * getY method-Calculate Y and add y break points
     *
     * @param x value of x
     * @param y value of y
     *
     * @return long First break point (-1), second breakpoint (-2) or value of y(greater than 0)
     */
    private static long getY(long x, long z){
        double right = 1 +  Math.pow(z, 3);
        double ySqrt = Math.sqrt(right - Math.pow(x, 2));


        // If statement to add break point for second exercise
        // y is smaller than x or unreal
        if(ySqrt<x || Double.isNaN(ySqrt)) return -1;

        // Second break point, y is a whole number
        if (ySqrt %1 == 0) {
                long y = (long) ySqrt;
                if (checkCommonDivisor(x, y) && checkCommonDivisor(y, z))
                    return y;
        }

        return -2;
    }

    /**
     * checkCommonDivisor method-CHekc if 2 numbers share any common divisor
     * Created based of greatest common divisor (GCD) algorithm.
     *
     * @param num1 first long number
     * @param num2 second long number
     *
     * @return Boolean whether there is common divisor (aka GCD is 1)
     */
    private static Boolean checkCommonDivisor(long num1, long num2) {
        long remainder = num1 % num2;

        while (remainder != 0) {
            num1 = num2;
            num2 = remainder;
            remainder = num1 % num2;
        }
        
        if (num2 == 1) { //This is when the greatest common divisor is 1
            return true;
        } else {
            return false;
        }
    }
    
}
