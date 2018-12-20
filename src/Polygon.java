import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

class Polygon extends PolyLine
{
    Polygon(Color color, float size, int x, int y)
    {
        super(color, size, x, y);
    }

    @Override
    public Type getType()
    {
        return Type.POLYGON;
    }

    @Override
    void render(Graphics2D g)
    {
        g.setColor(this.color);
        g.setStroke(new BasicStroke(this.size));
        java.awt.Polygon s = new java.awt.Polygon();
        for (int i = 0; i < this.points; i ++) {
            s.addPoint((int)(float)this.xPoints.get(i), (int)(float)this.yPoints.get(i));
        }
        g.fillPolygon(s);
    }
}
