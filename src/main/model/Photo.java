package model;

import java.awt.*;
import javax.imageio.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Photo extends Effects {
    private final String url;
    private BufferedImage image;

    public Photo(String fileName, String fileType) {
        this.url = fileName + "." + fileType;
        try {
            initializeImage();
        } catch (IOException i) {
            System.err.println("Unhandled IO Exception");
        }
    }

    public void initializeImage() throws IOException {
        image = ImageIO.read(new File(url));
    }

    public void simpleRecolor(String newHex, String oldHex, int tolerance) {
        int[] oldValues = stringToHex(oldHex);
        int[] newValues = stringToHex(newHex);
        preciseRecolor(
                newValues,
                new int[]{oldValues[0] - tolerance, oldValues[0] + tolerance},
                new int[]{oldValues[1] - tolerance, oldValues[1] + tolerance},
                new int[]{oldValues[2] - tolerance, oldValues[2] + tolerance});
    }

    private int[] stringToHex(String hex) {
        int red = Integer.parseInt(hex.substring(0, 2), 16);
        int green = Integer.parseInt(hex.substring(2, 4), 16);
        int blue = Integer.parseInt(hex.substring(4, 6), 16);

        return new int[]{red, green, blue};
    }


    public void preciseRecolor(int[] nextColor, int[] redBounds, int[] greenBounds, int[] blueBounds) {
        image = super.recolor(image, nextColor, redBounds, greenBounds, blueBounds);
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

//    public String getUrl() {
//        return url;
//    }
}
