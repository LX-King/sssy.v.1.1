package com.tyut.sssy.base.service;

import com.tyut.sssy.base.dao.TaxTypeDao;
import com.tyut.sssy.base.domain.TaxType;

import java.util.List;

public class TaxTypeService {

    public TaxType getTaxTypeById(String szDm) {
        TaxTypeDao taxTypeDao = new TaxTypeDao();
        TaxType taxType = taxTypeDao.getTaxTypeById(szDm);
        return taxType;
    }

    public List<TaxType> queryAll() {
        TaxTypeDao taxTypeDao = new TaxTypeDao();
        return taxTypeDao.queryAll();
    }

}
