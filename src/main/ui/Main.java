package ui;

import model.Photo;


public class Main {
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public static void main(String[] args) {
        Photo p = new Photo("src/assets/test/threetest.png");


        try {
//            p.blur();
            //        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                int[] pixels = raster.getPixel(x, y, (int[]) null);
//                if (x == 1 && y == 1) {
//                    assertEquals(pixels[0], 256);
//                    assertEquals(pixels[1], 256);
//                    assertEquals(pixels[2], 256);
//                } else {
//                    assertEquals(pixels[0], 0);
//                    assertEquals(pixels[1], 0);
//                    assertEquals(pixels[2], 0);
//                }
//            }
//        }
            p.displayImage();
            System.out.println("work");
        } catch (Exception e) {
            System.err.println("Error occurred: " + e);
        }
    }
}
