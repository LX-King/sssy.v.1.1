package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.dao.IndustryDao;
import com.tyut.sssy.sysmgr.domain.Industry;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName:IndustryService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-24
 * Time: 14:51:52
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndustryService {

    private IndustryDao dao = null;

    public IndustryService() {
        dao = new IndustryDao();
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

    public Industry getById(String id){
        return this.dao.getById(id);
    }

    public List<Industry> getAllIndustry(){
        return this.dao.getAllIndustry();
    }
}
