package main;

import java.awt.Graphics;

import gameStates.GameState;
import gameStates.Menu;
import gameStates.Playing;

public class Game implements Runnable {
  private GamePanel gamePanel;
  private Thread gameThread;

  private Playing playing;
  private Menu menu;

  private final int FPS_SET = 120;
  private final int UPS_SET = 200;

  public static final int TILE_DEFAULT_SIZE = 32;
  public static final float SCALE = 2f;
  public final static int TILES_SIZE = (int) (TILE_DEFAULT_SIZE * SCALE);

  public static final int TILES_IN_WIDTH = 26;
  public static final int TILES_IN_HEIGHT = 14;

  public final static int GAME_WIDTH = TILES_IN_WIDTH * TILES_SIZE;
  public final static int GAME_HEIGHT = TILES_IN_HEIGHT * TILES_SIZE;

  public Game() {
    initClasses();
    gamePanel = new GamePanel(this);
    new GameWindow(gamePanel);
    gamePanel.requestFocus();
    startGameLoop();
  }

  private void initClasses() {
    menu = new Menu(this);
    playing = new Playing(this);
  }

  private void startGameLoop() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  private void update() {
    switch (GameState.state) {
      case MENU:
        menu.update();
        break;
      case PLAYING:
        playing.update();
        break;
      default:
        break;
    }
  }

  public void render(Graphics g) {
    switch (GameState.state) {
      case MENU:
        menu.draw(g);
        break;
      case PLAYING:
        playing.draw(g);
        break;
      default:
        break;
    }
  }

  @Override
  public void run() {
    final double nanoSeconds = 1000000000.0;

    double timePerFrame = nanoSeconds / FPS_SET; // how long each frame will last
    double timePerUpdate = nanoSeconds / UPS_SET;
    long previousTime = System.nanoTime();

    int frames = 0;
    int updates = 0;
    long lastCheck = System.currentTimeMillis();

    double deltaU = 0;
    double deltaF = 0;

    while (true) {
      long currentTime = System.nanoTime();

      deltaU += (currentTime - previousTime) / timePerUpdate;
      deltaF += (currentTime - previousTime) / timePerFrame;
      previousTime = currentTime;

      if (deltaU >= 1) {
        update();
        deltaU--;
        updates++;
      }

      if (deltaF >= 1) {
        gamePanel.repaint();
        deltaF--;
        frames++;
      }

      if (System.currentTimeMillis() - lastCheck >= 1000) {
        System.out.println("FPS: " + frames + " | UPS: " + updates);
        lastCheck = System.currentTimeMillis();
        frames = 0;
        updates = 0;
      }
    }
  }
  public void windowFocusLost() {
    if (GameState.state == GameState.PLAYING)
      playing.getPlayer().resetDirBooleans();
  }
  public Menu getMenu() {
    return menu;
  }
  public Playing getPlaying() {
    return playing;
  }
}
