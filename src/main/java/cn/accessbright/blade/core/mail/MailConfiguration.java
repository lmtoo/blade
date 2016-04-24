package cn.accessbright.blade.core.mail;

import java.util.Properties;


public class MailConfiguration {
	private static MailConfiguration INSTANCE;

	private String username;
	private String password;
	private String address;
	private String smtpHost;
	private String auth;

	private MailConfiguration() {
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