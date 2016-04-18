package cn.accessbright.blade.core.domain;

/**
 * ������䣨���񹤣�
 * 
 * @author ll
 * 
 */
public class RewardAllotEvent extends TwoPointWorkflowJobEvent {
	public RewardAllotEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	protected String moduleName() {
		return "�������루���񹤣�����";
	}
}
