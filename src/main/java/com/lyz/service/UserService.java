package com.lyz.service;

import com.lyz.po.User;

public interface UserService {

    User checkUser(String username, String password);

}
