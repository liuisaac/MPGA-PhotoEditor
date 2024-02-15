package ui;

import model.Photo;

public class Main {
    public static void main(String[] args) {
        Photo p = new Photo("tesUImage", "png");

        try {
            p.setUrl("src/main/ui/assets/test", "png");
            p.initializeImage();
            System.out.println("work");
        } catch (Exception e) {
            System.out.println("fu");
        }
    }
}
