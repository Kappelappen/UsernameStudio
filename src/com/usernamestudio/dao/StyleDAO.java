package com.usernamestudio.dao;

import java.util.List;

import com.usernamestudio.model.Style;

public interface StyleDAO {

    List<Style> findAll();
    Style findById(int id);
    void insert(Style style);
    void delete(int id);
}
