package model;

import model.effects.ReplacementEffects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.WritableRaster;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReplacementEffectsTest {
    private Photo image;
    private int width;
    private int height;
    private WritableRaster raster;
    private ReplacementEffects replEffects;
    private int[][][] imageArray;
    private int[][][] outputArray;

    // Test helper that checks if raster is equivalent to the desired int[][][] array
    private void equalPhotos(int[][][] array) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int[] pixels = raster.getPixel(x, y, (int[]) null);
                assertEquals(array[x][y][0], pixels[0]);
                assertEquals(array[x][y][1], pixels[1]);
                assertEquals(array[x][y][2], pixels[2]);
            }
        }
    }

    @BeforeEach
    void setup() {
        image = new Photo("src/assets/test/threetest.png");
        replEffects = new ReplacementEffects();
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
    }

    @Test
    void testRecolor() {
        outputArray[0] = new int[][]{new int[]{21, 185, 67}, new int[]{157, 187, 166}, new int[]{63, 63, 63}};
        outputArray[1] = new int[][]{new int[]{184, 184, 184}, new int[]{255, 255, 255}, new int[]{203, 203, 203}};
        outputArray[2] = new int[][]{new int[]{79, 85, 173}, new int[]{242, 242, 249}, new int[]{255, 255, 255}};
        //setup
        equalPhotos(imageArray);
        //method
        replEffects.recolor(
                image.getImageRef(),
                new int[]{255, 255, 255},
                new int[]{0, 256},
                new int[]{0, 256},
                new int[]{0, 60});

        raster = image.getRaster();

        //test output
        equalPhotos(outputArray);
    }

    @Test
    void testInvert() {
        outputArray[0] = new int[][]{new int[]{256 - 21, 256 - 185, 256 - 67},
                new int[]{256 - 157, 256 - 187, 256 - 166},
                new int[]{256 - 63, 256 - 63, 256 - 63}};
        outputArray[1] = new int[][]{new int[]{256 - 184, 256 - 184, 256 - 184},
                new int[]{256 - 17, 256 - 17, 256 - 17},
                new int[]{256 - 203, 256 - 203, 256 - 203}};
        outputArray[2] = new int[][]{new int[]{256 - 79, 256 - 85, 256 - 173},
                new int[]{256 - 242, 256 - 242, 256 - 249},
                new int[]{256 - 215, 256 - 38, 256 - 45}};
        //setup
        equalPhotos(imageArray);
        //method
        replEffects.invert(image.getImageRef());

        raster = image.getRaster();

        //test output
        equalPhotos(outputArray);
    }
    @Test
    void testGrayscale() {
        outputArray[0] = new int[][]{new int[]{91, 91, 91}, new int[]{170, 170, 170}, new int[]{63, 63, 63}};
        outputArray[1] = new int[][]{new int[]{184, 184, 184}, new int[]{17, 17, 17}, new int[]{203, 203, 203}};
        outputArray[2] = new int[][]{new int[]{112, 112, 112}, new int[]{244, 244, 244}, new int[]{99, 99, 99}};
        //setup
        equalPhotos(imageArray);
        //method
        replEffects.grayscale(image.getImageRef());

        raster = image.getRaster();

        //test output
        equalPhotos(outputArray);
    }
}
