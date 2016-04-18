package cn.accessbright.blade.core.domain;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.util.Tools;
import com.icitic.hrms.wage.pojo.bo.QynjJobBO;
import com.icitic.hrms.wage.util.WageConstants;

/**
 * ��ҵ����¼�
 * 
 * @author ll
 * 
 */
public class EnterprisePensionEvent extends NotifyEvent {
	public EnterprisePensionEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	private QynjJobBO getJob() {
		return (QynjJobBO) source;
	}

	public Map getModleMap() {
		Map modle = new LinkedHashMap();
		QynjJobBO job = getJob();
		modle.put("��������", job.getJobName());
		modle.put("��������", job.getCreateDate());

		String status = job.getState();

		String submiter = "";// �ύ��
		String content = "";// ���
		if (WageConstants.QYNJ_STATUS_CHECKING.equals(status)) {
			submiter = job.getCreater();
		} else if (WageConstants.QYNJ_STATUS_APPROVING.equals(status)) {
			submiter = job.getChecker();
			content = job.getCheckerContent();
		} else if (WageConstants.QYNJ_STATUS_BACK.equals(status)) {
			if ("0".equals(job.getVerifierStatus())) {
				submiter = job.getVerifier();
				content = job.getVerifierContent();
			} else if ("0".equals(job.getCheckerStatus())) {
				submiter = job.getChecker();
				content = job.getCheckerContent();
			}
		} else if (WageConstants.QYNJ_STATUS_FINISH.equals(status)) {
			submiter = job.getVerifier();
			content = job.getVerifierContent();
		}

		if (!Tools.isEmpty(submiter)) {
			modle.put("�ύ��", submiter);
		}

		modle.put("״̬", job.getStateName());

		if (!Tools.isEmpty(content)) {
			modle.put("���", content);
		}
		return modle;
	}
	
	public String getJobId() {
		return getJob().getId();
	}

	public String getTitle() {
		String moduleName="��ҵ�������";
		return MessageFormat.format(getTitleTemplate(), new Object[]{moduleName,message});
	}
}