package com.cx.demo.nonsecure.commons;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;


public class CxDemoDAL {

	// Singleton class CxDemoDAL
	private static CxDemoDAL _instance = null;
	private CxDemoProperties props = null; 

	private String 		user		= "cxdemo";
	private String 		password	= "cxdemo";
	private int 		resSize		= 10;

	private CxDemoDAL(CxDemoProperties props) {
		try {
			this.props = props;
			createDB();
		}
		catch (CxDemoException ex) {
			ex.printStackTrace();
		}
	}

	public static CxDemoDAL getInstance(CxDemoProperties props){
		if (_instance == null) {
			_instance = new CxDemoDAL(props);
		}
		return _instance;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public void setDbParams(String dbUser, String dbPassword) {
		boolean modified = false;
		if (!user.equals(dbUser)) {
			user = dbUser;
			modified = true;
		}
		if (!password.equals(dbPassword)) {
			password = dbPassword;
			modified = true;
		}
		if (modified) {
			try {
				createDB();
			}
			catch (CxDemoException ex) {
				ex.printStackTrace();
			}
		}
	}

	private Connection getDBConnection(String user, String password) throws CxDemoException {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			return DriverManager.getConnection("jdbc:hsqldb:file:cx-demo-nonsecure", user, password);
		} catch (Exception ex) {
			throw new CxDemoException("Failure to create DB connection", ex);
		}
	}

	private void createDB() throws CxDemoException {
		try {
			Connection saConnection = getDBConnection("SA", "");
			System.out.println("CREATED CONNECTION FOR SA");
			Statement statement =  saConnection.createStatement();
			try {
				statement.executeQuery("create user " + user.replaceAll("'", "\"") + " password " + password.replaceAll("'", "\"") + " admin");
				System.out.println("CREATED DATABASE USER " + user);
			} 
			catch (SQLException ex) {
				System.out.println("CREATE DATABASE USER" + user + " FAILED - " + ex.getMessage());
			}
			resetUsers();
			resetPosts();
		}
		catch (SQLException ex) {
			throw new CxDemoException(ex);
		}
	}

	public void resetUsers() throws CxDemoException {
		try {
			copyFile(props.getRealPath(props.getProperty(CxDemoProperties.KEY_APP_STYLE) + "\\users-master.csv"), props.getRealPath("dat\\users.csv"));
			execute("drop table USERS if exists", null);
			execute("create text table USERS (ID varchar(32), FNAME varchar(32), LNAME varchar(32), COUNTRY varchar(32), PASS varchar(32))", null);
			execute("set table USERS source \"webapps\\demo-nonsecure\\dat\\users.csv;fs=,;ignore_first=true;encoding=Shift_JIS\"", null);
			ResultSet res = executeQuery("select * from USERS", null);
			while (res.next()) {
				System.out.println("USER - " + res.getString("ID") + " \\ " + res.getString("FNAME") + " \\ " + res.getString("LNAME") + " \\ " + res.getString("COUNTRY") + " \\ " + res.getString("PASS"));
			}
		}
		catch (Exception ex) {
			throw new CxDemoException(ex);
		}
	}

	public void resetPosts() throws CxDemoException {
		try {
			copyFile(props.getRealPath(props.getProperty(CxDemoProperties.KEY_APP_STYLE) + "\\posts-master.csv"), props.getRealPath("dat\\posts.csv"));
			execute("drop table POSTS if exists", null);
			execute("create text table POSTS (POSTTIME numeric, POSTTEXT varchar(2000), PUBLISHER varchar(32))", null);
			execute("set table POSTS source \"webapps\\demo-nonsecure\\dat\\posts.csv;fs=,;ignore_first=true;encoding=Shift_JIS\"", null);
			ResultSet res = executeQuery("select * from POSTS", null);
			while (res.next()) {
				System.out.println("POST - " + res.getString("POSTTIME") + " \\ " + res.getString("POSTTEXT") + " \\ " + res.getString("PUBLISHER"));
			}
		}
		catch (Exception ex) {
			throw new CxDemoException(ex);
		}
	}

	public void execute(String query, String[] args) throws CxDemoException {
		Statement statement = null;
		try {
			Connection connection = getDBConnection(user, password); 
			System.out.println("CREATE CONNECTION FOR USER " + user + "\\\\" + password);
			statement = connection.createStatement();
			System.out.println("CREATED STATEMENT FOR QUERY: " + query);
			if (args != null) {
				for (int idx=0; idx<args.length; idx++) {
					query = query.replaceFirst("\\?", "'" + args[idx] + "'");
				}
			}
			statement.setMaxRows(resSize);
			statement.execute(query);
			System.out.println("EXECUTED QUERY: " + query);
		}
		catch (SQLException ex) {
			throw new CxDemoException(ex);
		}
	}


