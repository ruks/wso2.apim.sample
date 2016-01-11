package com.example;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MyStr {
	@XmlElement
	String name = "json";

	@XmlElement
	String type = "json";

	@XmlElement
	String msg = "json";

	public MyStr(String name, String type, String msg) {
		super();
		this.name = name;
		this.type = type;
		this.msg = msg;
	}

	public MyStr() {
	}
}
