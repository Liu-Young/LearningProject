package ym.rf.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * User: WaitingForYou
 * Date: 13-9-18
 * Time: 下午9:03
 */
public class MainFrame extends JFrame {
    public static final int WIDTH = 300;
    public static final int HEIGTH = 300;
    private String TITLE = "批量修改相片名字-中心小学版";
    private JTextField path;
    private JFileChooser chooser;
    private JButton start, chooseFile, removePrefix;

    public MainFrame() {
        mylayou();
        init();
    }

    private void mylayou() {
        path = new JTextField(40);
        chooser = new JFileChooser("D://");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        start = new JButton("开始");
        chooseFile = new JButton("源文件夹");
        removePrefix = new JButton("去除前缀");

        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooser.showOpenDialog(null);
                File files = chooser.getSelectedFile();
                if (files.isDirectory()) {
                    path.setText(files.getAbsolutePath());
                }
            }
        });


        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filepath = path.getText();
                if (filepath == null || filepath.equals("")) {
                    JOptionPane.showMessageDialog(null, "文件目录不正确");
                } else {
                    File files = new File(filepath);
                    if (files.isDirectory()) {
                        new IDFrame(files.listFiles());
                        hideMyself();
                    }
                }
            }
        });

        removePrefix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        //layout
        this.setLayout(new BorderLayout());
        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1));
        p1.add(path);
        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(1, 1));
        p2.add(chooseFile);
        JPanel p3 = new JPanel();
        p3.setLayout(new FlowLayout());
        p3.add(start);
        p3.add(removePrefix);
        this.add(p1, BorderLayout.CENTER);
        this.add(p2, BorderLayout.EAST);
        this.add(p3, BorderLayout.SOUTH);
    }



    private void hideMyself() {
        this.setVisible(false);
    }

    private void init() {
        this.setResizable(false);
        this.setTitle(TITLE);
        Dimension screen = getToolkit().getScreenSize();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
