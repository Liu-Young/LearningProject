package ym.rf.entity;

import java.io.File;

/**
 * User: WaitingForYou
 * Date: 13-9-21
 * Time: 上午8:39
 */
public class Photo {
    private File file;
    /**
     * 学生名字
     */
    private String studentName;
    /**
     * 身份证
     */
    private String identity;
    /**
     * 年级和班级
     */
    private String gradeAndclass;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getGradeAndclass() {
        return gradeAndclass;
    }

    public void setGradeAndclass(String gradeAndclass) {
        this.gradeAndclass = gradeAndclass;
    }
}
