package cn.accessbright.blade.core.domain;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.emp.pojo.bo.PersonBO;
import com.icitic.hrms.util.Tools;
import com.icitic.hrms.wage.pojo.bo.CostTransferBO;

/**
 * ����ת��
 * 
 * @author ll
 * 
 */
public class CostTransferEvent extends NotifyEvent {

	public CostTransferEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	private CostTransferBO getCostTransfer() {
		return (CostTransferBO) source;
	}

	public Map getModleMap() {
		Map modle = new LinkedHashMap();
		CostTransferBO job = getCostTransfer();

		PersonBO pbo = SysCacheTool.findPersonById(job.getPersId());
		modle.put("����", Tools.filterNull(pbo.getPersonCode()));
		modle.put("����", Tools.filterNull(pbo.getName()));
		modle.put("����", Tools.filterNull(SysCacheTool.interpertCode("OU", pbo.getOrgId())));
		modle.put("����", Tools.filterNull(SysCacheTool.interpertCode("OU", pbo.getDeptId())));

		String submiter =job.getCreatorId();
		if (CostTransferBO.STATUS_WAITING_CONFIRM.equals(job.getStatus())) {
			submiter=job.getCreatorId();
		} else if (CostTransferBO.STATUS_READ.equals(job.getStatus())) {
			submiter=job.getConfirmerId();
		} else if (CostTransferBO.STATUS_FINISHED.equals(job.getStatus())) {
			submiter=job.getConfirmerId();
		} else if (CostTransferBO.STATUS_BACK.equals(job.getStatus())) {
			submiter=job.getConfirmerId();
		}

		if (!Tools.isEmpty(submiter)) {
			modle.put("�ύ��", SysCacheTool.findPersonById(submiter).getName());
		}

		modle.put("״̬", CostTransferBO.status2html(job.getStatus()));

		String content = job.getRemark();// ��ע
		if (!Tools.isEmpty(content)) {
			modle.put("��ע", content);
		}
		return modle;
	}
	
	public String getJobId() {
		return getCostTransfer().getTransferId();
	}

	public String getTitle() {
		String moduleName = "�����ɱ�ת����Ŀ";
		return MessageFormat.format(getTitleTemplate(), new Object[] { moduleName, message });
	}
}