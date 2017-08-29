package com.amx.bizservice.util;

import java.util.Date;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.model.bo.UserBo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;



public class JWTUtil {
	private static String secret = "Aimanxing Science and Technology Ltd";
	private static String issuer = "Aimanxing";
	private static Algorithm algorithm;
	
	static{
		try {
			algorithm = Algorithm.HMAC256(secret);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param userBo null - 创建临时token
	 * @return
	 */
	public static String createToken (UserBo userBo) {
		String token = null;
		try {
		    Builder builder = JWT.create();
		    if(userBo == null){
		    	userBo = new UserBo();
		    }
		    if( userBo.getId() == null){
		    	userBo.setId(getTempUserId());
		    	//userBo.setNickName(String.valueOf(-userBo.getId()));
		    	//userBo.setPortrait(CommonConfig.DEFAULT_PIC_PATH);
		    }
		    
		    /*payload*/
		    builder.withClaim("user_id", String.valueOf(userBo.getId()));
//		    builder.withClaim("user_nick", userBo.getNickName());
		    //builder.withClaim("user_avatar", userBo.getPortrait());
		    //有效期
		    /*
		    iss: 该JWT的签发者，是否使用是可选的；
		    sub: 该JWT所面向的用户，是否使用是可选的；
		    aud: 接收该JWT的一方，是否使用是可选的；
		    exp(expires): 什么时候过期，这里是一个Unix时间戳，是否使用是可选的；
		    iat(issued at): 在什么时候签发的(UNIX时间)，是否使用是可选的；
		    nbf (Not Before)：如果当前时间在nbf里的时间之前，则Token不被接受；一般都会留一些余地，比如几分钟；，是否使用是可选的；
		    */
		    //当前时间
		    Date nowDate = new Date();
		    builder.withIssuedAt(nowDate);
		    builder.withNotBefore(nowDate);
		    //过期时间
		    Date expireDate = new Date(nowDate.getTime() + (CommonConfig.TOKEN_EXPIRE  * 1000));
		    builder.withExpiresAt(expireDate);
		    //发布者
		    builder.withIssuer(issuer);
//		    Base64.class.getProtectionDomain();		    
		    token = builder.sign(algorithm);
		} catch (Exception exception){
			exception.printStackTrace();
		}
		
		return token;
	}
	
	/**
	 * 
	 * @param token
	 * @return 验证通过返回UserBo ， 失败返回空
	 */
	public static UserBo verify (String token) {
		UserBo user = null;
		try {
		    JWTVerifier verifier = JWT.require(algorithm) 
		    		.withIssuer(issuer)
		    		.acceptLeeway(5)   //5 sec for nbf and iat
		    	    .acceptExpiresAt(5)   //5 secs for exp
		    	    .build(); //Reusable verifier instance
		    DecodedJWT jwt = verifier.verify(token);
		    
		    user = new UserBo();
		    user.setId(Long.parseLong(jwt.getClaim("user_id").asString()));
//		    user.setNickName(jwt.getClaim("user_nick").asString());
//		    user.setPortrait(jwt.getClaim("user_avatar").asString());
		} catch (Exception exception){
			LogUtil.recordExceptionLog(exception);
		}
		
		return user;
	}
	
	/**
	 * 获取临时用户ID（负数）
	 * @return
	 */
	public static synchronized long getTempUserId(){
		//FIXME - 减小负数
		return 0 - System.currentTimeMillis();
	}
}
