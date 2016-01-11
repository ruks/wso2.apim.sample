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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Publisher {

	private final CloseableHttpClient httpClient;
	private final String USER_AGENT = "Apache-HttpClient/4.2.5 (java 1.5)";
	String host;
	String loginUrl = "https://localhost:9443/publisher/site/blocks/user/login/ajax/login.jag";
	String apiCreateUrl = "https://localhost:9443/publisher/site/blocks/item-add/ajax/add.jag?";
	String apiPublishUrl = "https://localhost:9443/publisher/site/blocks/life-cycles/ajax/life-cycles.jag?";
	String apiRemoveUrl = "https://localhost:9443/publisher/site/blocks/item-add/ajax/remove.jag";

	private List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	private HttpResponse response;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.setProperty(
				"javax.net.ssl.trustStore",
				"/home/rukshan/wso2-jks/wso2carbon.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");

		Publisher c = new Publisher("https://localhost:9443");
		c.login();
		// c.removeApi("sampleApi3");
		String e = "{\"production_endpoints\":{\"url\":\"http://localhost:8080/simple-service-webapp/webapi/myresource\",\"config\":null},\"endpoint_type\":\"http\"}";
		c.createAPI("api1", e, Constant.Unlimited);
		c.publishAPI("api1", "admin");
	}

	public Publisher(String host) {
		// TODO Auto-generated constructor stub
		httpClient = HttpClients.custom()
	            .setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
	            .build();
		
//		.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)

		loginUrl = host + "/publisher/site/blocks/user/login/ajax/login.jag";
		apiCreateUrl = host + "/publisher/site/blocks/item-add/ajax/add.jag?";
		apiPublishUrl = host
				+ "/publisher/site/blocks/life-cycles/ajax/life-cycles.jag?";
		apiRemoveUrl = host + "/publisher/site/blocks/item-add/ajax/remove.jag";

	}

	public void publishAPI(String name, String provider) throws Exception {
		urlParameters.clear();
		urlParameters.add(new BasicNameValuePair("action", "updateStatus"));
		urlParameters.add(new BasicNameValuePair("name", name));

		urlParameters.add(new BasicNameValuePair("version", "1.0.0"));
		urlParameters.add(new BasicNameValuePair("provider", provider));
		urlParameters.add(new BasicNameValuePair("status", "PUBLISHED"));
		urlParameters.add(new BasicNameValuePair("publishToGateway", "true"));
		urlParameters.add(new BasicNameValuePair("requireResubscription",
				"true"));

		response = sendPOSTMessage(apiPublishUrl, urlParameters);
		System.out.println(getResponseBody(response));
		EntityUtils.consume(response.getEntity());
	}

	public void createAPI(String name, String endp, String tier)
			throws Exception {
		// TODO Auto-generated method stub
		urlParameters.clear();
		urlParameters.add(new BasicNameValuePair("name", name));
		urlParameters.add(new BasicNameValuePair("context", name));
		urlParameters.add(new BasicNameValuePair("version", "1.0.0"));
		urlParameters.add(new BasicNameValuePair("visibility", "public"));
		urlParameters.add(new BasicNameValuePair("endpointType", "nonsecured"));
		urlParameters.add(new BasicNameValuePair("roles", ""));
		urlParameters.add(new BasicNameValuePair("apiThumb", ""));
		urlParameters.add(new BasicNameValuePair("description",
				"APIDescription"));
		urlParameters.add(new BasicNameValuePair("http_checked", "http"));
		urlParameters.add(new BasicNameValuePair("https_checked", "https"));
		urlParameters.add(new BasicNameValuePair("tags",
				"WSO2,APIM,IS,CrossProduct,PlatformTests"));
		urlParameters.add(new BasicNameValuePair("action", "addAPI"));
		urlParameters
				.add(new BasicNameValuePair("destinationStats", "Enabled"));

		urlParameters.add(new BasicNameValuePair("resourceCount", "0"));
		urlParameters.add(new BasicNameValuePair("resourceMethod-0", "GET"));
		urlParameters.add(new BasicNameValuePair("resourceMethodAuthType-0",
				"Application"));
		urlParameters.add(new BasicNameValuePair(
				"resourceMethodThrottlingTier-0", "Unlimited"));
		urlParameters.add(new BasicNameValuePair("uriTemplate-0", "/*"));

		urlParameters.add(new BasicNameValuePair("tiersCollection", tier));
		urlParameters.add(new BasicNameValuePair("endpoint_config", endp));

		response = sendPOSTMessage(apiCreateUrl, urlParameters);
		System.out.println(getResponseBody(response) + " as");
		EntityUtils.consume(response.getEntity());
	}

	public void removeApi(String api) throws Exception {
		// TODO Auto-generated method stub
		urlParameters.clear();
		urlParameters.add(new BasicNameValuePair("action", "removeAPI"));
		urlParameters.add(new BasicNameValuePair("name", api));

		urlParameters.add(new BasicNameValuePair("version", "1.0.0"));
		urlParameters.add(new BasicNameValuePair("provider", "admin"));

		response = sendPOSTMessage(apiRemoveUrl, urlParameters);
		System.out.println(getResponseBody(response));
		EntityUtils.consume(response.getEntity());
	}

	public void login() throws Exception {
		// TODO Auto-generated method stub
		urlParameters.clear();
		urlParameters.add(new BasicNameValuePair("action", "login"));
		urlParameters.add(new BasicNameValuePair("username", "admin"));
		urlParameters.add(new BasicNameValuePair("password", "admin"));

		response = sendPOSTMessage(loginUrl, urlParameters);
		// System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(getResponseBody(response));
		EntityUtils.consume(response.getEntity());
	}

	public void login(String user, String pass) throws Exception {
		// TODO Auto-generated method stub
		urlParameters.clear();
		urlParameters.add(new BasicNameValuePair("action", "login"));
		urlParameters.add(new BasicNameValuePair("username", user));
		urlParameters.add(new BasicNameValuePair("password", pass));

		response = sendPOSTMessage(loginUrl, urlParameters);
		// System.out.println(response.getStatusLine().getStatusCode());
		System.out.println(getResponseBody(response));
		EntityUtils.consume(response.getEntity());
	}

	private HttpResponse sendGetRequest(String url) throws Exception {
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", USER_AGENT);
		return httpClient.execute(request);
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
