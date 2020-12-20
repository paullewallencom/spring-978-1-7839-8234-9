package com.springessentials.chapter3.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
public class File {

	@Id
	private String id;

	private String fileName;

	public File() {
		super();
	}

	public File(String id, String fileName) {
		super();
		this.id = id;
		this.fileName = fileName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "File [id=" + id + ", fileName=" + fileName + "]";
	}

}
