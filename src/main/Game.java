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

  @Override
  public void run() {
    final double nanoSeconds = 1000000000.0;

    double timePerFrame = nanoSeconds / FPS_SET;
    double timerPerUpdate = nanoSeconds / UPS_SET;
    long lastTime = System.nanoTime();
    long previousTime = System.nanoTime();

    int frames = 0;
    int updates = 0;
    long lastCheck = System.currentTimeMillis();

    double deltaU = 0;

    while (true) {
      long now = System.nanoTime();
      long currentTime = System.nanoTime();

      deltaU += (currentTime - previousTime) / timerPerUpdate;
      previousTime = currentTime;

      if (deltaU >= 1) {
        // update();
        deltaU--;
        updates++;
      }

      if (now - lastTime >= timePerFrame) {
        gamePanel.repaint();
        lastTime = now;
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
