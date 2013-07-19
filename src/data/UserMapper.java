package data;

import model.User;

public interface UserMapper
{
    public User selectOneUser(User user);
    
    public int selectCountUser(User user);
    
    public int insertOneUser(User user);
}
