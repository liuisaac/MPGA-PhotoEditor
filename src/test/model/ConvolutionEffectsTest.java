package model;

import model.effects.ConvolutionEffects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.*;


// A class that holds helper methods that apply convolution-based image effects onto a BufferedImage
public class ConvolutionEffectsTest {
    private Photo image;
    private int width;
    private int height;
    private WritableRaster raster;
    private ConvolutionEffects convEffects;
    private int[][][] imageArray;
    private int[][][] outputArray;

    @BeforeEach
    void setup() {
        image = new Photo("src/assets/test/threetest.png", "image");
        convEffects = new ConvolutionEffects();
        width = image.getWidth();
        height = image.getHeight();
        raster = image.getRaster();

        // expected input
        imageArray = new int[3][3][3];
        imageArray[0] = new int[][]{new int[]{21, 185, 67}, new int[]{157, 187, 166}, new int[]{63, 63, 63}};
        imageArray[1] = new int[][]{new int[]{184, 184, 184}, new int[]{17, 17, 17}, new int[]{203, 203, 203}};
        imageArray[2] = new int[][]{new int[]{79, 85, 173}, new int[]{242, 242, 249}, new int[]{215, 38, 45}};

        //expected output
        outputArray = new int[3][3][3];
        outputArray[0] = new int[][]{new int[]{0, 0, 0}, new int[]{0, 0, 0}, new int[]{0, 0, 0}};
        outputArray[1] = new int[][]{new int[]{0, 0, 0}, new int[]{126, 129, 126}, new int[]{0, 0, 0}};
        outputArray[2] = new int[][]{new int[]{0, 0, 0}, new int[]{0, 0, 0}, new int[]{0, 0, 0}};
    }

    @Test
    void testBlur () {
        //setup
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int[] pixels = raster.getPixel(x, y, (int[]) null);
                    assertEquals(imageArray[x][y][0], pixels[0]);
                    assertEquals(imageArray[x][y][1], pixels[1]);
                    assertEquals(imageArray[x][y][2], pixels[2]);
            }
        }
        //method
        raster = convEffects.blur(image.getImageRef()).getRaster();

        //test output
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int[] pixels = raster.getPixel(x, y, (int[]) null);
                assertEquals(outputArray[x][y][0], pixels[0]);
                assertEquals(outputArray[x][y][1], pixels[1]);
                assertEquals(outputArray[x][y][2], pixels[2]);
            }
        }
    }
}
