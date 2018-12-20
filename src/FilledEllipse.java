import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

class FilledEllipse extends Ellipse
{
    FilledEllipse(Color color, float size, int x0, int y0, int x1, int y1)
    {
        super(color, size, x0, y0, x1, y1);
    }

    @Override
    public Type getType()
    {
        return Type.FILLED_ELLIPSE;
    }

    @Override
    void drawToGraphics2D(Graphics2D g, float left, float top, float width, float height)
    {
        g.fill(new java.awt.geom.Ellipse2D.Float(left, top, width, height));
    }
}
