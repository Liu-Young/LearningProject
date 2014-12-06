package ym.rf.frame;

import ym.rf.entity.PhotoPane;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;

/**
 * User: WaitingForYou
 * Date: 13-9-18
 * Time: 下午9:37
 */
public class IDFrame extends JFrame {
    public static final int WIDTH = 800 + 16, HEIGHT = 600 + 26;

    private int index;

    public File[] images;
    public int result = 0;
    public HashSet<File> error = new HashSet<File>();

    public String TITLE = "输入身份证号码";
    public Font font = new Font("微软雅黑", Font.BOLD, 14);
    public PhotoPane photoPane;
    public JLabel photoName;
    public JLabel filePath;
    public JFileChooser chooser;
    public JTextField identityField;

    public IDFrame(File[] images) {
        this.images = images;
        index = 0;
        mylayout();
        initMenu();
        init();
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

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("菜单");
        JMenuItem selectDir = new JMenuItem("选择文件夹");
        JMenuItem removePrefix = new JMenuItem("去前缀");
        JMenuItem exit = new JMenuItem("退出");

        chooser = new JFileChooser("D://");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selectDir.addActionListener(new SelectDirAction(this));
        removePrefix.addActionListener(new RemovePrefixAction(this));
        exit.addActionListener(new ExitAction());

        fileMenu.add(selectDir);
        fileMenu.add(removePrefix);
        fileMenu.add(exit);
        menuBar.add(fileMenu);

        this.setJMenuBar(menuBar);
    }

    private void mylayout() {
        this.setFocusable(false);
        photoPane = new PhotoPane();
        photoPane.setFocusable(false);
        photoName = new JLabel("没有显示");
        filePath = new JLabel("请选择文件夹");
        photoName.setBorder(BorderFactory.createEtchedBorder());
        photoName.setFont(font);
        photoName.setFocusable(false);
        filePath.setFont(font);
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
                                showNextImage();
                            }
                        } else if (id.length() == 7) {
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
                                case '8':
                                    identityField.setText(id.substring(0, 6) + "2007");
                                    break;
                                case '9':
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
                            String name = file.getAbsolutePath() + "\\" + images[index].getName().substring(0, 8)
                                    + "_" + identityField.getText() + ".jpg";
                            if (images[index].renameTo(new File(name))) {
                                result++;
                            } else {
                                error.add(images[index]);
                            }
                            showNextImage();
                        }
                        break;
                }
            }
        });

        // layout
        this.setLayout(new BorderLayout());
        JPanel operaPane = new JPanel();
        operaPane.setLayout(new FlowLayout());
        operaPane.add(photoName);
        operaPane.add(identityField);

        this.add(operaPane, BorderLayout.SOUTH);
        this.add(photoPane, BorderLayout.CENTER);
        this.add(filePath, BorderLayout.NORTH);
    }

    public void showNextImage() {
        if (images != null && images.length > 0) {
            if (nextPointer()) {
                JOptionPane.showConfirmDialog(null, "身份证已全部输入完成","退出",JOptionPane.OK_OPTION);
                return;
            }
            while (!Tools.isImage(images[index])) {
                if (nextPointer()) {
                    return;
                }
            }
            identityField.setText("");
            photoPane.setImage(images[index]);
            photoName.setText(images[index].getName());
        }
    }

    public void resetPointer(){
        index = -1;
    }

    // will return true when end of the images array
    public boolean nextPointer() {
        index++;
        if (images.length > index) {
            return false;
        }else {
            return true;
        }
    }

    private boolean havenoID(String name) {
        if (name.length() < 13) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        new IDFrame(null);
    }
}

class SelectDirAction implements ActionListener {

    public IDFrame idFrame;

    public SelectDirAction(IDFrame idFrame) {
        this.idFrame = idFrame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        idFrame.chooser.showOpenDialog(null);
        File files = idFrame.chooser.getSelectedFile();
        if (files.isDirectory()) {
            idFrame.resetPointer();
            idFrame.filePath.setText(files.getAbsolutePath());
            idFrame.images = files.listFiles();
            idFrame.showNextImage();
        }
    }
}

class RemovePrefixAction implements ActionListener {

    public IDFrame idFrame;

    public RemovePrefixAction(IDFrame idFrame) {
        this.idFrame = idFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        idFrame.chooser.showOpenDialog(null);
        File file = idFrame.chooser.getSelectedFile();
        int result = JOptionPane.showConfirmDialog(null, "确定要去队前缀?", "去除DSC_XXXX_操作", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            rename(file.getAbsoluteFile());
        }
    }

    private void rename(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                rename(f);
            } else {
                f.renameTo(new File(f.getParent() + "\\" + f.getName().substring(9)));
            }
        }
    }
}

class ExitAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}