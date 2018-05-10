package main.java.com.absyz.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.java.com.absyz.core.DbConnection;

public class Carts {
	
	public static String add_to_cart(HttpServletRequest request) throws JSONException
	{
		Connection conn =null;
		PreparedStatement psInsert = null;
		ResultSet rsCartsMaxId = null;
		ResultSet rsGetCartList = null;
		Statement stGetCartList = null;
		Statement stSelectMaxId = null;
		String strOutput="";
		String strQuery="";
		String strGetCartQuery="";
		int intCartId = 0;
		JSONArray json = new JSONArray();
		JSONObject obj=null;
		try {
			
			conn = DbConnection.getConnection();
			strQuery = "Select max(id) id from salesforce.carts__c";
			String intUserId = request.getParameter("userid");
			//int intProductId = Integer.parseInt(request.getParameter("productid"));
			String intsalesforceProductId = request.getParameter("productid");
			String productname= request.getParameter("productname");
			int intQuantity = Integer.parseInt(request.getParameter("quantity"));
			double dblAmount = Double.parseDouble(request.getParameter("amount"));
			strGetCartQuery = "Select * from salesforce.carts__C where product__c ='"+intsalesforceProductId+"' and contact__c = '"+intUserId+"'";
			System.out.println(strGetCartQuery);
			stGetCartList = conn.createStatement();
			rsGetCartList = stGetCartList.executeQuery(strGetCartQuery);
			if(rsGetCartList.next())
			{
				System.out.println("Inside exiting cart");
				obj = new JSONObject();      //extends HashMap
			    obj.put("success",JsonObjects.json_objects("success","Already Exist"));
			    json.put(obj);
			}
			else
			{
				System.out.println("Inside cart adding part");
				stSelectMaxId = conn.createStatement();
				rsCartsMaxId = stSelectMaxId.executeQuery(strQuery);
				if(rsCartsMaxId.next())
				{
					intCartId = rsCartsMaxId.getInt("id")+1;
				}
				else
				{
					intCartId = 100;
				}
				psInsert = conn.prepareStatement("Insert into salesforce.carts__c(id,name,contact__c,product__c,quantity__c,amount__c)values(?,?,?,?,?,?)");
				psInsert.setInt(1, intCartId);
				psInsert.setString(2, productname);
				psInsert.setString(3, intUserId);
				psInsert.setString(4, intsalesforceProductId);
				psInsert.setInt(5, intQuantity);
				psInsert.setDouble(6, dblAmount);
				psInsert.executeUpdate();
				obj = new JSONObject();      //extends HashMap
			    obj.put("success",JsonObjects.json_objects("success","Added Successfully"));
			    json.put(obj);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			obj = new JSONObject();      //extends HashMap
		    obj.put("success",JsonObjects.json_objects("failure","Try Again"));
		    json.put(obj);
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			obj = new JSONObject();      //extends HashMap
		    obj.put("success",JsonObjects.json_objects("failure","Try Again"));
		    json.put(obj);
		}
		System.out.println(json.toString());
		return json.toString();
	}
	
	public static String my_cart_list(HttpServletRequest request)
	{
		String strOutput="";
		String intUserId = request.getParameter("userid");
		
		System.out.println("userid"+intUserId);
		Connection conn = null;
		Statement stSelectCarts = null;
		ResultSet rsSelectCarts = null;
		ResultSet rsSelectCarts1 = null;
		JSONArray json = new JSONArray();
		JSONObject obj=null;
		try {
			//String strQuery = "Select * from carts where userid = "+intUserId; 
				
			String strQuery = "Select c.id,c.contact__c,c.name,c.product__c,c.sfid,c.quantity__c,c.amount__c,p.productname__c,p.price__c from salesforce.carts__c c "
					+ "join salesforce.product2 p on c.product__c = p.sfid where c.contact__c = '"+intUserId+"'";
				
				
			//console.log(intUserId);
			conn = DbConnection.getConnection();
			stSelectCarts = conn.createStatement();
			rsSelectCarts = stSelectCarts.executeQuery(strQuery);
			obj = new JSONObject();      //extends HashMap
			if(rsSelectCarts.next())
			{
				obj.put("success",JsonObjects.json_objects("success","Cart data available"));
				rsSelectCarts1 = stSelectCarts.executeQuery(strQuery);
			    obj.put("data",JsonObjects.convertResultSetToJson(rsSelectCarts1));
			    json.put(obj);
			}
			else
			{
				obj.put("success",JsonObjects.json_objects("failure","No Cart data available"+intUserId));
			    json.put(obj);
			}
		    
			//strOutput = Orders.convertResultSetToJson(rsSelectCarts);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(json.toString());
		return json.toString();
	}
	
	public static String remove_cart(HttpServletRequest request)
	{
		System.out.println("inside remove cart function");
		String strOutput="";
		String intCartId = request.getParameter("cartid");
		strOutput = remove_cart_data(intCartId);
		strOutput = my_cart_list(request);
		System.out.println(strOutput);
		return strOutput;
	}
	public static String remove_cart_data(String intCartId)
	{
		Connection conn = null;
		Statement stSelectCarts = null;
		ResultSet rsSelectCarts = null;
		PreparedStatement psDelete = null;
		JSONArray json = new JSONArray();
		JSONObject obj=null;
		String strOutput="";
		try {
			System.out.println("intCartId"+intCartId);
			String strQuery = "Select * from salesforce.carts__c where sfid = '"+intCartId+"'";
			conn = DbConnection.getConnection();
			stSelectCarts = conn.createStatement();
			rsSelectCarts = stSelectCarts.executeQuery(strQuery);
			if(rsSelectCarts.next())
			{
				String strDeleteQuery = "Delete from salesforce.carts__c where sfid = ?";
				psDelete = conn.prepareStatement(strDeleteQuery);
				psDelete.setString(1, intCartId);
				psDelete.executeUpdate();
				System.out.println("Record Deleted");
				strOutput="success";
			}
			
			//strOutput = Orders.convertResultSetToJson(rsSelectCarts);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return strOutput;
	}

}


