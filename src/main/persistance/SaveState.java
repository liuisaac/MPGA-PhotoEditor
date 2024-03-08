package persistance;

import model.Photo;
import model.PhotoAlbum;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

//A class that manages JSON save files and states on quit
//CITATION: CPSC210/JsonSerializationDemo github repo
public class SaveState {
    private final PrintWriter writer;
    private final String saveName;
    private static final char[] INVALID_CHARACTERS = {'<', '>', ':', '"', '/', '\\', '|', '?', '*', '.'};

    // REQUIRES: A valid filename corresponding to a JSON save file
    // MODIFIES: this
    // EFFECTS: Creates a SaveState object based on the directory / folder that manages the save data
    public SaveState(String saveName) throws IOException {
        if (!isValidFilename(saveName)) {
            System.err.println("Invalid File Name");
            throw new IOException();
        } else {
            File file = new File("./data/" + saveName + "/" + saveName + ".json");
            file.getParentFile().mkdir();
            this.writer = new PrintWriter(file);
            this.saveName = saveName;
        }
    }

    // MODIFIES: this
    // EFFECTS: prints values to the file destination set
    public void write(PhotoAlbum album) {
        JSONObject json = toJson(album);
        writer.print(json.toString(4));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    public String getSaveName() {
        return saveName;
    }

    // EFFECTS: Returns true if a filename contains any illegal characters
    private boolean isValidFilename(String filename) {
        for (char invalidChar : INVALID_CHARACTERS) {
            if (filename.indexOf(invalidChar) != -1) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: Converts a Photoalbum to a savable JSON
    private JSONObject toJson(PhotoAlbum album) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", saveName);
        jsonObject.put("all", arrayToJson(album.getAlbum()));
        jsonObject.put("selected", arrayToJson(album.getSelected()));

        return jsonObject;
    }

    // REQUIRES: An album with photos with unique names
    // EFFECTS: Converts a list of photos to an array in a savable JSON file
    private JSONArray arrayToJson(ArrayList<Photo> photos) {
        JSONArray jsonArray = new JSONArray();

        for (Photo photo : photos) {
            JSONObject entry = new JSONObject();
            entry.put("name", photo.getName());
            entry.put("path", photo.getUrl());

            jsonArray.put(entry);
        }

        return jsonArray;
    }
}
