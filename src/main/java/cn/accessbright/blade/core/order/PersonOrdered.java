package cn.accessbright.blade.core.order;

import com.icitic.hrms.core.util.Pair;
import com.icitic.hrms.emp.pojo.bo.PersonBO;

/**
 * ��Ա�������
 * @author Neusoft
 *
 */
class PersonOrdered implements SupplyOrdered {
	private String key;

	public PersonOrdered(String key) {
		this.key = key;
	}

	public String[] getOrders() {
		return new String[] { "deptSort", "sort" };
	}

	public Class getSupplyClass() {
		return PersonBO.class;
	}

	public Pair getRelation() {
		return new Pair(key, "personId");
	}
}