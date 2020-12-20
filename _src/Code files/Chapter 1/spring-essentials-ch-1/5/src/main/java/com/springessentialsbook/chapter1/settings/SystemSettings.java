package com.springessentialsbook.chapter1.settings;

public class SystemSettings {

	private int openUserTasksMaxLimit;
	private String systemDateFormat;
	private String appDisplayName;
	private String adminEmail;

	public SystemSettings(int openUserTasksMaxLimit, String systemDateFormat, String appDisplayName, String adminEmail) {
		super();
		this.openUserTasksMaxLimit = openUserTasksMaxLimit;
		this.systemDateFormat = systemDateFormat;
		this.appDisplayName = appDisplayName;
		this.adminEmail = adminEmail;
	}


	public int getOpenUserTasksMaxLimit() {
		return openUserTasksMaxLimit;
	}


	public void setOpenUserTasksMaxLimit(int openUserTasksMaxLimit) {
		this.openUserTasksMaxLimit = openUserTasksMaxLimit;
	}


	public String getSystemDateFormat() {
		return systemDateFormat;
	}


	public void setSystemDateFormat(String systemDateFormat) {
		this.systemDateFormat = systemDateFormat;
	}


	public String getAppDisplayName() {
		return appDisplayName;
	}


	public void setAppDisplayName(String appDisplayName) {
		this.appDisplayName = appDisplayName;
	}


	public String getAdminEmail() {
		return adminEmail;
	}


	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}


	@Override
	public String toString() {
		return "SystemSettings [openUserTasksMaxLimit=" + openUserTasksMaxLimit
				+ ", systemDateFormat=" + systemDateFormat
				+ ", appDisplayName=" + appDisplayName + ", adminEmail="
				+ adminEmail + "]";
	}
}
