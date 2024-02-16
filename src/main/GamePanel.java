package main;

import java.awt.Graphics;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {
  private MouseInputs mouseInput;
  private int xDelta = 100, yDelta = 100;

  public void changeXDelta(int delta) {
    xDelta += delta;
    repaint();
  }
  public void changeYDelta(int delta) {
    yDelta += delta;
    repaint();
  }

  public void changePosition(int x, int y) {
    xDelta = x;
    yDelta = y;
    repaint();
  }

  public GamePanel() {
    mouseInput = new MouseInputs(this);
    addKeyListener(new KeyboardInputs(this));
    addMouseListener(mouseInput);
    addMouseMotionListener(mouseInput);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.fillRect(xDelta, yDelta, 400, 100);
  }
}
