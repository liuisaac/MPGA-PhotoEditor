package persistance;

import model.Photo;
import model.PhotoAlbum;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

//A class that manages loading in states and JSON save files initially on startup
//CITATION: CPSC210/JsonSerializationDemo github repo
public class LoadState {
    private final PhotoAlbum oldAlbum;

    // REQUIRES: A valid destination for a JSON save file
    // MODIFIES: this
    // EFFECTS: Creates a LoadState object based on the directory that manages the save data
    public LoadState(String source) throws IOException {
        this.oldAlbum = new PhotoAlbum();
        JSONObject jsonObject = new JSONObject(readFile(source));
        loadAll(jsonObject);
        displayDiagnostics(jsonObject.getString("name"));
    }

    // REQUIRES: A valid destination for a JSON save file
    // EFFECTS: Attempts to read a file from the destination and convert it into text
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: Displays details of what elements have been loaded to the terminal
    public void displayDiagnostics(String name) {
        System.out.println("LOADED STATE :" + name);

        for (Photo p : oldAlbum.getAlbum()) {
            System.out.println(p.getName());
        }
        for (Photo p : oldAlbum.getSelected()) {
            System.out.println(p.getName());
        }
    }

    public PhotoAlbum getAlbum() {
        return oldAlbum;
    }

    // REQUIRES: A valid save JSON
    // MODIFIES: this
    // EFFECTS: Loads all the information from the JSON to an actual PhotoAlbum
    private void loadAll(JSONObject jsonObject) {
        ArrayList<Photo> album;
        ArrayList<Photo> selected;

        album = getAll(jsonObject);
        selected = getSelected(jsonObject, album);

        oldAlbum.setAlbum(album);
        oldAlbum.setSelected(selected);
    }

    // REQUIRES: A valid JSON save file
    // EFFECTS: Extracts all the photo in a save JSON
    private ArrayList<Photo> getAll(JSONObject jsonObject) {
        ArrayList<JSONObject> jsonArray = photoArray(jsonObject, "all");
        ArrayList<Photo> photos = new ArrayList<Photo>();

        for (Object object : jsonArray) {
            photos.add(toPhoto((JSONObject) object));
        }

        return photos;
    }

    // REQUIRES: A valid JSON save file and corresponding All Album list
    // EFFECTS: Extracts all the selected photo in a save JSON
    private ArrayList<Photo> getSelected(JSONObject jsonObject, ArrayList<Photo> album) {
        ArrayList<Photo> selected = new ArrayList<Photo>();
        JSONArray objects = jsonObject.getJSONArray("selected");

        for (Object object : objects) {
            String targetName = ((JSONObject) object).getString("name");
            for (Photo photo : album) {
                if (photo.getName().equals(targetName)) {
                    selected.add(photo);
                }
            }
        }

        return selected;
    }

    // REQUIRES: A valid JSON save file and a key for the JSON field
    // EFFECTS: Extracts all the content in a save JSON's Array field
    private ArrayList<JSONObject> photoArray(JSONObject json, String key) {
        ArrayList<JSONObject> temporaryArray = new ArrayList<JSONObject>();
        JSONArray objects = json.getJSONArray(key);

        for (Object object : objects) {
            JSONObject nextPhoto = (JSONObject) object;
            temporaryArray.add(nextPhoto);
        }

        return temporaryArray;
    }

    // REQUIRES: A valid JSON save file
    // EFFECTS: Formats a Photo recorded in the JSON to an actual Photo Object
    private Photo toPhoto(JSONObject json) {
        String path = json.getString("path");
        String name = json.getString("name");

        return new Photo(path, name);
    }
}