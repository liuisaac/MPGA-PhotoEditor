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

//CITATION: CPSC210/JsonSerializationDemo github repo
public class LoadState {
    private final PhotoAlbum oldAlbum;

    // EFFECTS: constructs reader to read from source file
    public LoadState(String source) throws IOException {
        this.oldAlbum = new PhotoAlbum();
        JSONObject jsonObject = new JSONObject(readFile(source));
        loadAll(jsonObject);
        displayDiagnostics(jsonObject.getString("name"));
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

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

    private void loadAll(JSONObject jsonObject) {
        ArrayList<Photo> album;
        ArrayList<Photo> selected;

        album = getAll(jsonObject);
        selected = getSelected(jsonObject, album);

        oldAlbum.setAlbum(album);
        oldAlbum.setSelected(selected);
    }

    private ArrayList<Photo> getAll(JSONObject jsonObject) {
        ArrayList<JSONObject> jsonArray = photoArray(jsonObject, "all");
        ArrayList<Photo> photos = new ArrayList<Photo>();

        for (Object object : jsonArray) {
            photos.add(toPhoto((JSONObject) object));
        }

        return photos;
    }

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

    private ArrayList<JSONObject> photoArray(JSONObject json, String key) {
        ArrayList<JSONObject> temporaryArray = new ArrayList<JSONObject>();
        JSONArray objects = json.getJSONArray(key);

        for (Object object : objects) {
            JSONObject nextPhoto = (JSONObject) object;
            temporaryArray.add(nextPhoto);
        }

        return temporaryArray;
    }

    private Photo toPhoto(JSONObject json) {
        String path = json.getString("path");
        String name = json.getString("name");

        return new Photo(path, name);
    }
}