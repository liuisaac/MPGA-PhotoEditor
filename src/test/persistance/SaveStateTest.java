package persistance;

import model.Photo;
import model.PhotoAlbum;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//CITATION: CPSC210/JsonSerializationDemo github repo
public class SaveStateTest {
    @Test
    void testWriterInvalidFile() {
        try {
            PhotoAlbum album = new PhotoAlbum();
            SaveState state = new SaveState("illegal:*.3r923knqfileName.json");
            state.write(album);
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySave() {
        try {
            PhotoAlbum album = new PhotoAlbum();
            SaveState state = new SaveState("generateEmptySave");
            state.write(album);
            state.close();

            LoadState reader = new LoadState("./data/generateEmptySave/generateEmptySave.json");
            album = reader.getAlbum();
            assertEquals(0, album.getSelectedSize());
            assertEquals(0, album.getAlbumSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSave() {
        try {
            PhotoAlbum album = new PhotoAlbum();
            Photo pivovoice = new Photo("src/assets/input/test.png", "Picovoice");
            Photo logo = new Photo("src/assets/input/logo-preview.png", "Columbia");
            Photo logo2 = new Photo("src/assets/input/logo-preview.png", "Columbian");
            album.addPhoto(pivovoice);
            album.addPhoto(logo);
            album.addPhoto(logo2);
            album.selectPhoto(logo2);
            SaveState state = new SaveState("generateGeneralSave");
            state.write(album);
            state.close();

            LoadState reader = new LoadState("./data/generateGeneralSave/generateGeneralSave.json");
            album = reader.getAlbum();
            assertEquals(1, album.getSelectedSize());
            assertEquals(3, album.getAlbumSize());
            assertEquals("Columbian", album.getSelected().get(0).getName());
            assertEquals("src/assets/input/logo-preview.png", album.getSelected().get(0).getUrl());

            assertEquals("Picovoice", album.getAlbum().get(0).getName());
            assertEquals("src/assets/input/test.png", album.getAlbum().get(0).getUrl());

            assertEquals("Columbia", album.getAlbum().get(1).getName());
            assertEquals("src/assets/input/logo-preview.png", album.getAlbum().get(1).getUrl());

            assertEquals("Columbian", album.getAlbum().get(2).getName());
            assertEquals("src/assets/input/logo-preview.png", album.getAlbum().get(2).getUrl());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testExportImages() {
        try {
            PhotoAlbum album = new PhotoAlbum();
            SaveState state = new SaveState("saveTest");
            Photo pivovoice = new Photo("src/assets/input/test.png", "Picovoice");
            Photo logo = new Photo("src/assets/input/logo-preview.png", "Columbia");
            Photo logo2 = new Photo("src/assets/input/logo-preview.png", "Columbian");
            album.addPhoto(pivovoice);
            album.addPhoto(logo);
            album.addPhoto(logo2);
            album.selectPhoto(logo2);
            assertEquals(3, album.getAlbumSize());
            state.exportImages(album);
            assertEquals(3, album.getAlbumSize());

            for (Photo p : album.getAlbum()) {
                assertEquals("./data/saveTest/" + p.getName() +".png", p.getUrl());
            }
        } catch (IOException e) {
            fail ("Should not throw IOException here");
        }
    }
}
