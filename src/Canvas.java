import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class Canvas extends JPanel implements MouseListener, MouseMotionListener {
  private static final long serialVersionUID = 1l;

  private int firstX, firstY;
  private int previousX, previousY;

  // global state machine
  private State state;

  private ArrayList<Shape> shapes;
  private Shape shapeToBeDrawn;
  private Shape shapeSelected;

  public Canvas(State state) {
    this.state = state;

    this.addMouseListener(this);
    this.addMouseMotionListener(this);

    this.shapes = new ArrayList<>();
    this.shapeToBeDrawn = null;
    this.shapeSelected = null;
  }

  @Override
  public void paintComponent(Graphics graphics) {
    // this.openFromFile();
    this.render();
  }

  @Override
  public void mousePressed(MouseEvent event) {
    int x = event.getX();
    int y = event.getY();

    this.firstX = x;
    this.firstY = y;

    this.previousX = x;
    this.previousY = y;

    switch (this.state.getTask()) {
      case SELECT:
        this.selectShape(x, y);
        break;
      case DRAW:
        switch (this.state.getShapeType()) {
          case POLY_LINE:
          case POLYGON:
            if (this.shapeToBeDrawn == null) {
              this.shapeToBeDrawn = this.createShape(this.state, x, y);
            } else {
              if (event.getClickCount() > 1) {
                this.shapes.add(this.shapeToBeDrawn);
                this.shapeToBeDrawn = null;
              } else {
                ((PolyLine) this.shapeToBeDrawn).addPoint(x, y);
              }
            }
            break;

          default:
            this.shapeToBeDrawn = this.createShape(this.state, x, y);
        }
        break;
    }
  }

  @Override
  public void mouseDragged(MouseEvent event) {
    int x = event.getX();
    int y = event.getY();
    switch (this.state.getTask()) {
      case RESIZE:
        if (this.shapeSelected != null) {
          this.shapeSelected.resize(x - this.previousX, y - this.previousY);
        }
        break;
      case SELECT:
        if (this.shapeSelected != null) {
          this.shapeSelected.move(x - this.previousX, y - this.previousY);
        }
        break;
      case DRAW:
        if (this.shapeToBeDrawn != null) {
          switch (this.state.getShapeType()) {
            case POLY_LINE:
            case POLYGON:
              break;
            default:
              this.shapeToBeDrawn.drag(
                  this.firstX, this.firstY, this.previousX, this.previousY, x, y);
              break;
          }
        }
        break;
    }

    this.render();

    this.previousX = x;
    this.previousY = y;
  }

  @Override
  public void mouseReleased(MouseEvent event) {
    int x = event.getX();
    int y = event.getY();

    switch (this.state.getTask()) {
      case SELECT:
        break;
      case DRAW:
        switch (this.state.getShapeType()) {
          case POLY_LINE:
          case POLYGON:
            break;
          default:
            this.shapes.add(this.shapeToBeDrawn);
            this.shapeToBeDrawn = null;
            break;
        }
        break;
    }

    this.render();
  }

  @Override
  public void mouseMoved(MouseEvent event) {
    int x = event.getX();
    int y = event.getY();

    switch (this.state.getTask()) {
      case DRAW:
        if (this.shapeToBeDrawn != null) {
          switch (this.state.getShapeType()) {
            case POLY_LINE:
            case POLYGON:
              this.shapeToBeDrawn.drag(
                  this.firstX, this.firstY, this.previousX, this.previousY, x, y);
              this.render();
              break;
          }
        }
        break;
    }

    // this.render();
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  @Override
  public void mouseClicked(MouseEvent e) {}

  private Shape createShape(State state, int x, int y) {
    Shape shape = null;
    switch (state.getShapeType()) {
      case STAR:
        shape = new Star(state.getColor(), state.getSize(), state.getPoints(), x, y, x, y);
        break;
      case RECTANGLE:
        shape = new Rectangle(state.getColor(), state.getSize(), x, y, x, y);
        break;
      case FILLED_RECTANGLE:
        shape = new FilledRectangle(state.getColor(), state.getSize(), x, y, x, y);
        break;
      case ELLIPSE:
        shape = new Ellipse(state.getColor(), state.getSize(), x, y, x, y);
        break;
      case FILLED_ELLIPSE:
        shape = new FilledEllipse(state.getColor(), state.getSize(), x, y, x, y);
        break;
      case LINE:
        shape = new Line(state.getColor(), state.getSize(), x, y, x, y);
        break;
      case POLY_LINE:
        shape = new PolyLine(state.getColor(), state.getSize(), x, y);
        break;
      case POLYGON:
        shape = new Polygon(state.getColor(), state.getSize(), x, y);
        break;
      case TEXT:
        shape = new Text(state.getColor(), state.getSize(), state.getText(), x, y);
        break;
    }
    return shape;
  }

  private void selectShape(int x, int y) {
    if (this.shapeSelected != null) {
      this.shapes.add(this.shapeSelected);
      this.shapeSelected = null;
    }

    int index = -1;
    for (int i = this.shapes.size() - 1; i >= 0; i--) {
      if (this.shapes.get(i).getBound().inside(x, y)) {
        index = i;
        break;
      }
    }

    if (index != -1) {
      this.shapeSelected = this.shapes.get(index);
      this.shapes.remove(index);
    }
  }

  private void render() {
    Graphics2D g = (Graphics2D) this.getGraphics();
    g.setColor(Color.WHITE);
    g.setStroke(new BasicStroke(0.0f));
    g.fill(new java.awt.Rectangle(0, 0, Setting.WINDOW_WIDTH, Setting.WINDOW_HEIGHT));
    for (Shape shape : this.shapes) {
      shape.render(g);
    }
    if (this.shapeToBeDrawn != null) {
      this.shapeToBeDrawn.render(g);
    }
    if (this.shapeSelected != null) {
      this.shapeSelected.render(g);
      this.shapeSelected.getBound().render(g);
    }
  }

  public void saveToFile(File file) {
    try {
      FileOutputStream fileOut = new FileOutputStream(file);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(this.shapes);
      out.close();
      fileOut.close();
    } catch (IOException i) {
      i.printStackTrace();
    } finally {
      this.render();
    }
  }

  public void openFromFile(File file) {
    try {
      FileInputStream fileIn = new FileInputStream(file);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      this.shapes = (ArrayList<Shape>) in.readObject();
      in.close();
      fileIn.close();
    } catch (IOException i) {
      i.printStackTrace();
    } catch (ClassNotFoundException c) {
      c.printStackTrace();
    } finally {
      this.render();
    }
  }

  public void removeSelectedShape() {
    this.shapeSelected = null;
    this.render();
  }

  public void setSelectedShapeText(String text) {
    if (this.shapeSelected != null && this.shapeSelected instanceof Text) {
      ((Text) this.shapeSelected).setText(text);
    } else {
      PaintFrame frame = (PaintFrame) SwingUtilities.getRoot(this);
      JOptionPane.showMessageDialog(frame, "You didn't select a text object");
    }
    this.render();
  }

  public void setSelectedShapeColor(Color color) {
    if (this.shapeSelected != null) {
      this.shapeSelected.setColor(color);
    } else {
      PaintFrame frame = (PaintFrame) SwingUtilities.getRoot(this);
      JOptionPane.showMessageDialog(frame, "You didn't select an object");
    }
    this.render();
  }

  public void setSelectedShapeSize(float size) {
    if (this.shapeSelected != null) {
      this.shapeSelected.setSize(size);
    } else {
      PaintFrame frame = (PaintFrame) SwingUtilities.getRoot(this);
      JOptionPane.showMessageDialog(frame, "You didn't select an object");
    }
    this.render();
  }

  public void cloneSelectedShape() {
    if (this.shapeSelected != null) {
      Shape newShape = null;
      try {
        newShape = (Shape) (this.shapeSelected.clone());
      } catch (CloneNotSupportedException e) {
        e.printStackTrace();
      } finally {
        newShape.move(50, 50);
        this.shapes.add(newShape);
      }
    } else {
      PaintFrame frame = (PaintFrame) SwingUtilities.getRoot(this);
      JOptionPane.showMessageDialog(frame, "You didn't select an object");
    }
    this.render();
  }
}
