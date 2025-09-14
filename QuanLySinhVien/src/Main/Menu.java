package Main;

import Class.Student;
import Class.StudentDAO;

import java.text.SimpleDateFormat;
import java.util.*;

public class Menu {
    private Scanner sc = new Scanner(System.in);
    private StudentDAO dao = new StudentDAO();

    public void start() {
        while (true) {
            if (!showMainMenu()) break;
        }
    }

    private boolean showMainMenu() {
        System.out.println("\n=== MENU CHÍNH ===");
        System.out.println("1. Thêm sinh viên");
        System.out.println("2. Sửa sinh viên");
        System.out.println("3. Xóa sinh viên");
        System.out.println("4. Xem tất cả sinh viên");
        System.out.println("5. Tìm kiếm / Sắp xếp");
        System.out.println("6. Thêm Student từ file");
        System.out.println("0. Thoát");
        System.out.print("Chọn: ");

        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1 -> addStudent();
            case 2 -> updateStudent();
            case 3 -> deleteStudent();
            case 4 -> viewAll();
            case 5 -> showSearchMenu();
            case 6 -> addStudentsFromFile();
            case 0 -> {
                System.out.println("Bye!");
                return false;
            }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }
        return true;
    }

    private void showSearchMenu() {
        System.out.println("\n=== MENU TÌM KIẾM / SẮP XẾP ===");
        System.out.println("1. Theo lớp học phần");
        System.out.println("2. Theo ngành");
        System.out.println("3. Theo GPA");
        System.out.println("4. Theo tháng sinh");
        System.out.println("0. Quay lại");
        System.out.print("Chọn: ");

        int ch = Integer.parseInt(sc.nextLine());
        switch (ch) {
            case 1 -> {
                System.out.print("Nhập group: ");
                String g = sc.nextLine();
                dao.getStudentsByGroup(g).forEach(System.out::println);
                pause();
            }
            case 2 -> {
                System.out.print("Nhập major (CNTT/KTPM): ");
                String m = sc.nextLine();
                dao.getStudentsByMajor(m).forEach(System.out::println);
                pause();
            }
            case 3 -> {
                System.out.println("Sắp xếp theo GPA:");
                System.out.println("1. Tăng dần");
                System.out.println("2. Giảm dần");
                System.out.print("Chọn: ");
                int opt = Integer.parseInt(sc.nextLine());

                boolean asc = (opt == 1);
                dao.getStudentsOrderByGpa(asc).forEach(System.out::println);

                pause();
            }
            case 4 -> {
                System.out.print("Nhập tháng (1-12): ");
                int month = Integer.parseInt(sc.nextLine());
                dao.getStudentsByBirthMonth(month).forEach(System.out::println);
                pause();
            }
            case 0 -> { return; }
            default -> System.out.println("Lựa chọn không hợp lệ!");
        }
    }

    private void addStudent() {
        try {
            Student s = inputStudent();
            if (dao.insertStudent(s)) System.out.println("Thêm thành công!");
            else System.out.println("Thêm thất bại!");
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        pause();
    }

    private void updateStudent() {
        System.out.print("Nhập ID cần sửa: ");
        String id = sc.nextLine();
        Student exist = dao.getStudentById(id);
        if (exist == null) {
            System.out.println("Không tìm thấy sinh viên!");
            pause();
            return;
        }
        try {
            Student s = inputStudent();
            s.setStudentID(id);
            if (dao.updateStudent(s)) System.out.println("Sửa thành công!");
            else System.out.println("Sửa thất bại!");
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        pause();
    }

    private void deleteStudent() {
        System.out.print("Nhập Mã sinh viên cần xóa: ");
        String id = sc.nextLine();
        if (dao.deleteStudent(id)) System.out.println("Xóa thành công!");
        else System.out.println("Xóa thất bại!");
        pause();
    }

    private void viewAll() {
        List<Student> list = dao.getAllStudents();
        list.forEach(System.out::println);
        pause();
    }

    private Student inputStudent() throws Exception {
        System.out.print("Mã sinh viên (10 chữ số): ");
        String id = sc.nextLine();
        System.out.print("Họ tên: ");
        String name = sc.nextLine();
        System.out.print("Ngày sinh (yyyy-MM-dd): ");
        Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(sc.nextLine());
        System.out.print("Ngành (CNTT/KTPM): ");
        String major = sc.nextLine();
        System.out.print("GPA: ");
        float gpa = Float.parseFloat(sc.nextLine());
        System.out.print("Lớp học phần: ");
        String group = sc.nextLine();

        return new Student(id, name, dob, major, gpa, group);
    }

    private void pause() {
        System.out.println("\nNhấn Enter để tiếp tục...");
        sc.nextLine();
    }
    
    private void addStudentsFromFile() {
        System.out.print("Nhập đường dẫn file: ");
        String path = sc.nextLine();
        int added = dao.insertStudentsFromFile(path);
        System.out.println("✅ Đã thêm " + added + " sinh viên từ file!");
        pause();
    }
}
