package cn.accessbright.blade.core.mail;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Transaction;

import com.icitic.hrms.common.dao.GenericDAO;
import com.icitic.hrms.common.exception.HrmsException;
import com.icitic.hrms.common.manager.GenericManager;
import com.icitic.hrms.util.Tools;

public class MailManager extends GenericManager {
	private GenericDAO dao;
	private String pattern = "yyyy-MM-dd hh:mm:ss";

	public MailManager() throws HrmsException {
		dao = new GenericDAO(s);
	}

	/**
	 * ������Ϣ��¼
	 * 
	 * @param records
	 * @throws HrmsException
	 */
	public void saveMailRecord(List records) throws HrmsException {
		if (!Tools.isEmpty(records)) {
			Transaction tx = null;
			try {
				tx = this.beginTransaction();
				for (int i = 0; i < records.size(); i++) {
					NotifyRecord record = (NotifyRecord) records.get(i);
					
					List notifyList = dao.find("from NotifyRecord n where n.status='" + NotifyRecord.STATUS_NOT_SEND + "' and n.jobId='"+record.getJobId()+"'");
					if (!Tools.isEmpty(notifyList)) {
						for (int j = 0; j < notifyList.size(); j++) {
							NotifyRecord recorded = (NotifyRecord) notifyList.get(j);
							recorded.cancel();
							dao.saveOrUpdate(recorded);
						}
					}
					dao.createBo(record);
				}
				this.commitTransaction(tx);
			} catch (Exception e) {
				this.rollbackTransaction(tx);
				throw new HrmsException("�½��ʼ���ʷ��¼ʧ��", e, this.getClass());
			}
		}
	}

	/**
	 * ֪ͨ������Ϣ
	 * 
	 * @param now
	 * @throws HrmsException
	 */
	public void send(Date now) throws HrmsException {
		Transaction tx = null;
		try {
			tx = this.beginTransaction();
			SimpleDateFormat format = new SimpleDateFormat(pattern);

			String hql = "from NotifyRecord n where n.status='" + NotifyRecord.STATUS_NOT_SEND 
					+ "' and to_char(n.sendDate,'" + pattern + "')<='"+ format.format(now) + "' "
					+ " order by n.sendDate asc";
			List records = dao.find(hql);
			if (!Tools.isEmpty(records)) {

				for (int i = 0; i < records.size(); i++) {
					NotifyRecord record = (NotifyRecord) records.get(i);
					MailEventListener.getInstance().sendEmail(record);
					dao.updateBo(record);
				}

			}
			this.commitTransaction(tx);
		} catch (Exception e) {
			this.rollbackTransaction(tx);
			throw new HrmsException("�½��ʼ���ʷ��¼ʧ��", e, this.getClass());
		}
	}
}