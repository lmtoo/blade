package cn.accessbright.blade.service;

public class ChangePasswordRequest implements CredentialHolder {

	private String originalPassword;

	public String getOriginalPassword() {
		return originalPassword;
	}

	public void setOriginalPassword(String originalPassword) {
		this.originalPassword = originalPassword;
	}

	public String getCredential() {
		return null;
	}

	public CharSequence getPassword() {
		return null;
	}

}
