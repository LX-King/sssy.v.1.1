package com.tyut.sssy.base.service;

import com.tyut.sssy.base.dao.PreCalLevelDao;
import com.tyut.sssy.base.domain.PreCalLevel;

import java.util.List;

public class PreCalLevelService {

    public PreCalLevel getPreCalLevelById(String ysjcDm) {
        PreCalLevelDao preCalLevelDao = new PreCalLevelDao();
        PreCalLevel preCalLevel = preCalLevelDao.getPreCalLevelById(ysjcDm);
        return preCalLevel;
    }

    public List<PreCalLevel> queryAll() {
        PreCalLevelDao preCalLevelDao = new PreCalLevelDao();
        return preCalLevelDao.queryAll();
    }

}
