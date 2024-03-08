package main;

public class Game implements Runnable {
  private GameWindow gameWindow;
  private GamePanel gamePanel;
  private Thread gameThread;
  private final int FPS_SET = 120;
  private final int UPS_SET = 200;

  public Game() {
    gamePanel = new GamePanel();
    gameWindow = new GameWindow(gamePanel);
    gamePanel.requestFocus();
    startGameLoop();
  }

  private void startGameLoop() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  private void update() {
    gamePanel.updateGame();
  }

  @Override
  public void run() {
    final double nanoSeconds = 1000000000.0;

    double timePerFrame = nanoSeconds / FPS_SET;
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
}
