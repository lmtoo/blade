package cn.accessbright.blade.core.order;

import com.icitic.hrms.core.util.Pair;
import com.icitic.hrms.wage.pojo.bo.WageUnitBO;

/**
 * н�ʵ�λ�������
 * 
 * @author Neusoft
 * 
 */
class WageUnitOrdered implements SupplyOrdered {
	private String key;

	public WageUnitOrdered(String key) {
		this.key = key;
	}

	public String[] getOrders() {
		return new String[] { "superId", "order+0" };
	}

	public Class getSupplyClass() {
		return WageUnitBO.class;
	}

	public Pair getRelation() {
		return new Pair(key, "unitId");
	}
}
