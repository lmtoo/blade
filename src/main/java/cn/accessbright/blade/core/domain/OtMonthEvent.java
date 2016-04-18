package cn.accessbright.blade.core.domain;

import com.icitic.hrms.kq.pojo.bo.OtMonthWorkflowJob;

/**
 * ������䣨���񹤣�
 * 
 * @author ll
 * 
 */
public class OtMonthEvent extends TwoPointWorkflowJobEvent {
	public OtMonthEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	protected String moduleName() {
		OtMonthWorkflowJob job = (OtMonthWorkflowJob)getWorkflowJob();
		return job.getTypeName();
	}
}
