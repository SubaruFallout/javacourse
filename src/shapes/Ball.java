package shapes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Ball extends Shape {
    double radius;

    public Ball(double radius) {
        this.radius = radius;
    }

    @Override
    public double getVolume() {
        return 4. / 3. * Math.PI * radius * radius * radius;
    }

    @Override
    public String toString() {
        return "Ball{" +
                "radius=" + radius +
                "}";
    }

    @Override
    public Node createElement(Document doc) {
        Element el = doc.createElement("ball");
        el.setAttribute("radius", String.valueOf(radius));

        return el;
    }
}
