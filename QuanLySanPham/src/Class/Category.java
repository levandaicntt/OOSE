package Class;
import java.util.ArrayList;

public class Category {
	String name;
	ArrayList<Product> products;
	public Category(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }
	public Category(String name, ArrayList<Product> products) {
		super();
		this.name = name;
		this.products = products;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
}
