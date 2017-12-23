package cn.tommyyang.haoyan.common;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanFactory implements ApplicationContextAware {
	
	private static Logger logger = Logger.getLogger(SpringBeanFactory.class);
	
	private static ApplicationContext ctx;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		logger.info("init ApplicationContext");
		ctx = applicationContext;
	}

	public static <T> T getBean(Class<T> clazz) {
		if(ctx != null){
			T bean = ctx.getBean(clazz);
			return bean;
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		if(ctx != null){
			T bean = (T) ctx.getBean(beanName);
			return bean;
		}else{
			return null;
		}
	}
	
	public static <T> T getBeanId(String beanId) {
		T bean = (T) ctx.getBean(beanId);
		return bean;
	}
}
