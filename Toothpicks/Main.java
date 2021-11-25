package toothpick;

import java.awt.*;
import javax.swing.*;

/**
 * Main Class to run toothpick. Used to take in input of number of toothpick
 * and the size of as percentage of the next generation.
 *
 * @author Minh Tran
 */
public class Main {
    public static void main( String[ ] args ) {
        int inNum = 1;
        double inLen = 1.0;

        if(args.length == 1){
            inNum = Integer.parseInt(args[0]);
        } else if (args.length == 2){
            inNum = Integer.parseInt(args[0]);
            inLen = Double.parseDouble(args[1]);
        }
        
        JFrame f = new JFrame();
        Toothpicks d = new Toothpicks(inNum, inLen);
        //d.setNumToothPick(3);
        //d.setLenToothPick(0.5);
        f.setResizable(false);
        f.getContentPane().add(d);
        f.pack();
        f.setVisible(true);
    } 
}
