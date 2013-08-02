package action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.Attendance;
import model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.IAttendanceService;
import service.IUserService;
import util.Message;

@Controller
@RequestMapping("/interface")
public class ApplicationController
{
    static Logger logger = LoggerFactory.getLogger(ApplicationController.class);
    
    static final String INIT_DEVICE_ACTION = "/initDevice";
    static final String DEVICE_CERTIFICATION_ACTION = "/deviceCertification";
    static final String REGISTER_ACTION = "/attendanceRequest";
    
    static final String MESSAGE_PATH = "application.result.";
    
    static final String RESULT_CD_KEY = "recode";
    static final String RESULT_MSG_KEY = "remsg";
    
    static final int SYSTEM_ERROR = -1;
    static final int SYSTEM_OK = 0;
    
    static final int INSERT_DEVICE_ID_SUCCESS_CD = 10;
    static final int EXISTS_DEVICE_ID_CD = 20;
    static final int NORMAL_ATTENDANCE_CD = 100;
    static final int LATE_ATTENDANCE_CD = 200;
    static final int FAIL_DEVICE_CHECK_CD = 300;
    static final int FAIL_AP_CHECK_CD = 400;
    
    static final String WIFI_NAME = "SFWLAN";
    static final String WIFI_BSS_ID = "0:8:9f:b:a7:4";
    
    static final int ATTENDANCE_HOUR_LIMIT = 9;
    static final int ATTENDANCE_MINUTE_LIMIT = 0;
    static final int ATTENDANCE_SECOND_LIMIT = 0;
        
    @Autowired IUserService userService;   
    @Autowired IAttendanceService attendanceService;
    
    private boolean isExistDevice(User user)
    {
        return userService.userCount(user) == 1;
    }
    
    private boolean isAllowAP(String wifiName, String wifiBssId)
    {
        return WIFI_NAME.equals(wifiName) && WIFI_BSS_ID.equals(wifiBssId);
    }
    
    /* @ExceptionHandler 가 가질 수 있는 파라미터 타입 정의
    parameter type : HttpServletRequest / Response, HttpSession, Locale
                     InputStream / Reader, OutputStream / Writer
                     Exception type
    
    return type : ModelAndView, Model, Map, View, String, Void
    */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, ? extends Object> ExceptionHandler(Exception e)
    {
        Map<String, Object> m = new HashMap<String, Object>();
        System.out.println(e.getMessage());
        m.put(RESULT_CD_KEY, SYSTEM_ERROR);
        m.put(RESULT_MSG_KEY, Message.get(MESSAGE_PATH + SYSTEM_ERROR));
        
        return m;
    }
    
    /**
     * 디바이스 최초 통신
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = INIT_DEVICE_ACTION)
    @ResponseBody
    public Map<String, ? extends Object> initDevice(HttpServletRequest req)
    {
        logger.debug("start initDevice");
        logger.debug("accept : " + req.getHeader("accept"));
        
        Map<String, String> m = new HashMap<String, String>();
        //112.216.116.254:38080/interface/initDevice
        m.put("appCloseYN", "N");
        m.put("appMsg", "");
        m.put("updateYN", "");
        m.put("updateUrl", "");
        m.put("updateMsg", "");
        m.put("wifiName", WIFI_NAME);
        m.put("wifiBssId", WIFI_BSS_ID);
        
        logger.debug("end initDevice");
        
        return m;
    }
    
    /**
     * 디바이스 인증
     * 
     * @param model
     * @param user
     * @return
     */
    @RequestMapping(value = DEVICE_CERTIFICATION_ACTION, method = RequestMethod.POST)
    @ResponseBody
    public Map<String, ? extends Object> deviceCertification(Model model
            , @RequestBody User user)
    {
        Map<String, Object> m = new HashMap<String, Object>();
        
        //TODO user 유효성 검사 해야함
        
        if(this.isExistDevice(user)) //존재하는 경우
        {
            m.put(RESULT_CD_KEY, EXISTS_DEVICE_ID_CD);
            m.put(RESULT_MSG_KEY, Message.get(MESSAGE_PATH
                    + EXISTS_DEVICE_ID_CD));
        }
        else
        {
            userService.userInsert(user); //generate key는 instance에 삽입된다
            
            m.put(RESULT_CD_KEY, INSERT_DEVICE_ID_SUCCESS_CD);
            m.put(RESULT_MSG_KEY, Message.get(MESSAGE_PATH + INSERT_DEVICE_ID_SUCCESS_CD));
        }

        return m;
    }
    
