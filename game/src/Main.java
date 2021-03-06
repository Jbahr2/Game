
/**
 * @author Kim Buckner
 * Date: Jan 13, 2019
 *
 *
 * A starting point for the COSC 3011 programming assignment
 * Probably need to fix a bunch of stuff, but this compiles and runs.
 *
 * This COULD be part of a package but I choose to make the starting point NOT a
 * package. However all other added elements should be sub-packages.
 *
 * Main should NEVER do much more than this in any program that is
 * user-interface intensive, such as this one. If I find that you have chosen
 * NOT to use Object-Oriented design methods, I will take huge deductions. 
 * 
 * Edited by Group H
 * Class: Software Design 3011
 * Last Edited: 03/10/2020
 * 
 */

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        // This is the play area
        GameWindow game = new GameWindow("Group H aMaze");

        game.setSize(new Dimension(900, 1000));

        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game.getContentPane().setBackground(new Color(119, 140, 163));
        game.setUp("input/default.mze");

        game.setVisible(true);

        try {
            // The 4 that are installed on Linux here
            // May have to test on Windows boxes to see what is there.
            UIManager.setLookAndFeel(
                    "javax.swing.plaf.nimbus.NimbusLookAndFeel");
            // This is the "Java" or CrossPlatform version and the default
            // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            // Linux only
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            // really old style Motif
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
            // System.out.println("Error: Unsupported look and feel
            // exception.");
            System.out.println(e);
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            System.exit(1);
        } catch (InstantiationException e) {
            System.out.println(e);
            System.exit(1);
        } catch (IllegalAccessException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

};
