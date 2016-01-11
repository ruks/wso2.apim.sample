package com.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("sql")
public class Stat {
	
	@GET
	public String getResult(){
		return "rukshan";
	}
}
