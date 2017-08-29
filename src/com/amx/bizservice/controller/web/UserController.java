package com.amx.bizservice.controller.web;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.amx.bizservice.annotation.Authentication;
import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.config.CommonConstants;
import com.amx.bizservice.controller.BaseController;
import com.amx.bizservice.enums.AccessLevelEnum;
import com.amx.bizservice.enums.GenderTypeEnum;
import com.amx.bizservice.enums.LoginStatusEnum;
import com.amx.bizservice.enums.LoginTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum;
import com.amx.bizservice.enums.ResponseTypeEnum.Response;
import com.amx.bizservice.model.bo.UpdateResponseBo;
import com.amx.bizservice.model.bo.UserBo;
import com.amx.bizservice.model.dto.JsonResponseDto;
import com.amx.bizservice.model.vo.SigninVo;
import com.amx.bizservice.model.vo.UserSnapVo;
import com.amx.bizservice.model.vo.web.LoginVo;
import com.amx.bizservice.model.vo.web.UserVo;
import com.amx.bizservice.service.SMSService;
import com.amx.bizservice.service.UserService;
import com.amx.bizservice.util.AccessLevelUtil;
import com.amx.bizservice.util.DesUtil;
import com.amx.bizservice.util.LogUtil;
import com.amx.bizservice.util.NumCaptchaUtil;
import com.amx.bizservice.util.PicCaptchaUtil;
import com.amx.bizservice.util.PictureUtil;
import com.amx.bizservice.util.SessionUtil;
import com.amx.bizservice.util.StringUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.qzone.UserInfoBean;

import weibo4j.Account;
import weibo4j.Users;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

@Authentication
@Controller
public class UserController extends BaseController{
	@Autowired
	private UserService userService;
	@Autowired
	private SMSService smsService;
	
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@RequestMapping("/share")
	public void share(com.amx.bizservice.model.vo.input.ShareVo invo,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		//检验时间是否过期
		if (invo.getExpire() >= System.currentTimeMillis()) {
			Long userId = Long.parseLong(DesUtil.decrypt(invo.getUid()));
			
			@SuppressWarnings("deprecation")
			String sign = URLDecoder.decode(DesUtil.encrypt(String.valueOf(invo.getExpire() + userId)));
			//检验签名
			if (sign.equals(invo.getSign())) {
				userService.addScore(userId, 1);
			}
		}
	}
	
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@ResponseBody
	@RequestMapping("/login")
	public Map<String, Object> login(com.amx.bizservice.model.vo.input.UserVo invo,HttpServletRequest request,HttpServletResponse response){
		
		Object exeResponse = ResponseTypeEnum.getNewOkResponse();
		LoginVo result = new LoginVo() ;
		UserBo loginUserBo = new UserBo();
		
//		loginUserBo.setLoginIp(IPAddressUtil.textToNumericFormatV4(request.getRemoteAddr()));
		loginUserBo.setNickName(invo.getName());
		loginUserBo.setPassword(invo.getPwd());

		/* 检查验证码 */
		//验证码是否有效
		boolean isCaptchaValid = true;
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute(CommonConstants.PIC_CAPTCHA) != null){
			isCaptchaValid = ((String)session.getAttribute(CommonConstants.PIC_CAPTCHA)).equalsIgnoreCase(invo.getAuthCode());
		}
		
		if(isCaptchaValid){
			UserBo userInfo = userService.login(loginUserBo, true);
			if(userInfo != null){
				//在线 &&在线状态尚未过期
				if(LoginStatusEnum.ONLINE.equals(userInfo.getLoginStatus()) && userInfo.getLoginTime().before(new Timestamp(System.currentTimeMillis() - CommonConfig.USER_ONLINE_EXPIRE))){
					result.setIsSucceeded(false);
					result.setMsg("您的账号已在其它地方登录，请先注销");
				}else{
					result.setIsSucceeded(true);
					this.onLoginSuccess(request, response, userInfo, invo.isRemember());
				}
			}else{
				result.setIsSucceeded(false);
				result.setMsg("用户名或密码错误");
				
				//检查用户登录失败的次数
				session = SessionUtil.getSession(request, response);
				Integer loginFailTimes = (Integer)session.getAttribute(CommonConstants.SESSION_LOGIN_FAIL_TIME);
				loginFailTimes = (loginFailTimes == null ? 0 : loginFailTimes) + 1;
				//大于三次则需要输入验证码
				if(loginFailTimes >= 3){
					result.setIsValidate(true);
				}
				session.setAttribute(CommonConstants.SESSION_LOGIN_FAIL_TIME, loginFailTimes);
			}
		}else{
			result.setIsSucceeded(false);
			result.setMsg("验证码错误");
			result.setIsValidate(true);
		}
		
