package action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import model.Attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import service.AttendanceService;
import util.Message;

@Controller
@RequestMapping("/attendance")
public class AttendanceController
{
    //static Logger logger = 
    final static String LIST_ACTION = "/list/{deviceId}";
    final static String LOGIN_ACTION = "/login";
    
    @Autowired AttendanceService attendanceService;
    
    //출근현황
    @RequestMapping(LIST_ACTION)
    public String list(@PathVariable String deviceId
            , @RequestParam(required = false) String searchDate/* 2009-12 */, Model model)
    {
        int firstDay = 1;
        Calendar viewCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = null;
        //검색년월이 없는경우 현재년월로 검색
        if(searchDate == null || "".equals(searchDate))
        {
            viewCalendar.set(Calendar.DAY_OF_MONTH, firstDay);
            
            dateFormat = new SimpleDateFormat(Message.get("dateformat.yearmonth"));
            searchDate = dateFormat.format(viewCalendar.getTime());
        }
        else
        {
            String[] arrDate = searchDate.split("-");
            viewCalendar.set(Integer.parseInt(arrDate[0])
                    , Integer.parseInt(arrDate[1]) - 1, firstDay);
        }
        
        Map<String, Object> m = new HashMap<String, Object>();
        
        for(Attendance attendance 
                : attendanceService.attendanceList(deviceId, searchDate))
        {
            System.out.println(attendance.getDate());
            m.put(attendance.getDate(), attendance);
        }

        model.addAttribute("lAttendance"
                , attendanceService.attendanceList(deviceId, searchDate))
             .addAttribute("viewCalendar", viewCalendar)
             .addAttribute("deviceId", deviceId);

        return "/list";
    }
    
    @RequestMapping(value = LOGIN_ACTION, method = RequestMethod.GET)
    public String login(Model model)
    {
        return "login";
    }
    
    @RequestMapping(value = LOGIN_ACTION, method = RequestMethod.POST)
    @ResponseBody
    public String login()
    {
        return LOGIN_ACTION;
    }
}
