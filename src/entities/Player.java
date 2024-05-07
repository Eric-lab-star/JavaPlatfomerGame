package entities;

import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.GetEntityXPosNextToWall;
import static utilz.HelpMethods.GetEntityYPosUnderRoofOrAboveFloor;
import static utilz.HelpMethods.IsEntityOnFloor;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import main.Game;
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
  private boolean right, left, down, up, jump;
  private float playerSpeed = 1.0f * Game.SCALE;
  private int lastKeyEvent;
  private int[][] lvlData;
  private float xDrawOffset = 21 * Game.SCALE;
  private float yDrawOffset = 4 * Game.SCALE;

  // jumpin / gravity
  private float airSpeed = 0f;
  private float gravity = 0.04f * Game.SCALE;
  private float jumpspeed = -2.25f * Game.SCALE;
  private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
  private boolean inAir = false;

  public Player(float x, float y, int width, int height) {
    super(x, y, width, height);
    loadAnimations();
    initHitbox(x, y, (int) (20 * Game.SCALE), (int) (27 * Game.SCALE));
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

    if (inAir) {
      if (airSpeed < 0) {
        playerAction = PlayerConstants.JUMP;
      } else {
        playerAction = PlayerConstants.FALLING;
      }
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
    if (!IsEntityOnFloor(hitbox, lvlData)) {
      inAir = true;
    }
  }

  public void update() {
    updatePosition();
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
    if (jump) {
      jump();
    }

    if (!left && !right && !inAir) {
      return;
    }

    float xSpeed = 0;

    if (left) {
      xSpeed -= playerSpeed;
    }
    if (right) {
      xSpeed += playerSpeed;
    }
    if (!inAir) {
      if (!IsEntityOnFloor(hitbox, lvlData)) {
        inAir = true;
      }
    }

    if (inAir) {
      if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
        hitbox.y += airSpeed;
        airSpeed += gravity;
        updateXPos(xSpeed);
      } else {
        hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
        if (airSpeed > 0) {
          resetInAir();
        } else {
          airSpeed = fallSpeedAfterCollision;
        }
        updateXPos(xSpeed);
      }
    } else {
      updateXPos(xSpeed);
    }
    moving = true;
  }

  private void jump() {
    if (inAir) {
      return;
    } else {
      inAir = true;
      airSpeed = jumpspeed;
    }
  }

  private void resetInAir() {
    inAir = false;
    airSpeed = 0;
  }

  private void updateXPos(float xSpeed) {
    if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
      hitbox.x += xSpeed;
    } else {
      hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
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

    g.drawImage(subImg, (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset),
        subImg.getWidth() * 2, subImg.getHeight() * 2, null);
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

  public void setJump(boolean jump) {
    this.jump = jump;
  }
}
