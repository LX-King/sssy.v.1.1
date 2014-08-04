package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.dao.CategoryDao;
import com.tyut.sssy.sysmgr.domain.Category;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:CategoryService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-3-28
 * Time: 21:19:05
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class CategoryService {

    private CategoryDao categoryDao = null;

    public CategoryService() {
        categoryDao = new CategoryDao();
    }


    /**
     * 添加菜单
     *
     * @param category
     * @return
     */
    public boolean add(Category category) {
        boolean flag = categoryDao.add(category);
        return flag;
    }

    /**
     * 根据主键查询菜单类
     *
     * @param id
     * @return
     */
    public Category getById(int id) {
        Category category = categoryDao.getById(id);
        return category;
    }


    /**
     * 修改单一菜单
     *
     * @param category
     * @return
     */
    public boolean modify(Category category) {
        boolean flag = categoryDao.modify(category);
        return flag;
    }


    public boolean del(int idArrs[]){
        boolean flag = categoryDao.del(idArrs);
        return flag;
    }
    /**
     * 返回菜单查询的分页列表
     *
     * @param pageNo
     * @param pageSize
     * @param conditions 条件
     * @return 菜单的列表
     */
    public Map<String, Object> getPagingList(int pageNo, int pageSize, String dir, HashMap<String, String> conditions) {
        Map<String, Object> map = categoryDao.getPagingList(pageNo, pageSize, dir, conditions);
        return map;
    }

    /**
     * 根据父节点查询子节点
     * @param parentId
     * @return  List 菜单的列表
     */
    public List<Category> getByParentId(int parentId) {
        return categoryDao.getByParentId(parentId);
    }

    public List<Category> getListByIds(int[] ids) {
        return categoryDao.getListByIds(ids);
    }

    /**
     * 返回父节点的分页列表
     *
     * @param pageSize
     * @param pageNo
     * @return map
     */
    public Map<String, Object> getAllParentCategory(int pageSize, int pageNo) {
        Map<String, Object> map = categoryDao.getAllParentCategory(pageNo, pageSize);
        return map;
    }


    /**
     * @param pageSize
     * @param pageNo
     * @param menuName
     * @param type
     * @param parentId
     * @return
     */
    public Map<String, Object> getCategoryListByCon(int pageSize, int pageNo, String menuName, String type, Integer parentId) {
        Map<String, Object> map = categoryDao.getCategoryListByCon(pageSize, pageNo, menuName, type, parentId);
        return map;
    }


    /**
     * 返回所有的菜单
     * @return map
     */
    public Map<String,Object>getAllCategorys(){
        return categoryDao.getAllCategorys();
    }

    /**
     * 返回最后一条记录
     *
     * @return maxId
     */
    public int getMaxId() {
        int maxId = categoryDao.getMaxId();
        return maxId;
    }

}
