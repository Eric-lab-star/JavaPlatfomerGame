package ui;

import static utilz.Constants.UI.Buttons.*;

import gameStates.GameState;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import utilz.LoadSave;

/**
 * MenuButton
 */

public class MenuButton {
  private int xPos, yPos, rowIndex, index;
  private int xOffsetCenter = B_WIDTH / 2;
  private GameState state;
  private BufferedImage[] imgs;
  private boolean mouseOver, mouseClick;
  private Rectangle bounds;

  public MenuButton(int xPos, int rowIndex, GameState gameState, int yPos) {
    this.xPos = xPos;
    this.rowIndex = rowIndex;
    this.state = gameState;
    this.yPos = yPos;
    loadImgs();
    initBounds();
  }

  private void initBounds() {
    bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
  }

  private void loadImgs() {
    imgs = new BufferedImage[3];
    BufferedImage tmp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
    for (int i = 0; i < imgs.length; i++) {
      tmp.getSubimage(
          i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
      imgs[i] = tmp;
    }
  }
  public void draw(Graphics g) {
    g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
  }
  public void update() {
    index = 0;
    if (mouseOver) {
      index = 1;
    }

    if (mouseClick) {
      index = 2;
    }
  }

  public boolean isMouseClick() {
    return mouseClick;
  }

  public void setMouseClick(boolean mouseClick) {
    this.mouseClick = mouseClick;
  }

  public boolean isMouseOver() {
    return mouseOver;
  }

  public void setMouseOver(boolean mouseOver) {
    this.mouseOver = mouseOver;
  }
  public void applyGameState() {
    GameState.state = state;
  }
  public void resetBools() {
    mouseOver = false;
    mouseClick = false;
  }
}
