package com.redhat.consultingbr.testekieserver.domain;

public class Greeting implements java.io.Serializable {


	static final long serialVersionUID = 1L;

	private final long id;
	private final String content;

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
