package util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class Message
{
    static MessageSource messageSource;
    
    @Autowired(required = true) /* static 일땐 setter 필요 */
    public void setMessageSource(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }
    
    public static String get(String arg0)
    {
        return get(arg0, null);
    }
    
    public static String get(String arg0, String[] arg1)
    {
        return messageSource.getMessage(arg0, arg1, Locale.KOREA);
    }
}
