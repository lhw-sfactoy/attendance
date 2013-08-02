package data;

import java.util.List;

import model.Attendance;

public interface AttendanceMapper
{
    public List<Attendance> selectAttendance(String deviceId, String date);
    
    public int insertOneAttendance(Attendance attendance);
}
