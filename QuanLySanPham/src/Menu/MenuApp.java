package Menu;

import java.util.*;
import Class.Category;
import Class.Product;
import Interface.DiscountStrategy;

public class MenuApp {

    public static Map<Product, String> products = new HashMap<>();
    public static Map<Product, List<DiscountStrategy>> discounts = new HashMap<>();

    public static void updateMap(ArrayList<Category> categories) {
        products.clear();
        for (Category c : categories) {
            if (c.getProducts() != null) {
                for (Product p : c.getProducts()) {
                    products.put(p, c.getName());
                }
            }
        }
    }

    public static float getFinalPrice(Product p) {
        List<DiscountStrategy> ds = discounts.get(p);
        float price = p.getPrice();
        if (ds != null) {
            for (DiscountStrategy d : ds) {
                price = d.applyDiscount(price);
            }
        }
        return price;
    }

    public static void viewProductsNormal(boolean showDiscount) {
        if (products.isEmpty()) {
            System.out.println("Chưa có sản phẩm nào!");
            return;
        }
        int i = 1;
        for (Map.Entry<Product, String> entry : products.entrySet()) {
            Product p = entry.getKey();
            float price = showDiscount ? getFinalPrice(p) : p.getPrice();
            System.out.println(i++ + ". " + p.getName() + " | Price: " + price + " -> Category: " + entry.getValue());
        }
    }

    public static void viewProductsByCategory(boolean showDiscount) {
        if (products.isEmpty()) {
            System.out.println("Chưa có sản phẩm nào!");
            return;
        }
        Set<String> categories = new HashSet<>(products.values());
        for (String cat : categories) {
            System.out.println("Category: " + cat);
            for (Map.Entry<Product, String> entry : products.entrySet()) {
                if (entry.getValue().equals(cat)) {
                    Product p = entry.getKey();
                    float price = showDiscount ? getFinalPrice(p) : p.getPrice();
                    System.out.println("  " + p.getName() + " | Price: " + price);
                }
            }
        }
    }

    public static void viewProductsByPrice(boolean ascending, boolean showDiscount) {
        if (products.isEmpty()) {
            System.out.println("Chưa có sản phẩm nào!");
            return;
        }
        List<Product> list = new ArrayList<>(products.keySet());
        list.sort((p1, p2) -> {
            float price1 = showDiscount ? getFinalPrice(p1) : p1.getPrice();
            float price2 = showDiscount ? getFinalPrice(p2) : p2.getPrice();
            return ascending ? Float.compare(price1, price2) : Float.compare(price2, price1);
        });
        for (Product p : list) {
            float price = showDiscount ? getFinalPrice(p) : p.getPrice();
            System.out.println(p.getName() + " | Price: " + price + " -> Category: " + products.get(p));
        }
    }


    public static void viewMenu(Scanner sc) {
        if (products.isEmpty()) {
            System.out.println("Chưa có sản phẩm nào!");
            return;
        }

        boolean showDiscount = false;
        while (true) {
            System.out.println("\n--- CHỌN KIỂU GIÁ ---");
            System.out.println("1. Xem giá gốc");
            System.out.println("2. Xem giá sau giảm");
            System.out.print("Chọn: ");
            int opt = Integer.parseInt(sc.nextLine());
            if (opt == 1) {
                showDiscount = false;
                break;
            } else if (opt == 2) {
                showDiscount = true;
                break;
            } else {
                System.out.println("Lựa chọn không hợp lệ!");
            }
        }

        while (true) {
            System.out.println("\n--- VIEW MENU ---");
            System.out.println("1. Xem bình thường");
            System.out.println("2. Xem theo Category");
            System.out.println("3. Xem theo Giá (Tăng dần)");
            System.out.println("4. Xem theo Giá (Giảm dần)");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> viewProductsNormal(showDiscount);
                case 2 -> viewProductsByCategory(showDiscount);
                case 3 -> viewProductsByPrice(true, showDiscount);
                case 4 -> viewProductsByPrice(false, showDiscount);
                case 0 -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
            pause(sc);
        }
    }


