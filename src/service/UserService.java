package service;

import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import data.UserMapper;

@Component
public class UserService implements IUserService
{
    @Autowired
    UserMapper userMapper;

    public User userView(User user)
    {
        return userMapper.selectOneUser(user);
    }
    
    public int userCount(User user)
    {
        return userMapper.selectCountUser(user);
    }
    
    public int userInsert(User user)
    {
        return userMapper.insertOneUser(user);
    }
}
