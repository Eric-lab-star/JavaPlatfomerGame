package utilz;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Image {
  public static BufferedImage FlipHorizontal(BufferedImage img) {
    AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
    tx.translate(-img.getWidth(), 0);
    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    img = op.filter(img, null);
    return img;
  }

  public static BufferedImage FlipVertical(BufferedImage img) {
    AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
    tx.translate(0, -img.getHeight(null));
    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    img = op.filter(img, null);
    return img;
  }
}
