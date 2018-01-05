package nju;

import java.awt.Image;

public class Thing2D {

    private final int SPACE = 20;

    private int x;
    private int y;
    private Image image;

    private static int id = 0;

    private int uid;

    public Thing2D(int x, int y) {
        this.x = x;
        this.y = y;
        uid = id++;
    }

    public Image getImage() {
        return this.image;
    }

    public void setImage(Image img) {
        image = img;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }

    public int getId() {
        return uid;
    }

    public void setId(int id) {
        uid = id;
    }
}