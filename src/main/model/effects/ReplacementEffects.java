package model.effects;

import java.awt.image.*;

// A class that holds helper methods that apply pixel by pixel recoloring effects onto a BufferedImage
public class ReplacementEffects {
    // REQUIRES: nextColor, redBounds, greenBounds, and blueBounds are all 2 element lists [x, y],
    // where x is within the range [0, y] and y is in the range [x, 255]
    // MODIFIES: image
    // EFFECTS: Recolors all the pixels within the red, green, and blue bounds to the nextColor color
    public void recolor(BufferedImage image,
                        int[] nextColor,
                        int[] redBounds,
                        int[] greenBounds,
                        int[] blueBounds) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int[] pixels = raster.getPixel(x, y, (int[]) null);
                if (filter(redBounds, pixels[0]) && filter(greenBounds, pixels[1]) && filter(blueBounds, pixels[2])) {
                    pixels[0] = nextColor[0];
                    pixels[1] = nextColor[1];
                    pixels[2] = nextColor[2];
                }
                raster.setPixel(x, y, pixels);
            }
        }
    }

    // MODIFIES: image
    // EFFECTS: Inverts the colors of a image
    public void invert(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int[] pixels = raster.getPixel(x, y, (int[]) null);

                pixels[0] = 256 - pixels[0];
                pixels[1] = 256 - pixels[1];
                pixels[2] = 256 - pixels[2];

                raster.setPixel(x, y, pixels);
            }
        }
    }

    // MODIFIES: image
    // EFFECTS: Converts a colored image to a grayscale image
    public void grayscale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int[] pixels = raster.getPixel(x, y, (int[]) null);

                int avg = ((pixels[0] + pixels[1] + pixels[2]) / 3);

                pixels[0] = avg;
                pixels[1] = avg;
                pixels[2] = avg;

                raster.setPixel(x, y, pixels);
            }
        }
    }

    // REQUIRES: bounds is a 2 element lists [x, y], where x is within the range [0, y] and y is in the range [x, 256]
    // EFFECTS: Returns true if a color's value is within the bounds for that color
    private boolean filter(int[] bounds, int color) {
        return (color > bounds[0] && color < bounds[1]);
    }
}
