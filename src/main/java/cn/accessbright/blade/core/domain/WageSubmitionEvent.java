package cn.accessbright.blade.core.domain;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.icitic.hrms.core.event.UrgentNotifyEvent;
import com.icitic.hrms.wage.pojo.bo.WageSetDateBO;

/**
 * ���׼��д���ʱ������
 * 
 * @author ll
 * 
 */
public class WageSubmitionEvent extends UrgentNotifyEvent {
	public WageSubmitionEvent(String[] ids, Object source, Object message) {
		super(ids, source, message);
	}

	public WageSubmitionEvent(List ids, Object source, Object message) {
		super(ids, source, message);
	}

	public WageSubmitionEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	protected WageSetDateBO getWageSetDate() {
		return (WageSetDateBO) source;
	}

	public String getJobId() {
		return getWageSetDate().getWageSetId();
	}

	public Map getModleMap() {
		Map modle = new LinkedHashMap();
		WageSetDateBO wageSetDate = getWageSetDate();
		modle.put("��������", wageSetDate.getWageSetName());
		modle.put("��ʼ����", wageSetDate.getStartDate());
		modle.put("��������", wageSetDate.getEndDate());
		return modle;
	}

	public String getTitle() {
		WageSetDateBO wageSetDate = getWageSetDate();
		String modleName = "н�ʷ�������(" + wageSetDate.getWageSetName() + ")";
		return MessageFormat.format(getTitleTemplate(), new Object[] { modleName, message });
	}
}