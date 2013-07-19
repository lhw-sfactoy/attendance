package model;

public class Attendance
{
    int id;
    String date;
    String time;
    String state;
    String msg;
    String userName;
    String userDeviceId;
    String createDate;
    
    public int getId()
    {
        return id;
    }
    public Attendance setId(int id)
    {
        this.id = id;
        return this;
    }
    public String getDate()
    {
        return date;
    }
    public Attendance setDate(String date)
    {
        this.date = date;
        return this;
    }
    public String getTime()
    {
        return time;
    }
    public Attendance setTime(String time)
    {
        this.time = time;
        return this;
    }
    public String getState()
    {
        return state;
    }
    public Attendance setState(String state)
    {
        this.state = state;
        return this;
    }
    public String getMsg()
    {
        return msg;
    }
    public Attendance setMsg(String msg)
    {
        this.msg = msg;
        return this;
    }
    public String getUserName()
    {
        return userName;
    }
    public Attendance setUserName(String userName)
    {
        this.userName = userName;
        return this;
    }
    public String getUserDeviceId()
    {
        return userDeviceId;
    }
    public Attendance setUserDeviceId(String userDeviceId)
    {
        this.userDeviceId = userDeviceId;
        return this;
    }
    public String getCreateDate()
    {
        return createDate;
    }
    public Attendance setCreateDate(String createDate)
    {
        this.createDate = createDate;
        return this;
    }
}
