package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.dao.MajorIndustryDao;
import com.tyut.sssy.sysmgr.domain.MajorIndustry;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * ClassName:MajorIndustryService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-24
 * Time: 14:53:24
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class MajorIndustryService {

    private MajorIndustryDao dao = null;

    public MajorIndustryService() {
        dao = new MajorIndustryDao();
    }


    /**
     * 添加
     *
     * @param obj
     * @return Boolean
     */
    public boolean add(MajorIndustry obj) {
        return this.dao.add(obj);
    }


    /**
     * 修改 发展指数过滤条件
     *
     * @param obj
     * @return boolean
     */
    public boolean modify(MajorIndustry obj) {
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
    public Map<String, Object> queryByCon(Map<String,Object> params,int pageSize, int pageNo) {
        Map<String,Object> map = this.dao.queryByCon(params, pageSize, pageNo);
        List<MajorIndustry> list = (List <MajorIndustry>) map.get("list");
        IndustryService industryService = new IndustryService();
        for(MajorIndustry mi : list){
            mi.setIndustryName(industryService.getById(mi.getIndustryCode()).getIndustryName());
        }
        return map;
    }


}

