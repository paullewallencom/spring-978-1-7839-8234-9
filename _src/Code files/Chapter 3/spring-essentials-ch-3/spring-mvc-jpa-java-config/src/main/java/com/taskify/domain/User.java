package com.taskify.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "TBL_USER", uniqueConstraints = @UniqueConstraint(name = "UK_USER_USERNAME", columnNames = {
		"USER_NAME" }) )
public class User {

	@Id
	@SequenceGenerator(name = "SEQ_USER", sequenceName = "SEQ_USER", allocationSize = 1, initialValue = 1001)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER")
	private Long id;

	@Column(name = "NAME", length = 200)
	private String name;

	@Column(name = "USER_NAME", length = 25)
	private String userName;

	@Column(name = "PASSWORD", length = 20)
	private String password;

	@Column(name = "DOB")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfBirth;

	@ManyToOne(optional = true)
	@JoinColumn(name = "FILE_ID", referencedColumnName = "ID")
	private File profileImage;

	public User() {
	}

	public User(Long id, String name, String userName, String password, Date dateOfBirth) {
		super();
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public File getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(File profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", userName=" + userName + ", password=" + password
				+ ", dateOfBirth=" + dateOfBirth + ", profileImage=" + profileImage + "]";
	}

}
