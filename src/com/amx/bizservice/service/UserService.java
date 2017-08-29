package com.amx.bizservice.service;

import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.bo.UserBo;

public interface UserService {

	UpdateResponseBo signin(UserBo bo);

	UserBo getUser(long userId);

	UpdateResponseBo updateProtrait(UserBo bo);

	UpdateResponseBo updateUser(UserBo bo);

	String getPortraitSavePath(long userId);

	UpdateResponseBo updatePassword(long userId, String oldPwd, String newPwd ,boolean isMd5Encrypted);

	UpdateResponseBo resetPassword(String phone, String newPwd, boolean isMd5Encrypted);

	/**
	 * 用户登陆
	 * @param name
	 * @param password
	 * @param isMd5Encrypted
	 * @return 成功返回用户信息，失败返回
	 */
	UserBo login(UserBo userBo, boolean isMd5Encrypted);

	UserBo login(UserBo loginUserBo);

	/**
	 * 用户登录时，保存临时用户数据
	 * @param tempUserId
	 * @param userId
	 */
	void saveTempData(Long tempUserId, Long userId);

	/**
	 * 登录成功后更新登录信息
	 */
	void updateLoginInfo(Long userID);
	
	/**
	 * 登录注销后更新登录信息
	 */
	void updateLogoutInfo(Long userID);

	/**
	 * 加积分
	 * @param userId
	 * @param i
	 */
	void addScore(Long userId, int score);

	String getShareUrl(long userId);

}
