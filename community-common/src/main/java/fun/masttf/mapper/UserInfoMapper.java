package fun.masttf.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Description:用户信息Mapper
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface UserInfoMapper<T, P> extends BaseMapper<T, P> {

	/**
	 * 根据UserId查询
	 */
	T selectByUserId(@Param("userId") String userId);

	/**
	 * 根据UserId更新
	 */
	Integer updateByUserId(@Param("bean") T t, @Param("userId") String userId);

	/**
	 * 根据UserId删除
	 */
	Integer deleteByUserId(@Param("userId") String userId);

	/**
	 * 根据Email查询
	 */
	T selectByEmail(@Param("email") String email);

	/**
	 * 根据Email更新
	 */
	Integer updateByEmail(@Param("bean") T t, @Param("email") String email);

	/**
	 * 根据Email删除
	 */
	Integer deleteByEmail(@Param("email") String email);

	/**
	 * 根据NickName查询
	 */
	T selectByNickName(@Param("nickName") String nickName);

	/**
	 * 根据NickName更新
	 */
	Integer updateByNickName(@Param("bean") T t, @Param("nickName") String nickName);

	/**
	 * 根据NickName删除
	 */
	Integer deleteByNickName(@Param("nickName") String nickName);


}