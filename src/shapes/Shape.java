package shapes;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public abstract class Shape {
    public abstract double getVolume();
    public abstract Node createElement(Document doc);
}
