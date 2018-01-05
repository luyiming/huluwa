package nju.bullet;

import nju.Thing2D;
import nju.creature.Creature;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;

public class Bullet extends Thing2D {

    private double angle;
    private double speed;
    private double damage;
    private Creature creature;
    private String bulletFilePath;

    public static final double SPEED_HIGH = 0.6;
    public static final double SPEED_MEDIAN = 0.4;
    public static final double SPEED_LOW = 0.3;

    public static final double DAMAGE_HIGH = 0.3;
    public static final double DAMAGE_MEDIAN = 0.2;
    public static final double DAMAGE_LOW = 0.1;

    public Bullet(int fromX, int fromY, int toX, int toY, double speed, double damage, String bulletFilePath, Creature creature) {
        super(fromX, fromY);

        this.speed = speed;
        this.damage = damage;
        this.creature = creature;
        this.bulletFilePath = bulletFilePath;

        URL loc = this.getClass().getClassLoader().getResource(bulletFilePath);
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();

        int dy = toY - fromY;
        int dx = toX - fromX;

        if (dx == 0) {
            angle = dy > 0 ? Math.toRadians(90.0) : Math.toRadians(-90.0);
            if (dy == 0)
                angle = 0;
        } else if(dx > 0) {
            angle = Math.atan((double)dy / dx);
        } else {
            angle = Math.PI - Math.atan((double)dy / -dx);
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(image, 0, 0, null);
        bGr.dispose();

        double rotationRequired = Math.toRadians (-120);
        double locationX = image.getWidth(null) / 2;
        double locationY = image.getHeight(null) / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(angle, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        this.setImage(op.filter(bimage, null));
    }

    public double getAngle() { return angle; }

    public double getSpeed() { return speed; }

    public double getDamage() {
        return damage;
    }

    public Creature getCreature() {
        return creature;
    }

    public String getSrc() { return bulletFilePath; }
}