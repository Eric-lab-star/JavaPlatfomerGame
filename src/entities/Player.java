package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import utilz.Constants.Directions;
import utilz.Constants.PlayerConstants;

public class Player extends Entity {
  private BufferedImage[][] animations;
  private BufferedImage img;
  private int imgW = 64, imgH = 40;
  private int aniTick, aniIndex, aniSpeed = 20;
  private PlayerConstants playerAction = PlayerConstants.IDLE;
  private Directions playerDir = Directions.RIGHT;
  private boolean moving = false;

  public Player(float x, float y) {
    super(x, y);
    importImage();
    loadAnimations();
  }

  private void importImage() {
    InputStream is = getClass().getResourceAsStream("/res/player_sprites.png");
    try {
      img = ImageIO.read(is);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        is.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void loadAnimations() {
    animations = new BufferedImage[PlayerConstants.values().length][];

    for (PlayerConstants p : PlayerConstants.values()) {
      animations[p.ordinal()] = new BufferedImage[p.GetSpriteAmount()];
      for (int i = 0; i < p.GetSpriteAmount(); i++) {
        animations[p.ordinal()][i] = img.getSubimage(i * imgW, p.ordinal() * imgH, imgW, imgH);
      }
    }
  }

  public void update() {
    updateAnimationTick();
    setAnimation();
    updatePosition();
  }

  private void updateAnimationTick() {
    aniTick++;
    if (aniTick >= aniSpeed) {
      aniTick = 0;
      aniIndex++;
      if (aniIndex + 1 > playerAction.GetSpriteAmount()) {
        aniIndex = 0;
      }
    }
  }

  private void setAnimation() {
    if (moving) {
      playerAction = PlayerConstants.RUNNING;
    } else {
      playerAction = PlayerConstants.IDLE;
    }
  }

  private void updatePosition() {
    if (moving) {
      switch (playerDir) {
        case LEFT:
          this.x -= 1;
          break;
        case UP:
          this.y -= 1;
          break;
        case RIGHT:
          this.x += 1;
          break;
        case DOWN:
          this.y += 1;
          break;
      }
    }
  }

  public void render(Graphics g) {
    int action = playerAction.ordinal();
    BufferedImage subImg = animations[action][aniIndex];
    g.drawImage(subImg, (int) this.x, (int) this.y, imgW * 3, imgH * 3, null);
  }

  public void setDirection(Directions dir) {
    this.playerDir = dir;
    moving = true;
  }

  public void setMoving(boolean moving) {
    this.moving = moving;
  }
}
