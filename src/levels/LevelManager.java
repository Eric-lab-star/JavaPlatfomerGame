package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

/**
 * LevelManager
 */
public class LevelManager {
  private BufferedImage[] levelSprite;
  private Level levelOne;
  private Game game;

  public LevelManager(Game game) {
    this.game = game;
    importOutSideSprites();
    levelOne = new Level(LoadSave.GetLevelData());
  }

  public void importOutSideSprites() {
    BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATALAS);
    this.levelSprite = new BufferedImage[48];
    for (int j = 0; j < 4; j++) {
      for (int i = 0; i < 12; i++) {
        int index = j * 12 + i;
        levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
      }
    }
  }

  public void draw(Graphics g) {
    for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
      for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
        int index = levelOne.getSpriteIndex(i, j);
        g.drawImage(this.levelSprite[index], Game.TILES_SIZE * i, Game.TILES_SIZE * j,
            Game.TILES_SIZE, Game.TILES_SIZE, null);
      }
    }
  }

  public void update() {}
}