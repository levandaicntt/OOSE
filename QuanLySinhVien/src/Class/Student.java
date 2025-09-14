package Class;

import java.util.Calendar;
import java.util.Date;

public class Student {
    private String studentID;
    private String name;
    private Date birthday;
    private String major;
    private float GPA;
    private String studentGroup;

    public Student() {}

    public Student(String studentID, String name, Date birthday, String major, float GPA, String studentGroup) {
        this.studentID = studentID;
        this.name = normalizeName(name);
        this.birthday = birthday;
        this.major = major;
        this.GPA = GPA;
        this.studentGroup = studentGroup;
    }

    public String getStudentID() { return studentID; }
    public void setStudentID(String studentID) { this.studentID = studentID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = normalizeName(name); }

    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public float getGPA() { return GPA; }
    public void setGPA(float gPA) { GPA = gPA; }

    public String getStudentGroup() { return studentGroup; }
    public void setStudentGroup(String studentGroup) { this.studentGroup = studentGroup; }

    @Override
    public String toString() {
        return studentID + " | " + name + " | " + birthday +
               " | " + major + " | " + GPA + " | " + studentGroup;
    }

    private String normalizeName(String raw) {
        if (raw == null || raw.isBlank()) return null;
        raw = raw.trim().toLowerCase();
        String[] parts = raw.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            sb.append(Character.toUpperCase(p.charAt(0)))
              .append(p.substring(1))
              .append(" ");
        }
        return sb.toString().trim();
    }

    public void validate() {
        if (studentID == null || !studentID.matches("\\d{10}")) {
            throw new IllegalArgumentException("StudentID phải gồm 10 chữ số!");
        }
        if (studentID.startsWith("455105")) {
            if (!"CNTT".equalsIgnoreCase(major)) {
                throw new IllegalArgumentException("StudentID 455105xxxx phải có major = CNTT!");
            }
        } else if (studentID.startsWith("455109")) {
            if (!"KTPM".equalsIgnoreCase(major)) {
                throw new IllegalArgumentException("StudentID 455109xxxx phải có major = KTPM!");
            }
        } else {
            throw new IllegalArgumentException("Mã sinh viên phải bắt đầu bằng 455105 (CNTT) hoặc 455109 (KTPM)!");
        }
        if (!"CNTT".equals(major) && !"KTPM".equals(major)) {
            throw new IllegalArgumentException("Major chỉ được CNTT hoặc KTPM!");
        }

        if (GPA < 0.0 || GPA > 10.0) {
            throw new IllegalArgumentException("GPA phải trong khoảng [0.0, 10.0]!");
        }

        if (birthday == null) {
            throw new IllegalArgumentException("Birthday không được null!");
        }
        int age = calculateAge(birthday);
        if (age < 15 || age > 110) {
            throw new IllegalArgumentException("Tuổi phải trong khoảng 15 đến 110!");
        }
    }

    private int calculateAge(Date dob) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(dob);
        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }
}
