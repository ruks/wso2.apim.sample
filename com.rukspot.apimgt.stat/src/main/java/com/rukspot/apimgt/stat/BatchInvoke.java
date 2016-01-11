package com.rukspot.apimgt.stat;

import java.rmi.RemoteException;

import org.wso2.carbon.tenant.mgt.stub.TenantMgtAdminServiceExceptionException;
import org.wso2.carbon.um.ws.api.stub.RemoteUserStoreManagerServiceUserStoreExceptionException;

public class BatchInvoke {

	Publisher pub;
	Store store;
	static int start = 0;
	static int stop = 10;
	static int call = 10;
	static String endpoint = "https://localhost:9443";

	static String superAdmin = "admin";
	static String superAdminPass = "admin";

	static String pub1 = "pub1";
	static String pub1Pass = "pub1@";
	static String pub2 = "pub2";
	static String pub2Pass = "pub2@";
	
	static String user1 = "user1";
	static String user1Pass = "user1@";
	static String user2 = "user2";
	static String user2Pass = "user2@";

	static String wso2Tenant = "wso2.com";
	static String wso2AdminId = "rukshan";
	static String wso2Admin = "rukshan@" + wso2Tenant;
	static String wso2AdminPass = "rukshan";

	static String wso2User1 = "user1";
	static String wso2User1Pass = "user1@";
	static String wso2Pub1 = "pub1";
	static String wso2Pub1Pass = "pub1@";
	
	static String wso2User2 = "user2";
	static String wso2User2Pass = "user2@";
	static String wso2Pub2 = "pub2";
	static String wso2Pub2Pass = "pub2@";
	
//	static String backend = "{\"production_endpoints\":{\"url\":\"http://localhost:8080/simple-service-webapp/webapi/myresource\",\"config\":null},\"endpoint_type\":\"http\"}";
	static String backend = "{\"production_endpoints\":{\"url\":\""
			+ "http://192.168.54.84:9763/simple-service-webapp/webapi/myresource"
			+ "\",\"config\":null},\"endpoint_type\":\"http\"}";
	static String url = "https://192.168.54.84:9443";
	public static void main(String[] args) throws Exception {
		System.setProperty("javax.net.ssl.trustStore",
				"/home/rukshan/wso2-jks/wso2carbon.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");

		String url = "https://localhost:9453";
		url = "https://192.168.54.84:9443";

		BatchInvoke run = new BatchInvoke(url);

		start = 2;
		stop = start + 1;
		call = 10;

		// boolean isCreateOnly = false;
		String prefixApi = "sampleApi_";
		String prefixApp = "sampleApp_";
		String version = "1.0.0";
		String tenant = "carbon.super";
		// String u="admin";
		// String p="admin";

		String u = "rukshan@wso2.com";
		String p = "rukshan@wso2.com";

//		run.setUp();
		// run.start(true,prefixApi,prefixApp,u,p,"t/wso2.com");
		// run.start(false); //for create API only
		// run.clean(u,p);
		
//		UserMgt uMgt1 = new UserMgt(superAdmin, superAdminPass, endpoint);
//		uMgt1.addUser(pub1,pub1Pass);
//		uMgt1.addUser(user1,user1Pass);
		
//		UserMgt uMgt2 = new UserMgt(wso2Admin, wso2AdminPass, endpoint);
//		uMgt2.addUser(wso2User1, wso2User1Pass);
//		uMgt2.addUser(wso2Pub1, wso2Pub11Pass);
		
		String offset="cloud_"+4;
		String k;
//		
//		run.createApi(wso2Pub1+"@"+wso2Tenant,wso2Pub1Pass,"api"+offset);
//		k=run.addApp(wso2User1+"@"+wso2Tenant, wso2User1Pass, "app"+offset, "api"+offset, wso2Pub1+"@"+wso2Tenant);
//		run.invokeApi("app"+offset, "api"+offset, version, wso2Tenant, k);
//		k=run.addApp(wso2User2+"@"+wso2Tenant, wso2User2Pass, "app"+offset, "api"+offset, wso2Pub1+"@"+wso2Tenant);
//		run.invokeApi("app"+offset, "api"+offset, version, wso2Tenant, k);
		
//		offset="_wso2_two"+1;
//		run.createApi(wso2Pub2+"@"+wso2Tenant,wso2Pub2Pass,"api"+offset);
//		k=run.addApp(wso2User1+"@"+wso2Tenant, wso2User1Pass, "app"+offset, "api"+offset, wso2Pub2+"@"+wso2Tenant);
//		run.invokeApi("app"+offset, "api"+offset, version, wso2Tenant, k);
//		k=run.addApp(wso2User2+"@"+wso2Tenant, wso2User2Pass, "app"+offset, "api"+offset, wso2Pub2+"@"+wso2Tenant);
//		run.invokeApi("app"+offset, "api"+offset, version, wso2Tenant, k);
		
//		run.createApi(pub1,pub1Pass,"api"+offset);
//		String k1=run.addApp(user1, user1Pass, "app"+offset, "api"+offset, pub1);
//		run.invokeApi("app"+offset, "api"+offset, version, tenant, k1);
		
		for (int i = 0; i < 10000; i++) {
			offset="_cloud_"+i;
			run.createApi(superAdmin,superAdminPass,"api"+offset);
			k=run.addApp(superAdmin, superAdminPass, "app"+offset, "api"+offset, superAdmin);
			run.invokeApi("app"+offset, "api"+offset, version, tenant, k);
		}
//		run.createApi(superAdmin,superAdminPass,"api"+offset);
//		String k=run.addApp("user_1", "user_1@", "app"+offset, "api"+offset, superAdmin);
//		run.invokeApi("app"+offset, "api"+offset, version, tenant, k);
		
//		run.invokeApi("app"+offset, "api"+offset, version, wso2Tenant, "a73d9843e928e0554632583fcf3c074b");
		
//		run.createApi(wso2Admin,wso2AdminPass,"api"+offset);
//		String k1 = run.addApp(wso2Admin, wso2AdminPass, "app" + offset, "api"
//				+ offset, wso2Admin);
//		run.invokeApi("app"+offset, "api"+offset, version, wso2Tenant, k1);
		
	}

