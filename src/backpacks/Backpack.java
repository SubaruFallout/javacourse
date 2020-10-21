package backpacks;

import exceptons.MyException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import shapes.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Backpack extends Shape {
    List<Shape> shapes = new ArrayList<>();
    int capacity;

    public Backpack(int capacity) {
        this.capacity = capacity;
    }

    public void tryAddShape(Shape shape) {
        if (getVolume() + shape.getVolume() > capacity) {
            throw new MyException();
        }
        shapes.add(shape);
        this.sort();
    }

    public void removeByIndex(int index) {
        shapes.remove(index);
    }

    public double getVolume() {
        double volume = 0;
        for (Shape shape : shapes) {
            volume += shape.getVolume();
        }
        return volume;
    }

    public Node createElement(Document doc) {
        return null;
    }

    public void sort() {
        shapes.sort(Comparator.comparingDouble(Shape::getVolume).reversed());
    }

    public List<String> getShapesInfo() {
        List<String> shapesInfo = new ArrayList<>();
        for (Shape shape : shapes) {
            shapesInfo.add(shape.toString());
        }
        return shapesInfo;
    }

    public void readFile(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        NodeList balls = document.getDocumentElement().getElementsByTagName("ball");
        for (int i = 0; i < balls.getLength(); i++) {
            Node ball = balls.item(i);
            NamedNodeMap attributes = ball.getAttributes();
            tryAddShape(new Ball(Double.parseDouble(attributes.getNamedItem("radius").getNodeValue())));
        }
        NodeList cubes = document.getDocumentElement().getElementsByTagName("cube");
        for (int i = 0; i < cubes.getLength(); i++) {
            Node cube = cubes.item(i);
            NamedNodeMap attributes = cube.getAttributes();
            tryAddShape(new Cube(Double.parseDouble(attributes.getNamedItem("side").getNodeValue())));
        }
        NodeList parallelepipeds = document.getDocumentElement().getElementsByTagName("parallelepiped");
        for (int i = 0; i < parallelepipeds.getLength(); i++) {
            Node parallelepiped = parallelepipeds.item(i);
            NamedNodeMap attributes = parallelepiped.getAttributes();
            tryAddShape(new Parallelepiped(Double.parseDouble(attributes.getNamedItem("width").getNodeValue()),
                    Double.parseDouble(attributes.getNamedItem("height").getNodeValue()),
                    Double.parseDouble(attributes.getNamedItem("depth").getNodeValue())));
        }
    }

    public void saveFile(File file) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElementNS("testXML", "shapes");
        doc.appendChild(root);

        for (Shape shape : shapes) {
            root.appendChild(shape.createElement(doc));
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transf = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(doc);

        StreamResult console = new StreamResult(System.out);
        StreamResult out = new StreamResult(file);

        transf.transform(source, console);
        transf.transform(source, out);
    }

    @Override
    public String toString() {
        return "Backpack total volume = " + getVolume() + ". Max volume = " + capacity;
    }
}
