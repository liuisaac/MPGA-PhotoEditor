package model;

import model.effects.ConvolutionEffects;
import model.effects.ReplacementEffects;
import model.tools.ManageHex;
import model.tools.ManageImage;

public class Photo extends ManageImage implements SimpleEffects {
    private final ManageHex hexManager;
    private final ConvolutionEffects convEffect;
    private final ReplacementEffects replEffect;


    public Photo(String fileName, String fileType) {
        super(fileName + "." + fileType);
        this.hexManager = new ManageHex();
        this.convEffect = new ConvolutionEffects();
        this.replEffect = new ReplacementEffects();
    }

    public void recolor(String newHex, String oldHex, int tolerance) {
        int[] oldValues = hexManager.stringToHex(oldHex);
        int[] newValues = hexManager.stringToHex(newHex);
        recolor(
                newValues,
                new int[]{oldValues[0] - tolerance, oldValues[0] + tolerance},
                new int[]{oldValues[1] - tolerance, oldValues[1] + tolerance},
                new int[]{oldValues[2] - tolerance, oldValues[2] + tolerance});
    }

    public void recolor(int[] nextColor, int[] redBounds, int[] greenBounds, int[] blueBounds) {
        replEffect.recolor(super.getImageRef(), nextColor, redBounds, greenBounds, blueBounds);
    }

    public void blur() {
        setImageRef(convEffect.blur(super.getImageRef()));
    }

    public void blur(int intensity) {
        for (int i = 0; i < intensity; i++) {
            setImageRef(convEffect.blur(super.getImageRef()));
        }
    }

    public void invert() {
        replEffect.invert(super.getImageRef());
    }

    public void grayscale() {
        replEffect.grayscale(super.getImageRef());
    }
}
