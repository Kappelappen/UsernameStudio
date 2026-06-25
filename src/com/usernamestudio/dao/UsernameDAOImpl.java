package com.usernamestudio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.usernamestudio.model.Username;

public class UsernameDAOImpl implements UsernameDAO {

	private Connection conn;
	
	public UsernameDAOImpl(Connection conn) {
		
		this.conn = conn;
		
	}
	
	@Override
	public void insert(Username user) {

	    String countSql =
	        "SELECT COUNT(*) FROM usernames WHERE username = ?";

	    String insertSql =
	        "INSERT INTO usernames (" +
	        "username, style, length, has_numbers, has_underscore, has_symbols, favorite" +
	        ") VALUES (?, ?, ?, ?, ?, ?, ?)";

	    try {

	        PreparedStatement countStat =
	            conn.prepareStatement(countSql);

	        countStat.setString(1, user.getName());

	        ResultSet rs = countStat.executeQuery();

	        int count = 0;

	        if (rs.next()) {
	            count = rs.getInt(1);
	        }

	        rs.close();
	        countStat.close();

	        if (count == 0) {

	            PreparedStatement insertStat =
	                conn.prepareStatement(insertSql);

	            insertStat.setString(1, user.getName());
	            insertStat.setString(2, user.getStyle());
	            insertStat.setInt(3, user.getLength());

	            insertStat.setInt(4, user.isHasNumbers() ? 1 : 0);
	            insertStat.setInt(5, user.isHasUnderscore() ? 1 : 0);
	            insertStat.setInt(6, user.isHasSymbols() ? 1 : 0);

	            insertStat.setInt(7, 0);

	            insertStat.executeUpdate();
	            insertStat.close();
 
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void setFavorite(String username) {

	    String sql =
	    
	    	"UPDATE usernames " +
	        "SET favorite = 1 " +
	        "WHERE username = ?";
	    
	    PreparedStatement stat;
	    
		try {
	
		stat = conn.prepareStatement(sql);
		stat.setString(1, username);
		
		stat.executeUpdate();
		stat.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Username> findAll() {
		
		java.util.List<Username> list = new ArrayList<Username>();
		
		String sql = "SELECT * FROM usernames";
		
		try {
			
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		
		while (rs.next()) {
			
			int id = rs.getInt(1);
			String name = rs.getString("username");
			Username user = new Username();
			
			user.setId(id);
			user.setName(name);
			
			list.add(user);
			
		}
		
		stat.close();
		rs.close();
		
		} catch (SQLException ex) { ex.printStackTrace(); }
		
		return list;
		
	}

	@Override
	public List<Username> findFavorites() {
		
		java.util.List<Username> list = new ArrayList<Username>();
		
		String sql = "SELECT * FROM usernames WHERE favorite = 1";
		
		try {
			
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		
		while (rs.next()) {
			
			int id = rs.getInt(1);
			String name = rs.getString("username");
			
			Username user = new Username();
			user.setId(id);
			user.setName(name);			
			list.add(user);
			
		}
		
		stat.close();
		rs.close();
				
		} catch (SQLException ex) { ex.printStackTrace(); }
		
		return list;
	}
}
