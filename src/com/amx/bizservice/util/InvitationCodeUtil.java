package com.amx.bizservice.util;

/**
 * 邀请码助手
 * @author leihaijun
 * @Title
 * @Descrption
 * @date 2018年9月10日 下午7:22:21
 * @Modified By
 */
public class InvitationCodeUtil {
	/**
     * 
     * 随机生成验证码（数字+字母）
     *
     * @return
     * 
     * @author ailo555
     * @date 2016年10月23日 上午9:27:09
     */
    public static String generateRandomStr() {
    	int len = 6;
        //字符源，可以根据需要删减
        String generateSource = "23456789abcdefghgklmnpqrstuvwxyz";//去掉1和i ，0和o
        String rtnStr = "";
        for (int i = 0; i < len; i++) {
            //循环随机获得当次字符，并移走选出的字符
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            rtnStr += nowStr;
            generateSource = generateSource.replaceAll(nowStr, "");
        }
        return rtnStr;
    }
}
