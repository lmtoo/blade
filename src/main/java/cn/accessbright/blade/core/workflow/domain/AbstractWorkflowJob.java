package cn.accessbright.blade.core.workflow.domain;

import org.apache.commons.lang.StringUtils;

import com.icitic.hrms.common.pojo.vo.User;
import com.icitic.hrms.util.Tools;

/**
 * ����Ĺ���������
 * 
 * @author ll
 * 
 */
public abstract class AbstractWorkflowJob implements WorkflowJob {
	protected String jobId; // ����id

	protected String jobName; // ��������

	protected String jobDate;// �����ڼ�

	protected String createDate; // ����ʱ��

	protected String jobDesc; // ����

	protected String status; // ״̬

	protected String createrId; // ������

	protected String creater;// ������

	public String getCreateDate() {
		return this.createDate;
	}

	public String getJobDesc() {
		return this.jobDesc;
	}

	public String getJobId() {
		return this.jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return this.jobName;
	}

	public String getStatus() {
		return this.status;
	}

	public String getJobDate() {
		return this.jobDate;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreaterId() {
		return createrId;
	}

	public String getStatusName() {
		return AbstractWorkflowJob.getStatusName(status);
	}

	/**
	 * ��ȡ����״̬���ƣ����޸ò���������״̬
	 * 
	 * @param status
	 * @return
	 */
	public static String getStatusName(String status) {
		if (STATUS_NEW.equals(status)) {
			return "�½�";
		} else if (STATUS_BACK.equals(status)) {
			return "�˻�";
		} else if (STATUS_FINISH.equals(status)) {
			return "����";
		} else {
			return "δ��������";
		}
	}

	/**
	 * �Ƿ����˻�״̬
	 */
	public boolean isBack() {
		return STATUS_BACK.equals(status);
	}

	/**
	 * �Ƿ��½�����
	 */
	public boolean isNew() {
		return STATUS_NEW.equals(status);
	}

	/**
	 * �Ƿ����
	 */
	public boolean isFinish() {
		return STATUS_FINISH.equals(status);
	}

	/**
	 * ����Ա�Ƿ����ά�������޸�����ɾ������
	 * 
	 * @param userId
	 * @return
	 */
	public boolean isEditable(String userId) {
		return (isNew() || isBack()) && StringUtils.equals(createrId, userId);
	}

	/**
	 * �ж������Ƿ���ĳ��״̬
	 */
	public boolean isInStatus(String status) {
		return StringUtils.equals(this.status, status);
	}

	/**
	 * �½�����
	 * 
	 * @param creater
	 * @param jobDate
	 * @param jobName
	 */
	public static void create(AbstractWorkflowJob job, User creater, String jobDate, String jobName) {
		create(job, creater, jobDate, jobName, null);
	}

	public static void create(AbstractWorkflowJob job, User creater, String jobDate, String jobName, String jobDesc) {
		job.setCreater(creater.getName());
		job.setCreaterId(creater.getUserId());
		job.setCreateDate(Tools.getSysDate("yyyy-MM-dd"));
		job.setJobDate(jobDate);
		job.setJobName(jobName);
		job.setJobDesc(jobDesc);
		job.setStatus(STATUS_NEW);
	}
}