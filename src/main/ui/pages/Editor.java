package ui.pages;

import model.Photo;
import model.PhotoAlbum;
import persistance.SaveState;
import ui.EditorGUI;
import ui.WFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

//A class that represents the primary Editor for images
//CITATION:  https://stackoverflow.com/questions/6578205/swing-jlabel-text-change-on-the-running-application
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
public class Editor extends Component implements ActionListener {
    private JFrame frame;
    private PhotoAlbum album;
    private JPanel dashBoard;
    private Photo viewingPhoto;

    // MODIFIES: this
    // EFFECTS: Constructs the Editor object, initializes fields and GUIs, kickstarts the main Editor's functions
    public Editor(PhotoAlbum album) {
        this.album = album;
        this.viewingPhoto = (album.getAlbum().size() == 0
                ? new Photo("/src/assets/input/test.png", "default")
                : album.getAlbum().get(0));
        this.dashBoard = dashBoard();

        frame = new JFrame("make photo good applicatoin");
        frame.addWindowListener(new WFrame("Close Listener"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(1000, 650));
        frame.getContentPane().setBackground(new Color(30, 30, 40));
        frame.setUndecorated(false);

        frame.add(navbar(), BorderLayout.WEST);
        frame.add(dashBoard, BorderLayout.CENTER);
        frame.add(leftNavbar(), BorderLayout.EAST);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(("./src/assets/GUI/huh.png")));

        frame.pack(); // Adjusts frame size to fit components
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // EFFECTS: Creates, sizes, and displays the central dashboard
    private JPanel dashBoard() {
        JPanel dash = new JPanel();
        dash.setPreferredSize(new Dimension(800, 650));
        dash.setBackground(new Color(30, 30, 40));
        dash.setLayout(new BoxLayout(dash, BoxLayout.Y_AXIS));
        dash.add(menu());
        return dash;
    }

    // EFFECTS: Creates, sizes, and displays the central images
    private JPanel menu() {
        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(900, 550));
        menu.setBackground(new Color(30, 30, 40));
        menu.setLayout(new GridLayout(1, 1, 10, 10));

        addImage(menu, viewingPhoto.getUrl(), 600, 550);
        menu.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        return menu;
    }

