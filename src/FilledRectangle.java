import java.awt.Color;
import java.awt.Graphics2D;

class FilledRectangle extends Rectangle {
  private static final long serialVersionUID = 1l;

  FilledRectangle(Color color, float size, int x0, int y0, int x1, int y1) {
    super(color, size, x0, y0, x1, y1);
  }

  @Override
  public Type getType() {
    return Type.FILLED_RECTANGLE;
  }

  @Override
  void drawToGraphics2D(Graphics2D g, int left, int top, int width, int height) {
    g.fill(new java.awt.Rectangle(left, top, width, height));
  }
}
