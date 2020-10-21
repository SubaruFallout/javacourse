package shapes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Parallelepiped extends Shape {
    double width;
    double height;
    double depth;

    public Parallelepiped(double width, double height, double depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    @Override
    public double getVolume() {
        return width * height * depth;
    }

    @Override
    public String toString() {
        return "Parallelepiped{" +
                "width=" + width +
                ", height=" + height +
                ", depth=" + depth +
                '}';
    }

    @Override
    public Node createElement(Document doc) {

        Element el = doc.createElement("parallelepiped");
        el.setAttribute("width", String.valueOf(width));
        el.setAttribute("height", String.valueOf(height));
        el.setAttribute("depth", String.valueOf(depth));

        return el;
    }
}
