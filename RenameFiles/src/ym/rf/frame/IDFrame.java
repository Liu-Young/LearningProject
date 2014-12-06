package ym.rf.frame;

import com.sun.java.swing.plaf.nimbus.LoweredBorder;
import ym.rf.entity.PhotoPane;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: WaitingForYou
 * Date: 13-9-18
 * Time: 下午9:37
 */
public class IDFrame extends JFrame {
    public static final int WIDTH = 800 + 16, HEIGHT = 600 + 26;

    private File[] images;
    private int result = 0;
    private HashSet<File> error = new HashSet<File>();
    private int index;

    private String TITLE = "输入身份证号码";
    private Font font = new Font("微软雅黑", Font.BOLD, 14);
    private PhotoPane photoPane;
    private JLabel photoName;
    private JTextField identityField;

    public IDFrame(File[] images) {
        this.images = images;
        index = 0;
        mylayout();
        init();
        while (!images[index].isFile() || (images[index].isFile() && images[index].getName().length() > 13)) {
            index++;
        }
        show(images[index]);
    }

    private void init() {
        this.setBackground(Color.blue);
        this.setTitle(TITLE);
        Dimension screen = getToolkit().getScreenSize();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(WIDTH, HEIGHT);
        this.setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    private void mylayout() {
        this.setFocusable(false);
        photoPane = new PhotoPane();
        photoPane.setFocusable(false);
        photoName = new JLabel("没有显示");
        photoName.setBorder(BorderFactory.createEtchedBorder());
        photoName.setFont(font);
        photoName.setFocusable(false);
        identityField = new JTextField(20);
        identityField.setFont(font);
        identityField.setFocusable(true);
        identityField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        String id = identityField.getText();
                        if (id.length() == 1) {
                            if (id.equals("3")) {
                                identityField.setText("440903");
                            } else if (id.equals("2")) {
                                identityField.setText("440923");
                            } else if (id.equals("0")) {
                                File file = new File(images[index].getParent()+"\\0");
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                images[index].renameTo(new File(file.getAbsolutePath()+"\\"+images[index].getName().substring(0, 8)
                                        + "_" + identityField.getText() + ".jpg"));
                                index++;
                                if (index < images.length) {
                                    while (!images[index].isFile() || (images[index].isFile() && images[index].getName().length() > 13)) {
                                        index++;
                                    }
                                    show(images[index]);
                                }
                            }
                        } else if (id.length() == 7) {
                            System.out.println(id.charAt(5));
                            switch (id.charAt(6)) {
                                case '1':
                                    identityField.setText(id.substring(0, 6) + "2001");
                                    break;
                                case '2':
                                    identityField.setText(id.substring(0, 6) + "2002");
                                    break;
                                case '3':
                                    identityField.setText(id.substring(0, 6) + "2003");
                                    break;
                                case '4':
                                    identityField.setText(id.substring(0, 6) + "2004");
                                    break;
                                case '5':
                                    identityField.setText(id.substring(0, 6) + "2005");
                                    break;
                                case '6':
                                    identityField.setText(id.substring(0, 6) + "2006");
                                    break;
                                case '7':
                                    identityField.setText(id.substring(0, 6) + "2007");
                                    break;
                                case '0':
                                    identityField.setText(id.substring(0, 6) + "2000");
                                    break;

                            }
                        } else if (id.length() == 18) {
                            String classAndGrade = JOptionPane.showInputDialog(null,"输入班级");
                            File file = new File(images[index].getParent() + "\\" + classAndGrade);
                            if (!file.exists()) {
                                file.mkdirs();
                            }
//                            String name =images[index].getParent() + "\\" + classAndGrade
//                                    + "\\" + images[index].getName().substring(0, 8) + "_" + identityField.getText() + ".jpg";
                            String name = file.getAbsolutePath() + "\\" + images[index].getName().substring(0, 8)
                                    + "_" + identityField.getText() + ".jpg";
                            if (images[index].renameTo(new File(name))) {
                                result++;
                            } else {
                                error.add(images[index]);
                            }
                            index++;
                            if (index < images.length) {
                                while (!images[index].isFile() || (images[index].isFile() && images[index].getName().length() > 13)) {
                                    index++;
                                }
                                show(images[index]);
                            } else {
                                JOptionPane.showConfirmDialog(null, "身份证已全部输入完成","退出",JOptionPane.OK_OPTION);
                                System.exit(0);
                            }
                        }
                        break;
                }
            }
        });

        //layout
        this.setLayout(new BorderLayout());
        this.add(photoPane, BorderLayout.CENTER);
        JPanel p1 = new JPanel();
        p1.setLayout(new FlowLayout());
        p1.add(photoName);
        p1.add(identityField);
        this.add(p1, BorderLayout.SOUTH);
    }

    private void show(File image) {
        identityField.setText("");
        photoPane.setImage(image);
        photoName.setText(image.getName());
    }

    private boolean havenoID(String name) {
        if (name.length() < 13) {
            return true;
        } else {
            return false;
        }
    }

}
