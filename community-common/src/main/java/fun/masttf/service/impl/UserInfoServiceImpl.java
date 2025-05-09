package fun.masttf.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.entity.po.UserInfo;
import fun.masttf.entity.query.UserInfoQuery;
import fun.masttf.service.EmailCodeService;
import fun.masttf.service.UserInfoService;
import fun.masttf.utils.StringTools;
import fun.masttf.mapper.UserInfoMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.enums.PageSize;
import fun.masttf.entity.enums.UserStatusEnum;

/**
 * @Description:用户信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private EmailCodeService emailCodeService;

	@Autowired
	private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

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
	}
}