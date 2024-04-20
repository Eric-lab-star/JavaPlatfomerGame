package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entities.Player;
import main.GamePanel;

public class KeyboardInputs implements KeyListener {
  private GamePanel gamePanel;
  private Player player;

  public KeyboardInputs(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
    this.player = gamePanel.getGame().getPlayer();
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_W:
        player.setUp(true);
        break;
      case KeyEvent.VK_A:
        player.setLeft(true);
        player.setLastKeyEvent(KeyEvent.VK_A);
        break;
      case KeyEvent.VK_S:
        player.setDown(true);
        break;
      case KeyEvent.VK_D:
        player.setRight(true);
        player.setLastKeyEvent(KeyEvent.VK_D);
        break;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_W:
        player.setUp(false);
        break;
      case KeyEvent.VK_A:
        player.setLeft(false);
        break;
      case KeyEvent.VK_S:
        player.setDown(false);
        break;
      case KeyEvent.VK_D:
        player.setRight(false);
        break;
    }
  }
}
