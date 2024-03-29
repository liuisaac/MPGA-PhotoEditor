package ui.pages;

import model.Photo;
import model.PhotoAlbum;
import ui.EditorGUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

//A class that represents a transition state between Editor and the Home GUI that loads an initial image
//CITATION:  https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
public class Quick extends Component {
    private JFrame frame;

    // EFFECTS: constructs a new Quick object that automatically manages a file chooser and then generates an Editor
    public Quick() {
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\liuis\\Downloads\\");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "PNG Image Files",
                "png");
        fileChooser.setFileFilter(filter);
        int i = fileChooser.showOpenDialog(this);

        if (i == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String fileName = file.getName();
            String name = fileName.substring(0, fileName.lastIndexOf('.'));
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1);

            System.out.println(name);

            File dest = new File("./src/assets/output/" + fileName);

            try {
                Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            PhotoAlbum startingAlbum = new PhotoAlbum();
            startingAlbum.addPhoto(new Photo("./src/assets/output/" + fileName, name));
            startingAlbum.selectPhoto(startingAlbum.getAlbum().get(0));
            new Editor(startingAlbum);
        }
    }

    // EFFECTS: automatically starts the process from file choosing to editing
    public static void main(String[] args) {
        new Quick();
    }
}
