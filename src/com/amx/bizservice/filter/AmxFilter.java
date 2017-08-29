package com.amx.bizservice.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.amx.bizservice.config.CommonConfig;
import com.amx.bizservice.filter.base.AmxRequest;
import com.amx.bizservice.filter.base.AmxResponse;
import com.amx.bizservice.util.LogUtil;
import com.amx.bizservice.util.StringUtil;

/**
 * 自定义过滤器：拦截并重写所有的请求
 * @author DangerousHai
 *
 */
public class AmxFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		//开始接受请求时间
		long startTimeMillis = System.currentTimeMillis();
		
		//重写请求和响应对象
		AmxRequest amxRequest = new AmxRequest(request);
		AmxResponse amxResponse = new AmxResponse(response);
		
		if(isLegalRequest(request)){
			
			filterChain.doFilter(amxRequest, amxResponse);
			
			//将响应内容输出到输出流
			try {
				//优先使用字节流输出（保证图片等非字符数据可以正常输出）
				ServletOutputStream outputStream = response.getOutputStream();
				byte[] responseBytes = amxResponse.getResponseBytes();
				if(responseBytes != null && responseBytes.length > 0){
					outputStream.write(responseBytes);	
				}
			} catch (Exception e) {
				//字节流输出出错才使用字符流输出
				String responseBody = amxResponse.getResponseBody();
				if(!StringUtil.isEmpty(responseBody)){
					PrintWriter writer = response.getWriter();
					writer.write(responseBody);
				}
			}
		} else {
			response.sendError(401, "WHO ARE YOU,WHY YOU HERE");
		}
		
		
		//请求处理时间
		long handleTimeMillis = System.currentTimeMillis() - startTimeMillis;
		//记录访问日志
		LogUtil.recordAccessLog(amxRequest, amxResponse, handleTimeMillis);
	}
	
	
	/**
	 * 请求合法性检查
	 * @param request
	 * @return
	 */
	private boolean isLegalRequest(HttpServletRequest request){
		//检查请求来源
		String referer = request.getHeader("Referer");
		if(referer != null && referer.contains(CommonConfig.WEB_URL)){
			return true;
		}
		
		return false;
	}
}
