package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.dao.IndexFeatureDBDao;
import com.tyut.sssy.sysmgr.domain.IndexFeatureDB;

import java.util.List;
import java.util.Map;

/**
 * ClassName:IndexFeatureDBService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-26
 * Time: 16:06:50
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndexFeatureDBService {

    private IndexFeatureDBDao dao = null;
    
    public IndexFeatureDBService() {
        dao = new IndexFeatureDBDao();
    }


    /**
     * 添加
     *
     * @param obj
     * @return Boolean
     */
    public boolean add(IndexFeatureDB obj) {
        return this.dao.add(obj);
    }


    /**
     * 修改 发展指数过滤条件
     *
     * @param obj
     * @return boolean
     */
    public boolean modify(IndexFeatureDB obj) {
        return this.dao.modify(obj);
    }

   
    /**
     * 查询
     *
     * @param pageSize
     * @param pageNo
     * @return map
     */
    public Map<String,Object> queryByCon(String indexCode, int pageSize, int pageNo) {
        return this.dao.queryByCon(indexCode, pageSize, pageNo);
    }

    public IndexFeatureDB getByCodeAndType(String code , String type) {
        return this.dao.getByCodeAndType(code ,type);
    }
}
