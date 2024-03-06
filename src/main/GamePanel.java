package main;

import static utilz.Constants.Directions.DOWN;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.Directions.RIGHT;
import static utilz.Constants.Directions.UP;
import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.RUNNING;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {
  private MouseInputs mouseInput;
  private float xDelta = 360, yDelta = 200;
  private BufferedImage img;
  private int imgW = 64, imgH = 40;
  private BufferedImage[][] animations;

  private int aniTick, aniIndex, aniSpeed = 20;
  private int playerAction = IDLE;
  private int playerDir = -1;
  private boolean moving = false;

  public GamePanel() {
    mouseInput = new MouseInputs(this);
    importImage();
    loadAnimations();
    setPanelSize();
    addKeyListener(new KeyboardInputs(this));
    addMouseListener(mouseInput);
    addMouseMotionListener(mouseInput);
  }

  private void loadAnimations() {
    animations = new BufferedImage[9][6];
    for (int j = 0; j < animations.length; j++) {
      for (int i = 0; i < animations[j].length; i++) {
        animations[j][i] = img.getSubimage(i * imgW, j * imgH, imgW, imgH);
      }
    }
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

  private void setPanelSize() {
    Dimension size = new Dimension(1280, 808);
    setPreferredSize(size);
  }

  public void setDirection(int dir) {
    this.playerDir = dir;
    moving = true;
  }

  public void setMoving(boolean moving) {
    this.moving = moving;
  }

  private void setAnimation() {
    if (moving) {
      playerAction = RUNNING;
    } else {
      playerAction = IDLE;
    }
  }

  private void updateAnimationTick() {
    aniTick++;
    if (aniTick >= aniSpeed) {
      aniTick = 0;
      aniIndex++;
      if (aniIndex >= GetSpriteAmount(playerAction)) {
        aniIndex = 0;
      }
    }
  }

  private void updatePosition() {
    if (moving) {
      switch (playerDir) {
        case LEFT:
          xDelta -= 5;
          break;
        case UP:
          yDelta -= 5;
          break;
        case RIGHT:
          xDelta += 5;
          break;
        case DOWN:
          yDelta += 5;
          break;
      }
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    updateAnimationTick();
    setAnimation();
    updatePosition();
    g.drawImage(
        animations[playerAction][aniIndex], (int) xDelta, (int) yDelta, imgW * 3, imgH * 3, null);
  }
}
