import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

class PaintFrame extends JFrame {
  private static final long serialVersionUID = 1l;

  private State state;
  private Canvas canvas;
  private ButtonPanel buttonPanel;
  private ColorPanel colorPanel;

  public Canvas getCanvas() {
    return this.canvas;
  }

  PaintFrame(String title) {
    super(title);

    this.state = new State();

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Setting.WINDOW_WIDTH, Setting.WINDOW_HEIGHT);
    this.setVisible(true);
    this.setBackground(Color.WHITE);
    this.setResizable(false);

    Container contentPane = this.getContentPane();
    contentPane.setLayout(new BorderLayout());

    this.colorPanel = new ColorPanel(this.state);
    this.colorPanel.setPreferredSize(
        new Dimension(Setting.COLOR_PANEL_WIDTH, Setting.WINDOW_HEIGHT));
    contentPane.add(this.colorPanel, BorderLayout.WEST);

    this.canvas = new Canvas(this.state);
    this.canvas.setPreferredSize(new Dimension(Setting.CANVAS_WIDTH, Setting.WINDOW_HEIGHT));
    contentPane.add(this.canvas, BorderLayout.CENTER);

    this.buttonPanel = new ButtonPanel(this.state);
    this.buttonPanel.setPreferredSize(new Dimension(Setting.BUTTON_WIDTH, Setting.WINDOW_HEIGHT));
    contentPane.add(this.buttonPanel, BorderLayout.EAST);

    Canvas canvasPtr = this.canvas;
    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("File");
    menuBar.add(menu);
    menu.add(
        new JMenuItem("open") {
          {
            this.addActionListener(
                new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                      File file = fileChooser.getSelectedFile();
                      canvasPtr.openFromFile(file);
                    }
                  }
                });
          }
        });
    menu.add(
        new JMenuItem("save") {
          {
            this.addActionListener(
                new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                      File file = fileChooser.getSelectedFile();
                      canvasPtr.saveToFile(file);
                    }
                  }
                });
          }
        });
    this.setJMenuBar(menuBar);

    this.revalidate();
    this.repaint();
  }
}
