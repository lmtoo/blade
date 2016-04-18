package cn.accessbright.blade.core.domain;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.emp.pojo.bo.PersonBO;
import com.icitic.hrms.util.Tools;
import com.icitic.hrms.wage.manager.WageSetManager;
import com.icitic.hrms.wage.pojo.bo.WageDateBO;
import com.icitic.hrms.wage.pojo.bo.WageSetBO;
import com.icitic.hrms.wage.util.WageConstants;

/**
 * н�ʷ����¼�
 * 
 * @author ll
 * 
 */
public class SalaryPaymentEvent extends NotifyEvent {
	public SalaryPaymentEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	private WageDateBO getWageDate() {
		WageDateBO wageDate=(WageDateBO) source;
		try {
			WageSetManager manager=new WageSetManager();
			WageSetBO wageSet=manager.findSetBySetId(wageDate.getSetId());
			wageDate.setSetName(wageSet.getName());
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return wageDate;
	}

	public Map getModleMap() {
		Map modle = new LinkedHashMap();

		WageDateBO wageDate = getWageDate();
		modle.put("��������", wageDate.getSetName());
		modle.put("ҵ������", wageDate.getDate());
		modle.put("����", wageDate.getDesc());

		String status = wageDate.getStatus();

		String submiter = "";// �ύ��
		String content = "";// ���
		if (WageConstants.FLOW_STATUS_CHECKING.equals(status)) {
			submiter = wageDate.getCreator();
		} else if (WageConstants.FLOW_STATUS_APPROVING.equals(status)) {
			submiter = wageDate.getChecker();
			content = wageDate.getCheckContent();
		}else if (WageConstants.FLOW_STATUS_PAYOFFING.equals(status)) {
			submiter = wageDate.getApprover();
			content = wageDate.getApproveContent();
		}else if (WageConstants.FLOW_STATUS_BACK.equals(status)) {
			if(!Tools.isEmpty(wageDate.getFinanceId())){
				submiter = wageDate.getFinanceId();
			}else if (!Tools.isEmpty(wageDate.getApprover())) {
				submiter = wageDate.getApprover();
				content = wageDate.getApproveContent();
			} else if (!Tools.isEmpty(wageDate.getChecker())) {
				submiter = wageDate.getChecker();
				content = wageDate.getCheckContent();
			}
		}

		if (!Tools.isEmpty(submiter)) {
			PersonBO person=SysCacheTool.findPersonById(submiter);
			modle.put("�ύ��", person.getName());
		}
		modle.put("״̬", wageDate.getStatusName());
		if (!Tools.isEmpty(content)) {
			modle.put("���", content);
		}
		return modle;
	}
	
	public String getJobId() {
		return getWageDate().getDateId();
	}

	public String getTitle() {
		String modleName="н�ʷ���";
		WageDateBO wageDate = getWageDate();
		if("0000102".equals(wageDate.getSetId())){
			modleName="�����ó�����";
		}
		modleName+="����("+wageDate.getSetName()+")";
		return MessageFormat.format(getTitleTemplate(), new Object[]{modleName,message});
	}
}