package cn.accessbright.blade.core.order;

import java.util.ArrayList;
import java.util.List;

import com.icitic.hrms.core.util.ListArrayUtil;
import com.icitic.hrms.util.Tools;

public class OrderUtils {
	/**
	 * ��ȡ����ʵ��
	 * 
	 * @param orders
	 * @return
	 */
	public static String getInnerJoinEntity(Class mainEntity, Ordered[] orders) {
		String joinEntities = mainEntity.getName() + " " + mainEntity.getSimpleName();

		if (Tools.isEmpty(orders))
			return joinEntities;

		List joins = new ArrayList();
		for (int i = 0; i < orders.length; i++) {
			if (orders[i] instanceof SupplyOrdered) {
				SupplyOrdered order = (SupplyOrdered) orders[i];
				joins.add(order.getSupplyClass().getName() + " " + order.getSupplyClass().getSimpleName());
			}
		}
		if (!Tools.isEmpty(joins)) {
			joinEntities += "," + ListArrayUtil.join(joins, ",");
		}
		return joinEntities;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @param mainEntity
	 * @param orders
	 * @return
	 */
	public static String getInnerJoinConditions(Class mainEntity, Ordered[] orders) {
		if (Tools.isEmpty(orders))
			return null;
		List filters = new ArrayList();
		for (int i = 0; i < orders.length; i++) {
			if (orders[i] instanceof SupplyOrdered) {
				SupplyOrdered order = (SupplyOrdered) orders[i];
				filters.add(mainEntity.getSimpleName() + "." + order.getRelation().getKey() + "=" + order.getSupplyClass().getSimpleName() + "."
						+ order.getRelation().getValue());
			}
		}
		return ListArrayUtil.join(filters, " and ");
	}

	/**
	 * ��ȡ����
	 * 
	 * @param ordereds
	 * @return
	 */
	public static String getOrder(Class mainEntity, Ordered[] ordereds) {
		if (Tools.isEmpty(ordereds))
			return null;
		List orderList = new ArrayList();
		for (int i = 0; i < ordereds.length; i++) {
			Ordered ordered = ordereds[i];
			String simpleEntityName = mainEntity.getSimpleName();
			if (ordered instanceof SupplyOrdered) {
				simpleEntityName = ((SupplyOrdered) ordered).getSupplyClass().getSimpleName();
			}

			String[] orders = ordered.getOrders();
			for (int j = 0; j < orders.length; j++) {
				orderList.add(simpleEntityName + "." + orders[j]);
			}
		}
		return ListArrayUtil.join(orderList, ",");
	}

	/**
	 * ����н�ʻ�������
	 * 
	 * @param key
	 * @return
	 */
	public static Ordered getWageUnitOrdered(String key) {
		return new WageUnitOrdered(key);
	}

	/**
	 * ������Ա����
	 * 
	 * @param key
	 * @return
	 */
	public static Ordered getPersonOrdered(String key) {
		return new PersonOrdered(key);
	}
}