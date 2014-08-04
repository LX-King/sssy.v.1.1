package com.tyut.sssy.task.service;

import com.tyut.sssy.task.dao.TaskStateDao;
import com.tyut.sssy.task.domain.TaskState;

public class TaskStateService {

	/*
	 * 根据任务状态获取任务
	 */
	public TaskState getTaskStateById(String rwztDm) {
		TaskStateDao taskStateDao = new TaskStateDao();
		TaskState taskState =  taskStateDao.getTaskStateById(rwztDm);
		return taskState;
	}

}
