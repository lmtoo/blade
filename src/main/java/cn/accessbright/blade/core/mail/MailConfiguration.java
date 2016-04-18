package cn.accessbright.blade.core.mail;

import java.util.Properties;

import com.icitic.hrms.cache.SysCacheTool;

public class MailConfiguration {
	private static MailConfiguration INSTANCE;

	private String username;
	private String password;
	private String address;
	private String smtpHost;
	private String auth;

	private MailConfiguration() {
		username = SysCacheTool.getSysParameter("MAIL_HR_USERNAME");
		password = SysCacheTool.getSysParameter("MAIL_HR_PASSWORD");
		address = SysCacheTool.getSysParameter("MAIL_HR_ADDRESS");
		smtpHost = SysCacheTool.getSysParameter("MAIL_SMTP_HOST");
		auth = SysCacheTool.getSysParameter("MAIL_SMTP_AUTH");
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getAddress() {
		return address;
	}

	public Properties getJavaMailProperties() {
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", auth);
		prop.setProperty("mail.smtp.host", smtpHost);
		return prop;
	}

	public static MailConfiguration getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MailConfiguration();
		}
		return INSTANCE;
	}
}