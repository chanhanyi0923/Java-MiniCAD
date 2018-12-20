import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

class PolyLine extends Shape {
  private static final long serialVersionUID = 1l;
  protected ArrayList<Float> xPoints, yPoints;
  protected int points;

  PolyLine(Color color, float size, int x, int y) {
    this.color = color;
    this.size = size;

    this.xPoints = new ArrayList<>();
    this.yPoints = new ArrayList<>();
    this.points = 0;

    this.addPoint(x, y);
    this.addPoint(x, y);
  }

  @Override
  public Type getType() {
    return Type.POLY_LINE;
  }

  @Override
  void render(Graphics2D g) {
    g.setColor(this.color);
    g.setStroke(new BasicStroke(this.size));

    Path2D p = new Path2D.Float();
    p.moveTo(this.xPoints.get(0), this.yPoints.get(0));
    for (int i = 1; i < this.points; i++) {
      p.lineTo(this.xPoints.get(i), this.yPoints.get(i));
    }
    g.draw(p);
  }

  @Override
  void move(int dx, int dy) {
    for (int i = 0; i < this.points; i++) {
      this.xPoints.set(i, this.xPoints.get(i) + dx);
      this.yPoints.set(i, this.yPoints.get(i) + dy);
    }
  }

  @Override
  void resize(int dx, int dy) {
    Bound bound = this.getBound();
    int left = bound.getLeft();
    int top = bound.getTop();
    float xScale = (float) (bound.getWidth() + dx) / (float) bound.getWidth();
    float yScale = (float) (bound.getHeight() + dy) / (float) bound.getHeight();
    for (int i = 0; i < this.points; i++) {
      float x = left + xScale * (this.xPoints.get(i) - left);
      float y = top + yScale * (this.yPoints.get(i) - top);
      this.xPoints.set(i, x);
      this.yPoints.set(i, y);
    }
  }

  void addPoint(int x, int y) {
    this.xPoints.add((float) x);
    this.yPoints.add((float) y);
    this.points++;
  }

  @Override
  void drag(int firstX, int firstY, int previousX, int previousY, int x, int y) {
    this.xPoints.set(this.points - 1, (float) x);
    this.yPoints.set(this.points - 1, (float) y);
  }

  @Override
  Bound getBound() {
    float xMin = this.xPoints.get(0);
    float xMax = this.xPoints.get(0);
    float yMin = this.yPoints.get(0);
    float yMax = this.yPoints.get(0);

    for (int i = 1; i < this.points; i++) {
      xMin = Math.min(xMin, this.xPoints.get(i));
      xMax = Math.max(xMax, this.xPoints.get(i));
      yMin = Math.min(yMin, this.yPoints.get(i));
      yMax = Math.max(yMax, this.yPoints.get(i));
    }

    return new Bound((int) xMin, (int) yMin, (int) (xMax - xMin), (int) (yMax - yMin), this.size);
  }
}
