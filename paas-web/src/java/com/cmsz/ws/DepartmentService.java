package com.cmsz.ws;

import java.util.List;

import com.cmsz.vo.DepartmentVO;

public interface DepartmentService {

	/* 部门查询 */
	public List<DepartmentVO> queryDepartment(String departmentId);

}