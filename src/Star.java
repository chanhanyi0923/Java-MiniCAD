import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

class Star extends Shape {
  private static final long serialVersionUID = 1l;
  private int points, x0, y0, x1, y1, width;

  Star(Color color, float size, int points, int x0, int y0, int x1, int y1) {
    this.color = color;
    this.size = size;

    this.points = points;
    this.x0 = x0;
    this.y0 = y0;
    this.x1 = x1;
    this.y1 = y1;
    this.updateWidth();
  }

  @Override
  public Type getType() {
    return Type.STAR;
  }

  @Override
  void render(Graphics2D g) {
    g.setColor(this.color);
    g.setStroke(new BasicStroke(this.size));

    Path2D p = new Path2D.Float();
    if (this.points % 2 == 1) {
      p.moveTo(this.x0 + this.width, this.y0);
      for (int i = 0; i < this.points; i++) {
        double theta = 2 * Math.PI * ((i * 2 + 2) % this.points) / this.points;
        p.lineTo(
            this.x0 + (int) (this.width * Math.cos(theta)),
            this.y0 + (int) (this.width * Math.sin(theta)));
      }
    } else {
      p.moveTo(this.x0 + this.width, this.y0);
      for (int i = 0; i * 2 < this.points; i++) {
        double theta = 2 * Math.PI * ((i * 2 + 2) % this.points) / this.points;
        p.lineTo(
            this.x0 + (int) (this.width * Math.cos(theta)),
            this.y0 + (int) (this.width * Math.sin(theta)));
      }

      double thetaOne = 2 * Math.PI / this.points;
      p.moveTo(
          this.x0 + (int) (this.width * Math.cos(thetaOne)),
          this.y0 + (int) (this.width * Math.sin(thetaOne)));
      for (int i = 0; i * 2 < this.points; i++) {
        double theta = 2 * Math.PI * ((i * 2 + 3) % this.points) / this.points;
        p.lineTo(
            this.x0 + (int) (this.width * Math.cos(theta)),
            this.y0 + (int) (this.width * Math.sin(theta)));
      }
    }
    g.draw(p);
  }

  void updateWidth() {
    this.width = (int) Math.sqrt(((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0)) / 2);
    if (this.x0 < this.x1) {
      this.x1 = this.x0 + width;
    } else {
      this.x1 = this.x0 - width;
    }

    if (this.y0 < this.y1) {
      this.y1 = this.y0 + width;
    } else {
      this.y1 = this.y0 - width;
    }
  }

  @Override
  void move(int dx, int dy) {
    this.x0 += dx;
    this.x1 += dx;
    this.y0 += dy;
    this.y1 += dy;
    this.updateWidth();
  }

  @Override
  void drag(int firstX, int firstY, int previousX, int previousY, int x, int y) {
    this.x1 = x;
    this.y1 = y;
    this.updateWidth();
  }

  @Override
  void resize(int dx, int dy) {
    this.x1 += dx;
    this.y1 += dy;
    this.updateWidth();
  }

  @Override
  Bound getBound() {
    int left = Math.min(this.x0, this.x1);
    int top = Math.min(this.y0, this.y1);
    int width = Math.max(this.x0, this.x1) - left;
    int height = Math.max(this.y0, this.y1) - top;

    left -= width;
    top -= height;
    width *= 2;
    height *= 2;

    return new Bound(left, top, width, height, this.size);
  }
}
