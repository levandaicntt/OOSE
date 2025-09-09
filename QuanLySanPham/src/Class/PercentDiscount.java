package Class;

import Interface.DiscountStrategy;

public class PercentDiscount implements DiscountStrategy {
	private float percent;

	public PercentDiscount(float percent) {
		super();
		this.percent = percent;
	}

	@Override
	public float applyDiscount(float originalPrice) {
		return Math.max(0, originalPrice * (1 - percent / 100));
	}
}
