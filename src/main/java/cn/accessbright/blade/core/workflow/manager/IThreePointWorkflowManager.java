package cn.accessbright.blade.core.workflow.manager;

import java.util.List;

import com.icitic.hrms.common.exception.HrmsException;

/**
 * �����ڵ����������
 * 
 * @author ll
 * 
 */
public interface IThreePointWorkflowManager extends ITwoPointWorkflowManager {
	/**
	 * ��ѯ�����������б�
	 * 
	 * @param year
	 * @param unitId
	 * @return
	 * @throws HrmsException
	 */
	List findFinanceJobList(String financerId) throws HrmsException;

	/**
	 * ���������״̬
	 * 
	 * @param jobId
	 * @param checkStatus
	 * @param checkContents
	 * @throws HrmsException
	 */
	void saveJobFinanceStatus(String jobId, String financeStatus, String financeContent) throws HrmsException;

	/**
	 * ������ͨ���˻�
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	void financeToBack(String jobId, String financeContent) throws HrmsException;

	/**
	 * ������ύ��������
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	void submitToFinance(String jobId, String verifieContent, String financerNo) throws HrmsException;

	/**
	 * �ύ�����״̬
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	void submitToFinish(final String jobId, final String financeContent) throws HrmsException;
}