	public ResultSet executeQuery(String query, String[] args) throws CxDemoException {
		ResultSet ret = null;
		try {
			Connection connection = getDBConnection(user, password); 
			System.out.println("CREATE CONNECTION FOR USER " + user + "\\\\" + password);
			Statement statement = connection.createStatement();
			System.out.println("CREATED STATEMENT FOR QUERY: " + query);
			if (args != null) {
				for (int idx=0; idx<args.length; idx++) {
					query = query.replaceFirst("\\?", "'" + args[idx] + "'");
				}
			}
			statement.setMaxRows(resSize);
			ret = statement.executeQuery(query);
			System.out.println("EXECUTED QUERY: " + query);
		}
		catch (SQLException ex) {
			throw new CxDemoException(ex);
		}
		return ret;
	}


	// USERS METHODS
	//////////////////

	public boolean isIdAvailable(String id) {
		boolean available = false;
		try {
			String[] args = {id};
			ResultSet res = executeQuery("select count(1) as ID_COUNT from USERS where ID = ?", args);
			if (res.next()) {
				available = res.getInt("ID_COUNT") == 0;
			}
		}
		catch (Exception ex) {
			System.out.println("Failed to retrieve count of usages of id " + id);
			ex.printStackTrace();
		}
		return available;
	}

	public void registerUser(CxDemoUser user, String password) throws CxDemoException {
		try {
			String[] args = {user.getId(), user.getName(), user.getSurname(), user.getCountry(), password};
			execute("insert into USERS (ID, FNAME, LNAME, COUNTRY, PASS) values (?,?,?,?,?)", args);
		}
		catch (CxDemoException ex) {
			System.out.println("Failed to register new user with id " + user.getId());
			throw ex;
		}
	}

	public String getPassword(String id) {
		String password = null;
		try {
			String[] args = {id};
			ResultSet res = executeQuery("select PASS from USERS where ID = ?", args);
			if (res.next()) {
				password = res.getString("PASS");
			}
		}
		catch (Exception ex) {
			System.out.println("Failed to get password of user with id " + id);
			ex.printStackTrace();
		}
		return password;
	}

	public void setPassword(String id, String currentPass, String newPass) {
		try {
			String[] args = {newPass, id, currentPass};
			execute("update USERS set PASS = ? where ID = ? and PASS = ?", args);
		}
		catch (CxDemoException ex) {
			System.out.println("Failed to set password of user with id " + id);
			ex.printStackTrace();
		}
	}

	public boolean authenticateUser(String id, String password) {
		boolean authenticated = false;
		try {
			String[] args = {id, password};
			ResultSet res = executeQuery("select PASS from USERS where ID = ? and PASS = ?", args);
			if (res.next()) {
				String storedPassword = res.getString("PASS");
				authenticated = (storedPassword != null);
			}
		}
		catch (Exception ex) {
			System.out.println("Failed to authenticate user with id " + id);
			ex.printStackTrace();
		}
		return authenticated;
	}

	public CxDemoUser getUserInfo(String id) {
		CxDemoUser user = null;
		try {
			String[] args = {id};
			ResultSet res = executeQuery("select * from USERS where ID = ?", args);
			if (res.next()) {
				id = res.getString("ID");
				String name = res.getString("FNAME");
				String surname = res.getString("LNAME");
				String country = res.getString("COUNTRY");
				if (name != null && surname != null && country != null) {
					user = new CxDemoUser(id, name, surname, country);
				}
			}
		}
		catch (Exception ex) {
			System.out.println("Failed to get into of user with id " + id);
			ex.printStackTrace();
		}
		return user;
	}


	// POSTS METHODS
	//////////////////

	public ArrayList<CxDemoPost> getLatestPosts(int size) {
		resSize = size;
		ArrayList<CxDemoPost> posts = new ArrayList<CxDemoPost>(0);
		try {
			ResultSet res = executeQuery("select * from POSTS order by POSTTIME desc", null);
			while (res.next()) {
				String publisher = res.getString("PUBLISHER");
				String text = res.getString("POSTTEXT");
				long   time = res.getLong("POSTTIME");
				if (publisher != null && text != null) {
					posts.add(new CxDemoPost(time, text, publisher));
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return posts;

	}

	public void post(CxDemoPost post) {
		try {
			String[] args = {post.getText(), post.getPublisher()};
			execute("insert into POSTS (POSTTIME, POSTTEXT, PUBLISHER) values (" + post.getPostTime() + ", ?, ?)", args);
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	// UTILITY METHODS
	////////////////////

	private void copyFile(String src, String dest) {
		try{
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dest);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
		catch(FileNotFoundException ex){
			ex.printStackTrace();
		}
		catch(IOException ex){
			ex.printStackTrace();      
		}
	}


}
