package ui;

import model.Photo;
import model.PhotoAlbum;
import persistance.LoadState;
import ui.pages.Editor;
import ui.pages.Quick;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

//A class that represents the primary Home page that allows access to other Editors
//CITATION:  https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
public class EditorGUI extends Component implements ActionListener {
    private JFrame frame;

    // MODIFIES: this
    // EFFECTS: Constructs a new EditorGUI Object that starts the Home Page frame
    public EditorGUI() {
        frame = new JFrame("make photo good applicatoin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(1000, 650));
        frame.getContentPane().setBackground(new Color(30, 30, 40));
        frame.setUndecorated(false);

        frame.add(navbar(), BorderLayout.WEST);
        frame.add(dashBoard(), BorderLayout.EAST);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(("./src/assets/GUI/huh.png")));

        frame.pack(); // Adjusts frame size to fit components
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // EFFECTS: creates a dashboard panel
    private JPanel dashBoard() {
        JPanel dash = new JPanel();
        dash.setPreferredSize(new Dimension(900, 650));
        dash.setBackground(new Color(30, 30, 40));
        dash.setLayout(new BoxLayout(dash, BoxLayout.Y_AXIS));
        dash.add(menu());
        return dash;
    }

    // EFFECTS: creates a menu panel with base images
    private JPanel menu() {
        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(900, 550));
        menu.setBackground(new Color(30, 30, 40));
        menu.setLayout(new GridLayout(1, 3, 10, 10));

        addImage(menu, "./src/assets/GUI/Frame 61.png");
        addImage(menu, "./src/assets/GUI/Frame 62.png");
        addImage(menu, "./src/assets/GUI/Frame 63.png");

        menu.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        return menu;
    }

    // REQUIRES: a valid src filename
    // MODIFIES: menu
    // EFFECTS: adds an image to the menu
    private void addImage(JPanel menu, String src) {
        try {
            BufferedImage frame = ImageIO.read(new File(src));
            JLabel picLabel = new JLabel(
                    new ImageIcon(
                            frame.getScaledInstance(
                                    300,
                                    350,
                                    Image.SCALE_SMOOTH)));
            menu.add(picLabel);
        } catch (IOException e) {
            System.err.println("Failed to grab images");
        }
    }

    // EFFECTS: constructs the left navbar
    private JPanel navbar() {
        JPanel nav = new JPanel();
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBackground(new Color(21, 25, 29));
        nav.setPreferredSize(new Dimension(100, 500));

        addButton(nav, "./src/assets/GUI/huh.png",
                "./src/assets/GUI/huh.png",
                "./src/assets/GUI/huh.png", 50, 40, "/logo");
        addButton(nav, "./src/assets/GUI/Frame 59 2.png",
                "./src/assets/GUI/Frame 59 7.png",
                "./src/assets/GUI/Frame 59 12.png", 120, 40, "/quick");
        addButton(nav, "./src/assets/GUI/Frame 59 3.png",
                "./src/assets/GUI/Frame 59 8.png",
                "./src/assets/GUI/Frame 59 13.png", 40, 40, "/new");
//        addButton(nav,"./src/assets/GUI/Frame 59 4.png",
//                "./src/assets/GUI/Frame 59 9.png",
//                "./src/assets/GUI/Frame 59 14.png", 40, 40, "/save");
        addButton(nav, "./src/assets/GUI/Frame 59 5.png",
                "./src/assets/GUI/Frame 59 10.png",
                "./src/assets/GUI/Frame 59 15.png", 40, 40, "/load");
        nav.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        return nav;
    }

