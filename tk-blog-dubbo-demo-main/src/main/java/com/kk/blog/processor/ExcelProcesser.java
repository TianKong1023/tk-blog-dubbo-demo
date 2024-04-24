package com.kk.blog.processor;

import com.kk.blog.annotations.ExcelProcesserAnnotation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 测试
 */
@Component
public class ExcelProcesser implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        // 检查是否标记了自定义注解
        if (bean.getClass().isAnnotationPresent(ExcelProcesserAnnotation.class)) {
            ExcelProcesserAnnotation annotation = bean.getClass().getAnnotation(ExcelProcesserAnnotation.class);
            String value = annotation.value(); // 获取注解的值
            // 在此处执行你的逻辑，可以根据注解的值进行不同的处理
            System.out.println("Bean '" + beanName + "' is annotated with ExcelProcesserAnnotation with value: " + value);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
