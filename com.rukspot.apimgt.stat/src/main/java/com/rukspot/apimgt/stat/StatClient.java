package com.rukspot.apimgt.stat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class StatClient {
	private final CloseableHttpClient httpClient;
	private final String USER_AGENT = "Apache-HttpClient/4.2.5 (java 1.5)";
	static String loginUrl = "https://localhost:9443/store/site/blocks/user/login/ajax/login.jag";
	
	static String enableUrl ="https://localhost:9443/store/site/blocks/stats/perAppAPICount/ajax/stats.jag";
	
	private List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	private HttpResponse response;

	static {
		System.setProperty(
				"javax.net.ssl.trustStore",
				"/home/rukshan/apim/1.10.x/wso2am-1.10.0-SNAPSHOT/repository/resources/security/wso2carbon.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");

	}
	public static void main(String[] args) throws Exception {
		StatClient c=new StatClient();
		c.login();
		c.isDataPublishingEnabled();
		c.getProviderAPIUsage();
	}
	
	public StatClient() {
		// TODO Auto-generated constructor stub
		httpClient = HttpClients.createDefault();
	}

	public void login() throws Exception {
		// TODO Auto-generated method stub
		urlParameters.add(new BasicNameValuePair("action", "login"));
		urlParameters.add(new BasicNameValuePair("username", "admin"));
		urlParameters.add(new BasicNameValuePair("password", "admin"));

		response = sendPOSTMessage(loginUrl, urlParameters);
		System.out.println(getResponseBody(response));
		Header[] hds=response.getAllHeaders();
//		for (Header header : hds) {
//			System.out.println(header);
//		}
		
		EntityUtils.consume(response.getEntity());
	}
	
	public void isDataPublishingEnabled() throws Exception{
		urlParameters.clear();
		urlParameters.add(new BasicNameValuePair("action", "isDataPublishingEnabled"));

		response = sendPOSTMessage(enableUrl, urlParameters);
		System.out.println(getResponseBody(response));
	
		EntityUtils.consume(response.getEntity());
	}
	
	public void getProviderAPIUsage() throws Exception{
		urlParameters.clear();
		urlParameters.add(new BasicNameValuePair("action", "getProviderAPIUsage"));
//		urlParameters.add(new BasicNameValuePair("currentLocation", "getProviderAPIUsage"));
		urlParameters.add(new BasicNameValuePair("fromDate", "2015-10-01"));
		urlParameters.add(new BasicNameValuePair("toDate", "2015-10-05"));
	
		
		response = sendPOSTMessage(enableUrl, urlParameters);
		System.out.println(getResponseBody(response));
	
		EntityUtils.consume(response.getEntity());
	}
	
	private HttpResponse sendPOSTMessage(String url,
			List<NameValuePair> urlParameters) throws Exception {
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		post.addHeader("Referer", url);
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		return httpClient.execute(post);
	}
	private String getResponseBody(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent(), "UTF-8"));
		String line;
		StringBuffer sb = new StringBuffer();

		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		return sb.toString();
	}
}
