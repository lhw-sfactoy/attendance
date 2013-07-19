package model;

public class User
{
    int id;
    String deviceId;
    String name;
    
    public int getId()
    {
        return id;
    }
    public User setId(int id)
    {
        this.id = id;
        return this;
    }
    public String getDeviceId()
    {
        return deviceId;
    }
    public User setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
        return this;
    }
    public String getName()
    {
        return name;
    }
    public User setName(String name)
    {
        this.name = name;
        return this;
    }
}
