package cn.accessbright.blade.core.workflow.domain;

import com.icitic.hrms.emp.pojo.bo.PersonBO;
import com.icitic.hrms.util.Tools;


/**
 * ���ڵ��������̣�����ֻ��һ�������ڵ�
 * 
 * @author ll
 * 
 */
public abstract class OnePointWorkflowJob extends AbstractWorkflowJob {
	public final static String STATUS_APPROVING = "2"; // ����

	private String verifierDate;//����ʱ��
	
	private String verifier; // ������

	private String verifierId; // �����id

	private String verifierStatus; // ���״̬

	private String verifierContent; // �������

	public String getVerifierDate() {
		return verifierDate;
	}

	public void setVerifierDate(String verifierDate) {
		this.verifierDate = verifierDate;
	}

	public String getVerifier() {
		return verifier;
	}

	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}

	public String getVerifierNo() {
		return verifierId;
	}

	public void setVerifierNo(String verifierNo) {
		this.verifierId = verifierNo;
	}

	public String getVerifierStatus() {
		return verifierStatus;
	}

	public void setVerifierStatus(String verifierStatus) {
		this.verifierStatus = verifierStatus;
	}

	public String getVerifierContent() {
		return verifierContent;
	}

	public void setVerifierContent(String verifierContent) {
		this.verifierContent = verifierContent;
	}

	public String getVerifierId() {
		return verifierId;
	}

	public void setVerifierId(String verifierId) {
		this.verifierId = verifierId;
	}

	public String getStatusName() {
		return OnePointWorkflowJob.getStatusName(status);
	}

	public static String getStatusName(String status) {
		if (STATUS_APPROVING.equals(status)) {
			return "������";
		}
		return AbstractWorkflowJob.getStatusName(status);
	}
	
	/**
	 * �½��ύ�����
	 * 
	 * @param checker
	 */
	public void submitToBegin(PersonBO verifier) {
		setVerifierNo(verifier.getPersonId());
		setVerifier(verifier.getName());
		setStatus(STATUS_APPROVING);
		setVerifierContent(null);
		setVerifierDate(null);
		setVerifierStatus(PROCESS_STATUS_PASS);
	}

	/**
	 * ����˻�
	 * 
	 * @param content
	 */
	public void verifyToBack(String verifyContent) {
		setVerifierDate(Tools.getSysDate("yyyy-MM-dd"));
		setVerifierStatus(PROCESS_STATUS_UNPASS);
		setVerifierContent(verifyContent);
		setStatus(STATUS_BACK);
	}

	/**
	 * �ύ������
	 * 
	 * @param verifyContent
	 */
	public void submitToFinish(String verifyContent) {
		setVerifierDate(Tools.getSysDate("yyyy-MM-dd"));
		setVerifierStatus(PROCESS_STATUS_PASS);
		setVerifierContent(verifyContent);
		setStatus(STATUS_FINISH);
	}

	/**
	 * �������״̬
	 * 
	 * @param verifyStatus
	 * @param verifyContents
	 */
	public void saveVerifyStatus(String verifyStatus, String verifyContents) {
		setVerifierStatus(verifyStatus);
		setVerifierContent(verifyContents);
	}

	/**
	 * �Ƿ񱣴�������ͨ��
	 * 
	 * @return
	 */
	public boolean isVerifyPass() {
		return PROCESS_STATUS_PASS.equals(verifierStatus);
	}
}