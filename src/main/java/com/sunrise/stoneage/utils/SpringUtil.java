package com.sunrise.stoneage.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring上下文工具类
 * SpringBoot 平台下通过ApplicationContextAware接口的方式实现ApplicationContext上下文实例的获取。
 * 注：需加 @Component 注解，否则spring将不会创建此Bean，也就不会自动调用setApplicationContext方法来为我们设置上下文实例。
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    // 上下文对象实例
    private static ApplicationContext context;

    /**
     * Spring在bean初始化后会判断是不是ApplicationContextAware的子类
     * 如果该类是,setApplicationContext()方法,会将容器中ApplicationContext作为参数传入进去
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    // 通过Name返回指定的Bean
    public static <T> T getBean(Class<T> beanClass){
        return context.getBean(beanClass);
    }
}
