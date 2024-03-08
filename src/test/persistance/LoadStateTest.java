package persistance;

import model.PhotoAlbum;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

//CITATION: CPSC210/JsonSerializationDemo github repo
public class LoadStateTest {
    @Test
    void testReaderNonExistentFile() {
        try {
            LoadState reader = new LoadState("./data/test/noSuchFile.json");
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        try {
            LoadState reader = new LoadState("./data/test/testEmptySave.json");
            PhotoAlbum album = reader.getAlbum();
            assertEquals(0, album.getSelectedSize());
            assertEquals(0, album.getAlbumSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        try {
            LoadState reader = new LoadState("./data/test/testGeneralSave.json");
            PhotoAlbum album = reader.getAlbum();
            assertEquals(1, album.getSelectedSize());
            assertEquals(3, album.getAlbumSize());

            assertEquals("src/assets/input/logo-preview.png", album.getAlbum().get(0).getUrl());
            assertEquals("src/assets/test/happy.png", album.getAlbum().get(1).getUrl());
            assertEquals("src/assets/input/test.png", album.getAlbum().get(2).getUrl());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
