package fun.masttf.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.entity.po.UserInfo;
import fun.masttf.entity.po.UserIntegralRecord;
import fun.masttf.entity.po.UserMessage;
import fun.masttf.entity.query.UserInfoQuery;
import fun.masttf.entity.query.UserIntegralRecordQuery;
import fun.masttf.entity.query.UserMessageQuery;
import fun.masttf.service.EmailCodeService;
import fun.masttf.service.UserInfoService;
import fun.masttf.utils.JsonUtils;
import fun.masttf.utils.OKHttpUtils;
import fun.masttf.utils.StringTools;
import fun.masttf.utils.SysCacheUtils;
import fun.masttf.mapper.UserInfoMapper;
import fun.masttf.mapper.UserIntegralRecordMapper;
import fun.masttf.mapper.UserMessageMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.config.WebConfig;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.MessageStatusEnum;
import fun.masttf.entity.enums.MessageTypeEnum;
import fun.masttf.entity.enums.PageSize;
import fun.masttf.entity.enums.UserIntegralChangeTypeEnum;
import fun.masttf.entity.enums.UserIntegralOperTypeEnum;
import fun.masttf.entity.enums.UserStatusEnum;

/**
 * @Description:用户信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserInfoServiceImpl.class);

	@Autowired
	private EmailCodeService emailCodeService;

	@Autowired
	private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

	@Autowired
	private UserMessageMapper<UserMessage, UserMessageQuery> userMessageMapper;

	@Autowired
	private UserIntegralRecordMapper<UserIntegralRecord, UserIntegralRecordQuery> userIntegralRecordMapper;
	@Autowired
	private WebConfig webConfig;
	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<UserInfo> findListByQuery(UserInfoQuery query) {
		return userInfoMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(UserInfoQuery query) {
		return userInfoMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<UserInfo> findListByPage(UserInfoQuery query) {
		Integer count = userInfoMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<UserInfo> list = userInfoMapper.selectList(query);
		PaginationResultVo<UserInfo> result = new PaginationResultVo<UserInfo>(count, page.getPageSize(),
				page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(UserInfo bean) {
		return userInfoMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<UserInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return userInfoMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<UserInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return userInfoMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据UserId查询
	 */
	@Override
	public UserInfo getByUserId(String userId) {
		return userInfoMapper.selectByUserId(userId);
	}

	/**
	 * 根据UserId更新
	 */
	@Override
	public Integer updateByUserId(UserInfo bean, String userId) {
		return userInfoMapper.updateByUserId(bean, userId);
	}

	/**
	 * 根据UserId删除
	 */
	@Override
	public Integer deleteByUserId(String userId) {
		return userInfoMapper.deleteByUserId(userId);
	}

	/**
	 * 根据Email查询
	 */
	@Override
	public UserInfo getByEmail(String email) {
		return userInfoMapper.selectByEmail(email);
	}

	/**
	 * 根据Email更新
	 */
	@Override
	public Integer updateByEmail(UserInfo bean, String email) {
		return userInfoMapper.updateByEmail(bean, email);
	}

	/**
	 * 根据Email删除
	 */
	@Override
	public Integer deleteByEmail(String email) {
		return userInfoMapper.deleteByEmail(email);
	}

	/**
	 * 根据NickName查询
	 */
	@Override
	public UserInfo getByNickName(String nickName) {
		return userInfoMapper.selectByNickName(nickName);
	}

	/**
	 * 根据NickName更新
	 */
	@Override
	public Integer updateByNickName(UserInfo bean, String nickName) {
		return userInfoMapper.updateByNickName(bean, nickName);
	}

	/**
	 * 根据NickName删除
	 */
	@Override
	public Integer deleteByNickName(String nickName) {
		return userInfoMapper.deleteByNickName(nickName);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void register(String email, String emailCode, String nickName, String password) {
		UserInfo userInfo = userInfoMapper.selectByEmail(email);
		if (userInfo != null) {
			throw new BusinessException("邮箱已存在");
		}
		userInfo = userInfoMapper.selectByNickName(nickName);
		if (userInfo != null) {
			throw new BusinessException("昵称已存在");
		}
		emailCodeService.checkCode(email, emailCode);
		String userId = StringTools.getRandomNumber(Constans.LENGTH_10);
		UserInfo bean = new UserInfo();
		bean.setUserId(userId);
		bean.setEmail(email);
		bean.setNickName(nickName);
		bean.setPassword(StringTools.encodeMd5(password));
		bean.setStatus(UserStatusEnum.NORMAL.getStatus());
		bean.setJoinTime(new Date());
		bean.setCurrentIntegral(0);
		bean.setTotalIntegral(0);
		userInfoMapper.insert(bean);
		//更新用户积分
		updateUserIntegral(userId, UserIntegralOperTypeEnum.REGISTER, UserIntegralChangeTypeEnum.ADD.getChangeType(), Constans.INTEGRAL_5);

		// 记录消息
		UserMessage userMessage = new UserMessage();
		userMessage.setReceivedUserId(userId);
		userMessage.setMessageType(MessageTypeEnum.SYS.getType());
		userMessage.setCreateTime(new Date());
		userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
		userMessage.setMessageContent(SysCacheUtils.getSysSetting().getRegisterSetting().getRegisterWelcomeInfo());
		userMessageMapper.insert(userMessage);
	}
	/*
	 * 更新用户积分
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateUserIntegral(String userId, UserIntegralOperTypeEnum operTypeEnum, Integer changeType, Integer integral) {
		integral = changeType * integral;
		if(integral == 0){
			return ;
		}
		UserInfo userInfo = userInfoMapper.selectByUserId(userId);
		if(UserIntegralChangeTypeEnum.REDUCE.getChangeType().equals(changeType) && userInfo.getCurrentIntegral() + integral < 0){
			throw new BusinessException("用户积分不足");
		}

		UserIntegralRecord record = new UserIntegralRecord();
		record.setUserId(userId);
		record.setOperType(operTypeEnum.getOperType());
		record.setCreateTime(new Date());
		record.setIntegral(integral);
		userIntegralRecordMapper.insert(record);
		//在数据库判断减后积分要>=0,确保积分不为负数
		//如果不行事物会滚
		Integer res = userInfoMapper.updateIntegral(userId, integral);
		if(res == 0){
			throw new BusinessException("更新用户积分失败");
		}
	}
	public String getIpProvince(String ip) {
		try {
			String url = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + ip;
			String responseJson = OKHttpUtils.getRequest(url);
			if(responseJson == null) {
				return Constans.NO_ADDRESS;
			}
			Map<String, String> addressInfo = JsonUtils.convertJson2Obj(responseJson, Map.class);
			return addressInfo.get("pro");
		} catch (Exception e) {
			logger.error("获取ip地址失败", e);
		}
		return Constans.NO_ADDRESS;
	}

	@Override
	public SessionWebUserDto login(String email, String password, String ip) {
		UserInfo userInfo = userInfoMapper.selectByEmail(email);
		// logger.info(userInfo.toString());
		if(userInfo == null || !userInfo.getPassword().equals(password)){
			throw new BusinessException("账号或密码错误");
		}
		if(userInfo.getStatus() == UserStatusEnum.DISABLE.getStatus()){
			throw new BusinessException("用户已被禁用");
		}
		UserInfo updateInfo = new UserInfo();
		updateInfo.setLastLoginTime(new Date());
		updateInfo.setLastLoginIp(ip);
		updateInfo.setLastLoginIpAddress(getIpProvince(ip));
		userInfoMapper.updateByUserId(updateInfo, userInfo.getUserId());
		SessionWebUserDto sessionWebUserDto = new SessionWebUserDto();
		sessionWebUserDto.setNickName(userInfo.getNickName());
		sessionWebUserDto.setProvice(getIpProvince(ip));
		sessionWebUserDto.setUserId(userInfo.getUserId());
		if(!StringTools.isEmpty(webConfig.getAdminEmails()) && ArrayUtils.contains(webConfig.getAdminEmails().split(","), email)){
			sessionWebUserDto.setIsAdmin(true);
		} else{
			sessionWebUserDto.setIsAdmin(false);
		}
		return sessionWebUserDto;
	}
	

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void resetPwd(String email, String emailCode, String password) {
		UserInfo userInfo = userInfoMapper.selectByEmail(email);
		if (userInfo != null) {
			throw new BusinessException("邮箱已存在");
		}
		emailCodeService.checkCode(email, emailCode);
		UserInfo bean = new UserInfo();
		bean.setPassword(StringTools.encodeMd5(password));
		userInfoMapper.updateByEmail(bean, emailCode);
	}

}