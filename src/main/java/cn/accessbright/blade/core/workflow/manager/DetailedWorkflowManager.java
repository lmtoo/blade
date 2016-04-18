package cn.accessbright.blade.core.workflow.manager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Transaction;

import com.icitic.hrms.common.exception.DAOException;
import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.core.order.OrderUtils;
import com.icitic.hrms.core.order.Ordered;
import com.icitic.hrms.core.util.ListArrayUtil;
import com.icitic.hrms.core.workflow.domain.WorkflowJob;
import com.icitic.hrms.core.workflow.domain.WorkflowJobDetail;
import com.icitic.hrms.util.Tools;

/**
 * ����ϸ�Ĺ�������
 * 
 * @author ll
 * 
 */
public abstract class DetailedWorkflowManager extends WorkflowManager {
	public DetailedWorkflowManager() throws HrmsException {
	}

	/**
	 * ��ȡ������ϸ��
	 * 
	 * @return
	 */
	protected abstract Class getJobDetailClass();

	// ================================������ϸά���������������������������£�==================================

	/**
	 * ���������ϸ
	 * 
	 * @param detail
	 * @return
	 * @throws HrmsException
	 */
	public String addJobDetail(WorkflowJobDetail detail) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			if (isDetailExist(detail)) {
				throw new HrmsException("�Ѵ��ڴ�������ϸ��", this.getClass());
			}
			String id = dao.createBo(detail);
			super.commitTransaction(tx); // �����ύ
			return id;
		} catch (DAOException e) {
			super.rollbackTransaction(tx);
			throw new HrmsException("����Id����" + getModuleName() + "��ϸmanager�����", e, this.getClass());
		}
	}

	/**
	 * �ж��Ƿ��Ѵ���ĳ����ϸ
	 * 
	 * @param jobId
	 * @param personId
	 * @return
	 */
	public abstract boolean isDetailExist(WorkflowJobDetail detail);

	/**
	 * ����������Ա������ϸ
	 * 
	 * @param details
	 * @throws HrmsException
	 */
	public void batchAddDetail(List details) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			for (int i = 0; i < details.size(); i++) {
				WorkflowJobDetail detail = (WorkflowJobDetail) details.get(i);
				if (isDetailExist(detail))
					throw new HrmsException("��������" + getModuleName() + "��ϸ���Ѵ��ڵ���ϸ", this.getClass());
				dao.createBo(detail);
			}
			super.commitTransaction(tx); // �����ύ
		} catch (DAOException e) {
			super.rollbackTransaction(tx);
			throw new HrmsException("��������" + getModuleName() + "��ϸmanager�����", e, this.getClass());
		}
	}

	/**
	 * ����������ϸ
	 * 
	 * @param params
	 * @throws HrmsException
	 */
	public void batchUpdateDetails(List details) throws HrmsException {
		batchExecute(getJobDetailUpdateSql(), details);
	}

	/**
	 * ����������ϸ
	 * 
	 * @param details
	 * @throws HrmsException
	 */
	public void batchImportDetails(List details) throws HrmsException {
		batchExecute(getJobDetailImportSql(), details);
	}

	private void batchExecute(String[] batchSql, List details) throws HrmsException {
		if (!Tools.isEmpty(details)) {
			Transaction tx = null;
			try {
				tx = super.beginTransaction();// ������
				for (int i = 0; i < batchSql.length; i++) {
					String[] sqls = new String[details.size()];
					for (int j = 0; j < details.size(); j++) {
						String[] params = Tools.filterNullToStr((String[]) details.get(j));
						sqls[j] = MessageFormat.format(batchSql[i], params);
					}
					dao.batchExecuteSql(sqls);
				}
				super.commitTransaction(tx); // �����ύ
			} catch (DAOException e) {
				super.rollbackTransaction(tx);
				throw new HrmsException("����" + getModuleName() + "��ϸmanager�����", e, this.getClass());
			}
		}
	}

	/**
	 * ��ȡ������ϸsql���<br>
	 * Ĭ�ϵ�����ϸ����������һ��
	 * 
	 * @return
	 */
	protected String[] getJobDetailImportSql() {
		return getJobDetailUpdateSql();
	}

	/**
	 * ��ȡ������ϸsql���
	 * 
	 * @return
	 */
	protected abstract String[] getJobDetailUpdateSql();
	
	public abstract String[] getJobDetailUpdateParamNames();

	// ===========================================������ϸ����ѯ��ɾ����=========================================

	/**
	 * ��ѯ������ϸ
	 * 
	 * @param jobId
	 * @return
	 * @throws HrmsException
	 */
	public List queryDetailByJobId(String jobId) throws HrmsException {
		return queryJobDetails(null, jobId);
	}

	/**
	 * ��ѯ��ϸ
	 * 
	 * @param ids
	 * @return
	 * @throws HrmsException
	 */
	public List queryDetailByIds(String[] ids) throws HrmsException {
		return queryJobDetails(ids, null);
	}

	private List queryJobDetails(String[] ids, String jobId) throws HrmsException {
		List filters = new ArrayList();
		if (!Tools.isEmpty(jobId)) {
			filters.add("jobId='" + jobId + "'");
		}
		if (!Tools.isEmpty(ids)) {
			filters.add("id in('" + ListArrayUtil.join(ids, "','") + "')");
		}
		return queryDetailByConditions(filters);
	}

	protected final List queryDetailByConditions(List filters) throws HrmsException {
		try {
			String hql = "select "+getJobDetailClass().getSimpleName()+" from " + OrderUtils.getInnerJoinEntity(getJobDetailClass(), getOrderedList());
			String where = OrderUtils.getInnerJoinConditions(getJobDetailClass(), getOrderedList());
			String order = OrderUtils.getOrder(getJobDetailClass(), getOrderedList());
			
			List conditions=new ArrayList();
			for (int i = 0; i < filters.size(); i++) {
				conditions.add(getJobDetailClass().getSimpleName()+"."+filters.get(i));
			}
			
			if (!Tools.isEmpty(where)) {
				conditions.add(where);
			}
			if (!conditions.isEmpty()) {
				hql += " where " + ListArrayUtil.join(conditions.iterator(), " and ");
			}

			if (!Tools.isEmpty(order)) {
				hql += " order by " + order;
			}
			return dao.queryByHql(hql);
		} catch (DAOException e) {
			throw new HrmsException("��ѯĿ��ʧ��", e, this.getClass());
		}
	}

	/**
	 * ����ϸ�ж����������ʱ�����Ǵ˷���
	 * 
	 * @return
	 */
	protected Ordered[] getOrderedList() {
		Ordered ordered=getSingleOrdered();
		return ordered==null?null:new Ordered[]{ordered};
	}
	
	/**
	 * ����ϸֻ��һ����������ʱ�����Ǵ˷������Ҳ�Ҫ����getOrderedList
	 * @return
	 */
	protected Ordered getSingleOrdered() {
		return null;
	}

	/**
	 * ����idɾ����ϸ
	 * 
	 * @param ids
	 * @throws HrmsException
	 */
	public void deleteDetailByIds(String[] ids) throws HrmsException {
		deleteDetails(ids, null);
	}

	/**
	 * ��������idɾ��
	 * 
	 * @param jobId
	 * @throws HrmsException
	 */
	protected void afterJobDelete(WorkflowJob job) throws HrmsException {
		deleteDetails(null, job.getJobId());
	}

	private void deleteDetails(String[] ids, String jobId) throws HrmsException {
		Transaction tx = null;
		try {
			tx = super.beginTransaction();// ������
			dao.deleteAll(queryJobDetails(ids, jobId));
			super.commitTransaction(tx); // �����ύ
		} catch (DAOException e) {
			super.rollbackTransaction(tx);
			throw new HrmsException("����Idɾ��" + getModuleName() + "��ϸmanager�����", e, this.getClass());
		}
	}

	// ========================================��������=========================================
	/**
	 * ��ȡ����һ������
	 * 
	 * @param deptId
	 * @return
	 */
	protected String getFirstLevelDept(String deptId) {
		String sql = "SELECT orguid FROM (SELECT * FROM B001 B START WITH B.ORGUID = '" + deptId
				+ "' CONNECT BY PRIOR B.B001002 = B.ORGUID) WHERE B001002 = '660'";
		return dao.queryForString(sql);
	}

	/**
	 * ��ѯĳ�������е����Ȼ���
	 * 
	 * @param deptId
	 * @return
	 */
	protected List findSuperDeptIds(String deptId) {
		String sql = "SELECT orguid FROM B001 B START WITH B.ORGUID = '" + deptId + "' CONNECT BY PRIOR B.B001002 = B.ORGUID";
		return dao.queryForList(sql, "orguid");
	}
}