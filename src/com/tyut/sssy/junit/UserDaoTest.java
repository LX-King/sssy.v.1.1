package com.tyut.sssy.junit;


import java.util.List;

import com.tyut.sssy.sysmgr.dao.UserDao;
import com.tyut.sssy.sysmgr.domain.User;

import junit.framework.TestCase;

public class UserDaoTest extends TestCase {
	
	/*public void testAdd() {
		UserDao userDao = new UserDao();
		User user = new User();
		user.setUsername("1987hasit");
		user.setPassword("123");
		user.setGender("M");
		user.setSchoolId("A");
		user.setBirthday("1987-02-04");
		
		userDao.add(user);
	}
	
	public void testGetById() {
		
		UserDao userDao = new UserDao();
		
		User user = userDao.getById(1);
		
		System.out.println(user.getUserId() + ":" + user.getUsername());
	}
	
	public void testGetByUsernameAndPwd() {
		UserDao userDao = new UserDao();
		User user = userDao.getByUsernameAndPwd("1987hasit", "1234");
		
		System.out.println(user.getUserId() + ":" + user.getUsername());
	}
	
	public void testUpdate() {
		UserDao userDao = new UserDao();
		
		User user = userDao.getById(1);
		
		user.setPassword("12345");
		
		userDao.update(user);
	}
	
	public void testDeleteById() {
		UserDao userDao = new UserDao();
		userDao.deleteById(14);
	}
	
	public void testGetList() {
		UserDao userDao = new UserDao();
		List<User> userList = userDao.getList();
		
		for (User user : userList) {
			System.out.println(user.getPassword() + ":" + user.getBirthday());
		}
	}
	
	public void testGetPageList() {
		UserDao userDao = new UserDao();
		List<User> userList = userDao.getPageList(0, 2, "asc");
		
		for (User user : userList) {
			System.out.println(user.getUserId() + ":" + user.getPassword() + ":" + user.getBirthday());
		}
	}*/
	
}
