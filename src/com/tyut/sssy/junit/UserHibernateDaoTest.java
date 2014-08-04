package com.tyut.sssy.junit;

import java.util.List;

import com.tyut.sssy.sysmgr.dao.UserHibernateDao;
import com.tyut.sssy.sysmgr.domain.User;

import junit.framework.TestCase;

public class UserHibernateDaoTest extends TestCase {
	
	/*public void testAdd() {
		*//*UserHibernateDao userDao = new UserHibernateDao();
		User user = new User();
		user.setUsername("1987hasit");
		user.setPassword("123");
		user.setGender("M");
		user.setSchoolId("A");
		user.setBirthday("1987-02-04");
		
		userDao.add(user);*//*
	}
	
	public void testGetById() {
		
		*//*UserHibernateDao userDao = new UserHibernateDao();
		
		User user = userDao.getById(1);*//*
		
	}
	
	public void testGetList() {
		*//*UserHibernateDao userDao = new UserHibernateDao();
		List<User> userList = userDao.getList();
		
		for (User user : userList) {
			System.out.println(user.getPassword() + ":" + user.getBirthday());
		}*//*
	}
	
	public void testUpdate() {
		*//*UserHibernateDao userDao = new UserHibernateDao();
		User user = userDao.getById(1);
		user.setBirthday("1986-03-29");
		userDao.update(user);*//*
	}
	
	public void testDeleteById() {
		*//*UserHibernateDao userDao = new UserHibernateDao();
		userDao.deleteById(1);*//*
	}*/
	
}
