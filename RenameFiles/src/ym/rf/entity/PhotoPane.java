package ym.rf.entity;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * User: WaitingForYou
 * Date: 13-9-18
 * Time: 下午9:47
 */
public class PhotoPane extends JPanel{
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private BufferedImage image;

    public PhotoPane() {
        init();
    }

    public void setImage(File file) {
        int resizeWidth = 800;
        int resizeHeight = 533;

        Resize resize = new Resize(file.getAbsolutePath(), resizeWidth, resizeHeight);
        image = Resize.rize(resize.bufImage, resizeWidth, resizeHeight);
        this.repaint();
    }

    private void init() {
        this.setSize(WIDTH,HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,0,WIDTH,HEIGHT);
        if (image != null) {
            g.drawImage(image, (WIDTH-image.getWidth())/2, (HEIGHT-image.getHeight())/2, null);
        }
    }
}
