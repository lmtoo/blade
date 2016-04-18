package cn.accessbright.blade.core.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import com.icitic.hrms.cache.SysCacheTool;
import com.icitic.hrms.core.converter.Converter;
import com.icitic.hrms.core.converter.VelocityContentConverter;
import com.icitic.hrms.core.event.EventListener;
import com.icitic.hrms.core.event.HrEvent;
import com.icitic.hrms.core.event.NotifyEvent;
import com.icitic.hrms.core.event.UrgentNotifyEvent;
import com.icitic.hrms.core.util.ListArrayUtil;
import com.icitic.hrms.core.util.ListArrayUtil.ObjectMapper;
import com.icitic.hrms.emp.pojo.bo.PersonBO;

/**
 * ����Email���¼�����������ʵ����������Ŀֻ��ͬһ���¼�����������ֻ��NotifyEvent�¼�ִ���ʼ�����
 * 
 * @author ll
 * 
 */
public class MailEventListener implements EventListener {
	private static Logger logger = Logger.getLogger(MailEventListener.class);

	private static MailEventListener INSTANCE = new MailEventListener();

	private static JavaMailSender MAIL_SENDER = createJavaMailSender();

	private static Converter CONTENT_CONVERTER = new VelocityContentConverter();

	/**
	 * ����Hr�¼�����ֻ��NotifyEvent�¼�ִ���ʼ����ͣ������ڴ˴����ʼ����ݼӹ�����Ӧ���ʼ�ģ��
	 */
	public void onEvent(HrEvent event) {
		if (event instanceof NotifyEvent && event.isValid()) {
			NotifyEvent notifyEvent = (NotifyEvent) event;
			List notifyList = createNotify(notifyEvent);
			for (int i = 0; i < notifyList.size(); i++) {
				NotifyRecord record = (NotifyRecord) notifyList.get(i);
				if (record.isImmediatelySend()) {
					sendEmail(record);
				}
			}

			try {
				MailManager mgr = new MailManager();
				mgr.saveMailRecord(notifyList);
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
			}
		}
	}

	public void sendEmail(NotifyRecord message) {
		try {
			synchronized (MAIL_SENDER) {// ��סͬһ���ʼ������ߣ����Ⲣ������
				MAIL_SENDER.send(createMimeMessagePrpartor(message));
				message.send();
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}
	}

	/**
	 * ����֪ͨ��¼
	 * 
	 * @param event
	 * @return
	 */
	private List createNotify(NotifyEvent event) {
		String[] to = idsToEamilUsernames(Arrays.asList(event.getIds()));
		List notifyList = new ArrayList();
		for (int i = 0; i < to.length; i++) {
			boolean isImmediatelySend = event instanceof UrgentNotifyEvent;
			
			NotifyRecord notify = new NotifyRecord(
					event.getJobId(), event.getTitle(), 
					generateContent(event), to[i], event.getDate(),isImmediatelySend);
			
			notifyList.add(notify);
		}
		return notifyList;
	}

	/**
	 * �������ı��ļ�
	 * 
	 * @param event
	 * @return
	 */
	protected MimeMessagePreparator createMimeMessagePrpartor(NotifyRecord message) {
		return MailMessageFactory.createMimeMessage(message.getSubject(), message.getContent(), message.getTo(), message.getCreateDate());
	}

	/**
	 * �����ʼ���������
	 * 
	 * @param event
	 * @return
	 */
	protected String generateContent(NotifyEvent event) {
		return CONTENT_CONVERTER.convert(event).toString();
	}

	/**
	 * ���û�idת��Ϊ�ʼ��û���
	 * 
	 * @param ids
	 * @return
	 */
	public String[] idsToEamilUsernames(List ids) {
		return (String[]) ListArrayUtil.map(ids, new ObjectMapper() {
			public Object map(Object target) {
				PersonBO person = SysCacheTool.findPersonById((String) target);
				return person.getNameSpell();
			}
		}).toArray(new String[ids.size()]);
	}

	public static JavaMailSender createJavaMailSender() {
		MailConfiguration config = MailConfiguration.getInstance();
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setJavaMailProperties(config.getJavaMailProperties());
		sender.setUsername(config.getUsername());
		sender.setPassword(config.getPassword());
		return sender;
	}

	public static MailEventListener getInstance() {
		return INSTANCE;
	}
}