package com.mybatis.demo.service;

import com.mybatis.demo.entity.User;
import com.mybatis.demo.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    public List<User> getAll(){
        return userMapper.getAll();
    }

    public User getUserByPK(String id){
        return userMapper.getByPrimaryKey(id);
    }

    public int create(User user){

        return userMapper.insert(user);
    }

    public int update(User user){
        return userMapper.update(user);
    }

    public int delete(String id){
        return userMapper.deleteUserById(id);
    }

    public int deleteAll(){
        return userMapper.deleteAll();
    }

}
