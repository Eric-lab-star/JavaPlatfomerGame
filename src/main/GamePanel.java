package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {
  private MouseInputs mouseInput;
  private float xDelta = 360, yDelta = 200;
  private float xDir = 0.3f, yDir = 0.3f;
  private Color color = new Color(140, 20, 90);
  private Random random = new Random();

  public void changeXDelta(int delta) {
    xDelta += delta;
  }
  public void changeYDelta(int delta) {
    yDelta += delta;
  }

  public void changePosition(int x, int y) {
    xDelta = x;
    yDelta = y;
  }

  public GamePanel() {
    mouseInput = new MouseInputs(this);
    setPanelSize();
    addKeyListener(new KeyboardInputs(this));
    addMouseListener(mouseInput);
    addMouseMotionListener(mouseInput);
  }
  private void setPanelSize() {
    Dimension size = new Dimension();
    setPreferredSize(size);
  }
  // paintComponent is called by JPanel
  // when the game starts
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    updateRectangle();
    g.setColor(color);
    g.fillRect((int) xDelta, (int) yDelta, 100, 100);
  }

  private void updateRectangle() {
    xDelta += xDir;
    if (xDelta < 0 || xDelta > 620) {
      xDir *= -1;
      color = getRndColor();
    }
    yDelta += yDir;
    if (yDelta < 0 || yDelta > 270) {
      yDir *= -1;
      color = getRndColor();
    }
  }
  private Color getRndColor() {
    final int r, g, b;
    r = random.nextInt(254);
    g = random.nextInt(254);
    b = random.nextInt(254);

    return new Color(r, g, b);
  }
}
