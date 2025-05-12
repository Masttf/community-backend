package fun.masttf.config;

import org.springframework.beans.factory.annotation.Value;

public class AppConfig {
    @Value("${project.folder:}")
    private String projectFolder;

    public String getProjectFolder() {
        return projectFolder;
    }

    
}
