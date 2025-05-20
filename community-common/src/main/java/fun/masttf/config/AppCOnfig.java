package fun.masttf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${project.folder:}")
    private String projectFolder;

    @Value("${inner.api.appKey:}")
    private String innerApiAppKey;
    @Value("${inner.api.appSecret:}")
    private String innerApiAppSecret;

    public String getProjectFolder() {
        return projectFolder;
    }

    public String getInnerApiAppKey() {
        return innerApiAppKey;
    }

    public String getInnerApiAppSecret() {
        return innerApiAppSecret;
    }

    
}
