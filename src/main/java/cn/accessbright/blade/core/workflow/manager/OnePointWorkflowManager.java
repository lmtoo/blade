package cn.accessbright.blade.core.workflow.manager;

import java.util.List;

import org.hibernate.Transaction;

import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.common.dao.GenericDAO;
import com.icitic.hrms.common.exception.DAOException;
import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.common.manager.GenericManager;
import com.icitic.hrms.core.event.DefaultEventMulticaster;
import com.icitic.hrms.core.event.EventPublisher;
import com.icitic.hrms.core.event.HrEvent;
import com.icitic.hrms.core.workflow.domain.OnePointWorkflowJob;
import com.icitic.hrms.core.workflow.domain.WorkflowJob;
import com.icitic.hrms.core.workflow.listener.WorkflowStateChangedListener;
import com.icitic.hrms.emp.pojo.bo.PersonBO;

/**
 * ���ڵ������ύ����������<br>
 * ��������ѯ�������񡢱��桢�����˻ء�������ɣ�
 * 
 * @author ll
 * 
 */
public class OnePointWorkflowManager extends GenericManager implements IOnePointWorkflowManager {
	protected EventPublisher publisher = new DefaultEventMulticaster();
	protected WorkflowInfoProvider inforProvider;
	protected WorkflowStateChangedListener stateChangeListener;
	protected GenericDAO dao;

	public OnePointWorkflowManager() throws HrmsException {
		this.dao = new GenericDAO(s);
	}

	public OnePointWorkflowManager(WorkflowInfoProvider inforProvider) throws HrmsException {
		this();
		this.inforProvider = inforProvider;
	}

	public OnePointWorkflowManager(WorkflowInfoProvider inforProvider, WorkflowStateChangedListener stateChangeListener) throws HrmsException {
		this(inforProvider);
		this.stateChangeListener = stateChangeListener;
	}

	/**
	 * �������¼�
	 * 
	 * @param id
	 * @param source
	 * @param message
	 * @return
	 */
	public HrEvent newEvent(String id, Object source, Object message) {
		return inforProvider.newEvent(id, source, message);
	}

	/**
	 * ��ѯ��������б�
	 * 
	 * @param year
	 * @param unitId
	 * @return
	 * @throws HrmsException
	 */
	public List findVerifyJobList(String verifierId) throws HrmsException {
		try {
			String hql = "from " + inforProvider.getJobClass().getName() + " b where b.verifierId = '" + verifierId + "' order by b.jobDate desc,b.createDate desc";
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
	public void submitToBegin(String verifierId, String jobId) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			PersonBO verifier = SysCacheTool.findPersonById(verifierId);
			OnePointWorkflowJob job = (OnePointWorkflowJob) inforProvider.findJob(jobId);
			job.submitToBegin(verifier);
			dao.updateBo(job);
			afterBegin(job);
			super.commitTransaction(tx); // �����ύ
			publisher.publishEvent(newEvent(job.getVerifierNo(), job, "��Ҫ����"));
		} catch (DAOException e) {
			throw new HrmsException("����Id�ύ" + inforProvider.getModuleName() + "����manager�����", e, this.getClass());
		}
	}

	/**
	 * ���ӷ����������ύ�����˺�
	 * 
	 * @param job
	 * @throws HrmsException 
	 */
	protected void afterBegin(OnePointWorkflowJob job) throws HrmsException {
		if(stateChangeListener!=null)
		stateChangeListener.onChanged(job);
	}

	/**
	 * ��������״̬
	 * 
	 * @param jobId
	 * @param checkStatus
	 * @param checkContents
	 * @throws HrmsException
	 */
	public void saveJobVerifyStatus(String jobId, String verifyStatus, String verifyContents) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			OnePointWorkflowJob job = (OnePointWorkflowJob) inforProvider.findJob(jobId);
			job.saveVerifyStatus(verifyStatus, verifyContents);
			dao.updateBo(job);
			super.commitTransaction(tx); // �����ύ
		} catch (DAOException e) {
			throw new HrmsException("����Id����" + inforProvider.getModuleName() + "����״̬manager�����", e, this.getClass());
		}
	}

	/**
	 * ��˲�ͨ���˻�
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	public void verifyToBack(String jobId, String verifyContent) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			OnePointWorkflowJob job = (OnePointWorkflowJob) inforProvider.findJob(jobId);
			job.verifyToBack(verifyContent);
			dao.updateBo(job);
			afterVerifyBack(job);
			super.commitTransaction(tx); // �����ύ
			publisher.publishEvent(newEvent(job.getCreaterId(), job, "�����˻�"));
		} catch (DAOException e) {
			throw new HrmsException("����Id�˻�" + inforProvider.getModuleName() + "����manager�����", e, this.getClass());
		}
	}

	/**
	 * ���ӷ�������������˻غ�
	 * 
	 * @param job
	 * @throws HrmsException 
	 */
	protected void afterVerifyBack(OnePointWorkflowJob job) throws HrmsException {
		if(stateChangeListener!=null)
		stateChangeListener.onChanged(job);
	}

	/**
	 * �ύ�����ͨ��
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	public void submitToFinish(final String jobId, final String verifyContent) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			OnePointWorkflowJob job = (OnePointWorkflowJob) inforProvider.findJob(jobId);
			job.submitToFinish(verifyContent);
			dao.updateBo(job);
			afterFinished(job);
			super.commitTransaction(tx); // �����ύ
			publisher.publishEvent(newEvent(job.getCreaterId(), job, "����ͨ��"));
		} catch (DAOException e) {
			throw new HrmsException("����Id�ύ" + inforProvider.getModuleName() + "����manager�����", e, this.getClass());
		}
	}

	/**
	 * ���ӷ�����������ɺ��Ӽ������ȹ���
	 * 
	 * @param job
	 */
	protected void afterFinished(OnePointWorkflowJob job) throws HrmsException {
		if(stateChangeListener!=null)
		stateChangeListener.onChanged(job);
	}

	public String getModuleName() {
		return inforProvider.getModuleName();
	}

	public Class getJobClass() {
		return inforProvider.getJobClass();
	}

	public WorkflowJob findJob(String jobId) throws HrmsException {
		return inforProvider.findJob(jobId);
	}
}