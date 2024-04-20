package main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {
  private MouseInputs mouseInput;
  private Game game;

  public GamePanel(Game game) {
    mouseInput = new MouseInputs(this);
    this.game = game;
    setPanelSize();
    addKeyListener(new KeyboardInputs(this));
    addMouseListener(mouseInput);
    addMouseMotionListener(mouseInput);
  }

  private void setPanelSize() {
    Dimension size = new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);
    setPreferredSize(size);
  }

  public void updateGame() {}

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    game.render(g);
  }
  public Game getGame() {
    return game;
  }
}
