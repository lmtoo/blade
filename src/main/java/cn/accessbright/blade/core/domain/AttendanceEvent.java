package cn.accessbright.blade.core.domain;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.emp.pojo.bo.PersonBO;
import com.icitic.hrms.kq.pojo.bo.JobBO;
import com.icitic.hrms.util.Tools;

/**
 * �����¼�
 * 
 * @author ll
 * 
 */
public class AttendanceEvent extends NotifyEvent {

	public AttendanceEvent(String[] ids, Object source, Object message) {
		super(ids, source, message);
	}

	private JobBO getJob() {
		return (JobBO) source;
	};

	public Map getModleMap() {
		Map modle = new LinkedHashMap();
		JobBO bo = getJob();
		PersonBO p = SysCacheTool.findPersonById(bo.getPid());
		String status = Tools.filterNull(bo.getStatus());
		modle.put("������", Tools.filterNull(bo.getYearMonth()));
		modle.put("����", Tools.filterNull(SysCacheTool.interpertCode("OU", bo.getOrg())));
		modle.put("����", Tools.filterNull(SysCacheTool.interpertCode("OU", bo.getDept())));
		modle.put("״̬", "00900".equals(status) ? "���" : "δ���");
		modle.put("����", Tools.filterNull(p.getPersonCode()));
		modle.put("����", Tools.filterNull(p.getName()));
		modle.put("�ύ����", Tools.filterNull(bo.getDate()));
		modle.put("��ע", Tools.filterNull(bo.getComments()));
		return modle;
	}

	public String getTitle() {
		JobBO bo = getJob();
		String orgName = Tools.filterNull(SysCacheTool.interpertCode("OU", bo.getOrg()));
		String deptName = Tools.filterNull(SysCacheTool.interpertCode("OU", bo.getDept()));
		String yearMonth = Tools.filterNull(bo.getYearMonth());
		String moduleName=orgName +"/"+ deptName+" "+ yearMonth+"�¿�������";
		return MessageFormat.format(getTitleTemplate(), new Object[]{moduleName,message});
	}
	
	public String getJobId() {
		return this.getClass().getSimpleName()+getJob().getSubid();
	}
}