import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

abstract class Shape implements Cloneable, Serializable {
  public enum Type {
    STAR,
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

  void setColor(Color color) {
    this.color = color;
  }

  void setSize(float size) {
    this.size = size;
  }

  public abstract Type getType();

  abstract void move(int dx, int dy);

  abstract void resize(int x, int y);

  abstract void drag(int firstX, int firstY, int previousX, int previousY, int x, int y);

  abstract void render(Graphics2D g);

  abstract Bound getBound();

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return (Shape) super.clone();
  }
}
