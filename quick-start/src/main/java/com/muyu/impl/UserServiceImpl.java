package com.muyu.impl;

import com.muyu.entity.User;
import com.muyu.service.UserService;

/**
 * @author 七小鱼
 */
public class UserServiceImpl implements UserService {
    @Override
    public User queryUserById(Integer id) {
        return new User(1, "张三", "123456");
    }
}
