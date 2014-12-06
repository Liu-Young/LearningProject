package ym.rf.entity;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * User: WaitingForYou
 * Date: 13-9-21
 * Time: 下午3:08
 */
public class Count {

    private Pattern idPattern = Pattern.compile("/^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$/; ");
    private int big;
    private int totalCount;
    FileWriter writer;
    private HashSet<String> photoNames = new HashSet<String>();
    private HashSet<String> identities = new HashSet<String>();
    private HashSet<Integer> nums = new HashSet<Integer>();

    public Count() {
    }

    public void countAndCheck(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                countAndCheck(f);
            }
        } else if(file.isFile()) {
            totalCount++;
            String photoName = file.getName().substring(0,8);
            String identity = file.getName().substring(9);
            String[] s = identity.split("\\.");
            identity = s[0];

            int n = Integer.parseInt(photoName.substring(4));
            if (n > big) {
                big = n;
            }
            nums.add(n);
            if (!identity.equals("0")) {
                if (photoNames.contains(photoName)) {
                    totalCount--;
                    System.out.println("重复图片：" + file.getAbsoluteFile());
                } else {
                    photoNames.add(photoName);
                }
                if (identities.contains(identity)) {
                    System.out.println("重复身份证：" + identity);
                } else {
                    identities.add(identity);
                }
            }
        }
    }

    public void getLack() throws IOException {
        writer = new FileWriter("D:\\1.txt");
        for (int i = 563; i < 855; i++) {
            if (!nums.contains(new Integer(i))) {
                try {
                    writer.write(i+"\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void main(String[] args) throws IOException {
        Count c = new Count();
        c.countAndCheck(new File("D:\\中小小学-修改完成3.0 - 保留相片号"));
        System.out.println("=======================================================");
        System.out.println("总数为：" + c.totalCount);
        System.out.println("最大："+c.big);
        c.getLack();
    }
}
