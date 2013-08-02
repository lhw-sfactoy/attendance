package action;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import service.AttendanceService;
import util.Message;

@Controller
@RequestMapping("/attendance")
public class AttendanceController
{
    //static Logger logger = 
    final static String LIST_ACTION = "/list/{deviceId}";
    
    @Autowired AttendanceService attendanceService;
    
    //출근현황
    @RequestMapping(LIST_ACTION)
    public String list(@PathVariable String deviceId, Model model)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Message.get("dateformat.yearmonth"));
        String date = dateFormat.format(Calendar.getInstance().getTime());

        
        model.addAttribute("lAttendance", attendanceService.attendanceList(deviceId, date));

        return "/list";
    }
}
