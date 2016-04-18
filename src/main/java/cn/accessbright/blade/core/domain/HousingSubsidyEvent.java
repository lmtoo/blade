package cn.accessbright.blade.core.domain;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.util.Tools;
import com.icitic.hrms.wage.pojo.bo.zfbt.ZfbtJobBO;

/**
 * ס�������¼�
 * 
 * @author ll
 * 
 */
public class HousingSubsidyEvent extends NotifyEvent {

	public HousingSubsidyEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	private ZfbtJobBO getJob() {
		return (ZfbtJobBO) source;
	}

	public Map getModleMap() {
		Map modle = new LinkedHashMap();
		ZfbtJobBO job = getJob();
		modle.put("��������", job.getJobName());
		modle.put("��������", job.getCreateDate());

		String status = job.getStatus();

		String submiter = "";// �ύ��
		String content = "";// ���
		if (ZfbtJobBO.ZFBT_STATUS_CHECKING.equals(status)) {
			submiter = job.getCreater();
		} else if (ZfbtJobBO.ZFBT_STATUS_APPROVING.equals(status)) {
			submiter = job.getChecker();
			content = job.getCheckerContent();
		} else if (ZfbtJobBO.ZFBT_STATUS_BACK.equals(status)) {
			if ("0".equals(job.getCheckerStatus())) {
				submiter = job.getChecker();
				content = job.getCheckerContent();
			} else if ("0".equals(job.getVerifierStatus())) {
				submiter = job.getVerifier();
				content = job.getVerifierContent();
			}
		} else if (ZfbtJobBO.ZFBT_STATUS_FINISH.equals(status)) {
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
		String moduleName="ס����������";
		return MessageFormat.format(getTitleTemplate(), new Object[]{moduleName,message});
	}
}