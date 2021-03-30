package com.liuzhanhui.test.mybatis.test;

import com.liuzhanhui.test.mybatis.dto.User;
import com.liuzhanhui.test.mybatis.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testQueryUserList () {
        List<User> users = userService.queryUserListService();
        System.out.println(users);
        List<User> users1 = userService.queryUserListService();
        System.out.println(users1);
    }

   // @Test
    public void addUser () {
        User user = new User();
        user.setUserId(100002l);
        user.setAddress("河南郑州常村镇");
        user.setUserName("刘文");
        user.setWeight(100);
        user.setUserPhone(15910401369l);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        userService.addUserService(user);
    }
}
