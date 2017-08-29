package com.amx.bizservice.filter.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/**
 * 之所以重写请求，原因如下：
 * 在做一级缓存时无法直接在拦截器中从Response获取完整的向浏览器输出的响应体（只能获取在缓冲区中尚未Flush的字节）
 * @author DangerousHai
 *
 */
public class AmxResponse extends HttpServletResponseWrapper {
	private ServletOutputStream os;
	
	private String responseBody;
	
	public AmxResponse(HttpServletResponse response) throws IOException {
		super(response);
		//以下代码会自动调用该行
		//response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;charset=utf-8");
		
		os = new WrapperOutputStream();
	}
	
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return os;
	}
	
	@Override
	public void flushBuffer() throws IOException {
		if(os != null){
			os.flush();
		}
	}
	
	/**
	 * 获取响应字节数组
	 * @return
	 */
	public byte[] getResponseBytes(){
		try {
			flushBuffer();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		ByteArrayOutputStream byteArrayOutputStream = ((WrapperOutputStream)os).getByteArrayOutputStream();
		if(byteArrayOutputStream != null){
			return byteArrayOutputStream.toByteArray();
		}
		return null;
	}
	/**
	 * 获取响应字符串
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getResponseBody(String charsetName){
		byte[] responseBytes = getResponseBytes();
		//避免重复构造响应体
		if(responseBytes != null && responseBody == null){
			try {
				responseBody = new String(responseBytes, charsetName).toString();
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return responseBody;
	}
	public String getResponseBody(){
		return getResponseBody("UTF-8");
	}
	
	private class WrapperOutputStream  extends ServletOutputStream{
/*		*//**
		 * HttpServletResponse 类的输出流
		 *//*
		private ServletOutputStream parentOutPutStream ;
		*//**
		 * HttpServletResponse 类的PrintWriter
		 *//*
		private PrintWriter parentPrintWriter ;*/
		/**
		 * 用于保存输出流的副本
		 */
		private ByteArrayOutputStream bos;
		
		private WrapperOutputStream() {
			bos = new ByteArrayOutputStream();
		}

/*		public WrapperOutputStream(ServletOutputStream outputStream) {
			this();
			this.parentOutPutStream = outputStream;
		}

		public WrapperOutputStream(PrintWriter writer) {
			this();
			this.parentPrintWriter = writer;
		}*/

		@Override
		public void write(int b) throws IOException {
			/*if(this.parentPrintWriter != null){//写到字符流
				this.parentPrintWriter.write(b);
			}
			if(this.parentOutPutStream != null){//写到输出流
				this.parentOutPutStream.write(b);
			}*/
			//写副本
			bos.write(b);
		}

		public ByteArrayOutputStream getByteArrayOutputStream() {
			return bos;
		}
	}
}