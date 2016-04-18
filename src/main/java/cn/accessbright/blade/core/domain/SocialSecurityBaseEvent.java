package cn.accessbright.blade.core.domain;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.icitic.hrms.bene.pojo.BeneSbjssbJobBO;
import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.util.Tools;

/**
 * �籣�����¼�
 * 
 * @author ll
 * 
 */
public class SocialSecurityBaseEvent extends NotifyEvent {
	public SocialSecurityBaseEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	private BeneSbjssbJobBO getJob() {
		return (BeneSbjssbJobBO) source;
	}

	public Map getModleMap() {
		Map modle = new LinkedHashMap();
		BeneSbjssbJobBO job = getJob();
		modle.put("��������", job.getJobName());
		modle.put("��������", job.getCreateDate());

		String status = job.getStatus();

		String submiter = "";// �ύ��
		String content = "";// ���
		if (BeneSbjssbJobBO.STATUS_CHECKING.equals(status)) {
			submiter = job.getCreater();
		} else if (BeneSbjssbJobBO.STATUS_APPROVING.equals(status)) {
			submiter = job.getChecker();
			content = job.getCheckerContent();
		} else if (BeneSbjssbJobBO.STATUS_BACK.equals(status)) {
			if (BeneSbjssbJobBO.PROCESS_STATUS_UNPASS.equals(job.getCheckerStatus())) {
				submiter = job.getChecker();
				content = job.getCheckerContent();
			} else if (BeneSbjssbJobBO.PROCESS_STATUS_UNPASS.equals(job.getVerifierStatus())) {
				submiter = job.getVerifier();
				content = job.getVerifierContent();
			}
		} else if (BeneSbjssbJobBO.STATUS_FINISH.equals(status)) {
			submiter = job.getVerifier();
			content = job.getVerifierContent();
		}

		if (!Tools.isEmpty(submiter)) {
			modle.put("�ύ��", submiter);
		}

		modle.put("״̬", job.getStatusName());

		if (!Tools.isEmpty(content)) {
			modle.put("���", content);
		}
		return modle;
	}
	
	public String getJobId() {
		return getJob().getId();
	}

	public String getTitle() {
		String modleName = "�籣��������";
		return MessageFormat.format(getTitleTemplate(), new Object[] { modleName, message });
	}
}