package main;

import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;

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

  public void changePosition(int x, int y) {
    xDelta = x;
    yDelta = y;
  }

  private void setPanelSize() {
    Dimension size = new Dimension(1280, 808);
    setPreferredSize(size);
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
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    updateAnimationTick();
    g.drawImage(
        animations[playerAction][aniIndex], (int) xDelta, (int) yDelta, imgW * 3, imgH * 3, null);
  }
}
