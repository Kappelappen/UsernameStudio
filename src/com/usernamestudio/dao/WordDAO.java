package com.usernamestudio.dao;

import java.util.List;

import com.usernamestudio.model.Word;

public interface WordDAO {
	
	List<String> findByStyle(int styleId);
	public void insert(int styleId, String word);
	public List<Word> findAll();

}
