package model;

import java.util.ArrayList;

// A class the represents multiple / a list of photos and stored the photos that are currently selected
public class PhotoAlbum implements SimpleEffects {
    private ArrayList<Photo> album;
    private ArrayList<Photo> selected;

    // MODIFIES: this
    // EFFECTS: Constructs a PhotoAlbum object with empty album and selected arrays
    public PhotoAlbum() {
        album = new ArrayList<Photo>();
        selected = new ArrayList<Photo>();
    }

    // MODIFIES: this
    // EFFECTS: Adds the photo to the photo album
    public void addPhoto(Photo p) {
        album.add(p);
    }

    // MODIFIES: this
    // EFFECTS: Removes the first instance of photo to the photo album
    public void removePhoto(Photo p) {
        album.remove(p);
        if (!album.contains(p)) {
            selected.remove(p);
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds the photo to the photo album if the Photo is in album, but not already selected
    public void selectPhoto(Photo p) {
        if (album.contains(p) && !selected.contains(p)) {
            selected.add(p);
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes photo from selected list if the photo is currently selected
    public void deselectPhoto(Photo p) {
        if (selected.contains(p)) {
            selected.remove(p);
        }
    }

    // REQUIRES: newHex and oldHex are both valid 6 digit hexadecimal values, tolerance is >= 0
    // MODIFIES: this
    // EFFECTS: Recolors all the pixels a 'tolerance' distance away from oldHex to newHex for an arbitrary amount
    // of selected photos
    @Override
    public void recolor(String newHex, String oldHex, int tolerance) {
        for (Photo p : selected) {
            p.recolor(newHex, oldHex, tolerance);
        }
    }

    // REQUIRES: nextColor, redBounds, greenBounds, and blueBounds are all 2 element lists [x, y],
    // where x is within the range [0, y] and y is in the range [x, 256]
    // MODIFIES: this
    // EFFECTS: Precisely recolors all the pixels a 'tolerance' distance away from oldHex to newHex for an arbitrary
    // amount of selected photos
    @Override
    public void recolor(int[] nextColor, int[] redBounds, int[] greenBounds, int[] blueBounds) {
        for (Photo p : selected) {
            p.recolor(nextColor, redBounds, greenBounds, blueBounds);
        }
    }

    // MODIFIES: this
    // EFFECTS: Applies a Gaussian blur to an arbitrary amount of selected Images
    @Override
    public void blur() {
        for (Photo p : selected) {
            p.blur();
        }
    }

    // REQUIRES: intensity >= 0
    // MODIFIES: this
    // EFFECTS: Applies a Gaussian blur to an arbitrary amount of selected Images (intensity) times
    @Override
    public void blur(int intensity) {
        for (Photo p : selected) {
            p.blur(intensity);
        }
    }

    // MODIFIES: this
    // EFFECTS: Applies the invert effect to an arbitrary amount of selected Images times
    @Override
    public void invert() {
        for (Photo p : selected) {
            p.invert();
        }
    }

    // MODIFIES: this
    // EFFECTS: Scales all colors in selected Album Images to shades of gray
    @Override
    public void grayscale() {
        for (Photo p : selected) {
            p.grayscale();
        }
    }

    public ArrayList<Photo> getAlbum() {
        return album;
    }

    public ArrayList<Photo> getSelected() {
        return selected;
    }

    public int getAlbumSize() {
        return album.size();
    }

    public int getSelectedSize() {
        return selected.size();
    }

    public void setAlbum(ArrayList<Photo> album) {
        this.album = album;
    }

    public void setSelected(ArrayList<Photo> selected) {
        this.selected = selected;
    }
}
