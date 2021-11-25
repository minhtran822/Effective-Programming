package toothpick;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Object Toothpick Class to create and draw toothpicks
 * Draw generations of toothpick as following input of number and length
 *
 * @author Minh Tran
 */
public class Toothpicks extends JPanel{
    private int numToothPick;
    private double lenToothPick;

    //Set the default drawing variables as final
    private final int DIMENSION_WIDTH = 1000;
    private final int DIMENSION_LENGTH = 600;

    private final Boolean DEBUG = false;

    /**
     * Constructor creating the Panel and take in input as global variables
     * 
     * @param numToothPick take in number as toothpick generations to be drawn
     * @param lenToothPick take in the length of new generation as percentage of previous gen.
     */
    Toothpicks(int numToothPick, double lenToothPick) {
        setPreferredSize(new Dimension(DIMENSION_WIDTH,DIMENSION_LENGTH));
        this.numToothPick = numToothPick;
        this.lenToothPick = lenToothPick;
    }

    /**
     * Default constructor creating the panel and set default global variables
     */
    Toothpicks() {
        setPreferredSize(new Dimension(DIMENSION_WIDTH,DIMENSION_LENGTH));
        this.numToothPick = 1;
        this.lenToothPick = 1.0;
    }

    /**
     * Mutator to set number of toothpicks
     * 
     * @param n take in number as toothpick generations to be drawn
     */
    public void setNumToothPick(int n){
        this.numToothPick = n;
    }

    /**
     * Mutator to set length of new generation
     * 
     * @param lenToothPick take in the length of new generation as percentage of previous gen.
     */
    public void setLenToothPick(double l){
        this.lenToothPick = l;
    }
   
    /**
     * paintCOmponent to paint the generations of toothpicks through iteration following num
     * of toothpicks
     * 
     * @param g graphics class for drawing
     */
    public void paintComponent ( Graphics g ) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        double offsetWidth = 1.0;
        for (int i = 0; i <= numToothPick; i+=2){
            offsetWidth+= Math.pow(lenToothPick, i);
        }

        double offsetLength = 0.0;
        for (int i = 1; i <= numToothPick; i+=2){
            offsetLength+= Math.pow(lenToothPick, i);
        }

        if (offsetLength == 0.0) offsetLength = 1.0;

        offsetLength = (DIMENSION_LENGTH-50)/offsetLength;
        offsetWidth = (DIMENSION_WIDTH-50)/offsetWidth;

        //Create variables for default generation 0
        int initialLen = (offsetLength<offsetWidth)? (int)offsetLength:(int)offsetWidth;
        int initialY = DIMENSION_LENGTH/2;
        int initialX = (DIMENSION_WIDTH - initialLen)/2;

        //Array list to store coordinate points of last drawn gen and new gen starting points
        ArrayList<double[]> lsCoor = new ArrayList<double[]>();
        ArrayList<double[]> lsTemp = new ArrayList<double[]>();

        //Draw first default generation 0
        Shape initialPick = new Line2D.Double(initialX, initialY, initialX+initialLen, initialY);
        g2.draw(initialPick);
        Boolean isVertical = false;

        double[] coor1 = {initialX, initialY};
        lsCoor.add(coor1);
        
        double[] coor2 = {initialX+initialLen, initialY};
        lsCoor.add(coor2);

        //Loop through generations of toothpicks
        for (int i = 0; i < numToothPick; i++){
            if (DEBUG)
                System.out.println(i);

            isVertical = !isVertical;
            initialLen *= lenToothPick;

            //Update the coorination list for new generation.
            lsTemp.addAll(calcOrgLine(lsCoor, initialLen, isVertical));
            lsCoor.clear();
            lsCoor.addAll(lsTemp);
            lsTemp.clear();

            for(int ls= 0; ls<lsCoor.size(); ls +=2){
                double[] points = lsCoor.get(ls);
                double x1 = points[0];
                double y1 = points[1];
                if (DEBUG)
                    System.out.printf("%.1f, %.1f%n", x1, y1);

                points = lsCoor.get(ls + 1);
                double x2 = points[0];
                double y2 = points[1];

                Shape picks = new Line2D.Double(x1, y1, x2, y2);
                g2.draw(picks);
            }
        }
    }

    /**
     * calcOrgLine method - generate starting and ending points of new generations
     * Store these points as pair of (x, y).
     * Starting points and ending points are stored as two list items next to each other.
     * 
     * @param lsOldCoor list of coordination of old generation
     * @param len the length of the new generation (not as percentage)
     * @param isVertical whether to draw the toothpick as vertical or not
     * 
     * @return ArrayList<double[]> the list of the coordinates points for new genration drawing
     */
    private ArrayList<double[]> calcOrgLine(ArrayList<double[]> lsOldCoor, double len, Boolean isVertical){
        ArrayList<double[]> lsNewCoor = new ArrayList<double[]>();

        /*
        Loop through old generations points to generate new generations
        FOr every edge points of old generation, create 2 more edge points
        */
        for (double[] coor: lsOldCoor){
            if(coor.length != 2){
                System.out.println("Error formating");
            } else {
                double x = coor[0];
                double y = coor[1];

                double[] newCoor1 = new double [2];
                newCoor1[0] = (isVertical)? x:(x - len/2);
                newCoor1[1] = (isVertical)? (y-len/2):y;
                lsNewCoor.add(newCoor1);

                double[] newCoor2 = new double [2];
                newCoor2[0] = (isVertical)? x:(x + len/2);
                newCoor2[1] = (isVertical)? (y+len/2):y;
                lsNewCoor.add(newCoor2);
            }
        }
        return lsNewCoor;
    }
}