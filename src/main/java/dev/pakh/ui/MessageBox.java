package dev.pakh.ui;
import javax.swing.JOptionPane;

public class MessageBox {
    public static void warning(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void error(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
