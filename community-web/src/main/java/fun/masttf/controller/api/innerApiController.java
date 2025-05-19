package fun.masttf.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.annotation.VerifyParam;
import fun.masttf.config.WebConfig;
import fun.masttf.controller.ABaseController;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.service.SysSettingService;
import fun.masttf.utils.StringTools;

@RestController
@RequestMapping("/innerApi")
public class innerApiController extends ABaseController {
    @Autowired
    private WebConfig webConfig;
    @Autowired
    private SysSettingService sysSettingService;

    @RequestMapping("/refreshSysSetting")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> refreshSysSetting(
        @VerifyParam(required = true) String appKey,
        @VerifyParam(required = true) Long timestamp,
        @VerifyParam(required = true) String sign){
        if(!webConfig.getInnerApiAppKey().equals(appKey)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if(System.currentTimeMillis() - timestamp > 1000 * 10) {
            throw new BusinessException(ResponseCodeEnum.CODE_900);
        }
        String mySign = StringTools.encodeMd5(appKey + timestamp + webConfig.getInnerApiAppSecret());
        if(!mySign.equals(sign)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        sysSettingService.refreshCache();
        return getSuccessResponseVo(null);
    }
}
