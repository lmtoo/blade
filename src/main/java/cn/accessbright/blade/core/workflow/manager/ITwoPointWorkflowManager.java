package cn.accessbright.blade.core.workflow.manager;

import java.util.List;

import com.icitic.hrms.common.exception.HrmsException;

/**
 * �����ڵ����������
 * 
 * @author ll
 * 
 */
public interface ITwoPointWorkflowManager extends IOnePointWorkflowManager {
	/**
	 * ��ѯ���������б�
	 * 
	 * @param year
	 * @param unitId
	 * @return
	 * @throws HrmsException
	 */
	List findCheckJobList(String checkerId) throws HrmsException;

	/**
	 * ���½��ύ����
	 * 
	 * @throws HrmsException
	 */
	void submitToBegin(String checkerId, String jobId) throws HrmsException;

	/**
	 * ���渴��״̬
	 * 
	 * @param jobId
	 * @param checkStatus
	 * @param checkContents
	 * @throws HrmsException
	 */
	void saveJobCheckStatus(String jobId, String checkStatus, String checkContents) throws HrmsException;

	/**
	 * ���˲�ͨ���˻�
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	void checkToBack(String jobId, String checkerContent) throws HrmsException;

	/**
	 * �Ӹ����ύ�����
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	void submitToVerify(String jobId, String checkerContent, String verifierNo) throws HrmsException;
}