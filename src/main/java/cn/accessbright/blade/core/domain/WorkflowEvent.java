package cn.accessbright.blade.core.domain;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.flowpath.pojo.bo.ClerkGradeBO;
import com.icitic.hrms.util.CodeUtil;
import com.icitic.hrms.util.Tools;

/**
 * ���̹����¼�
 * 
 * @author ll
 * 
 */
public class WorkflowEvent extends NotifyEvent {
	private String modleName="���̹�������";
	
	public WorkflowEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	private ClerkGradeBO getClerkGrade() {
		return (ClerkGradeBO) source;
	}

	public Map getModleMap() {
		Map modle = new LinkedHashMap();
		ClerkGradeBO clerkGradeBO = getClerkGrade();
		modle.put("��Ա", Tools.filterNull(clerkGradeBO.getName()));
		modle.put("����ʱ��", Tools.filterNull(clerkGradeBO.getApplyDate()));
		modle.put("�䶯��ʽ", CodeUtil.interpertCode("3104", clerkGradeBO.getChangeWay()));
		modle.put("����ԭ��", Tools.filterNull(CodeUtil.interpertCode("3105", clerkGradeBO.getApplyCause())));

		String status = clerkGradeBO.getStatus();
		String backStatusIndex = clerkGradeBO.getBackStatusIndex();
		String orgId = Tools.filterNullToStr(clerkGradeBO.getOrgId());
    	String toOrgId = Tools.filterNullToStr(clerkGradeBO.getToOrgId());
    	String deptId = Tools.filterNullToStr(clerkGradeBO.getDeptId());
    	String toDeptId = Tools.filterNullToStr(clerkGradeBO.getToDeptId());
    	String postId = Tools.filterNullToStr(clerkGradeBO.getPostId());
    	String toPostId = Tools.filterNullToStr(clerkGradeBO.getToPostId());
    	String headshipName = Tools.filterNullToStr(clerkGradeBO.getHeadshipName());
    	String toHeadshipName = Tools.filterNullToStr(clerkGradeBO.getToHeadshipName());
    	String postRank = Tools.filterNullToStr(clerkGradeBO.getPostRank());
    	String toPostRank = Tools.filterNullToStr(clerkGradeBO.getPostRank());
    	String postLevel = Tools.filterNullToStr(clerkGradeBO.getPostLevel());
    	String toPostLevel = Tools.filterNullToStr(clerkGradeBO.getToPostLevel());

		String submiter = "";// �ύ��
		String content = "";// ���
		
		String titleSuffix="";
		if (ClerkGradeBO.CLERKGRADE_STATUS_CHECKING.equals(status)) {//�ύ����֧�����
			submiter = clerkGradeBO.getCreater();
		} else if (ClerkGradeBO.CLERKGRADE_STATUS_ZH_CHECKING.equals(status)) {//�ύ���������
			if ("660".equals(clerkGradeBO.getGongZiGX())) {
				submiter = clerkGradeBO.getCreater();
			} else {
				submiter = clerkGradeBO.getVerifierByFH();
				content = clerkGradeBO.getCheckYjRemarkByFH();
			}
			titleSuffix="(����)";
		} else if (ClerkGradeBO.CLERKGRADE_STATUS_ZH_APPROVING.equals(status)) {// �ύ����������
			submiter = clerkGradeBO.getVerifierByZH();
			content = clerkGradeBO.getCheckYjRemarkByZH();
			titleSuffix="(����)";
		} else if (ClerkGradeBO.CLERKGRADE_STATUS_XC_CHECKING.equals(status)) {// �ύ��н�����
			submiter = clerkGradeBO.getApprovalByZH();
			content = clerkGradeBO.getApprovalYjRemarkByZH();
			titleSuffix="(����н��)";
		} else if (ClerkGradeBO.CLERKGRADE_STATUS_XC_APPROVING.equals(status)) {// �ύ��н������
			submiter = clerkGradeBO.getVerifierByXC();
			content = clerkGradeBO.getCheckYjRemarkByXC();
			titleSuffix="(����н��)";
		} else if (ClerkGradeBO.CLERKGRADE_STATUS_BACK.equals(status)) {
			if (backStatusIndex.equals(ClerkGradeBO.CLERKGRADE_STATUS_NEW)) {// �˻ص�����
				if ("660".equals(clerkGradeBO.getGongZiGX())) {//�������˻ص�����
					submiter = clerkGradeBO.getVerifierByZH();
					content = clerkGradeBO.getCheckYjRemarkByZH();
					titleSuffix="(����)";
				} else {//�ӷ�֧���˻ص�����
					submiter = clerkGradeBO.getVerifierByFH();
					content = clerkGradeBO.getCheckYjRemarkByFH();
				}
			} else if (backStatusIndex.equals(ClerkGradeBO.CLERKGRADE_STATUS_CHECKING)) {// �˻ص���֧�����
				submiter = clerkGradeBO.getVerifierByZH();
				content = clerkGradeBO.getCheckYjRemarkByZH();
				titleSuffix="(����)";
			} else if (backStatusIndex.equals(ClerkGradeBO.CLERKGRADE_STATUS_ZH_CHECKING)) {// �˻ص����������
				submiter = clerkGradeBO.getApprovalByZH();
				content = clerkGradeBO.getApprovalYjRemarkByZH();
				titleSuffix="(����)";
			} else if (backStatusIndex.equals(ClerkGradeBO.CLERKGRADE_STATUS_ZH_APPROVING)) {// �˻ص�����������
				submiter = clerkGradeBO.getVerifierByXC();
				content = clerkGradeBO.getCheckYjRemarkByXC();
				titleSuffix="(����н��)";
			} else if (backStatusIndex.equals(ClerkGradeBO.CLERKGRADE_STATUS_XC_CHECKING)) {// �˻ص�н�������
				submiter = clerkGradeBO.getApprovalByXC();
				content = clerkGradeBO.getApprovalYjRemarkByXC();
				titleSuffix="(����н��)";
			}
		} else if (ClerkGradeBO.CLERKGRADE_STATUS_FINISH.equals(status)) {// ����
			String salaryLevel = Tools.filterNullToStr(clerkGradeBO.getSalaryLevel());
			String toSalaryLevel = Tools.filterNullToStr(clerkGradeBO.getToSalaryLevel());
			String salaryGrade = Tools.filterNullToStr(clerkGradeBO.getSalaryGrade());
			String toSalaryGrade = Tools.filterNullToStr(clerkGradeBO.getToSalaryGrade());
			
			if ((!orgId.equals(toOrgId) || !deptId.equals(toDeptId) || !postId.equals(toPostId))
				&& (headshipName.equals(toHeadshipName) && postRank.equals(toPostRank) && postLevel.equals(toPostLevel)) ) {
				if ("660".equals(clerkGradeBO.getGongZiGX())) {
					submiter = clerkGradeBO.getApprovalByZH();
					content = clerkGradeBO.getApprovalYjRemarkByZH();
					titleSuffix="(����)";
				}else{
					submiter = clerkGradeBO.getVerifierByFH();
					content = clerkGradeBO.getCheckYjRemarkByFH();
				}
			}else { if (!"01601505".equals(clerkGradeBO.getToHeadshipName())) {// ��������ְ��䶯Ϊ��������Ա����Ҫ�ύ��н����˺�н������
				if (toSalaryLevel.equals(salaryLevel) && toSalaryGrade.equals(salaryGrade)) {// ����������Ա���ʵȼ��͵���û�����ı䣬���̵��˽�����
					submiter = clerkGradeBO.getApprovalByZH();
					content = clerkGradeBO.getApprovalYjRemarkByZH();
					titleSuffix="(����)";
				} else if (clerkGradeBO.getApplyCause() != null && clerkGradeBO.getApplyCause().equals("310501038")) {// ��������ԭ��Ϊ��ȿ��ˣ����̵��˽�����
					submiter = clerkGradeBO.getApprovalByZH();
					content = clerkGradeBO.getApprovalYjRemarkByZH();
					titleSuffix="(����)";
				} else {// н�����˳ɹ�
					submiter = clerkGradeBO.getApprovalByXC();
					content = clerkGradeBO.getApprovalYjRemarkByXC();
					titleSuffix="(����н��)";
				}
			}
		}
		modleName+=titleSuffix;
	}

		if (!Tools.isEmpty(submiter)) {
			modle.put("�ύ��", submiter);
		}
		modle.put("״̬", Tools.filterNull(ClerkGradeBO.getClerkGradeStatusName(clerkGradeBO.getStatus(), clerkGradeBO.getBackStatusIndex())));
		if (!Tools.isEmpty(content)) {
			modle.put("���", content);
		}
		return modle;
	}

	public String getJobId() {
		return getClerkGrade().getSubId();
	}
	public String getTitle() {
		return MessageFormat.format(getTitleTemplate(), new Object[] { modleName, message });
	}
}