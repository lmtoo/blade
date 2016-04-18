package cn.accessbright.blade.core.domain;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.util.Tools;
import com.icitic.hrms.wage.pojo.bo.WageNdgzdcJsJobBO;

/**
 * н�ʵ����¼�
 * 
 * @author ll
 * 
 */
public class SalaryLevelEvent extends NotifyEvent {
	public SalaryLevelEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	private WageNdgzdcJsJobBO getJob() {
		return (WageNdgzdcJsJobBO) source;
	}

	public Map getModleMap() {
		Map modle = new LinkedHashMap();
		WageNdgzdcJsJobBO job = getJob();
		modle.put("��������", job.getJobName());
		modle.put("��������", job.getCreateDate());

		String status = job.getStatus();

		String submiter = "";// �ύ��
		String content = "";// ���
		if (WageNdgzdcJsJobBO.STATUS_CHECKING.equals(status)) {
			submiter = job.getCreater();
		} else if (WageNdgzdcJsJobBO.STATUS_APPROVING.equals(status)) {
			submiter = job.getChecker();
			content = job.getCheckerContent();
		} else if (WageNdgzdcJsJobBO.STATUS_BACK.equals(status)) {
			if (WageNdgzdcJsJobBO.PROCESS_STATUS_UNPASS.equals(job.getVerifierStatus())) {
				submiter = job.getVerifier();
				content = job.getVerifierContent();
			} else if (WageNdgzdcJsJobBO.PROCESS_STATUS_UNPASS.equals(job.getCheckerStatus())) {
				submiter = job.getChecker();
				content = job.getCheckerContent();
			}
		} else if (WageNdgzdcJsJobBO.STATUS_FINISH.equals(status)) {
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
		String moduleName = getJob().getPromoteName() + "����";
		return MessageFormat.format(getTitleTemplate(), new Object[] { moduleName, message });
	}
}