    /**
     * 출근등록 요청
     * 
     * @param model
     * @param requestBodyMap
     * @return
     */
    @RequestMapping(value = REGISTER_ACTION, method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Map<String, ? extends Object> attendanceRequest(Model model
            , @RequestBody Map<String, String> requestBodyMap)
    {
        Map<String, Object> m = new HashMap<String, Object>();
        
        String userDeviceId = requestBodyMap.get("deviceId")
                , userName = requestBodyMap.get("name")
                , wifiName = requestBodyMap.get("wifiName")
                , wifiBssId = requestBodyMap.get("wifiBssId")
                , state; //출근상태
        
        //TODO 필드 유효성 검사 해야함
        
        if(!this.isAllowAP(wifiName, wifiBssId)) //AP 인증 실패
        {
            m.put(RESULT_CD_KEY, FAIL_AP_CHECK_CD);
            m.put(RESULT_MSG_KEY, Message.get(MESSAGE_PATH + FAIL_AP_CHECK_CD));
            return m;
        }
        
        User user = new User();
        user
            .setDeviceId(userDeviceId)
            .setName(userName);
        
        if(!this.isExistDevice(user)) //deviceId 없음
        {
            m.put(RESULT_CD_KEY, FAIL_DEVICE_CHECK_CD);
            m.put(RESULT_MSG_KEY, Message.get(MESSAGE_PATH + FAIL_DEVICE_CHECK_CD));
            return m;
        }
        
        //FIXME 달력 처리에 대한 고민 필요
        Calendar currentCalendar = Calendar.getInstance()
                , limitCalendar = (Calendar)currentCalendar.clone();
        
        String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentCalendar.getTime());
        String[] dateArr = dateTime.split(" ");
        String date = dateArr[0], time = dateArr[1];
        
        limitCalendar.set(Calendar.HOUR_OF_DAY, ATTENDANCE_HOUR_LIMIT);
        limitCalendar.set(Calendar.MINUTE, ATTENDANCE_MINUTE_LIMIT);
        limitCalendar.set(Calendar.SECOND, ATTENDANCE_SECOND_LIMIT);
        
        if(currentCalendar.after(limitCalendar)) //지각이면
        {
            m.put(RESULT_CD_KEY, LATE_ATTENDANCE_CD);
            m.put(RESULT_MSG_KEY, Message.get(MESSAGE_PATH + LATE_ATTENDANCE_CD));
            state = "지각";
        }
        else
        {
            m.put(RESULT_CD_KEY, NORMAL_ATTENDANCE_CD);
            m.put(RESULT_MSG_KEY, Message.get(MESSAGE_PATH + LATE_ATTENDANCE_CD));
            state = "출근";
        }
        
        m.put("attRegTime", time);
        
        Attendance attendance = new Attendance();
        attendance
                .setDate(date)
                .setTime(time)
                .setState(state)
                .setUserDeviceId(userDeviceId)
                .setUserName(userName);
        
        attendanceService.attendanceInsert(attendance);
        
        return m;
    }
    
    /**/
    /**
    @RequestMapping(value = DEVICE_CERTIFICATION_ACTION)
//    public @ResponseBody String String deviceCertification(@RequestBody Map<String, Object> map)
    //public @ResponseBody String deviceCertification(@RequestBody User user)
    //public @ResponseBody String deviceCertification(@RequestBody MultiValueMap<String, String> requestBodyMap)
    public @ResponseBody String deviceCertification(@RequestBody String body)
    {
        System.out.println(body);
       
        return body;
    }
    /**/
}
