package model;

import model.effects.ConvolutionEffects;
import model.effects.ReplacementEffects;
import model.tools.ManageImage;

import java.awt.image.WritableRaster;

// A class that represents a Photo that can be modified
public class Photo extends ManageImage {
    private final ConvolutionEffects convEffect;
    private final ReplacementEffects replEffect;
    private final String name;

    // REQUIRES: url corresponds to a valid PNG image path (including the file extension) within the project repository
    // MODIFIES: this
    // EFFECTS: Creates a photo object based on the image directory
    public Photo(String url, String name) {
        super(url);
        this.name = name;
        this.convEffect = new ConvolutionEffects();
        this.replEffect = new ReplacementEffects();
    }

    // REQUIRES: newHex and oldHex are both valid 6 digit hexadecimal values, tolerance is >= 0
    // MODIFIES: this
    // EFFECTS: Recolors all the pixels a 'tolerance' distance away from oldHex to newHex
    @Override
    public void recolor(String newHex, String oldHex, int tolerance) {
        int[] oldValues = stringToHex(oldHex);
        int[] newValues = stringToHex(newHex);
        recolor(
                newValues,
                new int[]{oldValues[0] - tolerance, oldValues[0] + tolerance},
                new int[]{oldValues[1] - tolerance, oldValues[1] + tolerance},
                new int[]{oldValues[2] - tolerance, oldValues[2] + tolerance});
    }

    // REQUIRES: nextColor, redBounds, greenBounds, and blueBounds are all 2 element lists [x, y],
    // where x is within the range [0, y] and y is in the range [x, 256]
    // MODIFIES: this
    // EFFECTS: Precisely recolors all the pixels within the red, green, and blue bounds to the nextColor color
    @Override
    public void recolor(int[] nextColor, int[] redBounds, int[] greenBounds, int[] blueBounds) {
        replEffect.recolor(super.getImageRef(), nextColor, redBounds, greenBounds, blueBounds);
    }

    // MODIFIES: this
    // EFFECTS: Applies a Gaussian blur to the existing Image
    @Override
    public void blur() {
        setImageRef(convEffect.blur(super.getImageRef()));
    }

    // REQUIRES: intensity >= 0
    // MODIFIES: this
    // EFFECTS: Applies a Gaussian blur to the existing Image (intensity) times
    @Override
    public void blur(int intensity) {
        for (int i = 0; i < intensity; i++) {
            setImageRef(convEffect.blur(super.getImageRef()));
        }
    }

    // MODIFIES: this
    // EFFECTS: inverts the colors in the existing Image
    @Override
    public void invert() {
        replEffect.invert(super.getImageRef());
    }

    // MODIFIES: this
    // EFFECTS: Scales all colors in Image to shades of gray
    @Override
    public void grayscale() {
        replEffect.grayscale(super.getImageRef());
    }

    // REQUIRES: hex is a valid 6 digit hexadecimal value
    // EFFECTS: returns the corresponding red, green, and blue values
    private int[] stringToHex(String hex) {
        int red = Integer.parseInt(hex.substring(0, 2), 16);
        int green = Integer.parseInt(hex.substring(2, 4), 16);
        int blue = Integer.parseInt(hex.substring(4, 6), 16);

        return new int[]{red, green, blue};
    }

    public int getWidth() {
        return getImageRef().getWidth();
    }

    public int getHeight() {
        return getImageRef().getHeight();
    }

    public WritableRaster getRaster() {
        return getImageRef().getRaster();
    }

    public String getName() {
        return name;
    }
}
