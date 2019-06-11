import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.text.StyledDocument;

public class SimpleNotePad extends JFrame {
    private JMenuBar menu;
    private JMenu fileMenu, editMenu, recent;
    private JTextPane display;
    private JMenuItem newFile, saveFile, printFile, openFile, replace, copy, paste;

    private HashMap<String, String> recentFilePath = new HashMap<>();
    private LinkedHashSet<String> recentFiles = new LinkedHashSet<>();

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

        newFile.addActionListener(this :: newFile);
        saveFile.addActionListener(this :: saveFile);
        printFile.addActionListener(this :: printFile);
        openFile.addActionListener(this :: openFile);
        replace.addActionListener(this :: replaceText);
        copy.addActionListener(this :: copyText);
        paste.addActionListener(this :: pasteText);

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

    // Adds a given element to a given menu, with or without a separator
    private static void addToMenu(JMenu menu, JMenuItem option, boolean withSeparator) {
        menu.add(option);
        if (withSeparator) {
            menu.addSeparator();
        }
    }

    // Create a new file
    private void newFile(ActionEvent e) {
        display.setText("");
    }

    // Saves the text file
    private void saveFile(ActionEvent e) {
        File fileToWrite = null;
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION)
            fileToWrite = fc.getSelectedFile();
        try {
            storeRecent(fileToWrite.getName(), fileToWrite.getAbsolutePath());
            PrintWriter out = new PrintWriter(new FileWriter(fileToWrite));
            out.println(display.getText());
            JOptionPane.showMessageDialog(null, "File is saved successfully...");
            out.close();
        } catch (IOException ex) {
        }
    }

    // Prints the text file
    private void printFile(ActionEvent e) {
        try {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setJobName("Sample Command Pattern");
            printerJob.setCopies(1);
            printerJob.setPrintable(new Printable() {
                public int print(Graphics pg, PageFormat pf, int pageNum) {
                    if (pageNum > 0)
                        return Printable.NO_SUCH_PAGE;
                    pg.drawString(display.getText(), 500, 500);
                    paint(pg);
                    return Printable.PAGE_EXISTS;
                }
            });

            if (!printerJob.printDialog())
                return;
            printerJob.print();
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(null,
                    "Printer error" + pe, "Printing error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Open a selected file using dialog box
    private void openFile(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fc.getSelectedFile();
            storeRecent(fileToOpen.getName(), fileToOpen.getAbsolutePath());
            readFile(fileToOpen);
        }
    }

    // Opens recently opened files
    private void openRecentFile(ActionEvent e) {
        String filename = e.getActionCommand();   // Get name of file that is selected
        String pathname = recentFilePath.get(filename);
        storeRecent(filename, pathname);
        readFile(new File(pathname));
    }

    // Store recently opened files in a LinkedHashSet and update menu with 5 most recently opened files
    private void storeRecent(String filename, String path) {
        // Add file and pathname to HashMap
        recentFilePath.put(filename, path);
        // Add file to ordered LinkedHashSet
        if (recentFiles.contains(filename)) {
            recentFiles.remove(filename);   //Repeated file should be put at the top of the list
        }
        recentFiles.add(filename);

        // Refresh Recent menu items
        recent.removeAll();

        // Iterate over last five elements
        Iterator<String> el = recentFiles.iterator();
        if (recentFiles.size() > 5) {
            for (int i = 0; i < recentFiles.size()-5; i++) {
                el.next();  //skip beginning elements
            }
        }
        // Add last 5 elements to menu
        while (el.hasNext()) {
            String name = el.next();
            JMenuItem item = new JMenuItem(name);
            recent.add(item, 0);
            item.addActionListener(this :: openRecentFile);
        }
    }

    // Reads file contents and prints to the display
    private void readFile(File fileToOpen) {
        display.setText("");
        Scanner in = null;
        try {
            in = new Scanner(fileToOpen);
            while (in.hasNext()) {
                String line = in.nextLine();
                StyledDocument doc = display.getStyledDocument();
                doc.insertString(doc.getLength(), line + "\n", null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            in.close();
        }
    }

    // Replace selected text with user input (through dialog box)
    private void replaceText(ActionEvent e) {
        display.replaceSelection(JOptionPane.showInputDialog(display, "Replace or insert with: "));
    }

    // Copy selected text
    private void copyText(ActionEvent e) {
        display.copy();
    }

    // Paste text from clipboard
    private void pasteText(ActionEvent e) {
        display.paste();
    }
}


