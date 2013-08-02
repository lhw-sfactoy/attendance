package service;

import java.util.List;

import model.Attendance;


public interface IAttendanceService
{
    public List<Attendance> attendanceList(String deviceId, String date);
    
    public int attendanceInsert(Attendance attendance);
}
