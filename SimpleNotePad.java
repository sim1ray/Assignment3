import java.awt.Dimension;
import javax.swing.*;

/**
 * SimpleNotePad is a GUI application that allows a user to create a new file, save a file, print a file, open recent files,
 * and edit text(copy, paste, replace).
 *
 * @author Simone Ray
 * @since 2019-06-09
 */

public class SimpleNotePad extends JFrame {
    private JMenuBar menu;
    private JMenu fileMenu, editMenu, recent;
    private JTextPane display;
    private JMenuItem newFile, saveFile, printFile, openFile, replace, copy, paste;

    public SimpleNotePad() {

        menu = new JMenuBar();
        display = new JTextPane();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        newFile = new JMenuItem("New File");
        saveFile = new JMenuItem("Save File");
        printFile = new JMenuItem("Print File");
        openFile = new JMenuItem("Open File");
        recent = new JMenu("Recent");
        replace = new JMenuItem("Replace");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");

        setTitle("A Simple Notepad Tool");
        addToMenu(fileMenu, newFile, true);
        addToMenu(fileMenu, saveFile, true);
        addToMenu(fileMenu, printFile, true);
        addToMenu(fileMenu, openFile, true);
        addToMenu(fileMenu, recent, false);
        addToMenu(editMenu, replace, true);
        addToMenu(editMenu, copy, true);
        addToMenu(editMenu, paste, false);
        menu.add(fileMenu);
        menu.add(editMenu);

        FileManager fm = new FileManager(display, recent);
        EditProperties ep = new EditProperties(display);

        newFile.addActionListener(fm :: newFile);
        saveFile.addActionListener(fm :: saveFile);
        printFile.addActionListener(fm :: printFile);
        openFile.addActionListener(fm :: openFile);
        replace.addActionListener(ep :: replaceText);
        copy.addActionListener(ep :: copyText);
        paste.addActionListener(ep :: pasteText);

        setJMenuBar(menu);
        add(new JScrollPane(display));
        setPreferredSize(new Dimension(600,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        SimpleNotePad app = new SimpleNotePad();
    }

    /**
     * Adds a given element to a given menu, with or without a separator
     * @param menu JMenu that option is to be added to
     * @param option JMenuItem to be added
     * @param withSeparator indicates whether a separator should be added
     */
    private static void addToMenu(JMenu menu, JMenuItem option, boolean withSeparator) {
        menu.add(option);
        if (withSeparator) {
            menu.addSeparator();
        }
    }

}
