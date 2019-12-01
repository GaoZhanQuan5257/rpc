package com.muyu.meta;

import java.io.Serializable;

/**
 * @author 七小鱼
 */
public class MethodInvokeMeta implements Serializable {
    private Class targetClass;//远程目标对象
    private String name;//方法名
    private Class<?>[] parameterTypes;//参数类型
    private Object[] args;//参数值

    public MethodInvokeMeta(Class targetClass, String name,
                            Class<?>[] parameterTypes, Object[] args) {
        super();
        this.targetClass = targetClass;
        this.name = name;
        this.parameterTypes = parameterTypes;
        this.args = args;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }


}
