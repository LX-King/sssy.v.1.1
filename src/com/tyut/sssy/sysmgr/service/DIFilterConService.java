package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.domain.DIFilterCon;
import com.tyut.sssy.sysmgr.dao.DIFilterConDao;

import java.util.Map;

/**
 * ClassName:DIFilterConService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-19
 * Time: 20:01:37
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class DIFilterConService {

    private DIFilterConDao diFilterConDao = null ;

    public DIFilterConService(){
        diFilterConDao = new DIFilterConDao();
    }


     /**
     * 添加 发展指数过滤条件
     *
     * @param filterCon
     * @return Boolean
     */
    public boolean add(DIFilterCon filterCon) {
        return this.diFilterConDao.add(filterCon);
     }


    public DIFilterCon getById(int id){
        return this.diFilterConDao.getById(id);
    }

    /**
     * 修改 发展指数过滤条件
     *
     * @param filterCon
     * @return boolean
     */
    public boolean modify(DIFilterCon filterCon) {
        return this.diFilterConDao.modify(filterCon);
    }

     /**
     * 删除 发展指数过滤条件
     *
     * @param ids
     * @return boolean
     */
    public boolean del(int ids[]) {
         return this.diFilterConDao.del(ids);
     }

    /**
     * 查询 发展指数过滤条件
     *
     * @param pageSize
     * @param pageNo
     * @return map
     */
    public Map<String, Object> queryByCon(int pageSize, int pageNo) {
        return this.diFilterConDao.queryByCon(pageSize, pageNo);
    }
}


