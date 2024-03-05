package main;

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
  private BufferedImage img, subImg;
  private int imgW = 64, imgH = 40;
  private BufferedImage defaultImg;

  private int frame = 0;
  public GamePanel() {
    mouseInput = new MouseInputs(this);
    importImage();
    setPanelSize();
    addKeyListener(new KeyboardInputs(this));
    addMouseListener(mouseInput);
    addMouseMotionListener(mouseInput);
  }

  private void importImage() {
    try {
      System.out.println();
      InputStream is = getClass().getResourceAsStream("/res/player_sprites.png");
      img = ImageIO.read(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void changeXDelta(int delta) {
    xDelta += delta;
  }

  public void changeYDelta(int delta) {
    yDelta += delta;
  }

  public void changePosition(int x, int y) {
    xDelta = x;
    yDelta = y;
  }

  private void setPanelSize() {
    Dimension size = new Dimension(1280, 808);
    setPreferredSize(size);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(img.getSubimage(imgW * frame, imgH * frame, imgW, imgH), (int) 0, (int) 0, imgW * 2,
        imgH * 2, null);
  }
}
