package com.cmsz.ws.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cmsz.vo.DepartmentVO;
import com.cmsz.ws.DepartmentService;

/** 
 * @author 林绵炎
 * @version 创建时间：2016年11月23日 下午4:12:44
 */
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {

	@Override
	public List<DepartmentVO> queryDepartment(String departmentId) {
		
		List<DepartmentVO> voList = new ArrayList<DepartmentVO>();
		
		DepartmentVO vo = new DepartmentVO();
		vo.setDepartmentId("");
		vo.setDepartmentname("");
		vo.setNote("");
		
		voList.add(vo);
		
		return voList;
	}

}
