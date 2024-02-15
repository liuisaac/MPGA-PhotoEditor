package model;

import java.awt.FlowLayout;
import javax.imageio.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Photo {
    private String url;
    private BufferedImage image;

    public Photo(String fileName, String fileType) {
        this.url = fileName + "." + fileType;
    }

    public void setUrl(String fileName, String fileType) throws IOException {
        this.url = fileName + "." + fileType;
        initializeImage();
    }

    public String getUrl() {
        return url;
    }

    public void initializeImage() throws IOException {
        image = ImageIO.read(new File(url));
    }

    public void displayImage() throws IOException {
        ImageIcon icon = new ImageIcon(image);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(200,300);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
