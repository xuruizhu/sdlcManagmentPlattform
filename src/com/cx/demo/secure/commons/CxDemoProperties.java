package com.cx.demo.secure.commons;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

public class CxDemoProperties {

	public static String KEY_APP_STYLE			= "app.style";
	public static String KEY_APP_LANG			= "app.lang";
	public static String KEY_APP_ENCODE			= "app.encode";
	public static String KEY_DB_USER			= "db.user";
	public static String KEY_DB_PASS			= "db.pass";
	
	public static final int HASH_SEED = 31; 

	private static CxDemoProperties 			_instance 	= null;

	private String rootPath = null;
	private Properties labels = null;
	private Properties props  = null;
	private HashMap<CxDemoUser, String> initUsers = null;
	private HashSet<CxDemoPost> initPosts = null;

	private CxDemoProperties(String rootPath) {
		if (this.rootPath == null) {
			this.rootPath = rootPath;
		}
	}

	public static CxDemoProperties getInstance(String rootPath) {
		if (_instance == null) {
			_instance = new CxDemoProperties(rootPath);
		}
		return _instance;
	}

	private void loadProperties() {
		try {
			props = new Properties();
			String labelsFile = _instance.getRealPath("dat\\demo.properties");
			props.load(new FileInputStream(labelsFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			labels = new Properties();
			String labelsFile = _instance.getRealPath(props.getProperty(KEY_APP_STYLE) + "\\labels.txt");
			labels.load(new FileInputStream(labelsFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setRootPath(String newRootPath) {
		if (rootPath == null) {
		}
	}

	public String getRealPath(String relativePath) {
		return rootPath + relativePath;
	}

	
	public HashMap<CxDemoUser, String> getInitUsers() {
		return initUsers;
	}

	public HashSet<CxDemoPost> getInitPosts() {
		return initPosts;
	}

	public String getLabel(String key) {
		loadProperties();
		String label = "no label";
		if (labels != null) {
			label = labels.getProperty(key);
		}
		return label;
	}

	public String getProperty(String key) {
		loadProperties();
		String prop = null;
		if (props != null) {
			prop = props.getProperty(key);
		}
		return prop;
	}

}
