package com.usernamestudio.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.usernamestudio.model.Word;

public class WordDAOImpl implements WordDAO {

	private Connection conn;
	
	public WordDAOImpl(Connection conn) {
		
		this.conn = conn;
		
	}
	
	@Override
	public void insert(int styleId, String word) {

	    String countSql =
	        "SELECT COUNT(*) FROM word WHERE style_id = ? AND word = ?";

	    String insertSql =
	        "INSERT INTO word (style_id, word) VALUES (?, ?)";

	    try {

	        PreparedStatement countStat =
	            conn.prepareStatement(countSql);

	        countStat.setInt(1, styleId);
	        countStat.setString(2, word);

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

	            insertStat.setInt(1, styleId);
	            insertStat.setString(2, word);

	            insertStat.executeUpdate();
	            insertStat.close();
	        
	        } 

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public List<String> findByStyle(int styleId) {

	    List<String> words = new ArrayList<>();

	    try {
	    
	    String sql ="SELECT word FROM word WHERE style_id = ?";

	    PreparedStatement stat = conn.prepareStatement(sql);
	    stat.setInt(1, styleId);
		    
	    ResultSet rs = stat.executeQuery();
	    
	    while (rs.next()) {
	    	
	    	words.add(rs.getString("word"));
	    		    	
	    }
	    
	    rs.close();
	    stat.close();
	    	    
	    } catch (SQLException ex) { ex.printStackTrace(); }
	    
	    return words;
	
	}

	@Override
	public List<Word> findAll() {
		
		java.util.List<Word> list = new ArrayList<Word>();
		
		try {
		
		String sql = "SELECT * FROM word";
			
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(sql);
		
		while (rs.next()) {
			
			int id = rs.getInt(1);
			String data = rs.getString("word");
			Word word = new Word();
			
			word.setId(id);
			word.setName(data);
			
			list.add(word);
			
		}
		
		} catch (SQLException ex) { ex.printStackTrace(); }
		
		return list;
	
	}
}
