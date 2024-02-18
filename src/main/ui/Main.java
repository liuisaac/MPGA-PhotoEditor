package ui;

import model.Photo;

public class Main {
    public static void main(String[] args) {
        Photo p = new Photo("src/assets/test.png");

        try {
            p.recolor("000000", "ffffff", 100);
            p.recolor("387dff", "387dff", 100);
            p.grayscale();
            p.invert();
            p.blur();
            p.blur(1);
            p.displayImage();
            System.out.println("work");
        } catch (Exception e) {
            System.err.println("Error occurred: " + e);
        }
    }
}
