package data;

import model.Attendance;

public interface AttendanceMapper
{
    public Attendance selectAttendance();
    
    public int insertOneAttendance(Attendance attendance);
}
