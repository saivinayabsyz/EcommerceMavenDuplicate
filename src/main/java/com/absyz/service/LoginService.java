package main.java.com.absyz.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement
//libraries
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.java.com.absyz.core.DbConnection;

public class LoginService {
	
	public static String adminLogin(HttpServletRequest request) throws JSONException
	{
		String strOutput="";
		Connection conn =null;
		ResultSet rsLogin = null;
		ResultSet rsLogData = null;
		Statement stSelectQuery = null;
		String strEmail = request.getParameter("email");
		String strPassword = request.getParameter("password");
		String strQuery = "Select userid from adminusers where email = '"+strEmail+"' and password = '"+strPassword+"'";
		System.out.println(strQuery);
		JSONArray json = new JSONArray();
		JSONObject obj=null;
		try {
			conn = DbConnection.getConnection();
			stSelectQuery = conn.createStatement();
			rsLogin = stSelectQuery.executeQuery(strQuery);
			//strOutput = Orders.convertResultSetToJson(rsLogin);
			if(rsLogin.next())
			{
				rsLogData = stSelectQuery.executeQuery(strQuery);
				//strOutput = Orders.convertResultSetToJson(rsLogData);
				System.out.println(strOutput);
				obj = new JSONObject();      //extends HashMap
			    obj.put("success",JsonObjects.json_objects("success","user data available"));
			    obj.put("data",JsonObjects.convertResultSetToJson(rsLogData));
			    json.put(obj);
				//strOutput = "[{+"'""+"success:"+JsonObjects.json_objects("success","user data available")+"}]"+",[{data:"+strOutput+"}]";
				
			}
			else
			{
				obj = new JSONObject();      //extends HashMap
			    obj.put("success",JsonObjects.json_objects("failure","user data not available"));
			    json.put(obj);
			}
		}
			 catch (SQLException e) {
					// TODO Auto-generated catch block
				 	obj = new JSONObject();      //extends HashMap
				    obj.put("success",JsonObjects.json_objects("failure","Data Connection Lost. Please Try again after sometime"));
				    json.put(obj);
					strOutput = "Data Connection Lost. Please Try again after sometime";
					e.printStackTrace();
				} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				System.out.println(json);
			
		return json.toString();
	}
	
	public static String userLogin(HttpServletRequest request) throws JSONException
	{
			
		String strOutput="";
		Connection conn =null;
		ResultSet rsLogin = null;
		ResultSet rsLogData = null;
		Statement stSelectQuery = null;
		String strEmail = request.getParameter("email");
		String strPassword = request.getParameter("password");
		String strQuery = "Select id,sfid from salesforce.contact where email = '"+strEmail+"' and password__c = '"+strPassword+"'";
		System.out.println(strQuery);
		JSONArray json = new JSONArray();
		JSONObject obj=null;
		try {
			conn = DbConnection.getConnection();
			stSelectQuery = conn.createStatement();
			rsLogin = stSelectQuery.executeQuery(strQuery);
			//strOutput = Orders.convertResultSetToJson(rsLogin);
			if(rsLogin.next())
			{
				rsLogData = stSelectQuery.executeQuery(strQuery);
				//strOutput = Orders.convertResultSetToJson(rsLogData);
				System.out.println(strOutput);
				obj = new JSONObject();      //extends HashMap
			    obj.put("success",JsonObjects.json_objects("success","user data available"));
			    obj.put("data",JsonObjects.convertResultSetToJson(rsLogData));
			    json.put(obj);
				//strOutput = "[{+"'""+"success:"+JsonObjects.json_objects("success","user data available")+"}]"+",[{data:"+strOutput+"}]";
				
			}
			else
			{
				obj = new JSONObject();      //extends HashMap
			    obj.put("success",JsonObjects.json_objects("failure","user data not available"));
			    json.put(obj);
			}
			Client client = ClientBuilder.newClient();
			Response response = client.target("https://webtopdf.expeditedaddons.com/?api_key=XKZ17UDF0B8Q440LI96EOGPW2578VJNT196R3HAS32YMC5&content=http://www.wikipedia.org&margin=10&html_width=1024&title=My PDF Title").request(MediaType.TEXT_PLAIN_TYPE).get();
 
			
		}
			 catch (SQLException e) {
					// TODO Auto-generated catch block
				 	obj = new JSONObject();      //extends HashMap
				    obj.put("success",JsonObjects.json_objects("failure","Data Connection Lost. Please Try again after sometime"));
				    json.put(obj);
					strOutput = "Data Connection Lost. Please Try again after sometime";
					e.printStackTrace();
				} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				System.out.println(json);
			
		return json.toString();
	}
	public static String show_user(HttpServletRequest request) throws JSONException
	{
		Connection conn =null;
		ResultSet rsLogData = null;
		ResultSet rsLogin = null;
		Statement stSelectQuery = null;
		String intUserid  = request.getParameter("userid");
		String strQuery = "Select id,sfid,firstname,lastname,email,mobilephone,address1__c,address_2__c	,mailingcity,mailingstate,mailingpostalcode,mailingcountry from salesforce.contact where sfid ='"+intUserid+"'";
		System.out.println(strQuery);
		JSONArray json = new JSONArray();
		JSONObject obj=null;
		try {
			conn = DbConnection.getConnection();
			stSelectQuery = conn.createStatement();
			rsLogData = stSelectQuery.executeQuery(strQuery);
			if(rsLogData.next())
			{
				rsLogin = stSelectQuery.executeQuery(strQuery);
				obj = new JSONObject();      //extends HashMap
			    obj.put("success",JsonObjects.json_objects("success","user data available"));
			    obj.put("data",JsonObjects.convertResultSetToJson(rsLogin));
			    json.put(obj);				
			}
			else
			{
				obj = new JSONObject();      //extends HashMap
			    obj.put("success",JsonObjects.json_objects("failure","user data not available"));
			    json.put(obj);
			}
		}
			 catch (SQLException e) {
					// TODO Auto-generated catch block
				 	obj = new JSONObject();      //extends HashMap
				    obj.put("success",JsonObjects.json_objects("failure","Data Connection Lost. Please Try again after sometime"));
				    json.put(obj);
					e.printStackTrace();
				} catch (JSONException e) {
				// TODO Auto-generated catch blockjknbjkn jk
				e.printStackTrace();
			}
				System.out.println(json);
			
		return json.toString();
	}

}
