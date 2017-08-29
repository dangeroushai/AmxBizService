package com.amx.bizservice.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.amx.bizservice.config.CommonConfig;


public class PictureUtil {
	
	/**
	 * 获取图像的URL
	 * @param picpath 数据库中存储的相对路径
	 * @param isStaticFile true-不经过API处理，直接请求静态文件
	 * @return
	 */
	public static String getPicUrl(String picpath,boolean isStaticFile){
		String prefix = null;
		
		String[] imgServerNoArr = {"", "1", "2"};
		
		if(isStaticFile){
			prefix = CommonConfig.IMAGE_SERVER_URL; 
		}else{
			//随机分配一台图像服务器
			String imgServerNo = imgServerNoArr[new Random().nextInt(imgServerNoArr.length)];
			String imgServerPrefix = CommonConfig.IMAGE_SERVER_URL.split("\\.")[0];
			prefix = CommonConfig.IMAGE_SERVER_URL.replace(imgServerPrefix, imgServerPrefix+imgServerNo) + "?f=";
		}
		
		//将缩略图路径替换为高清图路径
		if(picpath != null){
			picpath = picpath.replaceAll("litimg", "allimg");
		}
		
		return prefix + picpath;
	}
	
	public static String getPicUrl(String picpath){
		return getPicUrl(picpath,false);
	}
	
	/**
	 * 获取图片的后缀名
	 * @param picName
	 * @return
	 */
	public static String getPicSuffix(String picName){
		String[] split = picName.split("\\.");
		return split[split.length - 1];
	}
	
	public static List<String> saveCommnetPictures(HttpServletRequest request,long orderId,long userId){
		List<String> pictureList = null;
		/*获取上传文件*/
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
		try {
			//检查form中是否有enctype="multipart/form-data"
			if(multipartResolver.isMultipart(request)){
				int index = 0;
				pictureList = new ArrayList<String>();
				
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
				MultiValueMap<String, MultipartFile> multiFileMap = multiRequest.getMultiFileMap();
				List<MultipartFile> list = multiFileMap.get("file[]");
				Iterator<MultipartFile> iter = list.iterator();
				while (iter.hasNext()) {
					MultipartFile file = iter.next();
					if(file != null && !file.isEmpty()){
						//图像后缀
						String suffix = "." + PictureUtil.getPicSuffix(file.getOriginalFilename());
						//存储路径
						String savePath = getCommentPicSavePath(userId, orderId, index++) + suffix;
						pictureList.add(savePath);
						//保存文件
						file.transferTo(new File(CommonConfig.IMG_SITE_BASE_PATH + savePath));
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return pictureList;
	}
	
	/**
	 * 生成评论图片的保存相对路径（不含图片后缀）
	 * @param userId 用户ID
	 * @param orderId 订单ID
	 * @param index 图片序号
	 * @return
	 */
	private static String getCommentPicSavePath(long userId,long orderId,int index){
		return CommonConfig.UPLOAD_DIRECTORY + "/comments/comment_" + userId + "_" + orderId + "_" + index + "_" + System.currentTimeMillis();
	}
}
