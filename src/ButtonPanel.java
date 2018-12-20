import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class ButtonPanel extends JPanel {
  private static final long serialVersionUID = 1l;

  private abstract class ClickListener implements ActionListener {
    protected State state;

    ClickListener(State state) {
      this.state = state;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);
  }

  public ButtonPanel(State state) {
    ButtonPanel panel = this;
    GridLayout layout = new GridLayout(0, 1);
    this.setLayout(layout);

    this.add(
        new JButton("Star") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    String hint = "Input the number of points of the star";
                    String text =
                        JOptionPane.showInputDialog(panel, hint, hint, JOptionPane.PLAIN_MESSAGE);
                    int points = Integer.parseInt(text);
                    this.state.setPoints(points);
                    this.state.setShapeType(Shape.Type.STAR);
                    this.state.setTask(State.Task.DRAW);
                  }
                });
          }
        });

    this.add(
        new JButton("Rectangle") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    this.state.setShapeType(Shape.Type.RECTANGLE);
                    this.state.setTask(State.Task.DRAW);
                  }
                });
          }
        });

    this.add(
        new JButton("Ellipse") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    this.state.setShapeType(Shape.Type.ELLIPSE);
                    this.state.setTask(State.Task.DRAW);
                  }
                });
          }
        });

    this.add(
        new JButton("Line") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    this.state.setShapeType(Shape.Type.LINE);
                    this.state.setTask(State.Task.DRAW);
                  }
                });
          }
        });

    this.add(
        new JButton("Filled Rectangle") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    this.state.setShapeType(Shape.Type.FILLED_RECTANGLE);
                    this.state.setTask(State.Task.DRAW);
                  }
                });
          }
        });

    this.add(
        new JButton("Filled Ellipse") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    this.state.setShapeType(Shape.Type.FILLED_ELLIPSE);
                    this.state.setTask(State.Task.DRAW);
                  }
                });
          }
        });

    this.add(
        new JButton("PolyLine") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    this.state.setShapeType(Shape.Type.POLY_LINE);
                    this.state.setTask(State.Task.DRAW);
                  }
                });
          }
        });

    this.add(
        new JButton("Polygon") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    this.state.setShapeType(Shape.Type.POLYGON);
                    this.state.setTask(State.Task.DRAW);
                  }
                });
          }
        });

    this.add(
        new JButton("Text") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    String hint = "Input the text you want to draw";
                    String text =
                        JOptionPane.showInputDialog(panel, hint, hint, JOptionPane.PLAIN_MESSAGE);
                    this.state.setText(text);
                    this.state.setShapeType(Shape.Type.TEXT);
                    this.state.setTask(State.Task.DRAW);
                  }
                });
          }
        });

    this.add(
        new JButton("Select") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    this.state.setTask(State.Task.SELECT);
                  }
                });
          }
        });

    this.add(
        new JButton("Resize") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    this.state.setTask(State.Task.RESIZE);
                  }
                });
          }
        });

    this.add(
        new JButton("Remove") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    PaintFrame frame = (PaintFrame) SwingUtilities.getRoot(panel);
                    frame.getCanvas().removeSelectedShape();
                  }
                });
          }
        });

    this.add(
        new JButton("Change Text") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    String hint = "Input the text you want to draw";
                    String text =
                        JOptionPane.showInputDialog(panel, hint, hint, JOptionPane.PLAIN_MESSAGE);
                    PaintFrame frame = (PaintFrame) SwingUtilities.getRoot(panel);
                    frame.getCanvas().setSelectedShapeText(text);
                  }
                });
          }
        });

    this.add(
        new JButton("Change Color") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    PaintFrame frame = (PaintFrame) SwingUtilities.getRoot(panel);
                    frame.getCanvas().setSelectedShapeColor(state.getColor());
                  }
                });
          }
        });

    this.add(
        new JButton("Change Border Size") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    String hint = "Input the border size";
                    String text =
                        JOptionPane.showInputDialog(panel, hint, hint, JOptionPane.PLAIN_MESSAGE);
                    float size = Float.parseFloat(text);
                    PaintFrame frame = (PaintFrame) SwingUtilities.getRoot(panel);
                    frame.getCanvas().setSelectedShapeSize(size);
                  }
                });
          }
        });

    this.add(
        new JButton("Clone") {
          {
            this.addActionListener(
                new ClickListener(state) {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    PaintFrame frame = (PaintFrame) SwingUtilities.getRoot(panel);
                    frame.getCanvas().cloneSelectedShape();
                  }
                });
          }
        });
  }
}
