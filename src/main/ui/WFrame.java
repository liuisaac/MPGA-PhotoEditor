package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.event.*;
import java.util.Iterator;

public class WFrame extends JFrame implements WindowListener {

    public WFrame(String name) {
        super(name);
    }

    public void windowClosing(WindowEvent e) {
        System.out.println("Window Closing.");
        //A pause so user can see the message before
        //the window actually closes.
        System.out.println("\nLOGS");

        for (Event event : EventLog.getInstance()) {
            System.out.println("\n" + event.getDate());
            System.out.println(event.getDescription());
        }
    }

    public void windowClosed(WindowEvent e) {
        //This will only be seen on standard output.
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
}
