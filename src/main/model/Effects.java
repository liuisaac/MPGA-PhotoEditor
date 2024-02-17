package model;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Effects {
    public BufferedImage recolor(BufferedImage image,
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
        return image;
    }

    private boolean filter(int[] bounds, int color) {
        return (color > bounds[0] && color < bounds[1]);
    }
}
