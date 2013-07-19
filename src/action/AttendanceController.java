package action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attendance")
public class AttendanceController
{
    //static Logger logger = 
    final static String LIST_ACTION = "/list/{deviceId}";
    
    @RequestMapping(LIST_ACTION)
    public String list(@PathVariable String deviceId, Model model)
    {
        model.addAttribute("deviceId", deviceId);
        return "/index";
    }
}
