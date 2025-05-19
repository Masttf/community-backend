package fun.masttf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AdminConfig extends AppConfig {
    @Value("${admin.acount:}")
    private String adminAcount;
    @Value("${admin.password:}")
    private String adminPassword;
    @Value("${web:api:url:}")
    private String webApiUrl;
    @Value("${isDev:}")
    private boolean isDev;
    public String getAdminAcount() {
        return adminAcount;
    }
    public String getAdminPassword() {
        return adminPassword;
    }
    public String getWebApiUrl() {
        return webApiUrl;
    }
    public boolean isDev() {
        return isDev;
    }

    
}
