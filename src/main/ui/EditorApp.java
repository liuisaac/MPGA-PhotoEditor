package ui;

import model.PhotoAlbum;
import model.Photo;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class EditorApp {
    private PhotoAlbum photoAlbum;

    public EditorApp() {
        photoAlbum = new PhotoAlbum();
        runEditor();
    }

    public void runEditor() {
        boolean gameOver = false;
        startUp();
        while (!gameOver) {

        }
    }

    public void startUp() {
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

        stop(2000);
        System.out.println("\\u001B[35m Loading Editor...");
    }

    private void load() {
        File folder = new File("src/assets/input");
        File[] files = folder.listFiles();

        if (files.length == 0) {
            System.out.println("\u001B[31m  No Files in src/assets/input! Closing program");
            exit();
        }

        for (File file : files) {
            String fileName = file.getName();
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
            if (file.isFile() && extension.equals("png")) {
                photoAlbum.addPhoto(new Photo("src/assets/input/" + fileName));
                System.out.println("\t\u001B[33m - Photo " + fileName + " loaded!");
            }
        }

        if (photoAlbum.getAlbumSize() == 0) {
            System.err.println("Error: None of the files in [input] are valid .png files!");
            exit();
        } else {
            System.out.println("\u001B[32m Successfully loaded "
                    + Integer.toString(photoAlbum.getAlbumSize())
                    + "\u001B[32m item(s)!");
        }
    }

    private void clear() {
        File folder = new File("src/assets/output");
        File[] files = folder.listFiles();
        int count = 0;

        for (File file : files) {
            file.delete();
            count++;
        }

        System.out.println("\n\u001B[31m Successfully deleted "
                + Integer.toString(count)
                + "\u001B[31m item(s)!");
    }

    private boolean yesOrNo() {
        Scanner input = new Scanner(System.in);
        String answer = "";
        while (!answer.equals("y") && !answer.equals("n")) {
            System.out.print("\n\u001B[31m CONTINUE? Y/N  ");
            answer = input.nextLine();
            if (answer.toLowerCase().equals("y")) {
                input.close();
                return true;
            } else if (answer.toLowerCase().equals("n")) {
                input.close();
                return false;
            }
            System.out.print("\n\u001B[31m invalid argument: try again");
        }
        return false;
    }

    private void exit() {
        System.exit(0);
    }

    private void stop(int t) {
        try {
            TimeUnit.MILLISECONDS.sleep(t);
        } catch (Exception i) {
            System.out.println("Encountered InterruptedException.");
        }
    }
}
