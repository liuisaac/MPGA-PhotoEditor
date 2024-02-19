package model.tools;

import model.SimpleEffects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// A class that manages image functions, conversions, and Image object references
public abstract class ManageImage implements SimpleEffects {
    private BufferedImage image;

    // REQUIRES: A local url in the form src/assets/...
    // MODIFIES: this
    // EFFECTS: constructs a ManageImage object, prints IOException if filepath doesn't exist or image is corrupted
    public ManageImage(String url) {
        try {
            this.image = ImageIO.read(new File(url));
        } catch (IOException i) {
//            System.err.println("Unhandled IO Exception");

        }
    }

    public BufferedImage getImageRef() {
        return image;
    }

    public void setImageRef(BufferedImage image) {
        this.image = image;
    }

    // EFFECTS: displays the image to a full screen window
    public void displayImage() {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JLabel lbl = new JLabel();
        frame.setVisible(true);
        ImageIcon icon = new ImageIcon(
                image.getScaledInstance(
                        Math.min(frame.getWidth(), frame.getHeight() * image.getWidth() / image.getHeight()),
                        Math.min(frame.getWidth() * image.getHeight() / image.getWidth(), frame.getHeight()),
                        Image.SCALE_SMOOTH));
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
    }
}
