package main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import entities.Player;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {
  private MouseInputs mouseInput;
  private static Player player = new Player(30, 200);

  public GamePanel() {
    mouseInput = new MouseInputs(this);
    setPanelSize();
    addKeyListener(new KeyboardInputs(this));
    addMouseListener(mouseInput);
    addMouseMotionListener(mouseInput);
  }

  private void setPanelSize() {
    Dimension size = new Dimension(1280, 808);
    setPreferredSize(size);
  }

  public void updateGame() {
    player.update();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    player.render(g);
  }

  public Player getPlayer() {
    return player;
  }
}
