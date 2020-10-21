package exceptons;

import javax.swing.*;

public class MyException extends RuntimeException {
    public MyException() {
        super("Overflow of backpack");
        JOptionPane.showMessageDialog(new JFrame(),
                "Overflow of backpack.", "Fatality", JOptionPane.ERROR_MESSAGE);
    }
}
