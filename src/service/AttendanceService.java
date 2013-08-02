package service;

import java.util.List;

import model.Attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import data.AttendanceMapper;

@Component
public class AttendanceService implements IAttendanceService
{
    @Autowired
    AttendanceMapper attendanceMapper;
    
    public List<Attendance> attendanceList(String deviceId, String date)
    {
        return attendanceMapper.selectAttendance(deviceId, date);
    }
    
    public int attendanceInsert(Attendance attendance)
    {
        return attendanceMapper.insertOneAttendance(attendance);
    }
}
