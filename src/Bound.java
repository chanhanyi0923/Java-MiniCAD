import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

class Bound
{
    private int left, top, width, height;
    private float size;
    Bound(int left, int top, int width, int height, float size)
    {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    public boolean inside(int x, int y)
    {
        return left < x && x < left + width && top < y && y < top + height;
    }

    public int getLeft() { return this.left; }
    public int getTop() { return this.top; }
    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }

    public void render(Graphics2D g)
    {
        int borderSize = (int)this.size + 1;
        g.setColor(Color.BLACK);
        float[] dash = { 5f, 5f };
        g.setStroke(new BasicStroke( 1f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 3f, dash, 0f ));
        g.draw(new java.awt.Rectangle(left - borderSize, top - borderSize, width + 2 * borderSize, height + 2 * borderSize));
    }
}
