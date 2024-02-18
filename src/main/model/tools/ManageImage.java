package model.tools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// A class that manages image functions, conversions, and Image object references
public class ManageImage {
    private final String url;
    private BufferedImage image;

    // REQUIRES: A local url in the form /src/assets/...
    // MODIFIES: this
    // EFFECTS: constructs a ManageImage object, prints IOException if filepath doesn't exist or image is corrupted
    public ManageImage(String url) {
        this.url = url;
        try {
            initializeImage();
        } catch (IOException i) {
            System.err.println("Unhandled IO Exception");
        }
    }

    public BufferedImage getImageRef() {
        return image;
    }

    public void setImageRef(BufferedImage image) {
        this.image = image;
    }

    // EFFECTS: attempts to create a BufferedImage from the url provided when an object is constructed
    private void initializeImage() throws IOException {
        image = ImageIO.read(new File(url));
    }

    // EFFECTS: displays the image to a full screen window
    public void displayImage() {
        ImageIcon icon = new ImageIcon(image);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
