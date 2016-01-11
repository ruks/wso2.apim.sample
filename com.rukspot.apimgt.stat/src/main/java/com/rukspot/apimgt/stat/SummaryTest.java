package com.rukspot.apimgt.stat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class SummaryTest {

	private final String USER_AGENT = "Apache-HttpClient/4.2.5 (java 1.5)";
	private HttpResponse response;
	private CloseableHttpClient httpClient;
	private List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	private static String api_usage_user="https://localhost:9443/publisher/site/blocks/stats/api-usage-user/ajax/stats.jag";
	@Before
	public void before() {
		httpClient = HttpClients.createDefault();
	}

	@Test
	public void test() throws Exception {
		System.setProperty(
				"javax.net.ssl.trustStore",
				"/home/rukshan/apim/1/wso2am-1.9.1_0/repository/resources/security/wso2carbon.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
		Publisher pub = new Publisher("");
		pub.login();

		urlParameters.clear();
		urlParameters.add(new BasicNameValuePair("action", "updateStatus"));
		urlParameters.add(new BasicNameValuePair("name", "name"));

		response = sendPOSTMessage(api_usage_user, urlParameters);
		System.out.println(getResponseBody(response));
		EntityUtils.consume(response.getEntity());
	}

	private HttpResponse sendPOSTMessage(String url,
			List<NameValuePair> urlParameters) throws Exception {
		
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		post.addHeader("Referer", url);
//		post.addHeader("Cookie","JSESSIONID=7FF852DC9367FE6AB0C8EE98124BD6BD;");
//		post.addHeader("X-CSRFToken","8g0t12u9hd629aji9sod3qrdta");
		
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
