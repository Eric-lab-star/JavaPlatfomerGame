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
  private boolean mouseOver, mousePressed;
  private Rectangle bounds;

  public MenuButton(int xPos, int yPos, int rowIndex, GameState gameState) {
    this.xPos = xPos;
    this.rowIndex = rowIndex;
    this.state = gameState;
    this.yPos = yPos;
    this.index = 0;
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
      imgs[i] = tmp.getSubimage(
          i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
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

    if (mousePressed) {
      index = 2;
    }
  }

  public boolean isMousePressed() {
    return mousePressed;
  }

  public void setMousePressed(boolean mouseClick) {
    this.mousePressed = mouseClick;
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
    mousePressed = false;
  }
  public Rectangle getBounds() {
    return bounds;
  }
}
