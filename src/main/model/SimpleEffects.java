package model;

// An interface for all classes that implement basic image effects
public interface SimpleEffects {
    public void recolor(String newHex, String oldHex, int tolerance);

    public void recolor(int[] nextColor, int[] redBounds, int[] greenBounds, int[] blueBounds);

    public void blur();

    public void blur(int intensity);

    public void invert();

    public void grayscale();
}
