package nju;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class Background extends Thing2D {

    public Background(int x, int y) {
        super(x, y);

        URL loc = this.getClass().getClassLoader().getResource("background2.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();
        this.setImage(image);
    }
}