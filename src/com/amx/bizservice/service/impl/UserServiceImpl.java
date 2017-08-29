package com.amx.bizservice.service.impl;

import java.sql.Timestamp;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.enums.CommonTypeEnum;
import com.amx.bizservice.enums.GenderTypeEnum;
import com.amx.bizservice.enums.LoginStatusEnum;
import com.amx.bizservice.enums.LoginTypeEnum;
import com.amx.bizservice.enums.RoleTypeEnum;
import com.amx.bizservice.enums.UpdateCodeEnum;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.bo.UserBo;
import com.amx.bizservice.model.dto.UpdateResponseDto;
import com.amx.bizservice.service.CartService;
import com.amx.bizservice.service.CustomHodometerService;
import com.amx.bizservice.service.UserService;
import com.amx.bizservice.thrift.Response;
import com.amx.bizservice.util.DesUtil;
import com.amx.bizservice.util.JsonUtil;
import com.amx.bizservice.util.LogUtil;
import com.amx.bizservice.util.PictureUtil;

@Service("userService")
@CacheConfig(cacheNames = CommonConstants.CACHE_NAME.USER)
public class UserServiceImpl extends BaseService implements UserService{
	@Autowired
	private CartService cartService;	
	@Autowired
	private CustomHodometerService cthodometerService;	

	public UserServiceImpl(){
		this.ServiceName = "User";		
	}
	
