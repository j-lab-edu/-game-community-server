package com.gamecommunityserver.service.impl;

import com.gamecommunityserver.dto.UserDTO;
import com.gamecommunityserver.exception.DuplicateIdException;
import com.gamecommunityserver.mapper.UserInfoMapper;
import com.gamecommunityserver.service.UserService;
import com.gamecommunityserver.utils.sha256Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserInfoMapper userinfomapper;

    public UserServiceImpl(UserInfoMapper userinfomapper) {
        this.userinfomapper = userinfomapper;
    }

    @Override
    public void register(UserDTO userDTO){
        if(idOverlapCheck(userDTO.getId()))
            throw new DuplicateIdException("중복된 ID 입니다.");

        userDTO.setPassword(sha256Encrypt.encrypt(userDTO.getPassword()));
        userDTO.setAdmin(0);
        userDTO.setUserSecession(0);
        userinfomapper.register(userDTO);
    }
    @Override
    public boolean idOverlapCheck(String id){
        return userinfomapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO LoginCheckPassword(String id, String password){
        password = sha256Encrypt.encrypt(password);
        UserDTO userinfo = userinfomapper.passwordCheck(id, password);
        return userinfo;
    }
    @Override
    public void deleteUser(int usernumber){
        userinfomapper.deleteUser(usernumber);
    }

}