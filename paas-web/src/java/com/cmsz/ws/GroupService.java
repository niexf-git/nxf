package com.cmsz.ws;

import java.util.List;

import com.cmsz.vo.ResultVO;
import com.cmsz.vo.WorkGroupVO;

public interface GroupService {

	/* 查询工作组 */
	public List<WorkGroupVO> queryWorkGroup(String groupId);

	/* 添加工作组 */
//	public ResultVO addWorkGroup(WorkGroupVO vo);

	/* 修改工作组 */
//	public ResultVO modifyWorkGroup(WorkGroupVO vo);

	/* 删除工作组 */
//	public ResultVO delWorkGroup(String groupId);

}