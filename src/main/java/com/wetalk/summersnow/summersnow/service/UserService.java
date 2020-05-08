package com.wetalk.summersnow.summersnow.service;

import com.wetalk.summersnow.summersnow.mapper.UserMapper;
import com.wetalk.summersnow.summersnow.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
//upadate

    public void createOrUpdate(User user) {
        User dbuserByAccount = userMapper.findByAccountId(user.getAccountId());
        //没找到插入，找到了更新
        if(dbuserByAccount==null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else{
            dbuserByAccount.setGmtModified(System.currentTimeMillis());
            dbuserByAccount.setAvatarUrl(user.getAvatarUrl());
            dbuserByAccount.setName(user.getName());
            dbuserByAccount.setToken(user.getToken());
            userMapper.update(dbuserByAccount);
        }

    }
}
