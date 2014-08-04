package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.dao.CategoryDao;
import com.tyut.sssy.sysmgr.dao.RoleDao;
import com.tyut.sssy.sysmgr.domain.Category;
import com.tyut.sssy.sysmgr.domain.Role;

import java.util.List;
import java.util.Map;

/**
 * ClassName:RoleService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-3-21
 * Time: 11:06:58
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class RoleService {

    private RoleDao roleDao = new RoleDao();

    private CategoryDao categoryDao = new CategoryDao();


    /**
     * 添加
     *
     * @param role
     */
    public boolean add(Role role) {
        return roleDao.add(role);
    }

    /**
     * 通过角色-功能表主键获取该类
     *
     * @param id
     */
    public Role getRoleById(String id) {
        Role role = roleDao.getRoleById(id);
        String categorys = role.getCategorys();
        List<Category> list = null;
        String idsStr[] = categorys.split(",");
        int len = idsStr.length;
        int ids[] = new int[len];

        for (int j = 0; j < len; j++) {
            ids[j] = Integer.valueOf(idsStr[j]);
        }
        list = categoryDao.getListByIds(ids);

       /* List<Category> reSortList = new ArrayList<Category>();
        for(Category c : list){
            if(c.getLeaf().equals("y") && !reSortList.contains(c)){
                reSortList.add(c);
            }
        }

        for(Category c: list){
            if(c.getLeaf().equals("n") && !reSortList.contains(c)){
                reSortList.add(c);
            }
        }*/
        role.setCategroyList(list);

        return role;

    }


    /**
     * 返回所有的角色
     *
     * @return
     */
    public Map<String, Object> getAllRoles() {
        Map<String, Object> map = roleDao.getAllRoles();
        return map;
    }

    /**
     * 返回条件查询角色列表
     *
     * @param pageSize
     * @param pageNo
     * @param roleName
     * @param menuIds
     * @return map
     */
    public Map<String, Object> getRoleListByCon(int pageSize, int pageNo, String roleName, String menuIds[]) {
        return roleDao.getRoleListByCon(pageSize, pageNo, roleName, menuIds);
    }

    /**
     * 修改 Role
     *
     * @param role
     * @return flag
     */
    public boolean modify(Role role) {
        boolean flag = roleDao.modify(role);
        return flag;
    }

    public boolean del(String [] ids){
        return roleDao.del(ids);
    }

    /**
     * 返回最大的ID
     *
     * @return String
     */
    public String getMaxId() {
        return roleDao.getMaxId();
    }


}
