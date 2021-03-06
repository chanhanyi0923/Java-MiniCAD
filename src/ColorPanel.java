import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

class ColorPanel extends JPanel {
  private static final long serialVersionUID = 1l;

  private abstract class ClickListener implements ActionListener {
    protected State state;

    ClickListener(State state) {
      this.state = state;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);
  }

  public ColorPanel(State state) {
    GridLayout layout = new GridLayout(0, 1);
    this.setLayout(layout);

    Color colors[] = {
      Color.BLACK, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, Color.WHITE,
      Color.PINK, Color.MAGENTA, Color.RED, Color.ORANGE, Color.YELLOW,
      Color.GREEN, Color.BLUE, Color.CYAN
    };

    for (Color color : colors) {
      this.add(
          new JButton("") {
            {
              this.addActionListener(
                  new ClickListener(state) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      this.state.setColor(color);
                    }
                  });
              this.setBackground(color);
            }
          });
    }
  }
}
