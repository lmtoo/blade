package cn.accessbright.blade.core.domain;

import com.icitic.hrms.core.domain.OnePointWorkflowJobEvent;

/**
 * ���ʶ����������
 * 
 * @author ll
 * 
 */
public class LimitSalarySettingEvent extends OnePointWorkflowJobEvent {
	public LimitSalarySettingEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	protected String moduleName() {
		return "���ʶ����������";
	}


}
