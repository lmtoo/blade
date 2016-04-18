package cn.accessbright.blade.core.workflow.manager;

import java.util.List;

import org.hibernate.Transaction;

import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.common.exception.DAOException;
import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.core.workflow.domain.OnePointWorkflowJob;
import com.icitic.hrms.core.workflow.domain.ThreePointWorkflowJob;
import com.icitic.hrms.core.workflow.listener.WorkflowStateChangedListener;
import com.icitic.hrms.emp.pojo.bo.PersonBO;

/**
 * ���ڵ������ύ����������<br>
 * ���ˣ���ѯ�������񡢱��桢�����˻ء��ύ����ˣ�
 * 
 * @author ll
 * 
 */
public class ThreePointWorkflowManager extends TwoPointWorkflowManager implements IThreePointWorkflowManager {
	public ThreePointWorkflowManager() throws HrmsException {
		super();
	}

	public ThreePointWorkflowManager(WorkflowInfoProvider inforProvider) throws HrmsException {
		super(inforProvider);
	}

	public ThreePointWorkflowManager(WorkflowInfoProvider inforProvider, WorkflowStateChangedListener stateChangeListener) throws HrmsException {
		super(inforProvider, stateChangeListener);
	}
	
	// ==============================��������ѯ���������񡢱��桢�������˻ء��ύ����ɣ�=================================

	/**
	 * ��ѯ���д��ڲ�����״̬������
	 */
	public List findFinanceJobList(String financerId) throws HrmsException {
		try {
			String hql = "from " + inforProvider.getJobClass().getName() + " b where b.financerId = '" + financerId + "' order by b.jobDate desc";
			return dao.queryByHql(hql);
		} catch (Exception e) {
			throw new HrmsException("����Ŀ�꿨ʧ��", e, this.getClass());
		}
	}

	/**
	 * ���������״̬
	 */
	public void saveJobFinanceStatus(String jobId, String financeStatus, String financeContent) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			ThreePointWorkflowJob job = (ThreePointWorkflowJob) inforProvider.findJob(jobId);
			job.saveFinanceStatus(financeStatus, financeContent);
			dao.updateBo(job);
			super.commitTransaction(tx); // �����ύ
		} catch (DAOException e) {
			throw new HrmsException("����Id����" + inforProvider.getModuleName() + "������״̬manager�����", e, this.getClass());
		}
	}

	/**
	 * ������ͨ�����˻�
	 */
	public void financeToBack(String jobId, String financeContent) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			ThreePointWorkflowJob job = (ThreePointWorkflowJob) inforProvider.findJob(jobId);
			job.financeToBack(financeContent);
			dao.updateBo(job);
			afterFinanceBack(job);
			super.commitTransaction(tx); // �����ύ
			publisher.publishEvent(newEvent(job.getCreaterId(), job, "�����˻�"));
		} catch (DAOException e) {
			throw new HrmsException("����Id�˻�" + inforProvider.getModuleName() + "����manager�����", e, this.getClass());
		}
	}

	/**
	 * ���ӷ���������������˻غ�
	 * 
	 * @param job
	 * @throws HrmsException 
	 */
	protected void afterFinanceBack(ThreePointWorkflowJob job) throws HrmsException {
		if(stateChangeListener!=null)
		stateChangeListener.onChanged(job);
	}

	/**
	 * ������״̬���ύ��������״̬
	 * 
	 */
	public void submitToFinance(String jobId, String verifieContent, String financerId) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			ThreePointWorkflowJob job = (ThreePointWorkflowJob) inforProvider.findJob(jobId);
			PersonBO financer = SysCacheTool.findPersonById(financerId);
			job.submitToFinance(financer, verifieContent);
			dao.updateBo(job);
			afterSubmitToFinance(job);
			super.commitTransaction(tx); // �����ύ
			publisher.publishEvent(newEvent(job.getVerifierNo(), job, "��Ҫ����"));
		} catch (DAOException e) {
			throw new HrmsException("����Id�ύ" + inforProvider.getModuleName() + "���manager�����", e, this.getClass());
		}
	}
	

	/**
	 * ���ӷ����������ύ���������
	 * 
	 * @param job
	 * @throws HrmsException 
	 */
	protected void afterSubmitToFinance(ThreePointWorkflowJob job) throws HrmsException {
		if(stateChangeListener!=null)
		stateChangeListener.onChanged(job);
	}
	
	/**
	 * �����ύ�����״̬
	 * 
	 */
	public void submitToFinish(String jobId, String financeContent) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			OnePointWorkflowJob job = (OnePointWorkflowJob) inforProvider.findJob(jobId);
			job.submitToFinish(financeContent);
			dao.updateBo(job);
			afterFinished(job);
			super.commitTransaction(tx); // �����ύ
			publisher.publishEvent(newEvent(job.getCreaterId(), job, "������ͨ��"));
		} catch (DAOException e) {
			throw new HrmsException("����Id�ύ" + inforProvider.getModuleName() + "������manager�����", e, this.getClass());
		}
	}
}