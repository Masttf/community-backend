package fun.masttf.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Description:点赞记录Mapper
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface LikeRecordMapper<T, P> extends BaseMapper<T, P> {

	/**
	 * 根据OpId查询
	 */
	T selectByOpId(@Param("opId") Integer opId);

	/**
	 * 根据OpId更新
	 */
	Integer updateByOpId(@Param("bean") T t, @Param("opId") Integer opId);

	/**
	 * 根据OpId删除
	 */
	Integer deleteByOpId(@Param("opId") Integer opId);

	/**
	 * 根据ObjectIdAndUserIdAndOpType查询
	 */
	T selectByObjectIdAndUserIdAndOpType(@Param("objectId") String objectId, @Param("userId") String userId, @Param("opType") Integer opType);

	/**
	 * 根据ObjectIdAndUserIdAndOpType更新
	 */
	Integer updateByObjectIdAndUserIdAndOpType(@Param("bean") T t, @Param("objectId") String objectId, @Param("userId") String userId, @Param("opType") Integer opType);

	/**
	 * 根据ObjectIdAndUserIdAndOpType删除
	 */
	Integer deleteByObjectIdAndUserIdAndOpType(@Param("objectId") String objectId, @Param("userId") String userId, @Param("opType") Integer opType);


}