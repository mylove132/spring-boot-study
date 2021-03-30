package com.liuzhanhui.test.mybatis.service;
import com.liuzhanhui.test.mybatis.dto.User;
import java.util.List;

public interface IUserService {

    List<User> queryUserListService();

    int updateUserService(User user);

    User queryUserByIdService(Integer userId);

    int addUserService (User user);

    User queryUserByToken ();

}
