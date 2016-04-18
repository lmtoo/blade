package cn.accessbright.blade.core.workflow.manager;

import java.util.List;

import com.icitic.hrms.common.exception.HrmsException;

/**
 * ���ڵ���������
 * 
 * @author ll
 * 
 */
public interface IOnePointWorkflowManager extends WorkflowInfoProvider{
	/**
	 * ��ѯ��������б�
	 * 
	 * @param year
	 * @param unitId
	 * @return
	 * @throws HrmsException
	 */
	List findVerifyJobList(String verifierId) throws HrmsException;

	/**
	 * ���½��ύ����
	 * 
	 * @throws HrmsException
	 */
	void submitToBegin(String verifierId, String jobId) throws HrmsException;

	/**
	 * ��������״̬
	 * 
	 * @param jobId
	 * @param checkStatus
	 * @param checkContents
	 * @throws HrmsException
	 */
	void saveJobVerifyStatus(String jobId, String verifyStatus, String verifyContents) throws HrmsException;

	/**
	 * ��˲�ͨ���˻�
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	void verifyToBack(String jobId, String verifyContent) throws HrmsException;

	/**
	 * �ύ�����ͨ��
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	void submitToFinish(final String jobId, final String verifyContent) throws HrmsException;
}