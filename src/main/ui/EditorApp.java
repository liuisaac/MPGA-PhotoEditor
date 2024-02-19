package ui;

import model.PhotoAlbum;
import model.Photo;

import java.io.File;
import java.util.Scanner;

// A class that manages the startup, decision-behaviour, and closing of the application
public class EditorApp extends EditorUtils {
    private final PhotoAlbum photoAlbum;

    // MODIFIES: this
    // EFFECTS: Constructs the EditorApp object, initializes fields, and kickstart the program decision tree
    public EditorApp() {
        photoAlbum = new PhotoAlbum();
        startUp();
        decisionTree();
    }


    // MODIFIES: this
    // EFFECTS: Boots up the program and populates the photo album with all the png files in src/assets/input
    private void startUp() {
        System.out.println("\u001B[32m Photo Editor Booted!");
        System.out.println("\n\n\u001B[34m Welcome to this CPSC 210 Project: "
                + "A Photo Editor specialized for logo recoloring");
        System.out.println("\n\u001B[31m All files within src/assets/input will be loaded "
                + "and all files within src/assets/output will be deleted.");
        if (yesOrNo()) {
            load();
            System.out.println("\u001B[32m Loaded Input!");
            clear();
            System.out.println("\u001B[32m Cleared Output!");
        }
        System.out.println("\u001B[35m Loading Editor...");
        stop(1000);
        System.out.println("\u001B[36m Editor loaded!");
    }

    // MODIFIES: this
    // EFFECTS: Manages the entire first decision in the decision tree, the home page after the editor loads
    private void decisionTree() {
        while (true) {
            System.out.println("\n\n\n\u001B[37m What would you like to do?");
            System.out.println("\t\u001B[37m 1. Select/Deselect Photo");
            System.out.println("\t\u001B[37m 2. Edit Selected Photo");
            System.out.println("\t\u001B[37m 3. See Selected Photos");
            System.out.println("\t\u001B[37m 4. Add/Delete Photo");
            System.out.println("\t\u001B[31m 5. Save & Quit Program");

            System.out.print("\n\n\u001B[35m (Choose an option, 1-5): ");
            handleFirstSwitch(numericalInput(1, 5));
        }
    }

    // MODIFIES: this
    // EFFECTS: Determines which decision behaviour to run based on the switch value of answer from 1-5
    private void handleFirstSwitch(int answer) {
        switch (answer) {
            case 1:
                option1();
                break;
            case 2:
                option2();
                break;
            case 3:
                option3();
                break;
            case 4:
                option4();
                break;
            case 5:
                System.out.println("Quitting...");
                exit();
                break;
            default:
                System.err.println("That is not a valid option, please try again.");
                System.out.print("\n\u001B[35m (Choose an option, 1-5): ");
        }
    }

    // MODIFIES: this
    // EFFECTS: Handles the selection and deselection of all photos in the photo album
    private void option1() {
        System.out.println("\u001B[33m Option 1 chosen");

        while (true) {
            System.out.print("\n\u001B[32m SELECTED PHOTOS: " + getArrayNames(photoAlbum.getSelected()));
            System.out.print("\n\u001B[31m All PHOTOS: " + getArrayNames(photoAlbum.getAlbum()));

            System.out.println("\n\u001B[36m Which photo would you like to toggle (select/deselect)?");
            generateSwitchPrint(photoAlbum.getAlbum());

            int answer = numericalInput(1, photoAlbum.getAlbumSize() + 1);

            if (answer >= photoAlbum.getAlbumSize() + 1) {
                break;
            } else {
                Photo target = (photoAlbum.getAlbum().get(answer - 1));
                if ((photoAlbum.getSelected()).contains(target)) {
                    photoAlbum.deselectPhoto(target);
                } else {
                    photoAlbum.selectPhoto(target);
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: handles all the edits made to all selected photos in the photo album
    private void option2() {
        System.out.println("\u001B[33m Option 2 chosen");

        while (true) {
            System.out.print("\n\u001B[32m SELECTED PHOTOS: " + getArrayNames(photoAlbum.getSelected()));

            System.out.println("\n\u001B[36m Which effect would you like to apply?");
            generateEffectPrint();
            System.out.print("\n Your selection: ");
            int caseNumber = numericalInput(1, 6);
            if (caseNumber == 6) {
                break;
            }
            handleEffectSwitch(caseNumber, photoAlbum);
        }
    }

    // EFFECTS: handles the displaying and outputting of selected images
    private void option3() {
        System.out.println("\u001B[33m Option 3 chosen");
        while (true) {
            System.out.print("\n\u001B[32m SELECTED PHOTOS: " + getArrayNames(photoAlbum.getSelected()));

            System.out.println("\n\u001B[36m Which photo would you like to view?");
            generateSwitchPrint(photoAlbum.getSelected());
            int caseNumber = numericalInput(1, photoAlbum.getSelectedSize() + 1);
            if (caseNumber == photoAlbum.getSelectedSize() + 1) {
                break;
            }
            (photoAlbum.getSelected().get(caseNumber - 1)).displayImage();
            System.out.println(" Tip: Remember to close the tab once you are done!");
        }
    }

    // MODIFIES: this
    // EFFECTS: manages the addition/deletion of photos in the photo album
    private void option4() {
        System.out.println("\u001B[33m Option 4 chosen");
        boolean quit = false;

        while (!quit) {
            System.out.print("\n\u001B[32m ALL PHOTOS: " + getArrayNames(photoAlbum.getAlbum()));

            generateModifiesSwitch();
            int caseNumber = numericalInput(1, 3);
            switch (caseNumber) {
                case 1:
                    addPhoto(photoAlbum);
                    break;
                case 2:
                    deletePhoto(photoAlbum);
                    break;
                case 3:
                    quit = true;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Loads all the png files in the path to the photo album
    private void load() {
        File folder = new File("src/assets/input");
        File[] files = folder.listFiles();

        if (files.length == 0) {
            System.out.println("\u001B[31m  No Files in src/assets/input! Closing program");
            exit();
        }

        for (File file : files) {
            String fileName = file.getName();
            String name = fileName.substring(0, fileName.lastIndexOf('.'));
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
            if (file.isFile() && extension.equals("png")) {
                photoAlbum.addPhoto(new Photo("src/assets/input/" + fileName, name));
                System.out.println("\t\u001B[33m - Photo " + fileName + " loaded!");
            }
        }

        if (photoAlbum.getAlbumSize() == 0) {
            System.err.println("Error: None of the files in [input] are valid .png files!");
            exit();
        } else {
            System.out.println("\u001B[32m Successfully loaded "
                    + (photoAlbum.getAlbumSize())
                    + "\u001B[32m item(s)!");
        }
    }

    // MODIFIES: files within src/assets/output
    // EFFECTS: Clears the directory of all files
    private void clear() {
        File folder = new File("src/assets/output");
        File[] files = folder.listFiles();
        int count = 0;

        for (File file : files) {
            file.delete();
            count++;
        }

        System.out.println("\n\u001B[31m Successfully deleted "
                + (count)
                + "\u001B[31m item(s)!");
    }
}
