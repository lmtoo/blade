package cn.accessbright.blade.core.workflow.manager;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.icitic.hrms.common.dao.GenericDAO;
import com.icitic.hrms.common.exception.DAOException;
import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.common.manager.GenericManager;
import com.icitic.hrms.core.event.DefaultEventMulticaster;
import com.icitic.hrms.core.event.EventPublisher;
import com.icitic.hrms.core.event.HrEvent;
import com.icitic.hrms.core.workflow.domain.WorkflowJob;
import com.icitic.hrms.util.Tools;

/**
 * ����������Manager
 * 
 * @author ll
 * 
 */
public abstract class WorkflowManager extends GenericManager implements WorkflowInfoProvider{
	protected EventPublisher publisher = new DefaultEventMulticaster();
	protected IOnePointWorkflowManager pointManager;
	protected GenericDAO dao;

	public WorkflowManager(Session s) {
		super(s);
		dao = new GenericDAO(s);
	}
	

	public WorkflowManager() throws HrmsException {
		dao = new GenericDAO(s);
		pointManager = createPointManager();
	}
	
	protected abstract IOnePointWorkflowManager createPointManager() throws HrmsException;

	/**
	 * �������¼�
	 * 
	 * @param id
	 * @param source
	 * @param message
	 * @return
	 */
	public HrEvent newEvent(String id, Object source, Object message) {
		return null;
	}

	/**
	 * ��ȡģ������
	 * 
	 * @return
	 */
	public abstract String getModuleName();
	
	
	public IOnePointWorkflowManager getPointWorkflowManager() {
		return pointManager;
	}

	// ====================================================���������񣨲�ѯ�����ύ���ˣ�===================================================

	/**
	 * ��������id��ѯ����
	 * 
	 * @param jobId
	 * @return
	 * @throws HrmsException
	 */
	public WorkflowJob findJob(String jobId) throws HrmsException {
		try {
			return (WorkflowJob) dao.findBoById(getJobClass(), jobId);
		} catch (DAOException e) {
			throw new HrmsException("��ѯĿ��ʧ��", e, this.getClass());
		}
	}

	/**
	 * ��ѯ�����б�
	 * 
	 * @param creater
	 * @param beginJobDate
	 * @param endJobDate
	 * @return
	 * @throws HrmsException
	 */
	public List queryJobList(String createrId, String beginJobDate, String endJobDate) throws HrmsException {
		try {
			String hql = "from " + getJobClass().getName() + " j where j.createrId='" + createrId + "' ";

			if (!Tools.isEmpty(beginJobDate)) {
				hql += " and j.jobDate>='" + beginJobDate + "'";
			}
			if (!Tools.isEmpty(endJobDate)) {
				hql += " and j.jobDate<='" + endJobDate + "'";
			}
			hql += " order by j.jobDate desc,j.jobName desc ";
			return dao.queryByHql(hql);
		} catch (DAOException e) {
			throw new HrmsException("��ѯĿ��ʧ��", e, this.getClass());
		}
	}

	/**
	 * �������
	 * 
	 * @param detail
	 * @return
	 * @throws HrmsException
	 */
	public String addJob(WorkflowJob job) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			beforeAddJob(job);
			String id = dao.createBo(job);
			afterJobAdd(job);
			super.commitTransaction(tx); // �����ύ
			return id;
		} catch (HrmsException e) {
			super.rollbackTransaction(tx);
			throw e;
		} catch (Exception e) {
			super.rollbackTransaction(tx);
			throw new HrmsException("����Id����" + getModuleName() + "����manager�����", e, this.getClass());
		}
	}
	
	/**
	 * ��������֮ǰ��ִ�������Ƿ���ڵļ��
	 * 
	 * @param job
	 * @throws HrmsException
	 */
	protected void beforeAddJob(WorkflowJob job) throws HrmsException {
		if (isJobExist(job))
			throw new HrmsException("�Ѵ��ڴ�����", this.getClass());
	}

	/**
	 * �ж������Ƿ��Ѿ�����
	 * 
	 * @param job
	 * @return
	 * @throws HrmsException
	 */
	protected boolean isJobExist(WorkflowJob job) throws HrmsException {
		String hql = "select count(j.jobId) from " + getJobClass().getName() + " j where j.createrId='" + job.getCreaterId() + "' and j.jobDate='"+ job.getJobDate() + "'";
		Integer count=(Integer) dao.findOne(hql);
		return count!=null&&count.intValue()>0;
	}

	/**
	 * ���ӷ����������񴴽�֮��ִ��
	 * 
	 * @param jobId
	 */
	protected void afterJobAdd(WorkflowJob job) throws HrmsException {
	}


	/**
	 * ɾ��������ϸ
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	public void deleteJob(String jobId) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			WorkflowJob job = findJob(jobId);
			// ��ɾ������
			dao.deletePo(job);
			afterJobDelete(job);
			super.commitTransaction(tx); // �����ύ
		} catch (DAOException e) {
			super.rollbackTransaction(tx);
			throw new HrmsException("����jobIdɾ��" + getModuleName() + "manager�����", e, this.getClass());
		}
	}

	protected void afterJobDelete(WorkflowJob job) throws HrmsException {
	}
}