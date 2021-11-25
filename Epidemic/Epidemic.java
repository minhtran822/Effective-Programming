import java.util.*;

/**
 * Epidemic
 * Insert flag to use: E.g java Epidemic 1 < file.txt
 * Create and illustrate speading of sickness based on certain rules.
 * Part 1 Produce the final spreading.
 * Part 2 Generate minimum initial number of sick people for fully infected.
 *
 * @author Minh Tran
 */
public class Epidemic {
    private static final Boolean DEBUG = false;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        ArrayList <String> inputUniverse = new ArrayList<String>();

        String task = args[0].toString();

        while(sc.hasNextLine()){
            String inputLine = sc.nextLine();

            if(inputLine.length() != 0){
                inputUniverse.add(inputLine);
            } else {
                startProgram(inputUniverse, task);
                inputUniverse.clear();

                System.out.println("");
            }

        }

        startProgram(inputUniverse, task);

        sc.close();
    }

    /**
     * startProgram method - Generate universe array upon calling
     * Create to use distinctly when scanner finished or when find space
     * 
     * @param inputUniverse Universe as a List
     * @param task The task required to do
     */
    private static void startProgram(ArrayList<String> inputUniverse, String task) {
        char[][] arrUniverse = new char[inputUniverse.size()][inputUniverse.get(0).length()];

        for (int i = 0; i <inputUniverse.size(); i++){
            String input = inputUniverse.get(i);
            for (int j = 0; j < input.length(); j++){
                arrUniverse[i][j] = input.charAt(j);
            }
        }

        if (DEBUG) System.out.println(inputUniverse.size());

        if (task.equals("1")) {
            taskOne(arrUniverse);
        }else if (task.equals("2")) {
            taskTwo(arrUniverse);
        }

    }
    /**
     * taskOne method - To perform task 1
     * Generate the final universe status after infection spread.
     * 
     * @param universe the original universe as an array
     */
    private static void taskOne(char[][] universe){
        //System.out.println("");
        updateUniverse(universe);
        printArray(universe);
    }

    /**
     * task 2 method - To find minimum number if sick person required for 
     * fully infection in the universe
     * 
     * @param universe the original universe as character array
     */
    private static void taskTwo(char[][] universe){
        int count = 0;

        //For loop to create initial hard-coded sick people
        for (int i = 0; i < universe.length; i++){
            for (int j = 0; j < universe[i].length; j++){
                if(universe[i][j] == '.'){
                    Boolean toTurn = false;

                    //Surrounded by 3 immuned
                    int immuneCount = 0;
                    if(i+1 < universe.length && universe[i+1][j] != 'I') immuneCount++;

                    if(i-1 >=0 && universe[i-1][j] != 'I') immuneCount++;
                    
                    if(j+1 < universe[i].length && universe[i][j+1] != 'I') immuneCount++;

                    if(j-1 >=0 && universe[i][j-1] != 'I') immuneCount++;

                    if(immuneCount < 2) {
                        toTurn = true;
                        if (DEBUG) System.out.println("x");
                    }

                    //In the middle of 4 immuned and no sick
                    immuneCount = 0;
                    int sickCount = 0;
                    
                    if(j-1 >=0 && universe[i][j-1] == 'S') sickCount++;
                    if(j+1 < universe[i].length && universe[i][j+1] == 'S') sickCount++;

                    if (i-1 >= 0){
                        if (j-1 >= 0 && universe[i-1][j-1] == 'I') immuneCount++;
                        if (universe[i-1][j] == 'S') sickCount++;
                        if (j+1 < universe[i-1].length && universe[i-1][j+1] == 'I') immuneCount ++;
                    }

                    if (i+1 < universe.length){
                        if (j-1 >= 0 && universe[i+1][j-1] == 'I') immuneCount++;
                        if (universe[i+1][j] == 'S') sickCount++;
                        if (j+1 < universe[i+1].length && universe[i+1][j+1] == 'I') immuneCount ++;
                    }

                    if (sickCount == 0 && immuneCount == 4) {
                        toTurn = true;
                        if (DEBUG) System.out.println("y");
                    }

                    //Change to S if toTurn is true
                    if (toTurn) {
                        universe[i][j] = 'S';
                        count++;
                    }
                }
            }
        }
        if (DEBUG) System.out.println("Initial count: " + count);

        char[][] initialUniverse = copyArray(universe);
        int bestCount = -1;
        int iTurn = 0;
        int jTurn = 0;

        //While loop using brute force search
        while (bestCount != 0){

            bestCount = 0;
            iTurn =0;
            jTurn =0;

            for (int i = 0; i < universe.length; i++){
                for (int j = 0; j < universe[i].length; j++){
                    if(universe[i][j] == '.'){
                        int sickCount = 0;
                        if(i+1 < universe.length && universe[i+1][j] == 'S') sickCount++;
    
                        if(i-1 >=0 && universe[i-1][j] == 'S') sickCount++;
                        
                        if(j+1 < universe[i].length && universe[i][j+1] == 'S') sickCount++;
    
                        if(j-1 >=0 && universe[i][j-1] == 'S') sickCount++;
    
                        if(sickCount < 2) {/*
                            Create an experimental universe when this block
                            is sick to find best case
                            */
                            char[][] temp = copyArray(universe);
                            temp[i][j] = 'S';
                            int tempCount = updateUniverse(temp) + 1;
    
                            if (DEBUG) System.out.println(bestCount + " " + tempCount);
    
                            if(tempCount > bestCount){
                                if (DEBUG) System.out.println(bestCount + " " + tempCount);
                                bestCount = tempCount;
                                iTurn = i;
                                jTurn = j;
                            }
                        }

                        
                    }
                }
            }
        
            if(bestCount != 0){
                universe[iTurn][jTurn] = 'S';
                updateUniverse(universe);

                initialUniverse[iTurn][jTurn] = 'S';
                count ++;
            }
        }

        System.out.println(count);
        printArray(initialUniverse);
    }

    /**
     * updateUniverse - Iterate through universe to update the transmission
     * If 2 out of 4 adjacent block is  infected, then the main block is infected
     * 
     * @param universe the original universe as an array
     * 
     * @return int count the number of newly added sick people
     */
    private static int updateUniverse (char[][] universe){
        int count = 0;
        while (true){
            int countTurned = 0;

            for (int i = 0; i < universe.length; i++){
                for (int j = 0; j < universe[i].length; j++){
                    if (universe[i][j] == '.'){
                        int sickCount = 0;

                        if(i+1 < universe.length && universe[i+1][j] == 'S') sickCount++;

                        if(i-1 >=0 && universe[i-1][j] == 'S') sickCount++;
                        
                        if(j+1 < universe[i].length && universe[i][j+1] == 'S') sickCount++;

                        if(j-1 >=0 && universe[i][j-1] == 'S') sickCount++;

                        if(sickCount >=2){
                            universe[i][j] = 'S';
                            countTurned++;
                        }
                    }
                }
            }

            if (countTurned == 0) {
                break;
            } else {
                count+= countTurned;
                if (DEBUG) System.out.println(count);
            }
        }

        return count;
    }

    /**
     * printArray method - print the 2D array
     * 
     * @param universe the universe to be printed
     */
    private static void printArray(char[][] universe){
       
        for (int i = 0; i < universe.length; i++){
            for (int j = 0; j < universe[i].length; j++){
                System.out.printf("%c", universe[i][j]);
            }
            System.out.printf("%n");
        }
    }

    /**
     * copyArray method - create a copy of the universe to operate on
     * 
     * @param universe the original universe
     * 
     * @return char[][] the copy version
     */
    private static char[][] copyArray(char[][] universe){
        char [][] copyVersion = new char[universe.length][];
        for(int i = 0; i < universe.length; i++)
            copyVersion[i] = universe[i].clone();

        return copyVersion;
    }
}
