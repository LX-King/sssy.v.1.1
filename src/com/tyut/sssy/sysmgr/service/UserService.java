package com.tyut.sssy.sysmgr.service;

import java.util.List;
import java.util.Map;

import com.tyut.sssy.sysmgr.dao.UserDao;
import com.tyut.sssy.sysmgr.domain.User;
import com.tyut.sssy.base.service.TaxUnitService;
import com.tyut.sssy.base.domain.TaxUnit;

public class UserService {

    // JDBC处理数据
    private static UserDao userDao = new UserDao();
    private User user;

    /**
     * 添加用户
     *
     * @param user 用户
     */
    public boolean add(User user) {
        return userDao.add(user);
    }


    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    public List<User> getList() {
        List<User> userList = userDao.getList();
        for (User user : userList) {
            if (user != null) {
                TaxUnitService taxUnitService = new TaxUnitService();
                String swjgdm = user.getSwjgdm();
                TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjgdm);
                user.setTaxUnit(taxUnit);
            }
        }
        return userList;
    }

    /**
     * 根据用户ID获取用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    public User getById(Integer userId) {
        User user = userDao.getById(userId);
        if (user != null) {
            TaxUnitService taxUnitService = new TaxUnitService();
            String swjgdm = user.getSwjgdm();
            TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjgdm);
            user.setTaxUnit(taxUnit);
        }
        return user;
    }
    
    /**
     * 根据代码获取人员
     * @param code
     * @return
     */
    public User getByCode(String code) {
        User user = userDao.getByCode(code);
        if (user != null) {
            TaxUnitService taxUnitService = new TaxUnitService();
            String swjgdm = user.getSwjgdm();
            TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjgdm);
            user.setTaxUnit(taxUnit);
        }
        return user;
    }

    /**
     * 更新用户
     *
     * @param user 用户
     */
    public boolean update(User user) {
        return userDao.update(user);
    }

    /**
     * 根据用户ID删除用户
     *
     * @param userId 用户ID
     */
    public void deleteById(Integer userId) {
        userDao.deleteById(userId);
    }

    /**
     * 根据用户名密码查询用户
     *
     * @param code
     * @param password
     * @return
     */
    public User getByUserCodeAndPwd(String code, String password) {
        User user = userDao.getByCodeAndPwd(code, password);
        if (user != null) {
            TaxUnitService taxUnitService = new TaxUnitService();
            String swjgdm = user.getSwjgdm();
            TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjgdm);
            user.setTaxUnit(taxUnit);
        }
        return user;
    }

    /**
     * 获取用户分页列表
     *
     * @param start
     * @param limit
     * @param dir
     * @return
     */
    public List<User> getPageList(int start, int limit, String dir) {

        UserDao userDao = new UserDao();
        List<User> userList = userDao.getPageList(start, limit, dir);
        for (User user : userList) {
            if (user != null) {
                TaxUnitService taxUnitService = new TaxUnitService();
                String swjgdm = user.getSwjgdm();
                TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjgdm);
                user.setTaxUnit(taxUnit);
            }
        }

        return userList;
    }

    /**
     * 根据用户名跟角色代码查询
     *
     * @param pageNo
     * @param pageSize
     * @param name
     * @param roleCode
     * @return map
     */
    public Map<String, Object> getPageListByCon(int pageNo, int pageSize, String name, String roleCode) {
        Map<String, Object> map = userDao.getPageListByCon(pageNo, pageSize, name, roleCode);
        for (User user : (List<User>) map.get("list")) {
            if (user != null) {
                TaxUnitService taxUnitService = new TaxUnitService();
                String swjgdm = user.getSwjgdm();
                TaxUnit taxUnit = taxUnitService.getTaxUnitById(swjgdm);
                user.setTaxUnit(taxUnit);
            }
        }
        return map;
    }


}
