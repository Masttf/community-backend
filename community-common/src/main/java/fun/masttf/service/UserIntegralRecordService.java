package fun.masttf.service;

import java.util.List;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.UserIntegralRecord;
import fun.masttf.entity.query.UserIntegralRecordQuery;

/**
 * @Description:用户积分记录表Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface UserIntegralRecordService {

	/**
	 * 根据条件查询列表
	 */
	List<UserIntegralRecord> findListByQuery(UserIntegralRecordQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByQuery(UserIntegralRecordQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVo<UserIntegralRecord> findListByPage(UserIntegralRecordQuery query);

	/**
	 * 新增
	 */
	Integer add(UserIntegralRecord bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<UserIntegralRecord> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<UserIntegralRecord> listBean);

	/**
	 * 根据RecordId查询
	 */
	UserIntegralRecord getByRecordId(Integer recordId);

	/**
	 * 根据RecordId更新
	 */
	Integer updateByRecordId(UserIntegralRecord bean, Integer recordId);

	/**
	 * 根据RecordId删除
	 */
	Integer deleteByRecordId(Integer recordId);

}