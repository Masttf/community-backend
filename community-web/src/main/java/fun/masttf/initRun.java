package fun.masttf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import fun.masttf.service.SysSettingService;

@Component
public class initRun implements ApplicationRunner {

    @Autowired
    private SysSettingService sysSettingService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        sysSettingService.refreshCache();
    }

}
