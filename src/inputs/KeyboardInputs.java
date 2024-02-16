package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

/**
 * KeyboardInputs
 */
public class KeyboardInputs implements KeyListener {
  private GamePanel gamePanel;

  public KeyboardInputs(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // TODO Auto-generated method stub
  }

  @Override
  public void keyReleased(KeyEvent e) {
    switch (e.getKeyCode()) {
      case KeyEvent.VK_A:
        gamePanel.changeXDelta(-10);
        break;
      case KeyEvent.VK_S:
        gamePanel.changeYDelta(+10);
        break;
      case KeyEvent.VK_D:
        gamePanel.changeXDelta(+10);
        break;
      case KeyEvent.VK_W:
        gamePanel.changeYDelta(-10);
        break;
      default:
        break;
    }
  }
}