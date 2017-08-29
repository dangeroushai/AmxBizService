package com.amx.bizservice.util;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.amx.bizservice.config.CommonConfig;

public class NumCaptchaUtil {

	public static Random random;
	
	/**
     * 阻塞队列.
     */
    private static BlockingQueue<Captcha> queue;
    
    static{
    	queue = new LinkedBlockingQueue<Captcha>();
    }

	/**
	 * @return 6位随机验证码
	 */
	public static String generateCaptcha() {
		if (random == null) {
			random = new Random();
		}
		return (random.nextInt(9999) + 10000) + "";
	}

	/**
	 * 检查验证码是否有效
	 * 
	 * @param captcha
	 *            原始的验证码
	 * @param captchaWithTimeMillis
	 *            含有时间信息的验证码
	 * @return
	 */
	public static boolean isCaptchaValid(String captcha, String captchaWithTimeMillis) {
		try {
			// 抽取验证码创建时间
			String captchaArr[] = captchaWithTimeMillis.split(":");
			// 验证码是否相同
			boolean isEqual = captchaArr[0].equalsIgnoreCase(captcha);
			if (isEqual) {
				// 验证码创建的时间
				long createMillis = Long.parseLong(captchaArr[1]);
				long nowMillis = System.currentTimeMillis();
				// 过期校验
				if (nowMillis - createMillis <= CommonConfig.CAPTCHE_TIME_OUT) {
					return true;
				}
			}
		} catch (Exception e) {
		}

		return false;
	}

	public static String getCaptchaWithTimeMillisByPhone(String phone) {
		String code = null;
		Iterator<Captcha> iterator = queue.iterator();
		while (iterator.hasNext()) {
			Captcha captcha = iterator.next();
			if(captcha.getPhone().equals(phone)){
				code = captcha.getCode();
				break;
			}
		}
		
		return code;
	}

	//FIXME - 修改为异步执行
	public static void removeCaptchaByPhone(String phone) {
		Iterator<Captcha> iterator = queue.iterator();
		while (iterator.hasNext()) {
			Captcha captcha = iterator.next();
			if(captcha.getPhone().equals(phone)){
				queue.remove(captcha);
				break;
			}
		}
	}

	/**
	 * 生成并保存验证码
	 * @param phone
	 * @return
	 */
	public static String generateAndSaveCaptcha(String phone) {
		String captcha = generateCaptcha();
		String captchaWithTimeMillis = getCaptchaWithTimeMillis(captcha);
		
		try {
			saveCaptcha(phone,captchaWithTimeMillis);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		return captcha;
	}

	//FIXME - 修改为异步执行
	private static void saveCaptcha(String phone, String captchaWithTimeMillis) throws InterruptedException {
		queue.put(new Captcha(phone,captchaWithTimeMillis));
	}
	

	/**
	 * 
	 * @param srcCaptcha
	 *            原始验证码
	 * @return 含时间信息的验证码
	 */
	private static String getCaptchaWithTimeMillis(String srcCaptcha) {
		return srcCaptcha + ":" + System.currentTimeMillis();
	}
}

class Captcha{
	private String phone;
	private String code;
	
	public Captcha(String phone, String captchaWithTimeMillis) {
		this.phone = phone;
		this.code = captchaWithTimeMillis;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Captcha other = (Captcha) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
