package cn.accessbright.blade.core.workflow.domain;

/**
 * ����������
 * 
 * @author ll
 * 
 */
public interface WorkflowJob {

	String STATUS_NEW = "0"; // �½�

	String STATUS_BACK = "100"; // �˻�

	String STATUS_FINISH = "101"; // ����

	String PROCESS_STATUS_PASS = "1";// ͨ��

	String PROCESS_STATUS_UNPASS = "0";// �˻�

	String getJobId();

	String getJobName();

	String getJobDate();

	String getStatus();

	String getCreater();

	String getCreaterId();

	String getCreateDate();

	String getJobDesc();

	String getStatusName();

	/**
	 * �Ƿ����½�����
	 * 
	 * @return
	 */
	boolean isNew();

	/**
	 * �����Ƿ��˻�
	 * 
	 * @return
	 */
	boolean isBack();

	/**
	 * �����Ƿ����
	 * 
	 * @return
	 */
	boolean isFinish();

	/**
	 * �жϸ������Ƿ�����ĳ�˱༭
	 * 
	 * @param userId
	 * @return
	 */
	boolean isEditable(String userId);

	/**
	 * �жϹ����������Ƿ���ָ����״̬
	 * 
	 * @param status Ҫ�жϵ�״̬����״̬��һ���ǵ�ǰ�ӿ������е�״̬
	 * 
	 * @return
	 */
	boolean isInStatus(String status);
}