package com.rukspot.apimgt.stat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Store {
	private final CloseableHttpClient httpClient;
	private final String USER_AGENT = "Apache-HttpClient/4.2.5 (java 1.5)";
	String host;
	String loginUrl = "https://localhost:9443/store/site/blocks/user/login/ajax/login.jag";
	String addAppUrl = "https://localhost:9443/store/site/blocks/application/application-add/ajax/application-add.jag";
	String keyGenUrl = "https://localhost:9443/store/site/blocks/subscription/subscription-add/ajax/subscription-add.jag";
	String subscribeUrl = "https://localhost:9443/store/site/blocks/subscription/subscription-add/ajax/subscription-add.jag";
	String unSubscribeUrl = "https://localhost:9443/store/site/blocks/subscription/subscription-remove/ajax/subscription-remove.jag";
	String removeAppUrl = "https://localhost:9443/store/site/blocks/application/application-remove/ajax/application-remove.jag";
	List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	HttpResponse response;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.setProperty(
				"javax.net.ssl.trustStore",
				"/home/rukshan/wso2-jks/wso2carbon.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");

		String url = "https://localhost:9443";
		Store c = new Store(url);
//		c.login("rukshan@wso2.com","rukshan");
		// c.invokeApi();
//		c.addApplication("app3");
//		c.generateKey("app3");
//		for (int i = 0; i < 10; i++) {
//			String s="WSO2_com_App_"+i;
//			c.removeApplication(s);
//		}
		
		// c.subscribe();
		
		for (int i = 0; i < 10; i++) {
			HttpResponse response=c.sendGetRequest("https://localhost:8243/calc/1.0/add?x=1&y=1");
			String msg = c.getResponseBody(response);
			System.out.println(msg);
		}
	}

	public Store(String host) {
		// TODO Auto-generated constructor stub
		httpClient = HttpClients.custom()
	            .setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
	            .build();
		
		loginUrl = host + "/store/site/blocks/user/login/ajax/login.jag";
		addAppUrl = host
				+ "/store/site/blocks/application/application-add/ajax/application-add.jag";
		keyGenUrl = host
				+ "/store/site/blocks/subscription/subscription-add/ajax/subscription-add.jag";
		subscribeUrl = host
				+ "/store/site/blocks/subscription/subscription-add/ajax/subscription-add.jag";
		removeAppUrl = host
				+ "/store/site/blocks/application/application-remove/ajax/application-remove.jag";
	}

	public void invokeApi(String api, String path, String key,String version,String tenant) throws Exception {
		// TODO Auto-generated method stub
//		response = sendGetRequest("https://localhost:8243"+tenant + api +version+path, key);
		System.out.println("https://192.168.54.84:8243/"+(tenant.equals("carbon.super") ? "" : "t/"+tenant+"/")+api+"/"+version+"/"+path);
		response = sendGetRequest("https://192.168.54.84:8243/"+(tenant.equals("carbon.super") ? "" : "t/"+tenant+"/")+api+"/"+version+"/"+path, key);
		
//		https://192.168.56.1:8243/calc/1.0/add?x=1&y=1		
//		https://192.168.56.1:8243/t/rukspot.com/calc/1.0

		String msg = getResponseBody(response);
		System.out.println(msg);
		EntityUtils.consume(response.getEntity());
	}
	
	public void invokeApi(String url,String key) throws Exception {
		// TODO Auto-generated method stub
		response = sendGetRequest(url,key);

		String msg = getResponseBody(response);
		System.out.println(msg);
		EntityUtils.consume(response.getEntity());
	}

	public void subscribe(String api, String app, String tier,String provider) throws Exception {
		// TODO Auto-generated method stub

		urlParameters.clear();
		urlParameters
				.add(new BasicNameValuePair("action", "addAPISubscription"));
		urlParameters.add(new BasicNameValuePair("name", api));
		urlParameters.add(new BasicNameValuePair("version", "1.0.0"));
		urlParameters.add(new BasicNameValuePair("provider", provider));
		urlParameters.add(new BasicNameValuePair("tier", tier));
		urlParameters.add(new BasicNameValuePair("applicationName", app));

		response = sendPOSTMessage(subscribeUrl, urlParameters);
		System.out.println(getResponseBody(response));
		EntityUtils.consume(response.getEntity());
	}

	public void addApplication(String name) throws Exception {
		urlParameters.clear();
		urlParameters.add(new BasicNameValuePair("action", "addApplication"));
		urlParameters.add(new BasicNameValuePair("application", name));
		urlParameters.add(new BasicNameValuePair("tier", "Unlimited"));
		urlParameters.add(new BasicNameValuePair("description", ""));
		urlParameters.add(new BasicNameValuePair("callbackUrl", ""));

		response = sendPOSTMessage(addAppUrl, urlParameters);
		System.out.println(getResponseBody(response));
		EntityUtils.consume(response.getEntity());
	}

	public String generateKey(String app) throws Exception {
		urlParameters.clear();
		urlParameters.add(new BasicNameValuePair("action",
				"generateApplicationKey"));
		urlParameters.add(new BasicNameValuePair("application", app));
		urlParameters.add(new BasicNameValuePair("keytype", "PRODUCTION"));
		urlParameters.add(new BasicNameValuePair("callbackUrl", ""));
		urlParameters.add(new BasicNameValuePair("authorizedDomains", "ALL"));
		urlParameters.add(new BasicNameValuePair("validityTime", "-1"));

		response = sendPOSTMessage(keyGenUrl, urlParameters);
		String res = getResponseBody(response);

		Pattern pattern = Pattern.compile("\"accessToken\" : \"(\\w|\\d)+\"");
		Matcher matcher = pattern.matcher(res);
		String key = null;
		if (matcher.find()) {
			key = matcher.group(0).split(":")[1].trim().replace("\"", "");
		}

		EntityUtils.consume(response.getEntity());
		return key;
	}

	public void removeApplication(String app) throws Exception {
		// TODO Auto-generated method stub
		urlParameters.clear();
		urlParameters
				.add(new BasicNameValuePair("action", "removeApplication"));
		urlParameters.add(new BasicNameValuePair("application", app));

		response = sendPOSTMessage(removeAppUrl, urlParameters);
		System.out.println(getResponseBody(response));
		EntityUtils.consume(response.getEntity());
	}

	public void login() throws Exception {
		// TODO Auto-generated method stub
		urlParameters.add(new BasicNameValuePair("action", "login"));
		urlParameters.add(new BasicNameValuePair("username", "admin"));
		urlParameters.add(new BasicNameValuePair("password", "admin"));

		response = sendPOSTMessage(loginUrl, urlParameters);
		System.out.println(getResponseBody(response));
		EntityUtils.consume(response.getEntity());
	}
	
	public void login(String user,String pass) throws Exception {
		// TODO Auto-generated method stub
		urlParameters.add(new BasicNameValuePair("action", "login"));
		urlParameters.add(new BasicNameValuePair("username", user));
		urlParameters.add(new BasicNameValuePair("password", pass));

		response = sendPOSTMessage(loginUrl, urlParameters);
		System.out.println(getResponseBody(response));
		EntityUtils.consume(response.getEntity());
	}
	
	public String removeSubscription(String api,String provider,String version,String app) throws Exception {
		urlParameters.clear();
		urlParameters.add(new BasicNameValuePair("action",
				"removeSubscription"));
		urlParameters.add(new BasicNameValuePair("name", api));
		urlParameters.add(new BasicNameValuePair("version", version));
		urlParameters.add(new BasicNameValuePair("provider", provider));
		urlParameters.add(new BasicNameValuePair("applicationId", app));
		
		response = sendPOSTMessage(unSubscribeUrl, urlParameters);
		String res = getResponseBody(response);
		System.out.println(res);
		Pattern pattern = Pattern.compile("\"accessToken\" : \"(\\w|\\d)+\"");
		Matcher matcher = pattern.matcher(res);
		String key = null;
		if (matcher.find()) {
			key = matcher.group(0).split(":")[1].trim().replace("\"", "");
		}

		EntityUtils.consume(response.getEntity());
		return key;
	}

	private HttpResponse sendPOSTMessage(String url,
			List<NameValuePair> urlParameters) throws Exception {
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		post.addHeader("Referer", url);
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		return httpClient.execute(post);
	}

	private HttpResponse sendGetRequest(String url, String bearer)
			throws Exception {
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader("Accept", "application/json");
		request.addHeader("Authorization", "Bearer " + bearer);

//		System.out.println(request.getURI());
		return httpClient.execute(request);
	}
	
	private HttpResponse sendGetRequest(String url)
			throws Exception {
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", USER_AGENT);
		request.addHeader("Accept", "application/json");
//		request.addHeader("Authorization", "Bearer " + bearer);

//		System.out.println(request.getURI());
		return httpClient.execute(request);
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
