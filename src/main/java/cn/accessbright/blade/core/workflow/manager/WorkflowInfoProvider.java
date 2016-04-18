package cn.accessbright.blade.core.workflow.manager;

import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.core.event.HrEvent;
import com.icitic.hrms.core.workflow.domain.WorkflowJob;

public interface WorkflowInfoProvider {
	/**
	 * ��ȡģ������
	 * 
	 * @return
	 */
	String getModuleName();

	/**
	 * ��ȡ����������������
	 * 
	 * @return
	 */
	Class getJobClass();

	/**
	 * ��������id��ѯ����
	 * 
	 * @param jobId
	 * @return
	 * @throws HrmsException
	 */
	WorkflowJob findJob(String jobId) throws HrmsException;
	
	/**
	 * �������¼�
	 * 
	 * @param id
	 * @param source
	 * @param message
	 * @return
	 */
	HrEvent newEvent(String id, Object source, Object message) ;
}
