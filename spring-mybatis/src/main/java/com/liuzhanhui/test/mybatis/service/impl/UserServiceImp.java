package com.liuzhanhui.test.mybatis.service.impl;

import com.liuzhanhui.test.mybatis.dto.User;
import com.liuzhanhui.test.mybatis.annotation.Master;
import com.liuzhanhui.test.mybatis.mapper.UserMapper;
import com.liuzhanhui.test.mybatis.service.IRedisCacheService;
import com.liuzhanhui.test.mybatis.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IRedisCacheService redisCacheService;

    @Override
    public List<User> queryUserListService() {
        return userMapper.userList();
    }

    @Master
    @Override
    public int updateUserService(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User queryUserByIdService(Integer userId) {
        return userMapper.queryUserById(userId);
    }

    @Override
    public int addUserService(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User queryUserByToken() {
        return userMapper.queryUserById(100001);
    }
}
