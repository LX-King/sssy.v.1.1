package com.tyut.sssy.sysmgr.dao;

import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.utils.HibernateSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;


public class UserHibernateDao {

	/**
	 * 添加用户
	 * @param user	用户对象
	 */
	public void add(User user) {
		Session session = null;
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			if(user!=null){
				session.save(user);

			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateSessionFactory.closeSession();
		}
	}

	/**
	 * 根据id删除用户
	 * @param userId	用户id
	 */
	public void deleteById(Integer userId) {
		Session session = null;
		User user = null;

		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();

			user = (User)session.load(User.class, userId);
			session.delete(user);

			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateSessionFactory.closeSession();
		}
	}

	/**
	 * 更新用户
	 * @param user	用户对象
	 */
	public void update(User user) {
		Session session = null;
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();

			session.update(user);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateSessionFactory.closeSession();
		}
	}

	/**//**
	 * 获取用户列表
	 * @return	用户列表
	 *//*
	public List<User> getList() {
		Session session = null;
		List<User> userList = null;
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();
			Query query = session.createQuery("from User");
			userList = query.list();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateSessionFactory.closeSession();
		}

		return userList;
	}
*/
	/**
	 * 根据用户id查询用户
	 * @param userId	用户id
	 * @return	用户对象
	 */
	public User getById(Integer userId) {
		Session session = null;
		User user = null;
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();

			user = (User)session.get(User.class, userId);

			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateSessionFactory.closeSession();
		}

		return user;
	}

	/**
	 * 根据用户名、密码 查询用户
	 * @param username	用户名
	 * @param password	密码
	 * @return	用户对象
	 */
	/*public User getByUsernameAndPwd(String username, String password) {
		Session session = null;
		User user = null;
		try {
			session = HibernateSessionFactory.getSession();
			session.beginTransaction();

			Query query = session.createQuery("from User where username = :username " +
											  "and password = :password");
			query.setString("username", username);
			query.setString("password", password);

			user = (User)query.uniqueResult();

			session.getTransaction().commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			HibernateSessionFactory.closeSession();
		}

		return user;
	}*/
}