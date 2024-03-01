package main;

public class Game implements Runnable {
  private GameWindow gameWindow;
  private GamePanel gamePanel;
  private Thread gameThread;
  private final int FPS_SET = 120;

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
    final long nanoSeconds = 1000000000L;
    long timePerFrame = nanoSeconds / FPS_SET;
    long lastTime = System.nanoTime();

    int frames = 0;
    long lastCheck = System.currentTimeMillis();

    while (true) {
      long now = System.nanoTime();
      if (now - lastTime >= timePerFrame) {
        gamePanel.repaint();
        lastTime = now;
        frames++;
      }

      if (System.currentTimeMillis() - lastCheck >= 1000) {
        System.out.println("FPS: " + frames);
        lastCheck = System.currentTimeMillis();
        frames = 0;
      }
    }
  }
}