	private void setUp() throws RemoteException,
			TenantMgtAdminServiceExceptionException,
			RemoteUserStoreManagerServiceUserStoreExceptionException {

		TenantMgt mgt = new TenantMgt(superAdmin, superAdminPass, endpoint);
		mgt.addTenant(wso2Tenant, wso2AdminPass, wso2AdminId, "usagePlan");

		UserMgt uMgt1 = new UserMgt(superAdmin, superAdminPass, endpoint);
//		uMgt1.addUser(pub1,pub1Pass);
//		uMgt1.addUser(user1,user1Pass);
//		uMgt1.addUser(pub2,pub2Pass);
//		uMgt1.addUser(user2,user2Pass);

		UserMgt uMgt2 = new UserMgt(wso2Admin, wso2AdminPass, endpoint);
		uMgt2.addUser(wso2User1, wso2User1Pass);
		uMgt2.addUser(wso2User2, wso2User2Pass);
		uMgt2.addUser(wso2Pub1, wso2Pub1Pass);
		uMgt2.addUser(wso2Pub2, wso2Pub2Pass);

	}

	private void regen() throws Exception {
		String api = "WeatherAPI";
		String app = "DefaultApplication";
		store.login("admin", "admin");
		store.subscribe(api, app, Constant.Unlimited, "admin");
		String key = store.generateKey(app);
		// System.out.println(key);
		// store.removeSubscription(api,"admin", "1.0.0","1");
		// key = store.generateKey(app);
		// System.out.println(key);
	}

	private void exe() throws Exception {

		// String[][] gen = {
		// { "CalculatorAPI", "cross-app",
		// "64e5c32ad2947e359c4e22bb2dfb4f99" } };
		String[][] gen = { { "time", "DefaultApplication",
				"a18642babba0120c2e97eb9ae60a1f0b" } };

		// String[][] gen = {
		// { "CalculatorAPI", "custom-api",
		// "0e5ddee3004be5a6a175ee490633a975" } };

		// String[][] gen = {
		// { "CalculatorAPI", "custom-res",
		// "0affbfca476a2fa5d287204de1935e79" } };

		for (int i = 0; i < 1; i++) {

			String[] ss = gen[i];
			String key = ss[2];
			String api = ss[0];
			String app = ss[1];
			for (int j = 0; j < 100000000; j++) {
				// store.invokeApi("calc", "/add?x=1&y=1", key,
				// "/1.0","/t/wso2.com/");
				store.invokeApi("https://localhost:8243/time/1",
						"a18642babba0120c2e97eb9ae60a1f0b");
				System.out.println("executed " + j);
				// store.invokeApi("/"+api, "/"+app, key, "/1.0.0","/"+tenant);
			}

		}
	}