		((Response)exeResponse).setData(result);
		
		return JsonResponseDto.getResult(exeResponse);
	}

	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@ResponseBody
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public Map<String, Object> signin(HttpServletRequest request , com.amx.bizservice.model.vo.input.SigninVo inVo){
		Object response = ResponseTypeEnum.getNewOkResponse();
		SigninVo vo = new SigninVo();
		
		UserBo inBo = new UserBo();
		/* 检查验证码*/
		String captchaWithTimeMillis = NumCaptchaUtil.getCaptchaWithTimeMillisByPhone(inVo.getPhone());
		if( NumCaptchaUtil.isCaptchaValid(inVo.getAuthCode(), captchaWithTimeMillis)){//验证码合法
			//清除验证码
			NumCaptchaUtil.removeCaptchaByPhone(inVo.getPhone());
			
			inBo.setPhone(inVo.getPhone()); 
			inBo.setPassword(inVo.getPwd()); 
			
			UpdateResponseBo result = userService.signin(inBo);
			
			if(result.getIsSucceeded()){//注册成功
				vo.setIsSucceeded(true);
				
				UserSnapVo userSnap = new UserSnapVo(inBo);
				
				vo.setUserSnap(userSnap );
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
	@RequestMapping(value = "/msgAuthCode", method = RequestMethod.POST)
	public Map<String, Object> getMsgCaptcha(HttpServletRequest request){
		Object response = ResponseTypeEnum.getNewOkResponse();
		
		UpdateResponseBo result = new UpdateResponseBo(); 
		
		String phone = request.getParameter("phone");
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
	
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@RequestMapping(value = "/authCode", method = RequestMethod.GET)
	public void getPicCaptcha(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setContentType("image/jpeg");  
		/* 禁用缓存 */
		response.setHeader("Pragma", "No-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
  
        // 生成随机字串  
        String verifyCode = PicCaptchaUtil.generateVerifyCode(4);  
        HttpSession session = SessionUtil.getSession(request, response);
		session.setMaxInactiveInterval(Long.valueOf(CommonConfig.CAPTCHE_TIME_OUT).intValue());
        // 删除以前的  
        session.removeAttribute(CommonConstants.PIC_CAPTCHA);  
        // 存入会话session  
        session.setAttribute(CommonConstants.PIC_CAPTCHA, verifyCode.toLowerCase());  
        // 生成图片  
        int w = 100, h = 30;  
        //输出图片
        PicCaptchaUtil.outputImage(w, h, response.getOutputStream(), verifyCode);  
	}
	
//	@ResponseBody
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public Map<String, Object> getUser(HttpServletRequest request){
		Object response = ResponseTypeEnum.getNewOkResponse();
		
		UserVo result = new UserVo(); 
		
		long currentUserId = (long) request.getSession(false).getAttribute(CommonConstants.SESSION_USER_ID);
		UserBo userbo = userService.getUser(currentUserId );
		if(userbo != null){
			result = new UserVo(userbo);
			result.setShareUrl(userService.getShareUrl(currentUserId));
		}
		
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/userInfo", method = RequestMethod.PUT)
	public Map<String, Object> updateUser(com.amx.bizservice.model.vo.web.input.UserUpdateVo inVo,HttpServletRequest request){
		Object response = ResponseTypeEnum.SERVER_ERROR;
		UpdateResponseBo result ;
		
		long currentUserId = (long) request.getSession(false).getAttribute(CommonConstants.SESSION_USER_ID);
		UserBo inBo = new UserBo();
		inBo.setId(currentUserId);
		inBo.setNickName(inVo.getNickName()); 
		/* 处理姓名 */
		if(!StringUtil.isEmpty(inVo.getName())){
			String nameArr[] = inVo.getName().trim().split(" ");
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
		if(!StringUtil.isEmpty(inVo.getAddress())){
			String addrArr[] = inVo.getAddress().trim().split(" ");
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
	

	
	@ResponseBody
	@RequestMapping(value = "/protrait", method = RequestMethod.POST)
	public Map<String, Object> updatePortrait(HttpServletRequest request){
		boolean success = false;
		/*获取上传文件*/
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession(false).getServletContext());
		String savePath = null;
		try {
			long currentUserId = (long) request.getSession(false).getAttribute(CommonConstants.SESSION_USER_ID);
			//检查form中是否有enctype="multipart/form-data"
			if(multipartResolver.isMultipart(request)){
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
				//获取multiRequest 中所有的文件名
				Iterator<String> iter = multiRequest.getFileNames();
				if (iter.hasNext()) {
					MultipartFile file = multiRequest.getFile(iter.next());
					if(file != null){
						//图像后缀
						String suffix = "." + PictureUtil.getPicSuffix(file.getOriginalFilename());
						//存储路径
						savePath  = userService.getPortraitSavePath(currentUserId  ) + suffix;
						//保存文件
						file.transferTo(new File(CommonConfig.IMG_SITE_BASE_PATH + savePath));
					}
				}
			}
			
			UserBo inBo = new UserBo();
			inBo.setId(currentUserId);
			inBo.setPortrait(savePath);
			
			UpdateResponseBo result = userService.updateUser(inBo);
			if(result != null){
				success = result.getIsSucceeded();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return JsonResponseDto.getAvatarResult(success);
	}
	
	@ResponseBody
	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	public Map<String, Object> updatePwd(HttpServletRequest request){
		Object response = ResponseTypeEnum.SERVER_ERROR;
		UpdateResponseBo result ;
		
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		
		long currentUserId = (long) request.getSession(false).getAttribute(CommonConstants.SESSION_USER_ID);
		result = userService.updatePassword(currentUserId, oldPwd, newPwd, true);
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@ResponseBody
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public Map<String, Object> resetPwd(HttpServletRequest request){
		Object response = ResponseTypeEnum.SERVER_ERROR;
		UpdateResponseBo result = null ;
		
		String captcha = request.getParameter("authCode").trim();
		String phone = request.getParameter("phone").trim();
		/* 检查验证码*/
		/* 检查验证码*/
		String captchaWithTimeMillis = NumCaptchaUtil.getCaptchaWithTimeMillisByPhone(phone);
		if( NumCaptchaUtil.isCaptchaValid(captcha, captchaWithTimeMillis)){//验证码合法
			//清除验证码
			NumCaptchaUtil.removeCaptchaByPhone(phone);
			String newPwd = request.getParameter("newPwd");
			
			result = userService.resetPassword(phone, newPwd, true);
		}
		
		if(result != null){
			response = ResponseTypeEnum.getNewOkResponse();
			
			((Response)response).setData(result);
		}
		
		return JsonResponseDto.getResult(response);
	}
	
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	@ResponseBody
	@RequestMapping(value = "/isLogin", method = RequestMethod.GET)
	public Map<String, Object> isLogin(HttpServletRequest request){
		Object response = ResponseTypeEnum.getNewOkResponse();
		UpdateResponseBo result = new UpdateResponseBo();
		boolean isLogin = false;
		
		//获取用户的访问级别
		AccessLevelEnum userAccessLevel = AccessLevelUtil.getUserAccessLevel(request);
		//只有私有级别才代表登录用户
		if(userAccessLevel.equals(AccessLevelEnum.PRIVATE)){
			isLogin = true;
		}
		
		result.setIsSucceeded(isLogin);
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/loginOut", method = RequestMethod.GET)
	public Map<String, Object> logout(HttpServletRequest request,HttpServletResponse httpResponse){
		Object response = ResponseTypeEnum.getNewOkResponse();
		
		/*清空Cookie中的Token*/
		Cookie cookie = new Cookie(CommonConstants.COOKIE_TOKEN_NAME,null);
		cookie.setMaxAge(0);
		httpResponse.addCookie(cookie);
		/*清空Session*/
		HttpSession session = request.getSession(false);
		if(session != null){
			userService.updateLogoutInfo((Long)session.getAttribute(CommonConstants.SESSION_USER_ID));
			//使SESSION过期
			session.invalidate();
		}
			
		UpdateResponseBo result = new UpdateResponseBo();
		result.setIsSucceeded(true);
		((Response)response).setData(result);
		
		return JsonResponseDto.getResult(response);
	}
	
	private void onLoginSuccess(HttpServletRequest request,HttpServletResponse response,UserBo userInfo) {
		this.onLoginSuccess(request, response, userInfo, false);
	}
	
	/**
	 * 登录后执行的动作
	 * @param request
	 * @param response
	 * @param userInfo
	 * @param isRemember 是否记住
	 */
	private void onLoginSuccess(HttpServletRequest request,HttpServletResponse response,UserBo userInfo,boolean isRemember) {
		//登录成功
		if(userInfo != null){
			//持久化临时数据
			 HttpSession session = request.getSession(false);
			 if(session != null){
				Long tempUserId = (Long)session.getAttribute(CommonConstants.SESSION_USER_ID);
				try {
					userService.saveTempData(tempUserId,userInfo.getId());
				} catch (Exception e) {
					//持久化临时数据失败
					LogUtil.recordExceptionLog(e, request);
				}
			 }
			 //创建用户Session
			 SessionUtil.createUserSession(request, response, userInfo,isRemember);

			 userService.updateLoginInfo(userInfo.getId());
		}
	}
	
	
///////////////////////////////以下内容与第三方登录有关////////////////////////////
	
	private final String DEFAULT_PWD = "aimanxing";
	private String state = null;
	private String scope = null;
	
	/**
	 * 获取微博登录链接
	 * @return
	 * @throws WeiboException
	 */
	@ResponseBody
	@RequestMapping(value = "/third/weibo")
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	public String getWeiBoLoginUrl() throws WeiboException{
		return new weibo4j.Oauth().authorize("code",state,scope);		
	}
	/**
	 * 获取QQ登录链接
	 * @return
	 * @throws QQConnectException 
	 * @throws WeiboException
	 */
	@ResponseBody
	@Deprecated
	@RequestMapping(value = "/third/qq")
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	public String getQQLoginUrl(HttpServletRequest request) throws QQConnectException{
		return new com.qq.connect.oauth.Oauth().getAuthorizeURL(request);		
	}
	
	/**
	 * 接收微博登录回调
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping(value = "/third/weibo/onlogin")
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	public void onWeiBoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		UserBo userInfo = null;
		try{
			User user = getWeiboUserByCode(request.getParameter("code"));
			UserBo userBo = new UserBo();
			//尝试登录
			userBo.setOpenId(user.getId());
			userBo.setLoginType(LoginTypeEnum.WEIBO.getId());
			userInfo = userService.login(userBo);
			//登录失败
			if(userInfo == null){//注册用户
				userBo.setNickName(user.getName());
				userBo.setPassword(DEFAULT_PWD);
				userBo.setGender(GenderTypeEnum.getGenderByWeiBo(user.getGender()).getId());
				userBo.setPortrait(user.getProfileImageUrl());
				UpdateResponseBo responseBo = userService.signin(userBo );
				//注册成功
				if(responseBo != null && responseBo.getIsSucceeded()){//登录成功
					userInfo = userService.login(userBo);
				}
			}
		} catch (Exception e) {
			LogUtil.recordExceptionLog(e, request);
		}
		
		this.onLoginSuccess(request, response, userInfo);
		
		response.sendRedirect(CommonConfig.WEB_URL);
	}
	
	/**
	 * 接收QQ登录回调
	 * @param request
	 * @throws IOException 
	 */
	@RequestMapping(value = "/third/qq/onlogin")
	@Authentication(accessLevel = AccessLevelEnum.PUBLIC)
	public void onQQLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		UserBo userInfo = null;
		try {
			//com.qq.connect.javabeans.AccessToken accessTokenObj = (new com.qq.connect.oauth.Oauth()).getAccessTokenByRequest(request);

            String accessToken = request.getParameter("accesstoken");
            String openID = request.getParameter("openid");
            //long tokenExpireIn = 0L;

//            if (accessTokenObj.getAccessToken().equals("")) {
//            	LogUtil.recordExceptionLog(new RuntimeException("没有获取到响应参数"), request);
//            } else {
//               accessToken = accessTokenObj.getAccessToken();
               //tokenExpireIn = accessTokenObj.getExpireIn();

                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj =  new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();
                // 利用获取到的accessToken 去获取当前用户的openid --------- end

                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                
                if (userInfoBean.getRet() == 0) {
                	UserBo userBo = new UserBo();
                	userBo.setOpenId(openID);
                	userBo.setLoginType(LoginTypeEnum.QQ.getId());
        			//尝试登录
                	userInfo = userService.login(userBo);
                	//登录失败
        			if(userInfo == null){//注册用户
        				userBo.setNickName(userInfoBean.getNickname());
        				userBo.setPassword(DEFAULT_PWD);
        				userBo.setPortrait(userInfoBean.getAvatar().getAvatarURL100());
        				userBo.setGender(GenderTypeEnum.getGenderByQQ(userInfoBean.getGender()).getId());
        				UpdateResponseBo responseBo = userService.signin(userBo );
        				//注册成功
        				if(responseBo != null && responseBo.getIsSucceeded()){
        					userInfo = userService.login(userBo);
        				}
        			}
                } else {
                    LogUtil.recordExceptionLog(new Exception(userInfoBean.getMsg()), request);
                }
//            }
        } catch (QQConnectException e) {
        	LogUtil.recordExceptionLog(e, request);
        }
		
		this.onLoginSuccess(request, response, userInfo);
		
		response.sendRedirect(CommonConfig.WEB_URL);
	}
		
	/**
	 * 获取微博用户信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	private User getWeiboUserByCode(String code) throws Exception{
		weibo4j.http.AccessToken accessToken = new weibo4j.Oauth().getAccessTokenByCode(code);
		
		Account am = new Account();
		am.client.setToken(accessToken.getAccessToken());
		
		Users um = new Users();
		um.client.setToken(accessToken.getAccessToken());
		String uid = am.getUid().getString("uid");
		return  um.showUserById(uid);
	}
}
