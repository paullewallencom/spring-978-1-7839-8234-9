package com.taskify.web.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.taskify.domain.User;

@XmlRootElement(name = "UpdateUserRequest")
public class UpdateUserRequest implements Serializable {

	private User user;

	public UpdateUserRequest() {
		super();
	}

	public UpdateUserRequest(User user) {
		super();
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "UpdateUserRequest [user=" + user + "]";
	}
	
}
