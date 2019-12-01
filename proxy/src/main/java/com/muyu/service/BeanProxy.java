package com.muyu.service;


import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;

/**
 * @author 七小鱼
 */
public interface BeanProxy extends InvocationHandler, FactoryBean {

}
