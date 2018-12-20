import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

class Ellipse extends Shape
{
    private int x0, y0, x1, y1;

    Ellipse(Color color, float size, int x0, int y0, int x1, int y1)
    {
        this.color = color;
        this.size = size;

        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    @Override
    public Type getType()
    {
        return Type.ELLIPSE;
    }

    @Override
    void render(Graphics2D g)
    {
        int left = Math.min(this.x0, this.x1);
        int top = Math.min(this.y0, this.y1);
        int width = Math.max(this.x0, this.x1) - left;
        int height = Math.max(this.y0, this.y1) - top;

        g.setColor(this.color);
        g.setStroke(new BasicStroke(this.size));
        this.drawToGraphics2D(g, 1.0f * left, 1.0f * top, 1.0f * width, 1.0f * height);
    }

    void drawToGraphics2D(Graphics2D g, float left, float top, float width, float height)
    {
        g.draw(new java.awt.geom.Ellipse2D.Float(left, top, width, height));
    }

    @Override
    void move(int dx, int dy)
    {
        this.x0 += dx;
        this.x1 += dx;
        this.y0 += dy;
        this.y1 += dy;
    }

    @Override
    void resize(int dx, int dy)
    {
        this.x1 += dx;
        this.y1 += dy;
    }

    @Override
    void drag(int firstX, int firstY, int previousX, int previousY, int x, int y)
    {
        this.x1 = x;
        this.y1 = y;
    }

    @Override
    Bound getBound()
    {
        int left = Math.min(this.x0, this.x1);
        int top = Math.min(this.y0, this.y1);
        int width = Math.max(this.x0, this.x1) - left;
        int height = Math.max(this.y0, this.y1) - top;

        return new Bound(left, top, width, height, this.size);
    }
}
