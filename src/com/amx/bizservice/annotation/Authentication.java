package com.amx.bizservice.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.amx.bizservice.enums.AccessLevelEnum;

/**
 * 适用在类或方法上
 * 类：表示该类的所有方法都需要权限验证
 * 方法：表示该方法需要权限验证（并将覆盖类上的注解权限）
 * @author DangerousHai
 *
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authentication {
	
	/**
	 * 访问级别，默认 PRIVATE (最高限制)
	 * @return
	 */
	public AccessLevelEnum accessLevel() default AccessLevelEnum.PRIVATE;

}
