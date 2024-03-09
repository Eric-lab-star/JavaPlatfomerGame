package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import entities.Player;
// public class MouseInputs implements MouseListener, MouseMotionListener {}
import main.GamePanel;

/**
 * MouseInputs
 */
public class MouseInputs implements MouseListener, MouseMotionListener {
  private Player player;
  public MouseInputs(GamePanel gamePanel) {
    this.player = gamePanel.getPlayer();
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    // TODO Auto-generated method stub
  }

  @Override
  public void mouseMoved(MouseEvent e) {}

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON1) {
      player.setAttacking(true);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {}

  @Override
  public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub
  }
}
