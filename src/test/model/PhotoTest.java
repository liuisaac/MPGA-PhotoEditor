package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.WritableRaster;

import static org.junit.jupiter.api.Assertions.*;

public class PhotoTest {
    private Photo p;

    private WritableRaster raster;
    private int[][][] imageArray;
    private int[][][] outputArray;

    private int width;
    private int height;

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
        p = new Photo("src/assets/test/threetest.png", "p");
        width = p.getWidth();
        height = p.getHeight();
        raster = p.getRaster();

        imageArray = new int[3][3][3];
        imageArray[0] = new int[][]{new int[]{21, 185, 67}, new int[]{157, 187, 166}, new int[]{63, 63, 63}};
        imageArray[1] = new int[][]{new int[]{184, 184, 184}, new int[]{17, 17, 17}, new int[]{203, 203, 203}};
        imageArray[2] = new int[][]{new int[]{79, 85, 173}, new int[]{242, 242, 249}, new int[]{215, 38, 45}};

        outputArray = new int[3][3][3];
    }

    @Test
    void testRecolor() {
        outputArray[0] = new int[][]{new int[]{255, 0, 0}, new int[]{157, 187, 166}, new int[]{63, 63, 63}};
        outputArray[1] = new int[][]{new int[]{184, 184, 184}, new int[]{17, 17, 17}, new int[]{203, 203, 203}};
        outputArray[2] = new int[][]{new int[]{79, 85, 173}, new int[]{242, 242, 249}, new int[]{215, 38, 45}};

        int[][][] outputArray2 = new int[3][3][3];
        outputArray2[0] = new int[][]{new int[]{255, 0, 0}, new int[]{157, 187, 166}, new int[]{255, 0, 0}};
        outputArray2[1] = new int[][]{new int[]{184, 184, 184}, new int[]{255, 0, 0}, new int[]{203, 203, 203}};
        outputArray2[2] = new int[][]{new int[]{79, 85, 173}, new int[]{242, 242, 249}, new int[]{215, 38, 45}};

        //setup
        equalPhotos(imageArray);

        //method
        p.recolor("ff0000", "14b842", 2);

        equalPhotos(outputArray);

        p.recolor("ff0000", "000000", 100);

        equalPhotos(outputArray2);
    }

    @Test
    void testBlur() {
        outputArray[0] = new int[][]{new int[]{0, 0, 0}, new int[]{0, 0, 0}, new int[]{0, 0, 0}};
        outputArray[1] = new int[][]{new int[]{0, 0, 0}, new int[]{126, 129, 126}, new int[]{0, 0, 0}};
        outputArray[2] = new int[][]{new int[]{0, 0, 0}, new int[]{0, 0, 0}, new int[]{0, 0, 0}};

        int[][][] outputArray2 = new int[3][3][3];
        outputArray2[0] = new int[][]{new int[]{0, 0, 0}, new int[]{0, 0, 0}, new int[]{0, 0, 0}};
        outputArray2[1] = new int[][]{new int[]{0, 0, 0}, new int[]{1, 2, 1}, new int[]{0, 0, 0}};
        outputArray2[2] = new int[][]{new int[]{0, 0, 0}, new int[]{0, 0, 0}, new int[]{0, 0, 0}};

        //setup
        equalPhotos(imageArray);

        //method
        p.blur(1);
        raster = p.getRaster();

        equalPhotos(outputArray);

        p.blur(3);
        raster = p.getRaster();

        equalPhotos(outputArray2);
    }
}
