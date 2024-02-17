package model.tools;

public class ManageHex {
    public int[] stringToHex(String hex) {
        int red = Integer.parseInt(hex.substring(0, 2), 16);
        int green = Integer.parseInt(hex.substring(2, 4), 16);
        int blue = Integer.parseInt(hex.substring(4, 6), 16);

        return new int[]{red, green, blue};
    }
}
