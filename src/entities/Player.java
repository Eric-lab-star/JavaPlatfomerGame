package entities;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import utilz.Constants.PlayerConstants;
import utilz.Image;
import utilz.LoadSave;

public class Player extends Entity {
  private BufferedImage[][] animations;
  private BufferedImage img, subImg;
  private int imgW = 64, imgH = 40;
  private int aniTick, aniIndex, aniSpeed = 20;
  private PlayerConstants playerAction = PlayerConstants.IDLE;
  private boolean moving = false, attacking = false;
  private boolean right, left, down, up;
  private float playerSpeed = 2.0f;
  private int lastKeyEvent;
  private int[][] lvlData;

  public Player(float x, float y, int width, int height) {
    super(x, y, width, height);
    loadAnimations();
  }

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

  private void loadAnimations() {
    img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

    animations = new BufferedImage[PlayerConstants.values().length][];
    for (PlayerConstants p : PlayerConstants.values()) {
      animations[p.ordinal()] = new BufferedImage[p.GetSpriteAmount()];
      for (int i = 0; i < p.GetSpriteAmount(); i++) {
        animations[p.ordinal()][i] = img.getSubimage(i * imgW, p.ordinal() * imgH, imgW, imgH);
      }
    }
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

  public void loadLvlData(int[][] lvlData) {
    this.lvlData = lvlData;
  }

  public void update() {
    updatePosition();
    updateHitbox();
    setAnimation();
    updateAnimationTick();
  }

  public void resetDirBooleans() {
    left = false;
    right = false;
    down = false;
    up = false;
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

  public void setLastKeyEvent(int key) {
    this.lastKeyEvent = key;
  }

  public void render(Graphics g) {
    int action = playerAction.ordinal();
    subImg = animations[action][aniIndex];

    if (lastKeyEvent == KeyEvent.VK_A) {
      subImg = Image.FlipHorizontal(subImg);
    }

    g.drawImage(
        subImg, (int) this.x, (int) this.y, subImg.getWidth() * 2, subImg.getHeight() * 2, null);
    drawHitbox(g);
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
