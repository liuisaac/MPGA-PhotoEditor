package model.effects;

import java.awt.image.*;

public class ReplacementEffects {
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

    private boolean filter(int[] bounds, int color) {
        return (color > bounds[0] && color < bounds[1]);
    }
}
