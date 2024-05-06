package ui;

import java.awt.image.BufferedImage;

import gameStates.GameState;
import utilz.LoadSave;

/**
 * MenuButton
 */

public class MenuButton {
  private int xPos, yPos, rowIndex;
  private GameState gameState;
  private BufferedImage[] imgs;

  public MenuButton(int xPos, int rowIndex, GameState gameState, int yPos) {
    this.xPos = xPos;
    this.rowIndex = rowIndex;
    this.gameState = gameState;
    this.yPos = yPos;
    loadImgs();
  }

  private void loadImgs() {
    imgs = new BufferedImage[3];
    BufferedImage tmp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
    for (int i = 0; i < imgs.length; i++) {
      tmp.getSubimage(i, i, i, i);
      imgs[i] = tmp;
    }
  }
}
