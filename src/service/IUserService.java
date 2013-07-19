package service;

import model.User;

public interface IUserService
{
    public User userView(User user);
    
    public int userCount(User user);
    
    public int userInsert(User user);
}
