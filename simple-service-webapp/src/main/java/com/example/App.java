package com.example;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * @author Michal Gajdos
 */
@ApplicationPath("/")
public class App extends ResourceConfig {

	public App() {
		super(MyResource.class, MyStr.class, MultiPartFeature.class);
	}
}