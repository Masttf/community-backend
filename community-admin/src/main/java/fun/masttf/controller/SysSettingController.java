package fun.masttf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.protocol.x.Ok;

import fun.masttf.annotation.VerifyParam;
import fun.masttf.config.AdminConfig;
import fun.masttf.entity.dto.SysSetting4AuditDto;
import fun.masttf.entity.dto.SysSetting4CommentDto;
import fun.masttf.entity.dto.SysSetting4EmailDto;
import fun.masttf.entity.dto.SysSetting4LikeDto;
import fun.masttf.entity.dto.SysSetting4PostDto;
import fun.masttf.entity.dto.SysSetting4Register;
import fun.masttf.entity.dto.SysSettingDto;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.service.SysSettingService;
import fun.masttf.utils.JsonUtils;
import fun.masttf.utils.OKHttpUtils;
import fun.masttf.utils.StringTools;
import fun.masttf.utils.SysCacheUtils;

@RestController
@RequestMapping("/setting")
public class SysSettingController extends ABaseController {
    @Autowired
    private SysSettingService sysSettingService;
    @Autowired
    private AdminConfig adminConfig;

    @RequestMapping("/getSetting")
    public ResponseVo<Object> getSetting() {
        sysSettingService.refreshCache();
        return getSuccessResponseVo(SysCacheUtils.getSysSetting());
    }

    @RequestMapping("/saveSetting")
    public ResponseVo<Object> saveSetting(@VerifyParam SysSetting4AuditDto auditDto,
                                          @VerifyParam SysSetting4CommentDto commentDto,
                                          @VerifyParam SysSetting4EmailDto emailDto,
                                          @VerifyParam SysSetting4LikeDto likeDto,
                                          @VerifyParam SysSetting4PostDto postDto,
                                          @VerifyParam SysSetting4Register registerDto) {
        SysSettingDto sysSettingDto = new SysSettingDto();
        sysSettingDto.setAuditSetting(auditDto);
        sysSettingDto.setCommentSetting(commentDto);
        sysSettingDto.setEmailSetting(emailDto);
        sysSettingDto.setLikeSetting(likeDto);
        sysSettingDto.setPostSetting(postDto);
        sysSettingDto.setRegisterSetting(registerDto);
        sysSettingService.saveSetting(sysSettingDto);
        sendWebRequest();
        return getSuccessResponseVo(null);
    }

    private void sendWebRequest(){
        String appKey = adminConfig.getInnerApiAppKey();
        String appSecret = adminConfig.getInnerApiAppSecret();
        Long timestamp = System.currentTimeMillis();
        String sign = StringTools.encodeMd5(appKey + timestamp + appSecret);
        String url = adminConfig.getWebApiUrl() + "?appKey=" + appKey + "&timestamp=" + timestamp + "&sign=" + sign;
        String resJson = OKHttpUtils.getRequest(url);
        ResponseVo<?> responseVo = JsonUtils.convertJson2Obj(resJson, ResponseVo.class);
        if(!STATUS_SUCCESS.equals(responseVo.getStatus())){
            throw new BusinessException("刷新访客系统设置失败");
        }
    }

}