    // REQUIRES: A valid src image url
    // MODIFIES: menu
    // EFFECTS: Creates, sizes, and displays the central dashboard
    private void addImage(JPanel menu, String src, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(new File(src));
            double aspectRatio = (double) image.getWidth(null) / image.getHeight(null);
            int newWidth = (int) Math.min(width, height * aspectRatio);
            int newHeight = (int) Math.min(height, width / aspectRatio);

            JLabel picLabel = new JLabel(
                    new ImageIcon(
                            image.getScaledInstance(
                                    newWidth,
                                    newHeight,
                                    Image.SCALE_SMOOTH)));
            menu.add(picLabel);
        } catch (IOException e) {
            System.err.println("Failed to grab images");
        }
    }

    // EFFECTS: Creates, sizes, and displays the right navbar with image tools
    private JPanel navbar() {
        JPanel nav = new JPanel();
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBackground(new Color(21, 25, 29));
        nav.setPreferredSize(new Dimension(100, 500));

        addButton(nav, "./src/assets/GUI/huh.png",
                "./src/assets/GUI/huh.png",
                "./src/assets/GUI/huh.png", 60, 40, "/logo");
        addButton(nav, "./src/assets/GUI/Frame 59 4.png",
                "./src/assets/GUI/Frame 59 9.png",
                "./src/assets/GUI/Frame 59 14.png", 100, 40, "/save");
        addButton(nav, "./src/assets/GUI/Frame 59 34.png", "./src/assets/GUI/Frame 59 36.png",
                "./src/assets/GUI/Frame 59 35.png", 40, 40, "/upload");
        addButton(nav, "./src/assets/GUI/Frame 59 37.png", "./src/assets/GUI/Frame 59 39.png",
                "./src/assets/GUI/Frame 59 38.png", 40, 40, "/delete");

        nav.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        return nav;
    }

    // EFFECTS: Creates, sizes, and displays the left navbar with tools for file management
    private JPanel leftNavbar() {
        JPanel nav = new JPanel();
        nav.setLayout(new BoxLayout(nav, BoxLayout.Y_AXIS));
        nav.setBackground(new Color(21, 25, 29));
        nav.setPreferredSize(new Dimension(100, 500));
        addButton(nav, "./src/assets/GUI/Frame 59 22.png", "./src/assets/GUI/Frame 59 24.png",
                "./src/assets/GUI/Frame 59 23.png", 40, 40, "/recolor");
        addButton(nav, "./src/assets/GUI/Frame 59 25.png", "./src/assets/GUI/Frame 59 27.png",
                "./src/assets/GUI/Frame 59 26.png", 40, 40, "/blur");
        addButton(nav, "./src/assets/GUI/Frame 59 28.png", "./src/assets/GUI/Frame 59 30.png",
                "./src/assets/GUI/Frame 59 29.png", 40, 40, "/invert");
        addButton(nav, "./src/assets/GUI/Frame 59 31.png", "./src/assets/GUI/Frame 59 33.png",
                "./src/assets/GUI/Frame 59 32.png", 40, 40, "/grayscale");
        addButton(nav, "./src/assets/GUI/Frame 59 34.png", "./src/assets/GUI/Frame 59 36.png",
                "./src/assets/GUI/Frame 59 35.png", 40, 40, "/select");
        addButton(nav, "./src/assets/GUI/Frame 59 37.png", "./src/assets/GUI/Frame 59 39.png",
                "./src/assets/GUI/Frame 59 38.png", 40, 40, "/deselect");
        addButton(nav, "./src/assets/GUI/Frame 59 40.png", "./src/assets/GUI/Frame 59 42.png",
                "./src/assets/GUI/Frame 59 41.png", 40, 40, "/view");
        nav.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        return nav;
    }

    // REQUIRES: d, r, and s are valid src image urls
    // MODIFIES: nac
    // EFFECTS: Creates, sizes, and displays a general button
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

    // EFFECTS: This is the method that is called when the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "/recolor":
                recolor();
                break;
            case "/blur":
                blur();
                break;
            case "/invert":
                invert();
                break;
            case "/grayscale":
                grayscale();
                break;
            default:
                handleSelection(e.getActionCommand());
                handleFileBar(e.getActionCommand());
        }
    }

    // EFFECTS: This is the method that handles file-related actions
    public void handleSelection(String action) {
        switch (action) {
            case "/select":
                select();
                break;
            case "/deselect":
                deselect();
                break;
            case "/view":
                view();
                break;
        }
    }

    // EFFECTS: Creates, sizes, and displays a popup button for photo selection
    private void select() {
        JFrame miniframe = new JFrame("Which photo would you like to select?");
        ArrayList<String> names = new ArrayList<String>();
        for (Photo p : album.getAlbum()) {
            names.add(p.getName());
        }
        JComboBox selectBox = new JComboBox(names.toArray(new String[names.size()]));
        selectBox.show();
        JButton selection = new JButton("Submit");
        selection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                miniframe.setVisible(false);
                handleSelect(selectBox);
            }
        });
        selectBox.setBounds(50, 30, 280,20);
        selection.setBounds(50, 100, 280, 20);
        miniframe.add(selectBox);
        miniframe.add(selection);
        miniframe.setLayout(null);
        miniframe.setSize(400,200);
        miniframe.setVisible(true);
    }

    // EFFECTS: Handles the action of selecting the open button
    private void handleSelect(JComboBox selectBox) {
        for (Photo p : album.getAlbum()) {
            if (p.getName().equals(selectBox.getSelectedItem())) {
                album.selectPhoto(p);
            }
        }
    }

    // EFFECTS: Creates, sizes, and displays a popup button for deselection
    private void deselect() {
        JFrame miniframe = new JFrame("Which photo would you like to deselect?");
        ArrayList<String> names = new ArrayList<String>();
        for (Photo p : album.getAlbum()) {
            names.add(p.getName());
        }
        JComboBox selectBox = new JComboBox(names.toArray(new String[names.size()]));
        selectBox.show();
        JButton selection = new JButton("Submit");
        selection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                miniframe.setVisible(false);
                handleDeselect(selectBox);
            }
        });
        selectBox.setBounds(50, 30, 280,20);
        selection.setBounds(50, 100, 280, 20);
        miniframe.add(selectBox);
        miniframe.add(selection);
        miniframe.setLayout(null);
        miniframe.setSize(400,200);
        miniframe.setVisible(true);
    }

    // EFFECTS: Handles the action of selecting the deselect button
    private void handleDeselect(JComboBox selectBox) {
        for (Photo p : album.getAlbum()) {
            if (p.getName().equals(selectBox.getSelectedItem())) {
                album.deselectPhoto(p);
            }
        }
    }

    // EFFECTS: Creates, sizes, and displays a popup button for viewing photos
    private void view() {
        JFrame miniframe = new JFrame("Which photo would you like to view?");
        ArrayList<String> names = new ArrayList<String>();
        for (Photo p : album.getSelected()) {
            names.add(p.getName());
        }
        JComboBox selectBox = new JComboBox(names.toArray(new String[names.size()]));
        selectBox.show();
        JButton selection = new JButton("Submit");
        selection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                miniframe.setVisible(false);
                handleView(selectBox);
            }
        });
        selectBox.setBounds(50, 30, 280,20);
        selection.setBounds(50, 100, 280, 20);
        miniframe.add(selectBox);
        miniframe.add(selection);
        miniframe.setLayout(null);
        miniframe.setSize(400,200);
        miniframe.setVisible(true);
    }

    // EFFECTS: Handles the action of selecting the view button
    private void handleView(JComboBox selectBox) {
        for (Photo p : album.getAlbum()) {
            if (p.getName().equals(selectBox.getSelectedItem())) {
                viewingPhoto = p;
                break;
            }
        }
        updateImages();
    }

    // EFFECTS: Handles selection on the file bar
    public void handleFileBar(String action) {
        switch (action) {
            case "/logo":
                new EditorGUI();
                frame.setVisible(false);
                break;
            case "/load":
                save();
                new Quick(); //TODO
                break;
            case "/save":
                save();
                break;
            case "/upload":
                upload();
                break;
            case "/delete":
                delete();
                break;
        }
    }

    // EFFECTS: Creates, sizes, and displays a popup dropdown for saving
    private void save() {
        boolean on = true;
        while (on) {
            String answer = JOptionPane.showInputDialog(null,
                    "What would you like to name your save?", null);
            try {
                SaveState ss = new SaveState(answer);
                for (Photo photo : album.getAlbum()) {
                    photo.exportImage("./data/" + ss.getSaveName() + "/" + photo.getName() + ".png");
                }
                ss.write(album);
                ss.close();
                on = false;
            } catch (IOException e) {
                //  invalid filename: try again");
            } catch (Exception exit) {
                // exited
                on = false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates, sizes, and displays a popup dropdown for uploading files
    private void upload() {
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

            File dest = new File("./src/assets/output/" + fileName);

            try {
                Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            album.addPhoto(new Photo("./src/assets/output/" + fileName, name));
        }
    }

    // MODIFIES: this
    // EFFECTS: Creates, sizes, and displays a popup dropdown for deleting files
    private void delete() {
        JFrame miniframe = new JFrame("Which photo would you like to remove?");
        ArrayList<String> names = new ArrayList<String>();
        for (Photo p : album.getAlbum()) {
            names.add(p.getName());
        }
        JComboBox selectBox = new JComboBox(names.toArray(new String[names.size()]));
        selectBox.show();
        JButton selection = new JButton("Submit");
        selection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                miniframe.setVisible(false);
                albumDelete((String) selectBox.getSelectedItem());
            }
        });

        selectBox.setBounds(50, 30, 280,20);
        selection.setBounds(50, 100, 280, 20);
        miniframe.add(selectBox);
        miniframe.add(selection);
        miniframe.setLayout(null);
        miniframe.setSize(400,200);
        miniframe.setVisible(true);
    }

    // EFFECTS: A helper for deleting a file absed on its name
    private void albumDelete(String name) {
        for (Photo p : album.getAlbum()) {
            if (p.getName().equals(name)) {
                album.removePhoto(p);
                break;
            }
        }
    }

    // REQUIRES: normal, hover, and select are all valid file locations
    // EFFECTS: Creates, sizes, and displays a button based on a series of images
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

    // REQUIRES: src is a valid file locations
    // EFFECTS: Creates, sizes, and displays a scaled image icon
    private Icon iconMaker(String src, int buttonWidth, int buttonHeight) {
        ImageIcon x = new ImageIcon(src);
        Image img = x.getImage(); // Extract the Image object from ImageIcon
        Image scaledImage = img.getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH); // Scale the Image
        return new ImageIcon(scaledImage); // Create a new ImageIcon from the scaled Image
    }

    // REQUIRES: Valid hex codes
    // EFFECTS: Creates, sizes, and displays a opoup that handles the recolor function
    private void recolor() {
        String oldHex = JOptionPane.showInputDialog(null,
                "Enter the old hex code to replace:", null);
        String newHex = JOptionPane.showInputDialog(null,
                "Enter the new hex code to paint over:", null);
        String tolerance = "weuifhio";
        while (!isInteger(tolerance)) {
            tolerance = JOptionPane.showInputDialog(null,
                    "Enter the tolerance (1-100):", null);
        }

        album.recolor(newHex, oldHex, Integer.parseInt(tolerance));
        updateImages();
    }

    // EFFECTS: Creates, sizes, and displays a popup that handles the blur function
    private void blur() {
        String tolerance = "weuifhio";
        while (!isInteger(tolerance)) {
            tolerance = JOptionPane.showInputDialog(null,
                    "Enter the blur (1-10):", null);
        }
        album.blur(Integer.parseInt(tolerance));
        updateImages();
    }

    // EFFECTS: Inverts the selected images
    private void invert() {
        album.invert();
        updateImages();
    }

    // EFFECTS: Inverts the selected images
    private void grayscale() {
        album.grayscale();
        updateImages();
    }

    // EFFECTS: A helper that checks if a String can be converted to an integer
    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the selected images
    private void updateImages() {
        frame.getContentPane().remove(dashBoard); // Remove the existing dashBoard panel
        dashBoard = newDashboard(); // Create a new dashBoard panel
        frame.add(dashBoard, BorderLayout.CENTER); // Add the new dashBoard panel to the frame
        frame.revalidate(); // Revalidate the frame to reflect the changes
        frame.repaint(); // Repaint the frame
    }

    // EFFECTS: Creates a new dashboard panel
    private JPanel newDashboard() {
        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(900, 550));
        menu.setBackground(new Color(30, 30, 40));
        menu.setLayout(new GridLayout(1, 1, 10, 10));

        addPhoto(menu, viewingPhoto, 600, 550);
        menu.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        return menu;
    }

    // REQUIRES: src is a valid file path
    // MODIFIES: menu
    // EFFECTS: Adds a scaled image to the menu
    private void addPhoto(JPanel menu, Photo src, int width, int height) {
        BufferedImage image = src.getImageRef();
        double aspectRatio = (double) image.getWidth(null) / image.getHeight(null);
        int newWidth = (int) Math.min(width, height * aspectRatio);
        int newHeight = (int) Math.min(height, width / aspectRatio);

        JLabel picLabel = new JLabel(
                new ImageIcon(
                        image.getScaledInstance(
                                newWidth,
                                newHeight,
                                Image.SCALE_SMOOTH)));
        menu.add(picLabel);
    }
}