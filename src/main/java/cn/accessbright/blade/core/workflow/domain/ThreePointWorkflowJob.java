package cn.accessbright.blade.core.workflow.domain;

import com.icitic.hrms.emp.pojo.bo.PersonBO;
import com.icitic.hrms.util.Tools;

public class ThreePointWorkflowJob extends TwoPointWorkflowJob {
	public final static String STATUS_FINANCIAL = "3"; // ������

	private String financerDate;//������ʱ��
	
	private String financer; // ��������

	private String financerId; // ��������id

	private String financerStatus; // ������״̬

	private String financerContent; // ����������

	public String getFinancerDate() {
		return financerDate;
	}

	public void setFinancerDate(String financerDate) {
		this.financerDate = financerDate;
	}

	public String getFinancer() {
		return financer;
	}

	public void setFinancer(String financer) {
		this.financer = financer;
	}

	public String getFinancerId() {
		return financerId;
	}

	public void setFinancerId(String financerId) {
		this.financerId = financerId;
	}

	public String getFinancerStatus() {
		return financerStatus;
	}

	public void setFinancerStatus(String financerStatus) {
		this.financerStatus = financerStatus;
	}

	public String getFinancerContent() {
		return financerContent;
	}

	public void setFinancerContent(String financerContent) {
		this.financerContent = financerContent;
	}

	public String getStatusName() {
		return ThreePointWorkflowJob.getStatusName(status);
	}

	public static String getStatusName(String status) {
		if (STATUS_FINANCIAL.equals(status)) {
			return "��������";
		}
		return TwoPointWorkflowJob.getStatusName(status);
	}

	public void submitToBegin(PersonBO checker) {
		super.submitToBegin(checker);
		setFinancerId(null);
		setFinancer(null);
		setFinancerStatus(null);
		setFinancerContent(null);
		setFinancerDate(null);
	}

	/**
	 * ������ύ��������
	 * 
	 * @param verifyId
	 * @param checkerContent
	 */
	public void submitToFinance(PersonBO financer, String verifieContent) {
		setVerifierStatus(PROCESS_STATUS_PASS);
		setVerifierContent(verifieContent);

		setFinancerStatus(PROCESS_STATUS_PASS);
		setFinancerId(financer.getPersonId());
		setFinancer(financer.getName());
		setStatus(STATUS_FINANCIAL);
	}

	/**
	 * �������˻�
	 */
	public void financeToBack(String financeContent) {
		setFinancerDate(Tools.getSysDate("yyyy-MM-dd hh:mm:ss"));
		setFinancerStatus(PROCESS_STATUS_UNPASS);
		setFinancerContent(financeContent);
		setStatus(STATUS_BACK);
	}

	/**
	 * ���������״̬
	 */
	public void saveFinanceStatus(String financeStatus, String financeContents) {
		setFinancerStatus(financeStatus);
		setFinancerContent(financeContents);
	}

	/**
	 * �Ӳ������ύ�����
	 */
	public void submitToFinish(String financeContent) {
		setFinancerDate(Tools.getSysDate("yyyy-MM-dd"));
		setFinancerStatus(PROCESS_STATUS_PASS);
		setFinancerContent(financeContent);
		setStatus(STATUS_FINISH);
	}

	/**
	 * �Ƿ񱣴��˸���ͨ��
	 * 
	 * @return
	 */
	public boolean isFinancePass() {
		return STATUS_FINANCIAL.equals(financerStatus);
	}
}