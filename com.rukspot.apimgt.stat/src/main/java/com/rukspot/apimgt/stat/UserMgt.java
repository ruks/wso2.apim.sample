package com.rukspot.apimgt.stat;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HttpTransportProperties.Authenticator;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceStub;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceUserStoreExceptionException;

public class UserMgt {
	RemoteUserStoreManagerServiceStub stub;
	static {
		System.setProperty("javax.net.ssl.trustStore",
				"/home/rukshan/wso2-jks/wso2carbon.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");

	}

	public static void main(String[] args) throws Exception {


//		TenantMgt m=new TenantMgt("admin","admin","https://localhost:9443");
//		m.addTenant("wso2.com", "pass", "rukshan", "usagePlan");
		
	}

	public void addUser(String user,String pass) throws RemoteException, RemoteUserStoreManagerServiceUserStoreExceptionException{
		stub.addUser(user, pass, new String[]{"admin"}, null, null, false);
	}
	
	public UserMgt(String user,String pass,String endpoint) {
		try {
			stub = new RemoteUserStoreManagerServiceStub(endpoint+"/services/RemoteUserStoreManagerService");
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		ServiceClient client = stub._getServiceClient();
		Options client_options = client.getOptions();
		Authenticator authenticator = new Authenticator();
		authenticator.setUsername(user);
		authenticator.setPassword(pass);
		authenticator.setPreemptiveAuthentication(true);
		client_options.setProperty(
				org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE,
				authenticator);
		client.setOptions(client_options);
	}
	
	
}
