package inputs;

import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entities.Player;
import main.GamePanel;

public class KeyboardInputs implements KeyListener {
  private Player player;

  public KeyboardInputs(GamePanel gamePanel) {
    this.player = gamePanel.getPlayer();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_W:
        player.setDirection(UP);
        break;
      case KeyEvent.VK_A:
        player.setDirection(LEFT);
        break;
      case KeyEvent.VK_S:
        player.setDirection(DOWN);
        break;
      case KeyEvent.VK_D:
        player.setDirection(RIGHT);
        break;
      default:
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_W:
      case KeyEvent.VK_A:
      case KeyEvent.VK_S:
      case KeyEvent.VK_D:
        player.setMoving(false);
        break;
    }
  }
}
