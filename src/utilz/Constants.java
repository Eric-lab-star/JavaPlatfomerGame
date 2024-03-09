package utilz;

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
}