	private void manual() throws Exception {
		String end = "{\"production_endpoints\":{\"url\":\"http://localhost:8080/simple-service-webapp/webapi/myresource\",\"config\":null},\"endpoint_type\":\"http\"}";

		String api = "pub1_myapi1";
		String app = "pub1_myapp1";

		pub.login("admin", "admin");
		pub.createAPI(api, end, Constant.Unlimited);
		pub.publishAPI(api, "admin");

		// String api_2=api+"_2";
		// pub.createAPI(api_2, end,Constant.TIER50);
		// pub.publishAPI(api_2);

		store.login("admin", "admin");
		store.addApplication(app);
		store.subscribe(api, app, Constant.Unlimited, "admin");
		// store.subscribe(api_2, app, Constant.TIER50);
		String key = store.generateKey(app);
		System.out.println(key);

		for (int i = 0; i < 5; i++) {
			store.invokeApi(api, app, key, "1.0.0", "");
		}

	}

	public BatchInvoke(String url) {
		// TODO Auto-generated constructor stub
		pub = new Publisher(url);
		store = new Store(url);
	}

	private void start(boolean isCreateOnly, String prefixApi,
			String prefixApp, String u, String p, String tenant)
			throws Exception {
		// TODO Auto-generated method stub

		String api;
		String app;
		String e = "{\"production_endpoints\":{\"url\":\"http://localhost:8080/simple-service-webapp/webapi/myresource\",\"config\":null},\"endpoint_type\":\"http\"}";

		for (int i = start; i < stop; i++) {
			api = prefixApi + i;
			app = prefixApp + i;
			this.invoke(api, app, e, isCreateOnly, u, p, tenant);

		}

	}

	private void faultStart(boolean isCreateOnly) throws Exception {
		// TODO Auto-generated method stub

		String api;
		String app;
		String e = "{\"production_endpoints\":{\"url\":\"http://localhost:8018/nosuchservice\",\"config\":null},\"endpoint_type\":\"http\"}";
		start = 0;
		stop = 1;
		for (int i = start; i < stop; i++) {
			api = "fault_" + i;
			app = "fault_" + i;
			// this.invoke(api, app, e, isCreateOnly);

		}

	}

	private void invoke(String api, String app, String end,
			boolean isCreateOnly, String u, String p, String tenant)
			throws Exception {
		// TODO Auto-generated method stub
		pub.login(u, p);
		pub.createAPI(api, end, Constant.Silver);
		pub.publishAPI(api, u);

		store.login(u, p);
		store.addApplication(app);
		store.subscribe(api, app, Constant.Silver, u);
		String key = store.generateKey(app);
		System.out.println(key);

		String s2 = "-----------------{" + api + "," + app + "," + key + "},";
		if (isCreateOnly)
			for (int j = 0; j < call; j++) {
				store.invokeApi("/" + api, "/" + app, key, "/1.0.0", "/"
						+ tenant);
			}
		System.out.println(s2);

	}

	private void clean(String u, String p) throws Exception {
		// TODO Auto-generated method stub
		pub.login(u, p);
		store.login(u, p);

		String api;
		String app;

		for (int i = this.start; i < this.stop; i++) {
			api = "sampleApi_" + i;
			app = "sampleApp_" + i;
			store.removeApplication(app);
			pub.removeApi(api);
			// pub.removeApi(api + "_2");
		}

	}

	private void cleanFalut() throws Exception {
		// TODO Auto-generated method stub
		pub.login();
		store.login();

		String api;
		String app;

		for (int i = 0; i < 1; i++) {
			api = "fault_" + i;
			app = "fault_" + i;
			store.removeApplication(app);
			pub.removeApi(api);
		}

	}

	private void createApi(String user, String pass, String api)
			throws Exception {
		pub = new Publisher(url);		
		pub.login(user, pass);
		pub.createAPI(api, backend, Constant.Silver);
		pub.publishAPI(api, user);
	}

	private String addApp(String user, String pass, String app, String api,
			String provider) throws Exception {
		store = new Store(url);
		store.login(user, pass);
		store.addApplication(app);
		store.subscribe(api, app, Constant.Silver, provider);
		String key = store.generateKey(app);
		return key;
	}

	private void invokeApi(String app, String api, String version,
			String tenant, String key) throws Exception {
		store = new Store(url);
		for (int j = 0; j < call; j++) {
			store.invokeApi(api, app, key, version, tenant);
		}
	}
}
