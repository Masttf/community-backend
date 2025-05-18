package fun.masttf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebConfig extends AppConfig {
    @Value("${spring.mail.username:}")
    private String sendUserName;

    @Value("${admin.emails:}")
    private String adminEmails;

    @Value("${inner.api.appKey:}")
    private String innerApiAppKey;
    @Value("${inner.api.appSecret:}")
    private String innerApiAppSecret;


    public String getSendUserName() {
        return sendUserName;
    }
    public String getAdminEmails() {
        return adminEmails;
    }
    public String getInnerApiAppKey() {
        return innerApiAppKey;
    }
    public String getInnerApiAppSecret() {
        return innerApiAppSecret;
    }
    

}
