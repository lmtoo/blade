package cn.accessbright.blade.core.order;

import com.icitic.hrms.core.util.Pair;

/**
 * ��Ҫ�������������ṩ����
 * 
 * @author Neusoft
 * 
 */
public interface SupplyOrdered extends Ordered {

	/**
	 * �����������
	 * 
	 * @return
	 */
	Class getSupplyClass();

	/**
	 * ��ȡ������ϵ��keyΪ����������valueΪ�����������
	 * 
	 * @return
	 */
	Pair getRelation();
}