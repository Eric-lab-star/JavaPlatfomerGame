package main;

import java.awt.Graphics;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {
  private MouseInputs mouseInput;
  public GamePanel() {
    mouseInput = new MouseInputs();
    addKeyListener(new KeyboardInputs());
    addMouseListener(mouseInput);
    addMouseMotionListener(mouseInput);
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.fillRect(100, 100, 400, 100);
  }
}
