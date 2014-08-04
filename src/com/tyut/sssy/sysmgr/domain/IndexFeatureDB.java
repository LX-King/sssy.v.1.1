package com.tyut.sssy.sysmgr.domain;

/**
 * ClassName:IndexFeatureDB
 * Function:${FUNCTION}
 * Author: LiuXiang
 * Date: 2012-5-25
 * Time: 16:54:33
 * Mail:LXiang.tyut@gmail.com
 * Company:山西省太原市和信至诚科技有限公司
 */
public class IndexFeatureDB {

    private String indexCode ;

    private String indexName ;

    private String type ;

    private String feature ;

    private String question;

    private String option ;

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
