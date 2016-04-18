package cn.accessbright.blade.core.workflow.manager;

import java.util.List;

import org.hibernate.Transaction;

import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.common.exception.DAOException;
import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.core.workflow.domain.TwoPointWorkflowJob;
import com.icitic.hrms.core.workflow.listener.WorkflowStateChangedListener;
import com.icitic.hrms.emp.pojo.bo.PersonBO;

/**
 * ˫�ڵ������ύ����������<br>
 * ���ˣ���ѯ�������񡢱��桢�����˻ء��ύ����ˣ�
 * 
 * @author ll
 * 
 */
public class TwoPointWorkflowManager extends OnePointWorkflowManager implements ITwoPointWorkflowManager {
	public TwoPointWorkflowManager() throws HrmsException {
		super();
	}

	public TwoPointWorkflowManager(WorkflowInfoProvider inforProvider) throws HrmsException {
		super(inforProvider);
	}

	public TwoPointWorkflowManager(WorkflowInfoProvider inforProvider, WorkflowStateChangedListener stateChangeListener) throws HrmsException {
		super(inforProvider, stateChangeListener);
	}
	// ==============================���ˣ���ѯ�������񡢱��桢�����˻ء��ύ����ˣ�=================================

	/**
	 * ��ѯ���������б�
	 * 
	 * @param year
	 * @param unitId
	 * @return
	 * @throws HrmsException
	 */
	public List findCheckJobList(String checkerId) throws HrmsException {
		try {
			String hql = "from " + inforProvider.getJobClass().getName() + " b where b.checkerId = '" + checkerId + "' order by b.jobDate desc";
			return dao.queryByHql(hql);
		} catch (Exception e) {
			throw new HrmsException("����Ŀ�꿨ʧ��", e, this.getClass());
		}
	}

	/**
	 * ���½��ύ����
	 * 
	 * @throws HrmsException
	 */
	public void submitToBegin(String checkerId, String jobId) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			PersonBO checker = SysCacheTool.findPersonById(checkerId);
			TwoPointWorkflowJob job = (TwoPointWorkflowJob) inforProvider.findJob(jobId);
			job.submitToBegin(checker);
			dao.updateBo(job);
			afterBegin(job);
			super.commitTransaction(tx); // �����ύ
			publisher.publishEvent(newEvent(job.getCheckerNo(), job, "��Ҫ����"));
		} catch (DAOException e) {
			throw new HrmsException("����Id�ύ" + inforProvider.getModuleName() + "����manager�����", e, this.getClass());
		}
	}

	/**
	 * ���渴��״̬
	 * 
	 * @param jobId
	 * @param checkStatus
	 * @param checkContents
	 * @throws HrmsException
	 */
	public void saveJobCheckStatus(String jobId, String checkStatus, String checkContents) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			TwoPointWorkflowJob job = (TwoPointWorkflowJob) inforProvider.findJob(jobId);
			job.saveCheckStatus(checkStatus, checkContents);
			dao.updateBo(job);
			super.commitTransaction(tx); // �����ύ
		} catch (DAOException e) {
			throw new HrmsException("����Id����" + inforProvider.getModuleName() + "����״̬manager�����", e, this.getClass());
		}
	}

	/**
	 * ���˲�ͨ���˻�
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	public void checkToBack(String jobId, String checkerContent) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			TwoPointWorkflowJob job = (TwoPointWorkflowJob) inforProvider.findJob(jobId);
			job.checkToBack(checkerContent);
			dao.updateBo(job);
			afterCheckBack(job);
			super.commitTransaction(tx); // �����ύ
			publisher.publishEvent(newEvent(job.getCreaterId(), job, "�����˻�"));
		} catch (DAOException e) {
			throw new HrmsException("����Id�˻�" + inforProvider.getModuleName() + "����manager�����", e, this.getClass());
		}
	}

	/**
	 * ���ӷ��������񸴺��˻غ�
	 * 
	 * @param job
	 * @throws HrmsException 
	 */
	protected void afterCheckBack(TwoPointWorkflowJob job) throws HrmsException {
		if(stateChangeListener!=null)
		stateChangeListener.onChanged(job);
	}

	/**
	 * �Ӹ����ύ�����
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	public void submitToVerify(String jobId, String checkerContent, String verifierNo) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			TwoPointWorkflowJob job = (TwoPointWorkflowJob) inforProvider.findJob(jobId);
			PersonBO verifier = SysCacheTool.findPersonById(verifierNo);
			job.submitToVerify(verifier, checkerContent);
			dao.updateBo(job);
			afterSubmitToVerify(job);
			super.commitTransaction(tx); // �����ύ
			publisher.publishEvent(newEvent(job.getVerifierNo(), job, "��Ҫ����"));
		} catch (DAOException e) {
			throw new HrmsException("����Id�ύ" + inforProvider.getModuleName() + "���manager�����", e, this.getClass());
		}
	}

	/**
	 * ���ӷ����������ύ����˺�
	 * 
	 * @param job
	 * @throws HrmsException 
	 */
	protected void afterSubmitToVerify(TwoPointWorkflowJob job) throws HrmsException {
		if(stateChangeListener!=null)
		stateChangeListener.onChanged(job);
	}
}