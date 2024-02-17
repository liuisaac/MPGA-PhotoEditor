package model.tools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ManageImage {
    private final String url;
    private BufferedImage image;

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

    private void initializeImage() throws IOException {
        image = ImageIO.read(new File(url));
    }

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