    // REQUIRES: a valid src filename for d, r, and s
    // MODIFIES: nav
    // EFFECTS: adds a custom button to a nav panel
    private void addButton(JPanel nav, String d, String r, String s, int mt, int buttonSize, String key) {
        JButton button = imageToButton(d, r, s, buttonSize, buttonSize);
        button.setActionCommand(key);
        button.addActionListener(this);
        JButton space = new JButton();
        space.setBorder(BorderFactory.createEmptyBorder(mt, 0, 0, 0));
        nav.add(space);
        nav.add(button);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // EFFECTS: Handles requests sent when a button is clicked
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "/quick":
                new Quick();
                frame.setVisible(false);
                break;
            case "/new":
                new Editor(new PhotoAlbum());
                frame.setVisible(false);
                break;
            case "/load":
                load();
                frame.setVisible(false);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads a save file to the editor
    private void load() {
        File folder = new File("./data");
        File[] files = folder.listFiles();

        ArrayList<String> fileNames = getFileDestination(files);

        JFrame miniframe = new JFrame("Which save file would you like to select?");
        JComboBox selectBox = new JComboBox(fileNames.toArray(new String[fileNames.size()]));
        selectBox.show();
        JButton selection = new JButton("Submit");
        selection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                miniframe.setVisible(false);
                handleLoad(selectBox, fileNames);
            }
        });
        selectBox.setBounds(50, 30, 280, 20);
        selection.setBounds(50, 100, 280, 20);
        miniframe.add(selectBox);
        miniframe.add(selection);
        miniframe.setLayout(null);
        miniframe.setSize(400, 200);
        miniframe.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: loads a save file to the editorand closes the current window
    private void handleLoad(JComboBox selectBox, ArrayList<String> fileNames) {
        try {
            LoadState openstate = new LoadState("./data/"
                    + selectBox.getSelectedItem()
                    + "/"
                    + selectBox.getSelectedItem()
                    + ".json");
            PhotoAlbum album = openstate.getAlbum();
            new Editor(album);
            frame.setVisible(false);
        } catch (IOException e) {
            // pass
        }
    }

    // EFFECTS: explores a directory adn extracts all valid save files
    private ArrayList<String> getFileDestination(File[] files) {
        int indexer = 1;
        ArrayList<String> fileNames = new ArrayList<String>();

        for (File file : files) {
            if (file.isDirectory() && !file.getName().equals("test")) {
                File[] subfiles = file.listFiles();
                for (File subfile : subfiles) {
                    String fileName = subfile.getName();
                    String name = fileName.substring(0, fileName.lastIndexOf('.'));
                    String extension = fileName.substring(fileName.lastIndexOf('.') + 1);

                    if (subfile.isFile() && extension.equals("json")) {
                        fileNames.add(name);
                        indexer++;
                    }
                }
            }
        }
        return fileNames;
    }

    // REQUIRES: normal and hover and select are all valid file paths
    // EFFECTS: generates a button from a provided image
    private JButton imageToButton(String normal, String hover, String select, int buttonWidth, int buttonHeight) {
        JButton exit = new JButton();
        exit.setBorderPainted(false);
        exit.setBorder(null);
        exit.setOpaque(false);
        exit.setFocusPainted(false);
        exit.setMargin(new Insets(0, 0, 0, 0));
        exit.setContentAreaFilled(false);
        exit.setIcon(iconMaker(normal, buttonWidth, buttonHeight));
        exit.setRolloverIcon(iconMaker(hover, buttonWidth, buttonHeight));
        exit.setPressedIcon(iconMaker(select, buttonWidth, buttonHeight));
        return exit;
    }

    // REQUIRES: src is a valid file paths
    // EFFECTS: generates a scaled icon from an image
    private Icon iconMaker(String src, int buttonWidth, int buttonHeight) {
        ImageIcon x = new ImageIcon(src);
        Image img = x.getImage(); // Extract the Image object from ImageIcon
        Image scaledImage = img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH); // Scale the Image
        return new ImageIcon(scaledImage); // Create a new ImageIcon from the scaled Image
    }

    // EFFECTS: automatically starts the Home Editor GUI
    public static void main(String[] args) {
        new EditorGUI();
    }
}