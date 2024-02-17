package ui;

import model.Photo;

public class Main {
    public static void main(String[] args) {
        Photo p = new Photo("src/main/ui/assets/test", "png");

        try {
            p.initializeImage();
//            p.displayImage();
            p.simpleRecolor("000000", "ffffff", 100);
            p.simpleRecolor("387dff", "387dff", 100);
            p.displayImage();
            System.out.println("work");
        } catch (Exception e) {
            System.out.println("fu");
        }
    }
}
