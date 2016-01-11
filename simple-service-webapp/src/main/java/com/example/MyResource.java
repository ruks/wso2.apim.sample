package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.gson.Gson;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

	static int count=0;
	static MyStr res=new MyStr();
	Gson gson = new Gson();
	// Create JAX-RS application.
	final Application application = new ResourceConfig().packages(
			"org.glassfish.jersey.examples.multipart").register(
			MultiPartFeature.class);

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces("application/json")
	public String getIt(@QueryParam("key") String key) {
		if (key != null) {
			return "{\"key\":\"" + key + "\"}";
		}
		return "{\"s\":\"Got it!\"}";
	}

	@GET
	@Path("/{any}")
	@Produces("application/json")
	public String any(@PathParam("any") String any) {
		if (any != null) {
			return "{\"any\":\"" + any + "\"}";
		}
		return "{\"s\":\"Got it!---" + any + "\"}";
	}

	@GET
	@Path("xml")
	@Produces("application/json")
	public Response xml() {
		String jsonString = gson.toJson(new MyStr("xml", "xml",
				"This is xml message!"));
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}

	@GET
	@Path("txt")
	@Produces("application/json")
	public Response txt() {
		String jsonString = gson.toJson(new MyStr("txt", "text",
				"This is text message!"));
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}

	@GET
	@Path("json")
	@Produces("application/json")
	public Response json() {
		String jsonString = gson.toJson(new MyStr("json", "json",
				"This is json message!"));
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}

	@GET
	@Path("str")
	@Produces("application/json")
	public Response str() {
		List<String> li = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			li.add(i + "");
		}

		String jsonString = gson.toJson(li);
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}

	@GET
	@Path("time")
	@Produces("application/json")
	public Response time() throws InterruptedException {
		List<String> li = new ArrayList<String>();
		Thread.sleep(60*1000);
		count++;
		li.add(count+"");
		String jsonString = gson.toJson(li);
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}
	
	@POST
	@Path("post")
	@Produces("application/json")
	@Consumes("application/json")
	public Response post(InputStream data) throws IOException {
		MyStr obj = gson.fromJson(new InputStreamReader(data), MyStr.class);
		String jsonString = gson.toJson(obj);
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}

	@GET
	@Path("obj")
	@Produces(MediaType.APPLICATION_JSON)
	public MyStr obj() {
		MyStr s = new MyStr("json", "json", "This is json message!");
		return s;
	}

	@POST
	@Path("part2")
	@Produces("multipart/mixed")
	public MultiPart post(final FormDataMultiPart multiPart) {
		return multiPart;
	}

	@GET
	@Path("part3")
	@Produces("multipart/related")
	@Consumes("application/json")
	public MultiPart post3() {
		MyStr s = new MyStr("json", "json", "This is json message!");
		BodyPart part2 = new BodyPart();
		part2.setMediaType(MediaType.TEXT_PLAIN_TYPE);
		part2.getHeaders().add("Content-Disposition",
				"form-data; name=\"pics\"; filename=\"file1.txt\"");
		part2.setEntity("... contents of file1.txt ...\r\n");

		MultiPart multiPartEntity = new MultiPart().bodyPart(
				new BodyPart(s, MediaType.APPLICATION_JSON_TYPE));
//				.bodyPart(part2);

		return multiPartEntity;
	}
	
	@GET
	@Path("text")
	@Produces("text/xml; charset=UTF-8")
	public Response text() {
		String s = "This is String message!";
		return Response.status(Response.Status.OK).entity(s).build();
	}

	@PUT
	@Path("put")
	@Produces("application/json")
	public Response putRes(InputStream data) {
		MyStr obj = gson.fromJson(new InputStreamReader(data), MyStr.class);
		res=obj;
		String jsonString = gson.toJson(obj);
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}
	
	@GET
	@Path("get")
	@Produces("application/json")
	public Response getRes() {
//		MyStr obj = gson.fromJson(new InputStreamReader(data), MyStr.class);
//		res.add(obj);
		String jsonString = gson.toJson(res);
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}
	
	@PATCH
	@Path("patch")
	@Produces("application/json")
	public Response getPatch(InputStream data) {
		MyStr obj = gson.fromJson(new InputStreamReader(data), MyStr.class);
		
		res.name=obj.name;
		String jsonString = gson.toJson(res);
		return Response.status(Response.Status.OK).entity(jsonString).build();
	}
}
