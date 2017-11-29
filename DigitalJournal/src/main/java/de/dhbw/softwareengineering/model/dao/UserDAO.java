package de.dhbw.softwareengineering.model.dao;

import java.util.List;

import de.dhbw.softwareengineering.model.User;

public interface UserDAO {

	public void save(User p);

    public List<User> list();
	
}