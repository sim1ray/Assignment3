import javax.swing.*;
import javax.swing.text.StyledDocument;
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

/**
 * The FileManager class implements functionality for reading, opening, saving, and printing files in a Java GUI application.
 * It also provides functionality to store/display recently opened or saved files.
 *
 * @author Simone Ray
 * @since 2019-06-09
 */

public class FileManager extends JFrame {
    private HashMap<String, String> recentFilePath = new HashMap<>();   // Stores the names and file paths of recently opened files
    private LinkedHashSet<String> recentFiles = new LinkedHashSet<>();  // Stores the names of recently opened files in chronological order
    private JTextPane display;
    private JMenu recent;

    public FileManager(JTextPane display, JMenu recent) {
        this.display = display;
        this.recent = recent;
    }

    // Create a new file
    public void newFile(ActionEvent e) {
        display.setText("");
    }

    // Saves the text file
    public void saveFile(ActionEvent e) {
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
    public void printFile(ActionEvent e) {
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

    // Opens a selected file using dialog box
    public void openFile(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fc.getSelectedFile();
            storeRecent(fileToOpen.getName(), fileToOpen.getAbsolutePath());
            readFile(fileToOpen);
        }
    }

    // Opens recently opened files
    public void openRecentFile(ActionEvent e) {
        String filename = e.getActionCommand();   // Get name of file that is selected
        String pathname = recentFilePath.get(filename);
        storeRecent(filename, pathname);
        readFile(new File(pathname));
    }

    // Stores recently opened files and updates menu with 5 most recently opened files
    private void storeRecent(String filename, String path) {
        // Add file and pathname to HashMap
        recentFilePath.put(filename, path);
        // Add file to the beginning of ordered LinkedHashSet
        if (recentFiles.contains(filename)) {
            recentFiles.remove(filename);
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

}

