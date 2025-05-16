package fun.masttf.service;

import java.util.List;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.enums.RecordOpTypeEnum;
import fun.masttf.entity.po.LikeRecord;
import fun.masttf.entity.query.LikeRecordQuery;

/**
 * @Description:点赞记录Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface LikeRecordService {

	/**
	 * 根据条件查询列表
	 */
	List<LikeRecord> findListByQuery(LikeRecordQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByQuery(LikeRecordQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVo<LikeRecord> findListByPage(LikeRecordQuery query);

	/**
	 * 新增
	 */
	Integer add(LikeRecord bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<LikeRecord> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<LikeRecord> listBean);

	/**
	 * 根据OpId查询
	 */
	LikeRecord getByOpId(Integer opId);

	/**
	 * 根据OpId更新
	 */
	Integer updateByOpId(LikeRecord bean, Integer opId);

	/**
	 * 根据OpId删除
	 */
	Integer deleteByOpId(Integer opId);

	/**
	 * 根据ObjectIdAndUserIdAndOpType查询
	 */
	LikeRecord getByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType);

	/**
	 * 根据ObjectIdAndUserIdAndOpType更新
	 */
	Integer updateByObjectIdAndUserIdAndOpType(LikeRecord bean, String objectId, String userId, Integer opType);

	/**
	 * 根据ObjectIdAndUserIdAndOpType删除
	 */
	Integer deleteByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType);

	void doLike(String objectId, String userId, String nickName, RecordOpTypeEnum opTypeEnum);
}