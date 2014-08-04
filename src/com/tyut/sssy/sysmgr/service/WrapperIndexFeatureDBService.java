package com.tyut.sssy.sysmgr.service;

import com.tyut.sssy.sysmgr.domain.IndexFeatureDB;
import com.tyut.sssy.sysmgr.domain.WrapperIndexFeatureDB;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * ClassName:WrapperIndexFeatureDBService
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-26
 * Time: 16:20:04
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class WrapperIndexFeatureDBService {
    private IndexFeatureDBService service = null;

    private IndexService indexService = null;

    public WrapperIndexFeatureDBService() {
        service = new IndexFeatureDBService();
        indexService = new IndexService();
    }

    /**
     * 显示
     *
     * @param indexCode
     * @param pageSize
     * @param pageNo
     * @return map
     */
    public Map<String, Object> queryByCon(String indexCode, int pageSize, int pageNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map2 = service.queryByCon(indexCode, pageSize, pageNo);
        List<WrapperIndexFeatureDB> tempWrapperList = optionalConvertToWrapperIndexFeatureDB((List<IndexFeatureDB>)map2.get("list"));
        map.put("totalProperty", map2.get("totalProperty"));
        map.put("list", tempWrapperList);
        return map;
    }

    /**
     * 处理封装过程
     *
     * @param list
     * @return list
     */
    private List<WrapperIndexFeatureDB> convertToWrapperIndexFeatureDB(List<IndexFeatureDB> list) {
        List<WrapperIndexFeatureDB> tempWrapperList = new ArrayList<WrapperIndexFeatureDB>();
        List<IndexFeatureDB> tempIndex = new ArrayList<IndexFeatureDB>();
        int len = list.size();
        boolean flag = true;
        for (int i = 0; i < len; i++) {
            IndexFeatureDB indexFeatureDB1 = list.get(i);
            tempIndex.add(indexFeatureDB1);

            for (int j = i + 1; j < len; j++) {
                IndexFeatureDB indexFeatureDB2 = list.get(j);
                if (indexFeatureDB2.getIndexCode().equals(indexFeatureDB1.getIndexCode())) {
                    tempIndex.add(indexFeatureDB2);
                }

                if (tempIndex.size() == 5) {
                    i = j;
                    wrapperIndexFeatureDB(tempWrapperList, tempIndex);
                    break;
                }

            }
        }
        return tempWrapperList;
    }

    private void wrapperIndexFeatureDB(List<WrapperIndexFeatureDB> tempWrapperList, List<IndexFeatureDB> tempIndex) {
        WrapperIndexFeatureDB wifd = new WrapperIndexFeatureDB();
        IndexFeatureDB indexFeature = tempIndex.get(0);
        wifd.setIndexCode(indexFeature.getIndexCode());
        wifd.setIndexName(indexService.getById(indexFeature.getIndexCode()).getIndexName());
        for (IndexFeatureDB indexFeatureDB : tempIndex) {
            String type = indexFeatureDB.getType();
            if (type.trim().equals("ycqj1")) {
                wifd.setYc1_jy(indexFeatureDB.getOption());
                wifd.setYc1_tz(indexFeatureDB.getFeature());
                wifd.setYc1_yd(indexFeatureDB.getQuestion());
            } else if (type.trim().equals("ycqj2")) {
                wifd.setYc2_jy(indexFeatureDB.getOption());
                wifd.setYc2_tz(indexFeatureDB.getFeature());
                wifd.setYc2_yd(indexFeatureDB.getQuestion());
            } else if (type.trim().equals("zcqj")) {
                wifd.setZc_jy(indexFeatureDB.getOption());
                wifd.setZc_tz(indexFeatureDB.getFeature());
                wifd.setZc_yd(indexFeatureDB.getQuestion());
            } else if (type.trim().equals("ysqj")) {
                wifd.setYs_jy(indexFeatureDB.getOption());
                wifd.setYs_tz(indexFeatureDB.getFeature());
                wifd.setYs_yd(indexFeatureDB.getQuestion());
            } else if (type.trim().equals("lsqj")) {
                wifd.setLs_jy(indexFeatureDB.getOption());
                wifd.setLs_tz(indexFeatureDB.getFeature());
                wifd.setLs_yd(indexFeatureDB.getQuestion());
            }
        }
        tempIndex.clear();
        tempWrapperList.add(wifd);
    }


    /**
     * 优化
     *
     * @param list
     * @return list
     */
    private List<WrapperIndexFeatureDB> optionalConvertToWrapperIndexFeatureDB(List<IndexFeatureDB> list) {
        int len = list.size();
        List<WrapperIndexFeatureDB> result = new ArrayList<WrapperIndexFeatureDB>();
        List<IndexFeatureDB> singleWrapperIndexFeatureDB = new ArrayList<IndexFeatureDB>();
        int parentNextIteratorIndex = 0;
        boolean hasFindAllSub = false;
        boolean isSon = false;
        for (int i = parentNextIteratorIndex; i < len; i++) {
            IndexFeatureDB parent = list.get(i);
            singleWrapperIndexFeatureDB.add(parent);
            hasFindAllSub = false;
            while (!hasFindAllSub) {
                for (int j = i+1; j < len; j++) {
                    IndexFeatureDB indexFeatureDB = list.get(j);
                    isSon = indexFeatureDB.getIndexCode().equals(parent.getIndexCode()) && !indexFeatureDB.getType().equals(parent.getType());
                    if (singleWrapperIndexFeatureDB.size() < 5) {
                        if (isSon) {
                            singleWrapperIndexFeatureDB.add(indexFeatureDB);
                        } else {
                            if (parentNextIteratorIndex == 0 || parentNextIteratorIndex < j) {
                                parentNextIteratorIndex = j - 1;
                            } else {
                                if (parentNextIteratorIndex > j)
                                    parentNextIteratorIndex = j;
                            }
                            i = parentNextIteratorIndex;
                            break;
                        }
                    } else {
                        parentNextIteratorIndex = j-1;
                        i = parentNextIteratorIndex;
                        hasFindAllSub = true;
                        break;
                    }
                    if(j+1>=len){
                        parentNextIteratorIndex = j;
                        i = parentNextIteratorIndex;
                    }
                }
                hasFindAllSub = true;
            }
            wrapperIndexFeatureDB(result, singleWrapperIndexFeatureDB);


        }

        return result;
    }
}
