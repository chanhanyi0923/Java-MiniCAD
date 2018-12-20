import java.awt.Graphics2D;
import java.awt.Color;

abstract class Shape implements java.io.Serializable {
    public enum Type
    {
        ELLIPSE,
        RECTANGLE,
        FILLED_ELLIPSE,
        FILLED_RECTANGLE,
        LINE,
        POLY_LINE,
        POLYGON,
        TEXT
    };
    private static final long serialVersionUID = 1l;

    protected Color color;
    protected float size;

    void setColor(Color color) { this.color = color; }
    void setSize(float size) { this.size = size; }

    abstract public Type getType();
    abstract void move(int dx, int dy);
    abstract void resize(int x, int y);
    abstract void drag(int firstX, int firstY, int previousX, int previousY, int x, int y);
    abstract void render(Graphics2D g);
    abstract Bound getBound();
}
