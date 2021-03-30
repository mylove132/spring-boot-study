package com.liuzhanhui.test.mybatis.mapper;
import com.liuzhanhui.test.mybatis.dto.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Mapper
public interface UserMapper {

    int updateUser(User user);

    User queryUserById(Integer userId);

    List<User> userList();

    int addUser (User user);
}
