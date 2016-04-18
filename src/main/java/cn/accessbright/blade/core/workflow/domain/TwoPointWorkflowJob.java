package cn.accessbright.blade.core.workflow.domain;

import com.icitic.hrms.emp.pojo.bo.PersonBO;
import com.icitic.hrms.util.Tools;

/**
 * ���������ڵ������
 * 
 * @author ll
 * 
 */
public class TwoPointWorkflowJob extends OnePointWorkflowJob {
	public final static String STATUS_CHECKING = "1"; // ����

	private String checkerDate;//����ʱ��
	
	private String checker; // ������

	private String checkerId; // ������id

	private String checkerStatus; // ����״̬

	private String checkerContent; // ��������

	public String getCheckerDate() {
		return checkerDate;
	}

	public void setCheckerDate(String checkerDate) {
		this.checkerDate = checkerDate;
	}

	public String getChecker() {
		return checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public String getCheckerNo() {
		return checkerId;
	}

	public void setCheckerNo(String checkerNo) {
		this.checkerId = checkerNo;
	}

	public String getCheckerStatus() {
		return checkerStatus;
	}

	public void setCheckerStatus(String checkerStatus) {
		this.checkerStatus = checkerStatus;
	}

	public String getCheckerContent() {
		return checkerContent;
	}

	public void setCheckerContent(String checkerContent) {
		this.checkerContent = checkerContent;
	}

	public String getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}

	public String getStatusName() {
		return TwoPointWorkflowJob.getStatusName(status);
	}

	public static String getStatusName(String status) {
		if (STATUS_CHECKING.equals(status)) {
			return "������";
		}
		return OnePointWorkflowJob.getStatusName(status);
	}

	/**
	 * �½��ύ������
	 * 
	 * @param checker
	 */
	public void submitToBegin(PersonBO checker) {
		setChecker(checker.getName());
		setCheckerNo(checker.getPersonId());
		setStatus(STATUS_CHECKING);
		setCheckerContent(null);
		setCheckerStatus(PROCESS_STATUS_PASS);
		setVerifierNo(null);
		setVerifier(null);
		setVerifierStatus(null);
		setVerifierContent(null);
		setCheckerDate(null);
	}

	/**
	 * �Ӹ����ύ�����
	 * 
	 * @param verifyId
	 * @param checkerContent
	 */
	public void submitToVerify(PersonBO verifier, String checkerContent) {
		setCheckerDate(Tools.getSysDate("yyyy-MM-dd"));
		setCheckerStatus(PROCESS_STATUS_PASS);
		setCheckerContent(checkerContent);
		setVerifierStatus(PROCESS_STATUS_PASS);
		setVerifierNo(verifier.getPersonId());
		setVerifier(verifier.getName());
		setStatus(STATUS_APPROVING);
	}

	/**
	 * �����˻�
	 */
	public void checkToBack(String checkerContent) {
		setCheckerDate(Tools.getSysDate("yyyy-MM-dd"));
		setCheckerStatus(PROCESS_STATUS_UNPASS);
		setCheckerContent(checkerContent);
		setStatus(STATUS_BACK);
	}

	/**
	 * ���渴��״̬
	 */
	public void saveCheckStatus(String checkStatus, String checkContents) {
		setCheckerStatus(checkStatus);
		setCheckerContent(checkContents);
	}

	/**
	 * �Ƿ񱣴��˸���ͨ��
	 * 
	 * @return
	 */
	public boolean isCheckPass() {
		return PROCESS_STATUS_PASS.equals(checkerStatus);
	}
}
