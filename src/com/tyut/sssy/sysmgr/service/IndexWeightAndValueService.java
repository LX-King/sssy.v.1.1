package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.dao.IndexWeightAndValueDao;
import com.tyut.sssy.sysmgr.domain.IndexWeightAndValue;
import com.tyut.sssy.sysmgr.domain.Index;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * ClassName:IndexWeightAndValueService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-19
 * Time: 23:48:45
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndexWeightAndValueService {

    private IndexWeightAndValueDao dao = null;

    public IndexWeightAndValueService() {
        dao = new IndexWeightAndValueDao();
    }


    /**
     * 添加
     *
     * @param obj
     * @return Boolean
     */
    public boolean add(IndexWeightAndValue obj) {
        return this.dao.add(obj);
    }


    /**
     * 修改 发展指数过滤条件
     *
     * @param obj
     * @return boolean
     */
    public boolean modify(IndexWeightAndValue obj) {
        return this.dao.modify(obj);
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
    public Map<String, Object> queryByCon(Map<String, Object> params, int pageSize, int pageNo) {
        Map<String, Object> map = this.dao.queryByCon(params, pageSize, pageNo);
        List<IndexWeightAndValue> list = (List<IndexWeightAndValue>) map.get("list");
        IndexService indexService = new IndexService();
        for (IndexWeightAndValue indexAndValue : list) {
            String indexCode = indexAndValue.getIndexCode();
            Index index = indexService.getById(indexCode);
            indexAndValue.setIndex(index);
        }
        return map;
    }

    /**
     * 获取最大键值
     *
     * @return string
     */
    public String getMaxId() {
        return this.dao.getMaxId();
    }


    public IndexWeightAndValue getById(String indexCode) {
        IndexService indexService = new IndexService();
        IndexWeightAndValue indexWeightAndValue = this.dao.getById(indexCode);
        Index index = indexService.getById(indexCode);
        indexWeightAndValue.setIndex(index);
        return indexWeightAndValue;
    }
}
