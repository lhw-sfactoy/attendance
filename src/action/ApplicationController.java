package action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/interface")
public class ApplicationController
{
    @RequestMapping("/initDevice")
    public @ResponseBody Map<String, ? extends Object> initDevice()
    {
        Map<String, String> m = new HashMap<String, String>();
        
        m.put("val", "1");
        
        return m;
    }
}
