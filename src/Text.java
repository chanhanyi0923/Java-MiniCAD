import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

class Text extends Shape {
  private static final long serialVersionUID = 1l;
  private String text;
  private int x, y, fontSize;
  private int left, top, width, height;

  Text(Color color, float size, String text, int x, int y) {
    this.color = color;
    this.size = size;

    this.text = text;

    this.x = x;
    this.y = y;

    this.fontSize = 12;

    this.left = 0;
    this.top = 0;
    this.width = 0;
    this.height = 0;
  }

  @Override
  public Type getType() {
    return Type.TEXT;
  }

  void setText(String text) {
    this.text = text;
  }

  @Override
  void render(Graphics2D g) {
    // draw string
    Font font = new Font("Arial", Font.PLAIN, this.fontSize / 2);
    g.setColor(this.color);
    g.setStroke(new BasicStroke(1));
    g.setFont(font);
    g.drawString(this.text, this.x, this.y);

    // get bound
    FontRenderContext fontRenderContext = new FontRenderContext(g.getTransform(), true, true);
    Rectangle2D bound = font.getStringBounds(this.text, fontRenderContext);
    this.left = this.x + (int) bound.getX();
    this.top = this.y + (int) bound.getY();
    this.width = (int) bound.getWidth();
    this.height = (int) bound.getHeight();
  }

  @Override
  void move(int dx, int dy) {
    this.x += dx;
    this.y += dy;
  }

  @Override
  void drag(int firstX, int firstY, int previousX, int previousY, int x, int y) {
    int dy = y - previousY;
    int nextSize = this.fontSize - (int) (2.5 * dy);
    if (nextSize > 0) {
      this.fontSize = nextSize;
    }
  }

  @Override
  void resize(int dx, int dy) {
    int nextSize = this.fontSize - (int) (2.5 * dy);
    if (nextSize > 0) {
      this.fontSize = nextSize;
    }
  }

  @Override
  Bound getBound() {
    return new Bound(this.left, this.top, this.width, this.height, this.size);
  }
}
