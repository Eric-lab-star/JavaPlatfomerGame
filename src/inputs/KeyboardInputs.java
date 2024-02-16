package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyboardInputs
 */
public class KeyboardInputs implements KeyListener {
  public KeyboardInputs() {}

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
        System.out.println("key pressed A");
        break;
      case KeyEvent.VK_S:
        System.out.println("key pressed S");
        break;
      case KeyEvent.VK_D:
        System.out.println("key pressed D");
        break;
      case KeyEvent.VK_W:
        System.out.println("key pressed W");
        break;
      default:
        break;
    }
  }
}