	@Override
	public UpdateResponseBo signin(UserBo bo) {
		UpdateResponseBo response = null;
		//密码复杂性检查
		if(bo.getPassword().length() < 6){
			response = new UpdateResponseBo();
			response.setIsSucceeded(false);
			response.setMsg("密码复杂性不符合要求");
			
			return response;
		}
		
		//默认值
		if(bo.getNickName() == null){
			bo.setNickName(bo.getPhone());
		}
		if(bo.getPortrait() == null){
			bo.setPortrait(CommonConfig.DEFAULT_PIC_PATH);
		}
		if(bo.getGender() == null){
			bo.setGender(GenderTypeEnum.MAN.getId());
		}
		if(bo.getLoginType() == null){
			bo.setLoginType(LoginTypeEnum.OFFICIAL.getId());
		}
		bo.setRoleId(RoleTypeEnum.PERSON.getId());
		bo.setScore(0);
		bo.setRegistTime(new Timestamp(System.currentTimeMillis()));
		bo.setLoginTime(bo.getRegistTime());
		bo.setLoginStatus(CommonTypeEnum.FALSE.getId());
		
		if(bo.getPassword().length() < 32){
			//密码MD5加密
			bo.setPassword(DigestUtils.md5Hex(bo.getPassword()));
		}
		
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "save", bo);
		try {
			if (serverResponse != null) {
				if (serverResponse.isState()) {
					UpdateResponseDto result = JsonUtil.mapper.readValue(serverResponse.getData(),UpdateResponseDto.class);
					if(result != null){
						response = new UpdateResponseBo();
						
						if(result.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
							//注册成功
							response.setIsSucceeded(true);
							response.setMsg("注册成功");
						}else if(result.getCode().equals(UpdateCodeEnum.EXIST.getCode())){
							//用户名或手机号无效
							response.setIsSucceeded(false);
							response.setMsg(result.getMsg());
						}else{
							//注册失败
							response.setIsSucceeded(false);
							response.setMsg("注册失败");
						}
					}
				} else {
					LogUtil.recordWarnLog(serverResponse.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}
	
	@Override
	public UserBo login(UserBo loginUserBo){
		return login(loginUserBo, false);
	}
	
	@Override
	public UserBo login(UserBo loginUserBo, boolean isMd5Encrypted) {
		UserBo bo = null;
		if(LoginTypeEnum.WEIBO.getId().equals(loginUserBo.getLoginType())){
			bo = loginByWeibo(loginUserBo);
		}else if(LoginTypeEnum.QQ.getId().equals(loginUserBo.getLoginType())){
			bo = loginByQQ(loginUserBo);
		}else{
			bo = loginByOfficial(loginUserBo, isMd5Encrypted);
		}
		
		if(bo == null || bo.getId() == null){
			bo = null;
		}
		
		return bo;
	}

	@Override
	@Cacheable( key="#userId",condition="#userId > 0")
	public UserBo getUser(long userId) {
		UserBo bo = null;
		
		try {
			Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "findOne", userId);
			if (serverResponse != null && serverResponse.isState()) {
				bo = JsonUtil.mapper.readValue(serverResponse.getData(),UserBo.class);
				//头像
				bo.setPortrait(PictureUtil.getPicUrl(bo.getPortrait()));
			}else{
				LogUtil.recordWarnLog(serverResponse.getMsg());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return bo;
	}



	@Override
	//XXX - 緩存注解必須注释在直接由其他类调用的方法上
	@CacheEvict(key="#bo.getId()")
	public UpdateResponseBo updateProtrait(UserBo bo) {
		UpdateResponseBo response = update(bo);
		
		if(response != null){
			if(response.getIsSucceeded()){
				response.setMsg("头像修改成功");
			}else{
				response.setMsg("头像修改失败");
			}
		}
		
		return response;
	}
	
	@Override
	@CacheEvict(key="#bo.getId()")
	public UpdateResponseBo updateUser(UserBo bo) {
		UpdateResponseBo response = update(bo);
			
		if(response != null){
			if(response.getIsSucceeded()){
				response.setMsg("资料修改成功");
			}else{
				response.setMsg("资料修改失败");
			}
		}
		
		return response;
	}
	
	@Override
	public UpdateResponseBo updatePassword(long userId, String oldPwd , String newPwd ,boolean isMd5Encrypted) {
		UpdateResponseBo response = null;
		UserBo user = this.getUser(userId);
		//md5
		if(!isMd5Encrypted){
			oldPwd = DigestUtils.md5Hex(oldPwd);
			newPwd = DigestUtils.md5Hex(newPwd);
		}
		/* 核对旧密码 */
		if(oldPwd.equalsIgnoreCase(user.getPassword())){
			user.setPassword(newPwd);
			response = update(user);
			
			if(response != null){
				if(response.getIsSucceeded()){
					response.setMsg("密码修改成功");
				}else{
					response.setMsg("密码修改失败");
				}
			}
		}else{
			response = new UpdateResponseBo();
			response.setIsSucceeded(false);
			response.setMsg("原始密码错误");
		}
		
		return response;
	}
	

	@Override
	public String getPortraitSavePath(long userId) {
		return CommonConfig.UPLOAD_DIRECTORY + "/avatar/portrait_" + userId + "_" +  System.currentTimeMillis();
	}
	
	@Override
	public UpdateResponseBo resetPassword(String phone , String newPwd, boolean isMd5Encrypted) {
		UpdateResponseBo response = null;
		//md5
		if(!isMd5Encrypted){
			newPwd = DigestUtils.md5Hex(newPwd);
		}
		
		UserBo example = new UserBo();
		example.setPhone(phone);
		
		UserBo user = this.getUserByExample(example );
		user.setPassword(newPwd);
		response = update(user);
			
		if(response != null){
			if(response.getIsSucceeded()){
				response.setMsg("密码重置成功");
			}else{
				response.setMsg("密码重置失败");
			}
		}
		
		return response;
	}
	
	@Override
	public void saveTempData(Long tempUserId, Long userId) {
		if(tempUserId == null || userId == null) return;
		
		cartService.saveTempCart(tempUserId, userId);
		cthodometerService.saveTempCustomHodometer(tempUserId, userId);
	}
	
	/**
	 * 
	 *  通过官方账号登录
	 * @param loginUserBo
	 * @param isMd5Encrypted
	 * @return
	 */
	private UserBo loginByOfficial(UserBo loginUserBo, boolean isMd5Encrypted) {
		UserBo bo = null;
		if(!isMd5Encrypted){
			loginUserBo.setPassword(DigestUtils.md5Hex(loginUserBo.getPassword()));
		}
		UserBo example = new UserBo();
		/*手机号登陆*/
		try {
			example.setPhone(loginUserBo.getNickName());
			example.setPassword(loginUserBo.getPassword());
			Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "findOneByExample", example);
			if (serverResponse != null && serverResponse.isState()) {
				bo = JsonUtil.mapper.readValue(serverResponse.getData(),UserBo.class);
			}else{
				LogUtil.recordWarnLog(serverResponse.getMsg());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		/* 按昵称登陆 */
		if(bo == null){
			try {
				example.setPhone(null);
				example.setNickName(loginUserBo.getNickName());
				example.setPassword(loginUserBo.getPassword());
				Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "findOneByExample", example);
				if (serverResponse != null && serverResponse.isState()) {
					bo = JsonUtil.mapper.readValue(serverResponse.getData(),UserBo.class);
				}else{
					LogUtil.recordWarnLog(serverResponse.getMsg());
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return bo;
	}
	
	
	private UserBo loginByQQ(UserBo loginUserBo) {
		UserBo bo = null;

		try {
			Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "findOneByExample", loginUserBo);
			if (serverResponse.isState()) {
				bo = JsonUtil.mapper.readValue(serverResponse.getData(),UserBo.class);
			}else{
				LogUtil.recordWarnLog(serverResponse.getMsg());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return bo;
	}

	private UserBo loginByWeibo(UserBo loginUserBo) {
		UserBo bo = null;

		try {
			Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "findOneByExample", loginUserBo);
			if (serverResponse.isState()) {
				bo = JsonUtil.mapper.readValue(serverResponse.getData(),UserBo.class);
			}else{
				LogUtil.recordWarnLog(serverResponse.getMsg());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return bo;
	}
	
	private UpdateResponseBo update(UserBo bo) {
		UpdateResponseBo response = null;
		
		Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "update", bo);
		try {
			if (serverResponse != null) {
				if (serverResponse.isState()) {
					UpdateResponseDto result = JsonUtil.mapper.readValue(serverResponse.getData(),UpdateResponseDto.class);
					if(result != null){
						response = new UpdateResponseBo();
						
						if(result.getCode().equals(UpdateCodeEnum.SUCCESS.getCode())){
							//注册成功
							response.setIsSucceeded(true);
						}else{
							//注册失败
							response.setIsSucceeded(false);
						}
					}
				} else {
					LogUtil.recordWarnLog(serverResponse.getMsg());
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return response;
	}
	
	private UserBo getUserByExample(UserBo bo) {
		try {
			Response serverResponse = thriftClient.serviceInvoke(this.ServiceName, "findOneByExample", bo);
			if (serverResponse != null && serverResponse.isState()) {
				bo = JsonUtil.mapper.readValue(serverResponse.getData(),UserBo.class);
			}else{
				LogUtil.recordWarnLog(serverResponse.getMsg());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return bo;
	}
	
	/**
	 * 生成分享URL
	 * @param userId
	 * @return
	 */
	@Override
	public String getShareUrl(long userId) {
		String shareUrl = null;
		//有效期1天
		long millis = System.currentTimeMillis() + 1000 * 60 * 60 * 24;
		
		try {
			shareUrl =  CommonConfig.WEB_URL + "?uid=" + DesUtil.encrypt(String.valueOf(userId)) + "&expire=" + millis + "&sign=" + DesUtil.encrypt(String.valueOf(userId + millis));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return shareUrl;
	}

	@Override
	public void updateLoginInfo(Long userID) {
		//更新登录信息
		UserBo updateBo = new UserBo();
		updateBo.setId(userID);
		updateBo.setLoginStatus(LoginStatusEnum.ONLINE.getId());
//		updateBo.setLoginIp(loginUserBo.getLoginIp());
		updateBo.setLoginTime(new Timestamp(System.currentTimeMillis()));
		
		this.update(updateBo);
	}

	@Override
	public void updateLogoutInfo(Long userID) {
		UserBo updateBo = new UserBo();
		updateBo.setId(userID);
		updateBo.setLoginStatus(LoginStatusEnum.OFFLINE.getId());
		
		this.update(updateBo);
	}

	@Override
	public void addScore(Long userId, int score) {
		UserBo userBo = this.getUser(userId);
		
		UserBo updateBo = new UserBo();
		updateBo.setId(userId);
		updateBo.setScore(userBo.getScore() + score);
		
		this.update(updateBo);
	}
}
