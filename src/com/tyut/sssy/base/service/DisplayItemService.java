package com.tyut.sssy.base.service;

import java.util.List;

import com.tyut.sssy.base.dao.DisplayItemDao;
import com.tyut.sssy.base.domain.DisplayItem;

public class DisplayItemService {

	/**
	 * 获得显示项目列表
	 * @return
	 */
	public List<DisplayItem> getDisplayItemList() {

		DisplayItemDao displayItemDao = new DisplayItemDao();
		List<DisplayItem> displayItemList = displayItemDao.getDisplayItemList();
		return displayItemList;
	}

	/**
     * 根据名称返回指定项目
     *
     * @param id
     * @return DisplayItem
     */
    public DisplayItem getById(String id) {
        DisplayItemDao displayItemDao = new DisplayItemDao();
        return displayItemDao.getById(id);
    }
}
