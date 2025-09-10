package Class;

import Menu.MenuApp;

public class Product {
	String name;
	float price;
	String description;
	int amount;
	int sold;
	
	public Product(String name, float price, String description, int amount, int sold) {
		this.name = name;
		this.price = price;
		this.description = description;
		this.amount = amount;
		this.sold = sold;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getSold() {
		return sold;
	}
	public void setSold(int sold) {
		this.sold = sold;
	}
	public int getStock() {
        return amount - sold;
    }
	@Override
	public String toString() {
	    return name 
	         + " | Giá gốc: " + price
	         + " | " + description
	         + " | Số lượng: " + amount
	         + " | Đã bán: " + sold
	         + " | Tồn kho: " + getStock();
	}

	public String toStringWithDiscount() {
	    float finalPrice = MenuApp.getFinalPrice(this);
	    return name 
	         + " | Giá gốc: " + price 
	         + " | Giá sau giảm: " + finalPrice
	         + " | " + description
	         + " | Số lượng: " + amount
	         + " | Đã bán: " + sold
	         + " | Tồn kho: " + getStock();
	}

}
