package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.dao.IndexDao;
import com.tyut.sssy.sysmgr.domain.Index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:IndexService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-23
 * Time: 22:06:34
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndexService {

    private IndexDao dao = null;

    public IndexService() {
        dao = new IndexDao();
    }


    /**
     * 添加
     *
     * @param obj
     * @return Boolean
     */
    public boolean add(Index obj) {
        return this.dao.add(obj);
    }


    public Index getByName(String indexName){
        return this.dao.getByName(indexName);
    }

    /**
     * 修改 发展指数过滤条件
     *
     * @param obj
     * @return boolean
     */
    public boolean modify(Index obj) {
        return this.dao.modify(obj);
    }

    /**
     * Index
     * @param indexCode
     * @return index
     */
    public Index getById(String indexCode){
        return this.dao.getById(indexCode);
    }

    /**
     * 删除
     *
     * @param ids
     * @return boolean
     */
    public boolean del(String ids[]) {
        return this.dao.del(ids);
    }

    /**
     * 查询
     *
     * @param pageSize
     * @param pageNo
     * @return map
     */
    public Map<String, Object> queryByCon(HashMap<String, Object> params, int pageSize, int pageNo) {
        return this.dao.queryByCon(params, pageSize, pageNo);
    }
    
    /**
     * 获取指标列表
     * @return
     */
    public List<Index> getIndexList() {
    	List<Index> indexList = dao.getIndexList();
    	return indexList;
    }

    
}
