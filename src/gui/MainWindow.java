package gui;

import backpacks.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import java.awt.event.ActionEvent;
import javax.swing.JFileChooser;
import java.io.File;

import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;

public class MainWindow extends JFrame {
    private Backpack backpack = new Backpack(100000);
    private JLabel backpackInfo = new JLabel(backpack.toString());
    private JButton addShapeButton = new JButton("Add shape");
    private JButton deleteShapeButton = new JButton("Delete selected shape");
    private DefaultListModel<String> shapesData = new DefaultListModel<>();
    private JList<String> listOfData = new JList<>(shapesData);

    public MainWindow() {
        this.setTitle("Backpack");
        this.setBounds(100, 100, 760, 400);
        this.setMinimumSize(new Dimension(640, 300));
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        // add Label (backpack info)
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.insets = new Insets(10, 25, 0, 0);
        add(backpackInfo, constraints);

        // add ComboList (main data)
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.insets = new Insets(10, 25, 25, 20);
        container.add(new JScrollPane(listOfData), constraints);

        // add Button (Add shape)
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.SOUTH;
        constraints.weightx = 0;
        constraints.weighty = 1;
        constraints.insets = new Insets(0, 0, 20, 20);
        container.add(addShapeButton, constraints);

        // add Button (Delete shape)
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weightx = 0;
        constraints.weighty = 1;
        constraints.insets = new Insets(0, 0, 20, 20);
        container.add(deleteShapeButton, constraints);

        addShapeButton.setPreferredSize(new Dimension(250, 50));
        addShapeButton.addActionListener(new AddShape());

        deleteShapeButton.setPreferredSize(new Dimension(250, 50));
        deleteShapeButton.addActionListener(new DeleteShape());

        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createMenu());
        menuBar.add(createInfoMenu());

        setJMenuBar(menuBar);

    }

    public static boolean validateXMLSchema(File xmlfile){

        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("schema.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlfile));
        } catch (IOException | SAXException e) {
            JOptionPane.showMessageDialog(null,
                    "Couldn't validate file",
                    "Exception",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private JMenu createMenu() {
        JMenu menu = new JMenu("Menu");
        JMenuItem open = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem exit = new JMenuItem("Close");
        menu.add(open);
        menu.add(save);
        menu.addSeparator();
        menu.add(exit);

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open file");
                int result = fileChooser.showOpenDialog(menu);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    if (validateXMLSchema(selectedFile)) {
                        JOptionPane.showMessageDialog(null,
                                "Validate complete",
                                "File",
                                JOptionPane.INFORMATION_MESSAGE);
                        try {
                            backpack.readFile(selectedFile);
                        } catch (ParserConfigurationException | SAXException | IOException e) {
                            e.printStackTrace();
                        }
                        refreshWindow();
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Couldn't open file",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save file");
                int result = fileChooser.showSaveDialog(menu);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        backpack.saveFile(selectedFile);
                    } catch (ParserConfigurationException | TransformerException e) {
                        e.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null,
                            "Save as file: " + selectedFile.getAbsolutePath(),
                            "File",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Save incomplete",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });

        return menu;
    }

    private JMenu createInfoMenu() {
        JMenu info = new JMenu("Info");
        JMenuItem about = new JMenuItem("About");
        info.add(about);

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(null,
                        "This project was created by Kurash Vladislav",
                        "About",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return info;
    }

    class AddShape implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AddShapeDialogWindow dialogWindow = new AddShapeDialogWindow(backpack, getGui());
            dialogWindow.setVisible(true);
        }
    }

    class DeleteShape implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (listOfData.getSelectedIndex() == -1) {
                return;
            }
            backpack.removeByIndex(listOfData.getSelectedIndex());
            shapesData.remove(listOfData.getSelectedIndex());
            refreshWindow();
        }

    }

    public void refreshWindow() {
        backpackInfo.setText(backpack.toString());
        List<String> shapesInfo = backpack.getShapesInfo();
        shapesData.removeAllElements();
        for (String shapeInfo : shapesInfo) {
            shapesData.addElement(shapeInfo);
        }
    }

    public MainWindow getGui() {
        return this;
    }
}
