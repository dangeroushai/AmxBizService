package com.amx.bizservice.controller.wap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amx.bizservice.annotation.Authentication;
import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.AccessLevelEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.exception.AccessForbiddenException;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.bo.UserBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.vo.SigninVo;
import com.amx.bizservice.model.vo.UserSnapVo;
import com.amx.bizservice.model.vo.wap.LoginVo;
import com.amx.bizservice.model.vo.wap.TokenVo;
import com.amx.bizservice.model.vo.wap.UserInfoVo;
import com.amx.bizservice.model.vo.wap.UserVo;
import com.amx.bizservice.service.SMSService;
import com.amx.bizservice.service.UserService;
import com.amx.bizservice.util.JWTUtil;
import com.amx.bizservice.util.NumCaptchaUtil;

@Authentication
@Controller("mUserController")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	@Autowired
	private SMSService smsService;
	
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@ResponseBody
	@RequestMapping(value = "/tokens", method = RequestMethod.GET)
	public Map<String, Object> getTempToken(HttpServletRequest request){
		Object  response  = ResponseTypeEnum.getNewOkResponse();
		
		TokenVo vo = new TokenVo(JWTUtil.createToken(null));
		
		((Response)response).setData(vo);
		
		return JsonResponseDto.getResult(response);
	}
	
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@ResponseBody
	@RequestMapping("/users/login")
	public Map<String, Object> login(@RequestBody com.amx.bizservice.model.vo.input.UserVo invo){
		
		Object  response  = ResponseTypeEnum.getNewOkResponse();
		UserBo loginUserBo = new UserBo();
		
//		loginUserBo.setLoginIp(request.getRemoteAddr());
		loginUserBo.setNickName(invo.getName());
		loginUserBo.setPassword(invo.getPwd());
		
		UserBo user = userService.login(loginUserBo, false);
		
		LoginVo result = new LoginVo(user) ;
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@ResponseBody
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public Map<String, Object> signin(@RequestBody com.amx.bizservice.model.vo.input.SigninVo inVo, HttpServletRequest request){
		Object  response  = ResponseTypeEnum.getNewOkResponse();
		SigninVo vo = new SigninVo();
		
		UserBo inBo = new UserBo();
		/* 检查验证码*/
		String captchaWithTimeMillis = NumCaptchaUtil.getCaptchaWithTimeMillisByPhone(inVo.getPhone());
		if( NumCaptchaUtil.isCaptchaValid(inVo.getAuthCode(), captchaWithTimeMillis)){//验证码合法
			//清除验证码
			NumCaptchaUtil.removeCaptchaByPhone(inVo.getPhone());
			
			inBo.setNickName(inVo.getName()); 
			inBo.setPhone(inVo.getPhone()); 
			inBo.setPortrait(CommonConfig.DEFAULT_PIC_PATH);
			inBo.setNickName(inVo.getPhone());
			inBo.setPassword(inVo.getPwd()); 
			
			UpdateResponseBo result = userService.signin(inBo);
			
			if(result.getIsSucceeded()){//注册成功
				vo.setIsSucceeded(true);
				vo.setUserSnap(new UserSnapVo(inBo));
			}else{
				vo.setIsSucceeded(false);
				vo.setMsg(result.getMsg());
			}
		}else{
			vo.setIsSucceeded(false);
			vo.setMsg("验证码错误");
		}
		((Response)response).setData(vo);
		
		return JsonResponseDto.getResult(response);
	}
	
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@ResponseBody
	@RequestMapping(value = "/captchas/sms/{phone}", method = RequestMethod.GET)
	public Map<String, Object> getMsgCaptcha(@PathVariable("phone")String phone ,HttpServletRequest request){
		Object  response  = ResponseTypeEnum.getNewOkResponse();
		
		UpdateResponseBo result = new UpdateResponseBo(); 
		
		//生成验证码
		String captcha = NumCaptchaUtil.generateAndSaveCaptcha(phone);
		
		if(smsService.sendCaptcha(phone, captcha)){//验证码发送成功
			result.setIsSucceeded(true);
		}else{
			result.setIsSucceeded(false);
			result.setMsg("发送验证码失败，请稍后再试");
		}
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	@ResponseBody
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Map<String, Object> getUser(HttpServletRequest request){
		Object  response  = ResponseTypeEnum.getNewOkResponse();
		
		Long currentUserId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		
		UserInfoVo result = new UserInfoVo(); 
		
		UserBo userbo = userService.getUser(currentUserId );
		if(userbo != null){
			result.setIsSucceeded(true);
			result.setUserInfo(new UserVo(userbo));
		}else{
			result.setIsSucceeded(false);
			result.setMsg("获取用户信息失败");
		}
		
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/users/info", method = RequestMethod.PUT)
	public Map<String, Object> updateUser(@RequestBody com.amx.bizservice.model.vo.wap.UserVo inVo,HttpServletRequest request){
		Object  response  = ResponseTypeEnum.SERVER_ERROR;
		UpdateResponseBo result ;
		
		Long currentUserId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		UserBo inBo = new UserBo();
		inBo.setId(currentUserId);
		inBo.setNickName(inVo.getNickName()); 
		/* 处理姓名 */
		if(inVo.getName() != null){
			String nameArr[] = inVo.getName().split(" ");
			inBo.setLastName(nameArr[0]);
			if(nameArr.length > 1){
				StringBuilder sb = new StringBuilder();
				for (int i = 1; i < nameArr.length; i++) {
					sb.append(nameArr[i]);
				}
				inBo.setFirstName(sb.toString());
			}
		}
		/* 处理地址 */
		if(inVo.getAddress() != null){
			String addrArr[] = inVo.getAddress().split(" ");
			inBo.setCountry(addrArr[0]);
			if(addrArr.length > 1){
				inBo.setProvince(addrArr[1]);
				if(addrArr.length > 2){
					inBo.setCity(addrArr[2]);
					if(addrArr.length > 3){
						StringBuilder sb = new StringBuilder();
						for (int i = 3; i < addrArr.length; i++) {
							sb.append(addrArr[i]);
						}
						inBo.setRegion(sb.toString());
					}
				}	
			}
		}
		inBo.setGender(inVo.getGender());
		inBo.setPassport(inVo.getPassport()); 
		inBo.setEmail(inVo.getEmail()); 
		inBo.setPhone(inVo.getPhone()); 
		inBo.setBirthday (inVo.getBrithday());
		
		result = userService.updateUser(inBo);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	/**
	 * 兼容修改密码和重置密码功能
	 * @param invo
	 * @param request
	 * @return
	 */
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@ResponseBody
	@RequestMapping(value = "/users/pwd", method = RequestMethod.PUT)
	public Map<String, Object> updatePwd(@RequestBody com.amx.bizservice.model.vo.input.UserVo invo,HttpServletRequest request){
		//重置密码
		if(invo.getAuthCode() != null && invo.getOldPwd() == null){
			return this.resetPassword(invo, request);
		}
		//手动检查登录与否
		if(request.getAttribute(CommonConstants.SESSION_USER_ID) == null){
			throw new AccessForbiddenException();
		}
		
		//修改密码
		return this.updatePassword(invo, request);
	}
	
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@ResponseBody
	@RequestMapping(value = "/users/resetPwd", method = RequestMethod.PUT)
	public Map<String, Object> resetPwd(@RequestBody com.amx.bizservice.model.vo.input.UserVo invo,HttpServletRequest request){
		return this.resetPassword(invo, request);
	}
	
	private Map<String, Object> updatePassword(com.amx.bizservice.model.vo.input.UserVo invo,HttpServletRequest request){
		Object  response  = ResponseTypeEnum.SERVER_ERROR;
		UpdateResponseBo result ;
		
		Long currentUserId = (Long) request.getAttribute(CommonConstants.SESSION_USER_ID);
		result = userService.updatePassword(currentUserId , invo.getOldPwd(), invo.getNewPwd(), false);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	private Map<String, Object> resetPassword(com.amx.bizservice.model.vo.input.UserVo invo,HttpServletRequest request){
		Object  response  = ResponseTypeEnum.getNewOkResponse();
		
		UserBo user = null;
		String phone = invo.getPhone();
		/* 检查验证码*/
		String captchaWithTimeMillis = NumCaptchaUtil.getCaptchaWithTimeMillisByPhone(phone);
		if( NumCaptchaUtil.isCaptchaValid(invo.getAuthCode(), captchaWithTimeMillis)){//验证码合法
			 UpdateResponseBo updateResponseBo = userService.resetPassword(phone, invo.getNewPwd(), false);
			 if(updateResponseBo.getIsSucceeded()){
				 user =  new UserBo();
			 }
		}
		
		((Response)response).setData(new LoginVo(user));
		
		return JsonResponseDto.getResult(response);
	}
}
