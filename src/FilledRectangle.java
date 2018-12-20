import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

class FilledRectangle extends Rectangle
{
    FilledRectangle(Color color, float size, int x0, int y0, int x1, int y1)
    {
        super(color, size, x0, y0, x1, y1);
    }

    @Override
    public Type getType()
    {
        return Type.FILLED_RECTANGLE;
    }

    @Override
    void drawToGraphics2D(Graphics2D g, int left, int top, int width, int height)
    {
        g.fill(new java.awt.Rectangle(left, top, width, height));
    }
}
