package cn.accessbright.blade.core.order;

/**
 * ������
 * 
 * @author Neusoft
 * 
 */
public class SelfOrder implements Ordered {
	private String[] orders;

	public SelfOrder(String[] orders) {
		this.orders = orders;
	}

	public SelfOrder(String order) {
		this.orders = new String[] { order };
	}

	public String[] getOrders() {
		return orders;
	}
}