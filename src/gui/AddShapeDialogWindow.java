package gui;

import backpacks.*;
import shapes.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddShapeDialogWindow extends JDialog {
    private Backpack backpack;
    private MainWindow gui;
    private JComboBox<String> shapeType = new JComboBox<>();
    private JLabel nameOfTextField1 = new JLabel();
    private JTextField textField1 = new JTextField();
    private JLabel nameOfTextField2 = new JLabel();
    private JTextField textField2 = new JTextField();
    private JLabel nameOfTextField3 = new JLabel();
    private JTextField textField3 = new JTextField();
    private JButton addShape = new JButton("Add shape");
    private GridBagConstraints constraints = new GridBagConstraints();

    public AddShapeDialogWindow(Backpack backpack3, MainWindow gui) {
        super(new JFrame(), "Add shape");
        this.backpack = backpack3;
        this.gui = gui;
        this.setBounds(300, 200, 100, 200);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(100, 200));

        this.setLayout(new GridBagLayout());

        // add ComboList (shapeType)
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        this.add(shapeType, constraints);

        // add Label (nameOfTextField1)
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = new Insets(0, 10, 5, 10);
        this.add(nameOfTextField1, constraints);

        // add TextField (TextField1)
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = new Insets(0, 10, 0, 10);
        this.add(textField1, constraints);

        // add Button (addShape)
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        this.add(addShape, constraints);

        shapeType.addItem("Ball");
        shapeType.addItem("Cube");
        shapeType.addItem("Parallelepiped");
        shapeType.addActionListener(new SelectItem());
        shapeType.setSelectedItem("Cube");

        addShape.addActionListener(new AddShape());

        textField1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9'))) {
                    e.consume();  // игнорим введенные буквы и пробел
                }
            }
        });
        textField2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9'))) {
                    e.consume();  // игнорим введенные буквы и пробел
                }
            }
        });
        textField3.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (((c < '0') || (c > '9'))) {
                    e.consume();  // игнорим введенные буквы и пробел
                }
            }
        });
    }

    class AddShape implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            if (shapeType.getSelectedItem() == "Ball") {
                try {
                    backpack.tryAddShape(new Ball(Integer.parseInt(textField1.getText())));
                } catch (NumberFormatException nfe) {
                    return;
                }
            }
            if (shapeType.getSelectedItem() == "Cube") {
                try {
                    backpack.tryAddShape(new Cube(Integer.parseInt(textField1.getText())));
                } catch (NumberFormatException nfe) {
                    return;
                }
            }
            if (shapeType.getSelectedItem() == "Parallelepiped") {
                try {
                    backpack.tryAddShape(new Parallelepiped(Integer.parseInt(textField1.getText()),
                            Integer.parseInt(textField2.getText()),
                            Integer.parseInt(textField3.getText())));
                } catch (NumberFormatException nfe) {
                    return;
                }
            }
            close();
        }
    }

    class SelectItem implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (shapeType.getSelectedItem() == "Ball") {
                setSize(new Dimension(100, 200));
                nameOfTextField1.setText("Radius");
                remove(nameOfTextField2);
                remove(textField2);
                remove(nameOfTextField3);
                remove(textField3);
            }
            if (shapeType.getSelectedItem() == "Cube") {
                setSize(new Dimension(100, 200));
                nameOfTextField1.setText("Side");
                remove(nameOfTextField2);
                remove(textField2);
                remove(nameOfTextField3);
                remove(textField3);
            }
            if (shapeType.getSelectedItem() == "Parallelepiped") {
                setSize(new Dimension(200, 320));
                nameOfTextField1.setText("Width");
                nameOfTextField2.setText("Height");
                nameOfTextField3.setText("Depth");
                remove(addShape);

                // add Label (nameOfTextField2)
                constraints.gridx = 0;
                constraints.gridy = 3;
                constraints.gridwidth = 1;
                constraints.gridheight = 1;
                constraints.fill = GridBagConstraints.NONE;
                constraints.anchor = GridBagConstraints.WEST;
                constraints.weightx = 1;
                constraints.weighty = 1;
                constraints.insets = new Insets(0, 10, 5, 10);
                add(nameOfTextField2, constraints);

                // add TextField (textField2)
                constraints.gridx = 0;
                constraints.gridy = 4;
                constraints.gridwidth = 1;
                constraints.gridheight = 1;
                constraints.fill = GridBagConstraints.BOTH;
                constraints.anchor = GridBagConstraints.CENTER;
                constraints.weightx = 1;
                constraints.weighty = 1;
                constraints.insets = new Insets(0, 10, 0, 10);
                add(textField2, constraints);

                // add Label (nameOfTextField3)
                constraints.gridx = 0;
                constraints.gridy = 5;
                constraints.gridwidth = 1;
                constraints.gridheight = 1;
                constraints.fill = GridBagConstraints.NONE;
                constraints.anchor = GridBagConstraints.WEST;
                constraints.weightx = 1;
                constraints.weighty = 1;
                constraints.insets = new Insets(0, 10, 5, 10);
                add(nameOfTextField3, constraints);

                // add TextField (textField3)
                constraints.gridx = 0;
                constraints.gridy = 6;
                constraints.gridwidth = 1;
                constraints.gridheight = 1;
                constraints.fill = GridBagConstraints.BOTH;
                constraints.anchor = GridBagConstraints.CENTER;
                constraints.weightx = 1;
                constraints.weighty = 1;
                constraints.insets = new Insets(0, 10, 0, 10);
                add(textField3, constraints);

                // add Button (addShape)
                constraints.gridx = 0;
                constraints.gridy = 7;
                constraints.gridwidth = 1;
                constraints.gridheight = 1;
                constraints.fill = GridBagConstraints.BOTH;
                constraints.anchor = GridBagConstraints.CENTER;
                constraints.weightx = 1;
                constraints.weighty = 1;
                constraints.insets = new Insets(10, 10, 10, 10);
                add(addShape, constraints);
            }
        }
    }

    private void close() {
        gui.refreshWindow();
        dispose();
    }

}
