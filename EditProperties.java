import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * The EditProperties class implements functionality for editing text in a Java GUI application.
 *
 * @author Simone Ray
 * @since 2019-06-09
 */
public class EditProperties {
    private JTextPane display;

    public EditProperties(JTextPane display) {
        this.display = display;
    }

    // Replace selected text with user input (through dialog box)
    public void replaceText(ActionEvent e) {
        display.replaceSelection(JOptionPane.showInputDialog(display, "Replace or insert with: "));
    }

    // Copy selected text
    public void copyText(ActionEvent e) {
        display.copy();
    }

    // Paste text from clipboard
    public void pasteText(ActionEvent e) {
        display.paste();
    }
}


