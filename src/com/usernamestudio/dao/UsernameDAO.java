package com.usernamestudio.dao;

import com.usernamestudio.model.Username;

public interface UsernameDAO {
		
	void insert(Username user);
	java.util.List<Username> findAll();
	java.util.List<Username> findFavorites();
	void setFavorite(String username);

	
}
