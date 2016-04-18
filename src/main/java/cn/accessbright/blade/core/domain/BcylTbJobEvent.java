package cn.accessbright.blade.core.domain;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.lp.pojo.bo.BcylTbJobBO;
import com.icitic.hrms.util.Tools;

/**
 * ����ҽ��
 * 
 * @author ll
 * 
 */
public class BcylTbJobEvent extends NotifyEvent {
	public BcylTbJobEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	private BcylTbJobBO getWorkflowJob() {
		return (BcylTbJobBO) source;
	}

	public Map getModleMap() {
		Map modle = new LinkedHashMap();
		BcylTbJobBO job = getWorkflowJob();
		modle.put("��������", job.getJobName());
		modle.put("��������", job.getCreateDate());

		String status = job.getStatus();

		String submiter = "";// �ύ��
		String content = "";// ���
		if (BcylTbJobBO.STATUS_CHECKING.equals(status)) {
			submiter = job.getCreater();
		} else if (BcylTbJobBO.STATUS_APPROVING.equals(status)) {
			submiter = job.getChecker();
			content = job.getCheckerContent();
		} else if (BcylTbJobBO.STATUS_BACK.equals(status)) {
			if (BcylTbJobBO.PROCESS_STATUS_UNPASS.equals(job.getCheckerStatus())) {
				submiter = job.getChecker();
				content = job.getCheckerContent();
			} else if (BcylTbJobBO.PROCESS_STATUS_UNPASS.equals(job.getVerifierStatus())) {
				submiter = job.getVerifier();
				content = job.getVerifierContent();
			}
		} else if (BcylTbJobBO.STATUS_FINISH.equals(status)) {
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
		return getWorkflowJob().getSubId();
	}

	public String getTitle() {
		return MessageFormat.format(getTitleTemplate(), new Object[] { moduleName(), message });
	}

	protected String moduleName() {
		return "֧�в���ҽ��Ͷ��";
	}
}
