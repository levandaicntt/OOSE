package Class;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private Student mapRowToStudent(ResultSet rs) throws SQLException {
        return new Student(
            rs.getString("StudentID"),
            rs.getString("Name"),
            rs.getDate("Birthday"),
            rs.getString("Major"),
            rs.getFloat("GPA"),
            rs.getString("StudentGroup")
        );
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM Student";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) list.add(mapRowToStudent(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Student getStudentById(String id) {
        String sql = "SELECT * FROM Student WHERE StudentID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRowToStudent(rs);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean insertStudent(Student s) {
        try { s.validate(); } 
        catch (Exception e) { 
            System.out.println(e.getMessage()); 
            return false; 
        }

        if (existsById(s.getStudentID())) {
            System.out.println("StudentID đã tồn tại, không thể thêm!");
            return false;
        }

        String sql = "INSERT INTO Student (StudentID, Name, Birthday, Major, GPA, StudentGroup) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getStudentID());
            ps.setString(2, s.getName());
            ps.setDate(3, new java.sql.Date(s.getBirthday().getTime()));
            ps.setString(4, s.getMajor());
            ps.setFloat(5, s.getGPA());
            ps.setString(6, s.getStudentGroup());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }


    public boolean updateStudent(Student s) {
        try { s.validate(); } 
        catch (Exception e) { 
            System.out.println(e.getMessage()); 
            return false; 
        }

        if (!existsById(s.getStudentID())) {
            System.out.println("StudentID không tồn tại, không thể sửa!");
            return false;
        }

        String sql = "UPDATE Student SET Name=?, Birthday=?, Major=?, GPA=?, StudentGroup=? WHERE StudentID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setDate(2, new java.sql.Date(s.getBirthday().getTime()));
            ps.setString(3, s.getMajor());
            ps.setFloat(4, s.getGPA());
            ps.setString(5, s.getStudentGroup());
            ps.setString(6, s.getStudentID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { 
            e.printStackTrace(); 
            return false; 
        }
    }


    public boolean deleteStudent(String id) {
        String sql = "DELETE FROM Student WHERE StudentID=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Student> getStudentsByGroup(String group) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM Student WHERE StudentGroup=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, group);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRowToStudent(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Student> getStudentsByMajor(String major) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM Student WHERE Major=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, major);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRowToStudent(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Student> getStudentsOrderByGpa(boolean ascending) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM Student ORDER BY GPA " + (ascending ? "ASC" : "DESC");
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRowToStudent(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Student> getStudentsByBirthMonth(int month) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM Student WHERE MONTH(Birthday)=? ORDER BY Birthday";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, month);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRowToStudent(rs));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    
    public int insertStudentsFromFile(String filePath) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 6) continue;

                try {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    java.sql.Date dob = java.sql.Date.valueOf(parts[2].trim());
                    String major = parts[3].trim();
                    float gpa = Float.parseFloat(parts[4].trim());
                    String group = parts[5].trim();

                    Student s = new Student(id, name, dob, major, gpa, group);
                    if (existsById(s.getStudentID())) {
                    	System.out.println("StudentID tồn tại, không thể thêm!");
                    	continue;
                    }
                    s.validate();
                    if (insertStudent(s)) count++;
                } catch (Exception e) {
                    System.out.println("Lỗi dòng: " + line + " | " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public boolean existsById(String id) {
        String sql = "SELECT 1 FROM Student WHERE StudentID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
