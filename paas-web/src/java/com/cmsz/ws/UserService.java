package com.cmsz.ws;

import java.util.List;

import com.cmsz.vo.ResultVO;
import com.cmsz.vo.UserInfoVO;

public interface UserService {

	/* 用户查询 */
	public List<UserInfoVO> queryUserInfo(String loginUser);

	/* 用户新增 */
	public ResultVO addUserInfo(UserInfoVO userInfo);

	/* 用户修改 */
	public ResultVO modifyUserInfo(UserInfoVO userInfo);

	/* 用户密码修改 */
	public ResultVO modifyUserPassword(String loginUser, String oldPassword, String newPassword);

	/* 用户删除 */
	public ResultVO delUserInfo(String loginUsers);

}