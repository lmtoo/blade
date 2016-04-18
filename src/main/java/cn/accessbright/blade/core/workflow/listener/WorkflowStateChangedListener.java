package cn.accessbright.blade.core.workflow.listener;

import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.core.workflow.domain.WorkflowJob;

/**
 * ������״̬������
 * 
 * @author ll
 *
 */
public interface WorkflowStateChangedListener {
	
	/**
	 * ������״̬�ı�ʱ��������
	 * 
	 * @param job ����������
	 * @throws HrmsException
	 */
	void onChanged(WorkflowJob job)throws HrmsException;
}