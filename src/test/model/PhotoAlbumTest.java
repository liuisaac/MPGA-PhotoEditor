package model;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class PhotoAlbumTest {
    private Photo photoH;
    private Photo photoL;
    private Photo photoT;
    private PhotoAlbum album;

    private WritableRaster raster;
    private int[][][] imageArray;
    private int[][][] outputArray;

    private int width;
    private int height;

    // Test helper that checks if two bufferedImages are equivalent
    private boolean equalPhotos(BufferedImage first, BufferedImage second) {
        //not same image size
        if (!(first.getWidth() == second.getWidth()) || !(first.getHeight() == second.getHeight())) {
            return false;
        }
        //pixel by pixel checks
        for (int x = 0; x < first.getWidth(); x++) {
            for (int y = 0; y < first.getHeight(); y++) {
                if (first.getRGB(x, y) != second.getRGB(x, y))
                    return false;
            }
        }
        return true;
    }

    @BeforeEach
    void setup() {
        photoH = new Photo("src/assets/test/happy.png");
        photoL = new Photo("src/assets/test/logo.png");
        photoT = new Photo("src/assets/test/threetest.png");

        album = new PhotoAlbum();

        assertEquals(0, album.getAlbumSize());
        album.addPhoto(photoH);
        album.addPhoto(photoL);
        album.addPhoto(photoT);
        album.addPhoto(photoH);
        assertEquals(4, album.getAlbumSize());
    }

    @Test
    void testAddPhoto() {
        ArrayList<Photo> albumList = album.getAlbum();

        assertEquals(albumList.get(0), photoH);
        assertEquals(albumList.get(1), photoL);
        assertEquals(albumList.get(2), photoT);
        assertEquals(albumList.get(3), photoH);
    }

    @Test
    void testRemovePhoto() {
        album.removePhoto(photoH);

        ArrayList<Photo> albumList = album.getAlbum();

        assertEquals(albumList.get(0), photoL);
        assertEquals(albumList.get(1), photoT);
        assertEquals(albumList.get(2), photoH);

        album.removePhoto(photoH);

        assertEquals(2, album.getAlbumSize());

        assertEquals(albumList.get(0), photoL);
        assertEquals(albumList.get(1), photoT);
    }

    @Test
    void testSelectPhoto() {
        album.selectPhoto(photoH);

        assertEquals(1, album.getSelectedSize());

        album.selectPhoto(photoT);
        album.selectPhoto(photoH);

        assertEquals(2, album.getSelectedSize());

        ArrayList<Photo> albumList = album.getSelected();

        assertEquals(albumList.get(0), photoH);
        assertEquals(albumList.get(1), photoT);

        album.removePhoto(photoH);

        assertEquals(albumList.get(0), photoH);
        assertEquals(albumList.get(1), photoT);

        album.removePhoto(photoH);

        assertEquals(albumList.get(0), photoT);
    }

    @Test
    void testDeselectPhoto() {
        album.selectPhoto(photoH);
        album.selectPhoto(photoT);
        album.selectPhoto(photoL);

        assertEquals(3, album.getSelectedSize());

        ArrayList<Photo> albumList = album.getSelected();

        assertEquals(albumList.get(0), photoH);
        assertEquals(albumList.get(1), photoT);
        assertEquals(albumList.get(2), photoL);

        album.deselectPhoto(photoT);

        assertEquals(2, album.getSelectedSize());

        assertEquals(albumList.get(0), photoH);
        assertEquals(albumList.get(1), photoL);

        assertEquals(4, album.getAlbumSize());
    }

    @Test
    void testRecolor() {
        album.selectPhoto(photoT);
        album.selectPhoto(photoH);

        Photo tcopy = new Photo("src/assets/test/threetest.png");
        Photo hcopy = new Photo("src/assets/test/happy.png");
        Photo lcopy = new Photo("src/assets/test/logo.png");

        assertEquals(2, album.getSelectedSize());

        album.recolor("000000", "ff0000", 10);
        tcopy.recolor("000000", "ff0000", 10);
        hcopy.recolor("000000", "ff0000", 10);

        assertTrue(equalPhotos(album.getSelected().get(0).getImageRef(), tcopy.getImageRef()));
        assertTrue(equalPhotos(album.getSelected().get(1).getImageRef(), hcopy.getImageRef()));

        assertTrue(equalPhotos(album.getAlbum().get(0).getImageRef(), hcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(1).getImageRef(), lcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(2).getImageRef(), tcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(3).getImageRef(), hcopy.getImageRef()));
    }

    @Test
    void testPreciseRecolor() {
        album.selectPhoto(photoT);
        album.selectPhoto(photoH);

        Photo tcopy = new Photo("src/assets/test/threetest.png");
        Photo hcopy = new Photo("src/assets/test/happy.png");
        Photo lcopy = new Photo("src/assets/test/logo.png");

        assertEquals(2, album.getSelectedSize());

        album.recolor(new int[]{255, 255, 255},
                new int[]{0, 256},
                new int[]{0, 256},
                new int[]{0, 60});
        tcopy.recolor(new int[]{255, 255, 255},
                new int[]{0, 256},
                new int[]{0, 256},
                new int[]{0, 60});
        hcopy.recolor(new int[]{255, 255, 255},
                new int[]{0, 256},
                new int[]{0, 256},
                new int[]{0, 60});

        assertTrue(equalPhotos(album.getSelected().get(0).getImageRef(), tcopy.getImageRef()));
        assertTrue(equalPhotos(album.getSelected().get(1).getImageRef(), hcopy.getImageRef()));

        assertTrue(equalPhotos(album.getAlbum().get(0).getImageRef(), hcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(1).getImageRef(), lcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(2).getImageRef(), tcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(3).getImageRef(), hcopy.getImageRef()));
    }

    @Test
    void testBlur() {
        album.selectPhoto(photoT);
        album.selectPhoto(photoH);

        Photo tcopy = new Photo("src/assets/test/threetest.png");
        Photo hcopy = new Photo("src/assets/test/happy.png");
        Photo lcopy = new Photo("src/assets/test/logo.png");

        assertEquals(2, album.getSelectedSize());

        album.blur();
        tcopy.blur();
        hcopy.blur();

        assertTrue(equalPhotos(album.getSelected().get(0).getImageRef(), tcopy.getImageRef()));
        assertTrue(equalPhotos(album.getSelected().get(1).getImageRef(), hcopy.getImageRef()));

        assertTrue(equalPhotos(album.getAlbum().get(0).getImageRef(), hcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(1).getImageRef(), lcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(2).getImageRef(), tcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(3).getImageRef(), hcopy.getImageRef()));
    }

    @Test
    void testBlurIntensity() {
        album.selectPhoto(photoT);
        album.selectPhoto(photoH);

        Photo tcopy = new Photo("src/assets/test/threetest.png");
        Photo hcopy = new Photo("src/assets/test/happy.png");
        Photo lcopy = new Photo("src/assets/test/logo.png");

        assertEquals(2, album.getSelectedSize());

        album.blur(2);
        tcopy.blur(2);
        hcopy.blur(2);

        assertTrue(equalPhotos(album.getSelected().get(0).getImageRef(), tcopy.getImageRef()));
        assertTrue(equalPhotos(album.getSelected().get(1).getImageRef(), hcopy.getImageRef()));

        assertTrue(equalPhotos(album.getAlbum().get(0).getImageRef(), hcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(1).getImageRef(), lcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(2).getImageRef(), tcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(3).getImageRef(), hcopy.getImageRef()));
    }

    @Test
    void testInvert() {
        album.selectPhoto(photoT);
        album.selectPhoto(photoH);

        Photo tcopy = new Photo("src/assets/test/threetest.png");
        Photo hcopy = new Photo("src/assets/test/happy.png");
        Photo lcopy = new Photo("src/assets/test/logo.png");

        assertEquals(2, album.getSelectedSize());

        album.invert();
        tcopy.invert();
        hcopy.invert();

        assertTrue(equalPhotos(album.getSelected().get(0).getImageRef(), tcopy.getImageRef()));
        assertTrue(equalPhotos(album.getSelected().get(1).getImageRef(), hcopy.getImageRef()));

        assertTrue(equalPhotos(album.getAlbum().get(0).getImageRef(), hcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(1).getImageRef(), lcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(2).getImageRef(), tcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(3).getImageRef(), hcopy.getImageRef()));
    }

    @Test
    void testGrayscale() {
        album.selectPhoto(photoT);
        album.selectPhoto(photoH);

        Photo tcopy = new Photo("src/assets/test/threetest.png");
        Photo hcopy = new Photo("src/assets/test/happy.png");
        Photo lcopy = new Photo("src/assets/test/logo.png");

        assertEquals(2, album.getSelectedSize());

        album.grayscale();
        tcopy.grayscale();
        hcopy.grayscale();

        assertTrue(equalPhotos(album.getSelected().get(0).getImageRef(), tcopy.getImageRef()));
        assertTrue(equalPhotos(album.getSelected().get(1).getImageRef(), hcopy.getImageRef()));

        assertTrue(equalPhotos(album.getAlbum().get(0).getImageRef(), hcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(1).getImageRef(), lcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(2).getImageRef(), tcopy.getImageRef()));
        assertTrue(equalPhotos(album.getAlbum().get(3).getImageRef(), hcopy.getImageRef()));
    }
}
