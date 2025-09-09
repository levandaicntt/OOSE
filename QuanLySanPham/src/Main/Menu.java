package Main;

import java.util.ArrayList;
import java.util.List;

import Class.Category;
import Class.Product;
import Menu.MenuApp;

public class Menu {
	public static ArrayList<Category> Data() {
	    ArrayList<Category> temp = new ArrayList<Category>();

	    temp.add(new Category("Tai nghe", new ArrayList<Product>(
	            List.of(
	                new Product("Sony WH-1000XM5", 300, "Chống ồn cao cấp", 5, 2),
	                new Product("AirPods Pro", 250, "Apple, chống ồn", 10, 6),
	                new Product("Razer Kraken", 100, "Gaming headset", 15, 8)
	            )
	    )));

	    temp.add(new Category("Điện thoại", new ArrayList<Product>(
	            List.of(
	                new Product("iPhone 14", 999, "Apple flagship", 20, 12),
	                new Product("Samsung Galaxy S23", 850, "Samsung flagship", 15, 7),
	                new Product("Xiaomi Redmi Note 12", 300, "Giá rẻ cấu hình tốt", 30, 20)
	            )
	    )));

	    temp.add(new Category("Laptop", new ArrayList<Product>(
	            List.of(
	                new Product("Dell XPS 13", 1200, "Laptop văn phòng cao cấp", 8, 4),
	                new Product("Asus ROG", 1500, "Laptop gaming mạnh mẽ", 6, 3),
	                new Product("MacBook Air M2", 1100, "Apple Silicon", 12, 9)
	            )
	    )));

	    temp.add(new Category("Game", new ArrayList<Product>(
	            List.of(
	                new Product("TFT", 0, "Auto chess", 1000, 500),
	                new Product("Umamusume", 0, "Game waifu chạy đua", 500, 200),
	                new Product("Genshin Impact", 0, "Open world RPG", 2000, 1500)
	            )
	    )));

	    temp.add(new Category("Sách", new ArrayList<Product>(
	            List.of(
	                new Product("Clean Code", 40, "Robert C. Martin", 50, 30),
	                new Product("Design Patterns", 55, "Gang of Four", 40, 25),
	                new Product("Java Concurrency in Practice", 60, "Brian Goetz", 35, 15)
	            )
	    )));

	    return temp;
	}
	public static void main(String[] args) {
		ArrayList<Category> categories;
		categories = Data();
		MenuApp.menu(categories); 
	}
}
