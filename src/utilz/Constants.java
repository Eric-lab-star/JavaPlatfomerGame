package utilz;

import main.Game;

public class Constants {
  public static enum Directions { LEFT, UP, RIGHT, DOWN }
  public static enum PlayerConstants {
    IDLE((byte) 5),
    RUNNING((byte) 6),
    JUMP((byte) 3),
    FALLING((byte) 1),
    GROUND((byte) 2),
    HIT((byte) 4),
    ATTACK_1((byte) 3),
    ATTACK_JUMP_1((byte) 3),
    ATTACK_JUMP_2((byte) 3);

    private byte amount;

    PlayerConstants(byte amount) {
      this.amount = amount;
    }

    public byte GetSpriteAmount() {
      return this.amount;
    }
  }

  public static class UI {
    public static class Buttons {
      public static final int B_WIDTH_DEFAULT = 140;
      public static final int B_HEIGHT_DEFAULT = 56;
      public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
      public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
    }
  }
}
