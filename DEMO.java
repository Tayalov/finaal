public class MainDemo {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}

