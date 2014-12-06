package ym.rf.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ImageTransformDemo extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static double scaleX = 1.0;
    private static double scaleY = 1.0; // 水平方向和垂直方向的伸缩比例，默认为1.0

    private Image srcImg = null; // 源图像
    private static BufferedImage originBufImg = null; // 原始缓冲图像
    private static BufferedImage filteredBufImg = null; // 过滤后的图像

    private static BufferedImage bufImg = null;        //将要绘制的图像

    private static JLabel scaleXLbl = new JLabel("scaleX: ");
    private static JLabel scaleYLbl = new JLabel("scaleY: ");
    private static JTextField scaleXFld = new JTextField("1.0");
    private static JTextField scaleYFld = new JTextField("1.0");

    private static ImageTransformDemo itf = null;
    private static Container pane = null;

    public ImageTransformDemo() throws IOException {
        super();

        if (srcImg == null) {
            srcImg = loadImage("1.png");
        }
        if (srcImg != null) {
//			originBufImg = toBufferedImage(srcImg);
            InputStream in = new FileInputStream(System.getProperty("user.dir")
                    + "/images/1.png");
            originBufImg = ImageIO.read(in);
        }

        // 过滤后的图像
        filteredBufImg = new BufferedImage(
                (int) (srcImg.getWidth(null) * scaleX), (int) (srcImg
                .getHeight(null) * scaleY), BufferedImage.TYPE_INT_ARGB);
        filterImage(originBufImg, filteredBufImg);

        bufImg = originBufImg;
    }

    /*
     * 利用AffineTransform的setToScale方法实现仿射变换
     */
    public static void filterImage(BufferedImage src, BufferedImage dst) {
        // 仿射变换对象
        AffineTransform transform = new AffineTransform();
        transform.setToScale(scaleX, scaleY);

        AffineTransformOp imageOp = new AffineTransformOp(transform, null);
        imageOp.filter(src, dst); // 处理后的图像储存在dst中
    }

    public static Image loadImage(String imgName) {
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir")
                + "/images/" + imgName);
        return icon.getImage();
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent
        // Pixels
        boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the
        // screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image
                    .getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image
                    .getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }

    // This method returns true if the specified image has transparent pixels
    public static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }

        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        if (cm != null)
            return cm.hasAlpha();
        else
            return false;
    }

    public static void setScaleX(double x) {
        scaleX = x;
    }

    public static void setScaleY(double y) {
        scaleY = y;
    }

    public static double getScaleX() {
        return scaleX;
    }

    public static double getScaleY() {
        return scaleY;
    }

    public BufferedImage getFilterdImage() {
        return filteredBufImg;
    }

    public static void setBufImage(BufferedImage img) {
        bufImg = img;
    }

    public void paint(Graphics g) {
//		super.paintComponent(g);
//		g.setColor(this.getForeground());
        g.clearRect(0, 0, getWidth(), getHeight());

        if (bufImg != null) {
            Graphics2D g2 = (Graphics2D) g;

            int w = this.getWidth();
            int h = this.getHeight();
            int iw = bufImg.getWidth(null);    //图片的宽度
            int ih = bufImg.getHeight(null);//图片的高度

            System.out.println("缓冲图像现在的宽和高: " + iw + "\t" + ih);

//			//先清除原来的图
//			g2.fillRect(0, 0, w, h);
//			g2.clearRect(0, 0, w, h);

            //在画布中央绘制
            g2.drawImage(bufImg, (w - iw) / 2, (h - ih) / 2, null);
            g2.dispose();
        }
    }

    public static void main(String[] args) throws IOException {

        itf = new ImageTransformDemo();

        JFrame ui = new JFrame("图片缩放Demo");
        ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        ui.setBounds((dim.width - 850) / 2, (dim.height - 650) / 2, 850, 650);

        pane = ui.getContentPane();
        pane.setLayout(null);

        scaleXLbl.setBounds(735, 500, 50, 25);
        pane.add(scaleXLbl);
        scaleYLbl.setBounds(735, 530, 50, 25);
        pane.add(scaleYLbl);
        scaleXFld.setBounds(785, 500, 45, 25);
        pane.add(scaleXFld);
        scaleYFld.setBounds(785, 530, 45, 25);
        pane.add(scaleYFld);

        JButton bt = new JButton("缩放");
        bt.setBounds(735, 570, 60, 25);
        bt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double scaleX = 0.0;
                double scaleY = 0.0;
                try {
                    scaleX = Double.parseDouble(scaleXFld.getText());
                    scaleY = Double.parseDouble(scaleYFld.getText());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "输入的数字格式不对!");
                }
                ImageTransformDemo.setScaleX(scaleX);
                ImageTransformDemo.setScaleY(scaleY);

                filterImage(originBufImg, filteredBufImg);
                bufImg = filteredBufImg;

                itf.repaint();
                pane.validate();
                pane.repaint();
            }

        });
        pane.add(bt);

        itf.setBounds(25, 5, 800, 600);
        pane.add(itf);

        BufferedImage bufImg = itf.getFilterdImage();
        ImageIO.write(bufImg, "png", new File(System.getProperty("user.dir")
                + "/images/" + "缩放后的图像.png"));        //保存缩放后的图像

        ui.setVisible(true);
    }

}

