package ui;

import model.Photo;
import model.PhotoAlbum;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// A class that sorts utility / helper methods for the EditorApp class
public class EditorUtils {
    // MODIFIES: this
    // EFFECTS: Handles a basic yes or no input through terminal, returns true if yes is selected
    protected boolean yesOrNo() {
        Scanner input = new Scanner(System.in);
        String answer = "";
        while (!answer.equals("y") && !answer.equals("n")) {
            System.out.print("\n\u001B[31m CONTINUE? Y/N  ");
            answer = input.nextLine();
            if (answer.equalsIgnoreCase("y")) {
                return true;
            } else if (answer.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.print("\n\u001B[31m invalid argument: try again");
        }
        return false;
    }

    // EFFECTS: Returns a concatenated list of all the photo names in the photo album
    protected String getArrayNames(ArrayList<Photo> photoAlbum) {
        String selection = "[ ";

        for (Photo p : photoAlbum) {
            selection += p.getName() + " ";
        }
        selection += "]";

        return selection;
    }

    // EFFECTS: creates a menu to select items out of the elements in a photo album
    protected void generateSwitchPrint(ArrayList<Photo> photoAlbum) {
        System.out.println("\n\n\u001B[36m Choose one of the following options: \n");
        int count = 1;
        for (Photo p : photoAlbum) {
            System.out.println("\t" + (count) + ". " + p.getName());
            count++;
        }
        System.out.println("\t" + (count) + ". quit");
        System.out.print("\n Your selection: ");
    }

    // EFFECTS: creates a menu to select items out of all the possible applicable image effects
    protected void generateEffectPrint() {
        System.out.println("\n\n\u001B[36m Choose one of the following options: \n");

        System.out.println("\t1. Recolor");
        System.out.println("\t2. Blur");
        System.out.println("\t3. Blur Multiple Times");
        System.out.println("\t4. Invert");
        System.out.println("\t5. Grayscale");
        System.out.println("\t6. Quit");
    }

    // REQUIRES: caseNumber <= 5 && caseNumber >= 1
    // MODIFIES: selected
    // EFFECTS: handles the application of all effects onto the selected photos
    protected void handleEffectSwitch(int caseNumber, PhotoAlbum selected) {
        switch (caseNumber) {
            case 1:
                handleRecolor(selected);
                break;
            case 2:
                handleBlur(selected);
                break;
            case 3:
                handleMultipleBlur(selected);
                break;
            case 4:
                handleInvert(selected);
                break;
            case 5:
                handleGrayscale(selected);
                break;
        }
    }

    // EFFECTS: creates a menu for the creation and deletion of items into the photo album
    protected void generateModifiesSwitch() {
        System.out.println("\n\n\u001B[36m Choose one of the following options: \n");

        System.out.println("\t1. Add a new Image (must be within src folder)");
        System.out.println("\t2. Delete an existing Image (from the program)");
        System.out.println("\t3. Quit");
    }

    // MODIFIES: album
    // EFFECTS: Adds a new photo within src to the photo album
    protected void addPhoto(PhotoAlbum album) {
        Scanner input = new Scanner(System.in);
        System.out.print("\n\n\u001B[36m Declare the directory of the file: ");
        String temp = input.nextLine();
        try {
            BufferedImage testImage = ImageIO.read(new File(temp));
        } catch (IOException i) {
            System.err.println("Unhandled IO Exception: Does this file really exist?");
            return;
        }
        System.out.print("\n\n\u001B[36m Declare the name of the image: ");
        String name = input.nextLine();
        album.addPhoto(new Photo(temp, name));
    }

    // MODIFIES: album
    // EFFECTS: Deletes a photo within src to the photo album
    protected void deletePhoto(PhotoAlbum album) {
        System.out.println("\n\u001B[36m Delete a photo:");
        generateSwitchPrint(album.getAlbum());
        int answer = numericalInput(1, album.getAlbumSize());


        Photo target = (album.getAlbum().get(answer - 1));
        album.removePhoto(target);

        System.out.println("\n\n\u001B[36m Successfully removed photo!: \n");
    }

    // MODIFIES: selected
    // EFFECTS: Recolors all the photos that have been selected
    private void handleRecolor(PhotoAlbum selected) {
        String oldHex = hexInput("\n Old Hex digit: ");
        String newHex = hexInput("\n New Hex digit: ");
        System.out.print("\n Tolerance: ");
        int tolerance = numericalInput(0);
        selected.recolor(newHex, oldHex, tolerance);
        System.out.println(" Applied effects to " + (selected.getSelectedSize()) + " items!");
        stop(750);
    }

    // MODIFIES: selected
    // EFFECTS: Blurs all the photos that have been selected
    private void handleBlur(PhotoAlbum selected) {
        selected.blur();
        System.out.println(" Applied effects to " + (selected.getSelectedSize()) + " items!");
        stop(750);
    }

    // MODIFIES: selected
    // EFFECTS: Blurs all the photos that have been selected multiple times
    private void handleMultipleBlur(PhotoAlbum selected) {
        System.out.print("\n Blur repetitions: ");
        int reps = numericalInput(0);
        selected.blur(reps);
        System.out.println(" Applied effects to " + (selected.getSelectedSize()) + " items!");
        stop(750);
    }

    // MODIFIES: selected
    // EFFECTS: Inverts all the photos that have been selected
    private void handleInvert(PhotoAlbum selected) {
        selected.invert();
        System.out.println(" Applied effects to " + (selected.getSelectedSize()) + " items!");
        stop(750);
    }

    // MODIFIES: selected
    // EFFECTS: Grayscales all the photos that have been selected
    private void handleGrayscale(PhotoAlbum selected) {
        selected.grayscale();
        System.out.println(" Applied effects to " + (selected.getSelectedSize()) + " items!");
        stop(750);
    }

    // EFFECTS: Exits out of the JVM
    protected void exit() {
        System.exit(0);
    }

    // REQUIRES: t >= 0
    // EFFECTS: delays time for t milliseconds
    protected void stop(int t) {
        try {
            TimeUnit.MILLISECONDS.sleep(t);
        } catch (Exception i) {
            System.out.println("Encountered InterruptedException.");
        }
    }

    // REQUIRES: low >= 1, low <= high
    // EFFECTS: handles a numerical Input within a certain range
    protected int numericalInput(int low, int high) {
        Scanner input = new Scanner(System.in);
        int answer = low - 1;

        while (answer > high || answer < low) {
            String temp = input.nextLine();
            answer = tryParse(temp);
            if (answer > high || answer < low) {
                System.err.println("Not within range!");
            }
        }

        return answer;
    }

    // REQUIRES: low >= 1
    // EFFECTS: handles a numerical Input above a certain range
    protected int numericalInput(int low) {
        Scanner input = new Scanner(System.in);
        int answer = low - 1;

        while (answer < low) {
            String temp = input.nextLine();
            answer = tryParse(temp);
        }

        return answer;
    }

    // EFFECTS: handles a hexadecimal Input with strict specifications
    protected String hexInput(String question) {
        int temp = -1;
        Scanner input = new Scanner(System.in);
        String answer = "";

        while (temp == -1) {
            System.out.print(question);
            String lastInput = input.nextLine();
            if (lastInput.length() != 6) {
                System.err.println("Not a valid RGB value! (length should be 6)");
                continue;
            }
            temp = tryParseHex(lastInput);
            answer = lastInput;
        }
        return answer;
    }

    // EFFECTS: Attempts to parse a integer-representing string into an integer
    protected int tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            System.err.println("Not a number!");
            return -1;
        }
    }

    // EFFECTS: Attempts to parse a hexadecimal-representing string into an integer
    protected int tryParseHex(String text) {
        try {
            return Integer.parseInt(text, 16);
        } catch (NumberFormatException e) {
            System.err.println("Not a valid hex value!");
            return -1;
        }
    }
}