    public static void addCategory(ArrayList<Category> categories, Scanner sc) {
        System.out.print("Tên Category mới: ");
        String newCat = sc.nextLine();
        categories.add(new Category(newCat));
        updateMap(categories);
    }

    public static void deleteCategory(ArrayList<Category> categories, Scanner sc) {
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }
        System.out.print("Chọn Category muốn xóa (số): ");
        int delCat = Integer.parseInt(sc.nextLine()) - 1;
        if (delCat >= 0 && delCat < categories.size()) {
            categories.remove(delCat);
            updateMap(categories);
        }
    }

    public static void editCategory(ArrayList<Category> categories, Scanner sc) {
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }
        System.out.print("Chọn Category muốn sửa (số): ");
        int editCat = Integer.parseInt(sc.nextLine()) - 1;
        if (editCat >= 0 && editCat < categories.size()) {
            System.out.print("Tên mới: ");
            categories.get(editCat).setName(sc.nextLine());
            updateMap(categories);
        }
    }

    public static void addProduct(ArrayList<Category> categories, Scanner sc) {
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }
        System.out.print("Chọn Category để thêm Product (số): ");
        int catIndex = Integer.parseInt(sc.nextLine()) - 1;
        if (catIndex >= 0 && catIndex < categories.size()) {
            System.out.print("Tên sản phẩm: ");
            String pname = sc.nextLine();
            System.out.print("Giá: ");
            float price = Float.parseFloat(sc.nextLine());
            System.out.print("Mô tả: ");
            String desc = sc.nextLine();
            System.out.print("Số lượng: ");
            int amount = Integer.parseInt(sc.nextLine());
            System.out.print("Đã bán: ");
            int sold = Integer.parseInt(sc.nextLine());
            categories.get(catIndex).getProducts()
                    .add(new Product(pname, price, desc, amount, sold));
            updateMap(categories);
        }
    }

    public static void deleteProduct(ArrayList<Category> categories, Scanner sc) {
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }
        System.out.print("Chọn Category: ");
        int catDel = Integer.parseInt(sc.nextLine()) - 1;
        if (catDel >= 0 && catDel < categories.size()) {
            ArrayList<Product> prods = categories.get(catDel).getProducts();
            for (int j = 0; j < prods.size(); j++) {
                System.out.println((j + 1) + ". " + prods.get(j));
            }
            System.out.print("Chọn Product muốn xóa (số): ");
            int prodDel = Integer.parseInt(sc.nextLine()) - 1;
            if (prodDel >= 0 && prodDel < prods.size()) {
                prods.remove(prodDel);
                updateMap(categories);
            }
        }
    }

    public static void editProduct(ArrayList<Category> categories, Scanner sc) {
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i).getName());
        }
        System.out.print("Chọn Category: ");
        int catEdit = Integer.parseInt(sc.nextLine()) - 1;
        if (catEdit >= 0 && catEdit < categories.size()) {
            ArrayList<Product> prods = categories.get(catEdit).getProducts();
            for (int j = 0; j < prods.size(); j++) {
                System.out.println((j + 1) + ". " + prods.get(j));
            }
            System.out.print("Chọn Product muốn sửa (số): ");
            int prodEdit = Integer.parseInt(sc.nextLine()) - 1;
            if (prodEdit >= 0 && prodEdit < prods.size()) {
                Product p = prods.get(prodEdit);
                System.out.print("Tên mới (" + p.getName() + "): ");
                p.setName(sc.nextLine());
                System.out.print("Giá mới (" + p.getPrice() + "): ");
                p.setPrice(Float.parseFloat(sc.nextLine()));
                System.out.print("Mô tả mới (" + p.getDescription() + "): ");
                p.setDescription(sc.nextLine());
                System.out.print("Số lượng mới (" + p.getAmount() + "): ");
                p.setAmount(Integer.parseInt(sc.nextLine()));
                System.out.print("Đã bán mới (" + p.getSold() + "): ");
                p.setSold(Integer.parseInt(sc.nextLine()));
                updateMap(categories);
            }
        }
    }
    
    public static void manageDiscounts(Scanner sc) {
        if (products.isEmpty()) {
            System.out.println("Chưa có sản phẩm nào!");
            return;
        }

        List<Product> productList = new ArrayList<>(products.keySet());
        for (int i = 0; i < productList.size(); i++) {
            System.out.println((i + 1) + ". " + productList.get(i).getName());
        }

        System.out.print("Chọn sản phẩm: ");
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        if (idx < 0 || idx >= productList.size()) {
            System.out.println("Sản phẩm không hợp lệ!");
            return;
        }

        Product selected = productList.get(idx);

        while (true) {
            System.out.println("\n--- QUẢN LÝ GIẢM GIÁ CHO: " + selected.getName() + " ---");
            System.out.println("1. Thêm giảm giá");
            System.out.println("2. Xem giảm giá hiện tại");
            System.out.println("3. Xóa tất cả giảm giá");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.println("Chọn loại giảm giá:");
                    System.out.println("1. Giảm theo phần trăm");
                    System.out.println("2. Giảm theo số tiền");
                    int type = Integer.parseInt(sc.nextLine());
                    System.out.print("Nhập giá trị giảm: ");
                    float value = Float.parseFloat(sc.nextLine());

                    discounts.putIfAbsent(selected, new ArrayList<>());
                    if (type == 1) {
                        discounts.get(selected).add(price -> price * (1 - value / 100));
                    } else if (type == 2) {
                        discounts.get(selected).add(price -> Math.max(0, price - value));
                    }
                    System.out.println("Đã thêm giảm giá!");
                }
                case 2 -> {
                    List<DiscountStrategy> ds = discounts.get(selected);
                    if (ds == null || ds.isEmpty()) {
                        System.out.println("Chưa có giảm giá nào.");
                    } else {
                        float finalPrice = getFinalPrice(selected);
                        System.out.println("Giá gốc: " + selected.getPrice());
                        System.out.println("Giá sau giảm: " + finalPrice);
                    }
                }
                case 3 -> {
                    discounts.remove(selected);
                    System.out.println("Đã xóa toàn bộ giảm giá!");
                }
                case 0 -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
            pause(sc);
        }
    }

    public static void manageCart(Scanner sc) {
        while (true) {
            System.out.println("\n--- GIỎ HÀNG ---");
            System.out.println("1. Thêm sản phẩm vào giỏ");
            System.out.println("2. Xem giỏ hàng");
            System.out.println("3. Thanh toán");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> addToCart(sc);
                case 2 -> viewCart();
                case 3 -> checkout(sc);
                case 0 -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    
    private static void pause(Scanner sc) {
        System.out.println("\nNhấn Enter để tiếp tục...");
        sc.nextLine();
    }

    public static void menu(ArrayList<Category> categories) {
        Scanner sc = new Scanner(System.in);
        updateMap(categories);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Xem danh sách sản phẩm");
            System.out.println("2. Thêm Category");
            System.out.println("3. Xóa Category");
            System.out.println("4. Sửa Category");
            System.out.println("5. Thêm Product");
            System.out.println("6. Xóa Product");
            System.out.println("7. Sửa Product");
            System.out.println("8. Quản lý giảm giá");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> viewMenu(sc);
                case 2 -> addCategory(categories, sc);
                case 3 -> deleteCategory(categories, sc);
                case 4 -> editCategory(categories, sc);
                case 5 -> addProduct(categories, sc);
                case 6 -> deleteProduct(categories, sc);
                case 7 -> editProduct(categories, sc);
                case 8 -> manageDiscounts(sc);
                case 0 -> {
                    System.out.println("Thoát chương trình.");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
            pause(sc);
        }
    }
}
