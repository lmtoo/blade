package cn.accessbright.blade.core.mail;

import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

/**
 * �ʼ�����
 * 
 * @author ll
 * 
 */
public class MailMessageFactory {

	public static SimpleMailMessage createSimpleMailMessage(Object subject, Object content, String[] to) {
		return createSimpleMailMessage(subject, content, to, new Date());
	}

	public static SimpleMailMessage createSimpleMailMessage(Object subject, Object content, String to) {
		return createSimpleMailMessage(subject, content, new String[] { to });
	}

	/**
	 * ���ݲ��������ʼ�����
	 * 
	 * @param subject
	 *            �ʼ�����
	 * @param content
	 *            �ʼ�����
	 * @param to
	 *            �ʼ����յ�ַ�����ʼ������û���
	 * @param sendDate
	 *            ����ʱ��
	 * @return �ʼ�����
	 */
	public static SimpleMailMessage createSimpleMailMessage(Object subject, Object content, String[] to, Date sendDate) {
		MailConfiguration config = MailConfiguration.getInstance();

		String from = config.getUsername();
		String host = config.getAddress();
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setSentDate(sendDate);
		message.setSubject(subject.toString());
		message.setText(content.toString());

		String[] toAddress = new String[to.length];
		for (int i = 0; i < to.length; i++) {
			String address = to[i];
			if (address.indexOf('@') < 0) {
				address = address + "@" + host;
			}
			toAddress[i] = address;
		}

		message.setTo(toAddress);
		return message;
	}

	public static MimeMessagePreparator createMimeMessage(final Object subject, final Object content, final String[] to, final Date sendDate) {
		return new MimeMessagePreparator() {
			public void prepare(MimeMessage message) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

				MailConfiguration config = MailConfiguration.getInstance();

				String from = config.getUsername();
				String host = config.getAddress();

				helper.setFrom(new InternetAddress(from));
				helper.setSentDate(sendDate);
				helper.setSubject(subject.toString());
				helper.setText(content.toString(), true);
				InternetAddress[] toAddress = new InternetAddress[to.length];
				for (int i = 0; i < to.length; i++) {
					String address = to[i];
					if (address.indexOf('@') < 0) {
						address = address + "@" + host;
					}
					toAddress[i] = new InternetAddress(address);
				}
				helper.setTo(toAddress);
			}
		};
	}

	public static MimeMessagePreparator createMimeMessage(final Object subject, final Object content, final String address, final Date sendDate) {
		return new MimeMessagePreparator() {
			public void prepare(MimeMessage message) throws Exception {
				MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

				MailConfiguration config = MailConfiguration.getInstance();

				String from = config.getUsername();
				String host = config.getAddress();

				helper.setFrom(new InternetAddress(from));
				helper.setSentDate(sendDate);
				helper.setSubject(subject.toString());
				helper.setText(content.toString(), true);

				String to = address;
				if (address.indexOf('@') < 0) {
					to = address + "@" + host;
				}
				helper.setTo(new InternetAddress(to));
			}
		};
	}
}