package model.effects;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;


// A class that holds helper methods that apply convolution-based image effects onto a BufferedImage
public class ConvolutionEffects {
    private static final float[] gaussianKernel = {
            1 / 16f, 1 / 8f, 1 / 16f,
            1 / 8f, 1 / 4f, 1 / 8f,
            1 / 16f, 1 / 8f, 1 / 16f,
    };

    // REQUIRES: A Buffered Image with a width and height > 3 pixels
    // EFFECTS: Applies / convolve a filter (gaussian blur) over an image
    public BufferedImage blur(BufferedImage image) {
        BufferedImageOp operate = new ConvolveOp(new Kernel(3, 3, gaussianKernel));
        return operate.filter(image, null);
    }
}
