package com.lyz;

import com.lyz.po.User;
import com.lyz.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyblogApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        User user = userService.checkUser("lyz", "a123456");
        System.out.println(user);
    }

}
