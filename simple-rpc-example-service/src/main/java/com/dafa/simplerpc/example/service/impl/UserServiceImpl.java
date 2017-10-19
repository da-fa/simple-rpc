package com.dafa.simplerpc.example.service.impl;

import com.dafa.simplerpc.annatation.RpcService;
import com.dafa.simplerpc.example.iface.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by Mtime on 2017/10/18 0018.
 */
@RpcService(UserService.class)
@Service
public class UserServiceImpl implements UserService {

    @Override
    public String getUserName(int userId) {
        if (userId == 1) {
            return "xiaoming";

        } else if (userId == 2) {
            return "lilei";

        } else {
            return "anyname";
        }
    }
}
