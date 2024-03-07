package persistance;

import model.Photo;
import model.PhotoAlbum;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;

//CITATION: CPSC210/JsonSerializationDemo github repo
public class SaveState {
    private final PrintWriter writer;
    private final String saveName;
    private static final char[] INVALID_CHARACTERS = {'<', '>', ':', '"', '/', '\\', '|', '?', '*'};

    public SaveState(String destination, String saveName) throws IOException {
        if (!isValidFilename(saveName)) {
            throw new IOException("Invalid File Name");
        } else {
            File file = new File(destination + saveName + ".json");
            if (!file.createNewFile()) {
                throw new IOException("File already exists or cannot be created");
            } else {
                this.writer = new PrintWriter(file);
                this.saveName = saveName;
            }
        }
    }

    public SaveState(String saveName) throws IOException {
        if (!isValidFilename(saveName)) {
            throw new IOException("Invalid File Name");
        } else {
            File file = new File("./data/" + saveName + ".json");
            if (!file.createNewFile()) {
                throw new IOException("File already exists or cannot be created");
            } else {
                this.writer = new PrintWriter(file);
                this.saveName = saveName;
            }
        }
    }

    public void write(PhotoAlbum album) {
        JSONObject json = toJson(album);
        writer.print(json.toString(4));
    }

    public void close() {
        writer.close();
    }

    private boolean isValidFilename(String filename) {
        for (char invalidChar : INVALID_CHARACTERS) {
            if (filename.indexOf(invalidChar) != -1) {
                return false;
            }
        }
        return true;
    }

    private JSONObject toJson(PhotoAlbum album) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", saveName);
        jsonObject.put("all", arrayToJson(album.getAlbum()));
        jsonObject.put("selected", arrayToJson(album.getSelected()));

        return jsonObject;
    }

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
