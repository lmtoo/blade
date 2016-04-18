package cn.accessbright.blade.core.domain;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.emp.pojo.bo.PersonBO;
import com.icitic.hrms.kq.pojo.bo.XiuJiaInfoBO;
import com.icitic.hrms.kq.util.XjConstants;
import com.icitic.hrms.util.CodeUtil;
import com.icitic.hrms.util.Tools;

/**
 * �ݼ��¼�
 * 
 * @author ll
 * 
 */
public class VacationEvent extends NotifyEvent {

	public VacationEvent(String id, Object source, Object message) {
		super(id, source, message);
	}

	public VacationEvent(String[] ids, Object source, Object message) {
		super(ids, source, message);
	}

	private XiuJiaInfoBO getXiuJiaInfo() {
		return (XiuJiaInfoBO) source;
	}

	public Map getModleMap() {
		Map modle = new LinkedHashMap();
		XiuJiaInfoBO bo = getXiuJiaInfo();
		PersonBO pbo = SysCacheTool.findPersonById(bo.getPid());
		modle.put("����", Tools.filterNull(pbo.getPersonCode()));
		modle.put("����", Tools.filterNull(pbo.getName()));
		modle.put("����", Tools.filterNull(SysCacheTool.interpertCode("OU", pbo.getOrgId())));
		modle.put("����", Tools.filterNull(SysCacheTool.interpertCode("OU", pbo.getDeptId())));
		modle.put("����", Tools.filterNull(CodeUtil.interpertCode(XjConstants.KAOQIN, bo.getType())));
		modle.put("��������ְ��", Tools.filterNull(CodeUtil.interpertCode("0165", pbo.getPostLevel())));
		modle.put("��ʼ����", Tools.filterNull(bo.getBeginDate()));
		modle.put("��������", Tools.filterNull(bo.getEndDate()));
		modle.put("������", Tools.filterNull(bo.getSumDays()));
		modle.put("����", Tools.filterNull(bo.getComments()));
		modle.put("״̬", Tools.filterNull(CodeUtil.interpertCode(XjConstants.LIUCHENG, bo.getProcType())));
		return modle;
	}

	public String getTitle() {
		XiuJiaInfoBO bo = getXiuJiaInfo();
		String modleName = "�ݼ�����";
		if (XjConstants.JIABAN.equals(bo.getProcType())) {
			modleName = "�Ӱ�¼��";
		}
		return MessageFormat.format(getTitleTemplate(), new Object[] { modleName, message });
	}

	public String getJobId() {
		return this.getClass().getSimpleName()+getXiuJiaInfo().getSubid();
	}
	
	public boolean isEndPoint() {
		XiuJiaInfoBO bo = getXiuJiaInfo();
		return XjConstants.QUERENYX.equals(bo.getProcType());
	}
}