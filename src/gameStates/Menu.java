package gameStates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import main.Game;
import ui.MenuButton;

/**
 * Menu
 */
public class Menu extends State implements StateMethods {
  private MenuButton[] buttons = new MenuButton[3];
  public Menu(Game game) {
    super(game);
    initButtons();
  }

  private void initButtons() {
    buttons[0] =
        new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, GameState.PLAYING);
    buttons[1] =
        new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, GameState.OPTIONS);
    buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, GameState.QUIT);
  }

  @Override
  public void update() {
    for (MenuButton menuButton : buttons) {
      menuButton.update();
    }
  }

  @Override
  public void draw(Graphics g) {
    for (MenuButton menuButton : buttons) {
      menuButton.draw(g);
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    for (MenuButton menuButton : buttons) {
      if (isIn(e, menuButton)) {
        menuButton.setMousePressed(true);
        break;
      }
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    for (MenuButton menuButton : buttons) {
      if (isIn(e, menuButton)) {
        if (menuButton.isMousePressed()) {
          menuButton.applyGameState();
          break;
        }
      }
    }
    resetButtons();
  }

  private void resetButtons() {
    for (MenuButton menuButton : buttons) {
      menuButton.resetBools();
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    for (MenuButton menuButton : buttons) {
      menuButton.setMouseOver(false);
    }

    for (MenuButton menuButton : buttons) {
      if (isIn(e, menuButton)) {
        menuButton.setMouseOver(true);
        break;
      }
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      GameState.state = GameState.PLAYING;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}
}
