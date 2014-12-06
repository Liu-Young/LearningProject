package ym.rf.frame;

import java.io.File;

public class Tools {
    public static boolean isImage(File file) {
        if (file.isDirectory()) {
            return false;
        }
        return true;
    }
}
