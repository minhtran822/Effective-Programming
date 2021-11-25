import java.util.*;

/**
 * Find the repeated digits in different base number
 * 
 * Produce the length of numbers with repeated digits in given base b
 * and given number ceiling n.
 * 
 * Find the smallest number n that has repeated digits in 2 bases b and c
 *
 * @author Minh Tran
 */
public class RepeatedDigit {
    private static final Boolean DEBUG = false;
    private static final int INT_MAX = 2147483647;
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String inputLine = sc.nextLine();
            Boolean taskOne = false;

            if(inputLine.charAt(0) == 'A'){
                taskOne = true;
            }else if (inputLine.charAt(0) == 'B'){
                 taskOne = false;
            } else{
                System.out.println("Bad line: " + inputLine);
                continue;
            }

            int[] inputInt = getInputInt(inputLine);

            if (inputInt == null) {
                System.out.println("Bad line: " + inputLine);
                continue;
            } else {
                if (taskOne) firstExercise(inputInt[0], inputInt[1]);
                else secondExercise(inputInt[0], inputInt[1]);
            }
        }

        sc.close();
    }

    /**
     * firstExercise method-Produce the length of numbers with repeated digits in given base b
     * and given number ceiling n.
     * 
     * @param b the base number
     * @param n the ceiling number
     */
    private static void firstExercise(int b, int n){
        int lengthMax = 0;
        int firstMax = 0;

        int largestNumB = largestInBase(b);

        //System.out.println(n);
        //System.out.println(hasRepeatedInt(b, n));

        if(!(b > n-1)){
            int length = 0;
            int first = 0;

            if (largestNumB < n){
                if ((n - largestNumB) > (largestNumB - b)){
                    //lengthMax = n - largestNumB;
                    firstMax = largestNumB +1;
                    lengthMax = n - firstMax;
                }
            } else {

                for (int i = b+1; i <= n; i++){
                    if (DEBUG)
                        System.out.println(i);
                    
                    if (hasRepeatedInt(b, i)) {
                        if(first == 0){
                            first = i;
                            length = 0;
                        } else {
                            length ++;
                        }
                    } else {
                        if(lengthMax<length){
                            lengthMax = length;
                            firstMax = first;
                        }
                        //Reset the recording after it is broken
                        length = 0;
                        first = 0;
                    }

                    if(lengthMax<=length && firstMax==0){
                        lengthMax = length;
                        firstMax = first;
                    }

                    if(lengthMax<length){
                        lengthMax = length;
                        firstMax = first;
                    }

                    if(DEBUG) {
                        System.out.println("length " + length);
                        System.out.println("first " + first);
                        System.out.println("length " + lengthMax);
                        System.out.println("first " + firstMax);
                        System.out.println("---------------");
                    }
                }
            }
        }

        if(DEBUG){
            if (largestNumB < n){
                if ((n - largestNumB) > (largestNumB - b)){
                    System.out.println("Casted");
                }
            }
        }   
        
        System.out.printf("%d %d%n", firstMax, lengthMax);
    }

    /**
     * Find the smallest number n that has repeated digits in 2 bases b and c
     *
     * @param b the first base
     * @param c the second base
     */
    private static void secondExercise(int b, int c){
        //Find smaller bases
        //Call Assign largestInBase to larger base.
        //Search from that num+1;

        int largerBase = (b>c)? b:c;
        Boolean found = false;

        int i = largerBase + 1;

        if (i != 0){
            while(!found){
                if(hasRepeatedInt(b, i) && hasRepeatedInt(c, i)){
                    found = true;
                    break;
                }

                i++;
            }
        }

        System.out.println(i);
    }

    /**
     * largestInBase method-Find the largest decimal integer possible in a base
     * without a repeated digits.
     * Capped at INT_MAX the outer range of an integer without causing overflow.
     *
     * @param base the base to find the largest num without repeated digits
     *
     * @return int the largest num
     */
    private static int largestInBase(int base){
        int n = 0;
        long l = 0;
        
        for (int i = base-1; i >=0; i --){
            l+= i*Math.pow(base,i);
            if (l >= INT_MAX) break;
        }

        n = (int) l;
        return n;
    }

    /**
     * hasRepeatedDigits method-check if a decimal integer n has repeated digit in base b
     *
     * @param b the base
     * @param n the decimal
     *
     * @return Boolean if true, the number has repeated digits
     */
    private static Boolean hasRepeatedInt(int base, int num){

        List<Integer> digitInBase = new ArrayList<Integer>();
        Boolean flag = false;
        //Conversion from dec to base.
        while (num > 0){
            int remainder = num % base;
            if (digitInBase.contains(remainder)) return true;
            //else
            digitInBase.add(remainder);

            num /= base;
        }

        if (DEBUG)
            System.out.println(digitInBase);

        Collections.reverse(digitInBase);
        if (DEBUG)
            System.out.println(digitInBase);
        return flag;
    }

    /**
     * getInputInt-Extract two numerical values presented from the input
     *
     * @param str String of input
     *
     * @return int[] the two value presented as an array of 2 numbers
     */
    private static int[] getInputInt(String str){
        String[] inpuStrings = str.trim().split(" ");
        if(inpuStrings.length != 3){
            return null;
        } else {
            int[] inputInt = new int[inpuStrings.length - 1];
            for(int i = 1; i < inpuStrings.length; i++){
                try{
                    inputInt[i-1] = Integer.parseInt(inpuStrings[i]);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return inputInt;
        }
    }
}
