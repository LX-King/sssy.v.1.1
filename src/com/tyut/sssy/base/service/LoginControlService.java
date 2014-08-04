package com.tyut.sssy.base.service;

import com.tyut.sssy.base.dao.LoginControlDao;
import com.tyut.sssy.base.domain.LoginControl;

public class LoginControlService {

	/**
	 * 根据代码前缀获取登录控制类
	 * @param dmQz
	 * @return
	 */
	public LoginControl getBySwjgDm(String dmQz) {
		LoginControlDao loginControlDao = new LoginControlDao();
		LoginControl loginControl = loginControlDao.getBySwjgDm(dmQz);
		return loginControl;
	}

}
