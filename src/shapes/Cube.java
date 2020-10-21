package shapes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Cube extends Shape {
    double side;

    public Cube(double side) {
        this.side = side;
    }

    @Override
    public double getVolume() {
        return side * side * side;
    }

    @Override
    public String toString() {
        return "Cube{" +
                "side=" + side +
                "}";
    }

    @Override
    public Node createElement(Document doc) {

        Element el = doc.createElement("cube");
        el.setAttribute("side", String.valueOf(side));

        return el;
    }
}
