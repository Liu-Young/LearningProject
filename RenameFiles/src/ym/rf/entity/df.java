package ym.rf.entity;

import java.io.*;

/**
 * User: WaitingForYou
 * Date: 13-9-21
 * Time: 上午9:22
 */
public class df {

    public static void main(String[] args) throws IOException {
        FileWriter writer = new FileWriter("D:\\2.txt");
        writer.write("1");
        writer.write("1");
        writer.close();
    }
}
