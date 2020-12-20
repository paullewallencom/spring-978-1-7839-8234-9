package com.taskify.web.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.taskify.domain.User;

@XmlRootElement(name = "CreateUserRequest")
public class CreateUserRequest implements Serializable {

	private User user;

	public CreateUserRequest() {
		super();
	}

	public CreateUserRequest(User user) {
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
		return "CreateUserRequest [user=" + user + "]";
	}
	
}
