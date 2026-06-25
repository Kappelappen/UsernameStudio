package com.usernamestudio.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.usernamestudio.model.Style;

public class StyleDAOImpl implements StyleDAO {

    private final Connection conn;

    public StyleDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Style> findAll() {

        List<Style> styles = new ArrayList<>();

        String sql = "SELECT * FROM style";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Style style = new Style(
                        rs.getInt("id"),
                        rs.getString("name")
                );

                styles.add(style);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return styles;
    
    }

	@Override
	public Style findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Style style) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}
}