package fun.masttf.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.UserIntegralOperTypeEnum;
import fun.masttf.entity.po.UserInfo;
import fun.masttf.entity.query.UserInfoQuery;

/**
 * @Description:用户信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface UserInfoService {

	/**
	 * 根据条件查询列表
	 */
	List<UserInfo> findListByQuery(UserInfoQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByQuery(UserInfoQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVo<UserInfo> findListByPage(UserInfoQuery query);

	/**
	 * 新增
	 */
	Integer add(UserInfo bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<UserInfo> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<UserInfo> listBean);

	/**
	 * 根据UserId查询
	 */
	UserInfo getByUserId(String userId);

	/**
	 * 根据UserId更新
	 */
	Integer updateByUserId(UserInfo bean, String userId);

	/**
	 * 根据UserId删除
	 */
	Integer deleteByUserId(String userId);

	/**
	 * 根据Email查询
	 */
	UserInfo getByEmail(String email);

	/**
	 * 根据Email更新
	 */
	Integer updateByEmail(UserInfo bean, String email);

	/**
	 * 根据Email删除
	 */
	Integer deleteByEmail(String email);

	/**
	 * 根据NickName查询
	 */
	UserInfo getByNickName(String nickName);

	/**
	 * 根据NickName更新
	 */
	Integer updateByNickName(UserInfo bean, String nickName);

	/**
	 * 根据NickName删除
	 */
	Integer deleteByNickName(String nickName);

	void register(String email, String emailCode, String nickName, String password);

	void updateUserIntegral(String userId, UserIntegralOperTypeEnum operTypeEnum, Integer changeType, Integer integral);

	SessionWebUserDto login(String email, String password, String ip);

	void resetPwd(String email, String emailCode, String password);

	void updateUserInfo(UserInfo userInfo, MultipartFile avatar);

	void updateUserStatus(String userId, Integer status);

	void sendUserMessage(String userId, String message, Integer integral);
}