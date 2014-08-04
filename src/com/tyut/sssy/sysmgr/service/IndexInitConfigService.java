package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.dao.IndexInitConfigDao;
import com.tyut.sssy.sysmgr.domain.IndexInitConfig;
import com.tyut.sssy.sysmgr.domain.IndexWeightAndValue;
import com.tyut.sssy.sysmgr.domain.Index;

import java.util.Map;
import java.util.List;

/**
 * ClassName:IndexInitConfigService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-24
 * Time: 16:21:58
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndexInitConfigService {

    private IndexInitConfigDao dao = null;

    public IndexInitConfigService() {
        dao = new IndexInitConfigDao();
    }


    /**
     * 修改 发展指数过滤条件
     *
     * @param obj
     * @return boolean
     */
    public boolean modify(IndexInitConfig obj) {
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
        List<IndexInitConfig> list = (List<IndexInitConfig>) map.get("list");
        IndexService indexService = new IndexService();
        for (IndexInitConfig indexInitConfig : list) {
            String indexCode = indexInitConfig.getIndexCode();
            Index index = indexService.getById(indexCode);
            indexInitConfig.setIndexName(index.getIndexName());
        }
        return map;
    }

    public IndexInitConfig getById(String id) {
        IndexInitConfig indexInitConfig = this.dao.getById(id);
        String indexCode = indexInitConfig.getIndexCode();
        IndexService indexService = new IndexService();
        Index index = indexService.getById(indexCode);
        indexInitConfig.setIndexName(index.getIndexName());
        return indexInitConfig;
    }

}
