package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import utilz.Constants.PlayerConstants;

public class Player extends Entity {
  private BufferedImage[][] animations;
  private BufferedImage img;
  private int imgW = 64, imgH = 40;
  private int aniTick, aniIndex, aniSpeed = 20;
  private PlayerConstants playerAction = PlayerConstants.IDLE;
  private boolean moving = false, attacking = false;
  private boolean right, left, down, up;
  private float playerSpeed = 2.0f;

  public boolean isUp() {
    return up;
  }

  public void setUp(boolean up) {
    this.up = up;
  }

  public boolean isDown() {
    return down;
  }

  public void setDown(boolean down) {
    this.down = down;
  }

  public boolean isLeft() {
    return left;
  }

  public void setLeft(boolean left) {
    this.left = left;
  }

  public boolean isRight() {
    return right;
  }

  public void setRight(boolean right) {
    this.right = right;
  }

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
    updatePosition();
    setAnimation();
    updateAnimationTick();
  }

  private void updateAnimationTick() {
    aniTick++;
    if (aniTick >= aniSpeed) {
      aniTick = 0;
      aniIndex++;
      if (aniIndex + 1 > playerAction.GetSpriteAmount()) {
        aniIndex = 0;
        attacking = false;
      }
    }
  }

  private void setAnimation() {
    PlayerConstants startAni = playerAction;

    if (moving) {
      playerAction = PlayerConstants.RUNNING;
    } else {
      playerAction = PlayerConstants.IDLE;
    }

    if (attacking) {
      playerAction = PlayerConstants.ATTACK_1;
    }

    if (startAni != playerAction) {
      resetAniTick();
    }
  }

  private void resetAniTick() {
    aniIndex = 0;
    aniTick = 0;
  }

  private void updatePosition() {
    moving = false;

    if (left && !right) {
      x -= playerSpeed;
      moving = true;
    } else if (!left && right) {
      x += playerSpeed;
      moving = true;
    }

    if (up && !down) {
      y -= playerSpeed;
      moving = true;
    } else if (!up && down) {
      y += playerSpeed;
      moving = true;
    }
  }

  public void render(Graphics g) {
    int action = playerAction.ordinal();
    BufferedImage subImg = animations[action][aniIndex];
    g.drawImage(subImg, (int) this.x, (int) this.y, imgW * 3, imgH * 3, null);
  }

  public void resetDir() {
    left = false;
    right = false;
    down = false;
    up = false;
  }

  public void setAttacking(boolean attacking) {
    this.attacking = attacking;
  }
}
