package Class;

import Interface.DiscountStrategy;

public class FixedDiscount implements DiscountStrategy {
	private float amount;
	
	public FixedDiscount(float amount) {
		this.amount = amount;
	}

	@Override
	public float applyDiscount(float originalPrice) {
		return Math.max(0, originalPrice - amount);
	}
}
