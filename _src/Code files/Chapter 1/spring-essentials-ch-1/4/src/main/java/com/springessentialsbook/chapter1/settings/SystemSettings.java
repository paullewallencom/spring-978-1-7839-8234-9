package com.springessentialsbook.chapter1.settings;

public class SystemSettings {

	private int openUserTasksMaxLimit;
	private String systemDateFormat;
	private String appDisplayName;

	public SystemSettings(int openUserTasksMaxLimit, String systemDateFormat, String appDisplayName) {
		super();
		this.openUserTasksMaxLimit = openUserTasksMaxLimit;
		this.systemDateFormat = systemDateFormat;
		this.appDisplayName = appDisplayName;
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


	@Override
	public String toString() {
		return "SystemSettings [openUserTasksMaxLimit=" + openUserTasksMaxLimit + ", systemDateFormat=" + systemDateFormat + ", appDisplayName=" + appDisplayName + "]";
	}
}
