import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *  A program simulating similar Langdon's ant behaviour with a given DNA sequence
 *
 * @author Elbert Alcantara, Minh Tran
 */
public class Ant 
{
    /** Main method read the DNA sequence and number of step and print out the final coordination.
     **/
    public static void main(String[] args) 
    {
        HashMap<Character, String> DNA = new HashMap<Character, String>();
        int stepsToFollow;

        Scanner scan = null;
        scan = new Scanner(System.in);
		
        boolean firstLine = true;
        char initialState = ' ';
        while(scan.hasNext())
            {
                String dnaLine = scan.nextLine();
                if(!dnaLine.isEmpty())
                    {
                        if (dnaLine.length() == 11 && dnaLine.charAt(0) != '#') 
                            {	
                                if(firstLine)
                                    {
                                        initialState = dnaLine.charAt(0);
                                        firstLine = false;
                                    }
                                DNA.put(dnaLine.charAt(0), dnaLine);
                                System.out.println(dnaLine);
                            }
                        else if (dnaLine.charAt(0) != '#')
                            {
                                System.out.println(dnaLine);
                                stepsToFollow = Integer.parseInt(dnaLine);
                                calcFinalCoord(DNA, stepsToFollow, initialState);
                                DNA.clear();
                                stepsToFollow = 0;
                                firstLine = true;
                            }
                    }
            }
    }
    
    /** Calculate the final position of the Ant at the end of the steps numer using the given DNA sequence.

        @param DNA the random roll value of the seventh dice used.
        @param stepsToFollow
        @param initialState
    **/
    public static void calcFinalCoord(HashMap<Character, String> DNA, int stepsToFollow, char initialState) 
    {	
        HashMap<String, Character> visitedTiles = new HashMap<String, Character>();
		
        int currentX = 0;
        int currentY = 0;

        char currentTileSign = initialState;
        char initialTileSign = currentTileSign;
        char prevDirection = 'N';
		
        visitedTiles.put(currentX + "," + currentY, currentTileSign);

        //for loop updating the position for a number of steps
        for(int i = 0; i < stepsToFollow; i++)
            {
                String dnaString = DNA.get(currentTileSign);

                //Switch statements update the pprevious directions  based on the dna string correspoding to NESW
                switch(prevDirection) 
                    {
			case 'N':
                            prevDirection = dnaString.charAt(2);
                            visitedTiles.put(currentX + "," + currentY, dnaString.charAt(7));
                            break;
				
			case 'E':
                            prevDirection = dnaString.charAt(3);
                            visitedTiles.put(currentX + "," + currentY, dnaString.charAt(8));
                            break;
				
			case 'S':
                            prevDirection = dnaString.charAt(4);
                            visitedTiles.put(currentX + "," + currentY, dnaString.charAt(9));
                            break;
				
			case 'W':
                            prevDirection = dnaString.charAt(5);
                            visitedTiles.put(currentX + "," + currentY, dnaString.charAt(10));
                            break;
                    }

                //Switch statements update the position based on the dna string correspoding to NESW
                switch(prevDirection) 
                    {
			case 'N':
                            currentY += 1;
                            break;
			case 'E':
                            currentX += 1;
                            break;
			case 'S':
                            currentY -= 1;
                            break;
			case 'W':
                            currentX -= 1;
                            break;
                    }
			
                if(visitedTiles.get(currentX + "," + currentY) != null) 
                    {
                        currentTileSign = visitedTiles.get(currentX + "," + currentY);
                    }
                else if(visitedTiles.get(currentX + "," + currentY) == null) 
                    {
                        visitedTiles.put(currentX + "," + currentY, initialTileSign);
                        currentTileSign = initialTileSign;
                    }
            }
        System.out.println("# " + currentX + "  " + currentY + "\n");
    }
}
