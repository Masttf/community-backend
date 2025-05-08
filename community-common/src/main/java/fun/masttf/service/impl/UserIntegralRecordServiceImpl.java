package fun.masttf.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.UserIntegralRecord;
import fun.masttf.entity.query.UserIntegralRecordQuery;
import fun.masttf.service.UserIntegralRecordService;
import fun.masttf.mapper.UserIntegralRecordMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.enums.PageSize;

/**
 * @Description:用户积分记录表Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("userIntegralRecordService")
public class UserIntegralRecordServiceImpl implements UserIntegralRecordService {

	@Autowired
	private UserIntegralRecordMapper<UserIntegralRecord, UserIntegralRecordQuery> userIntegralRecordMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<UserIntegralRecord> findListByQuery(UserIntegralRecordQuery query) {
		return userIntegralRecordMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(UserIntegralRecordQuery query) {
		return userIntegralRecordMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<UserIntegralRecord> findListByPage(UserIntegralRecordQuery query) {
		Integer count = userIntegralRecordMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<UserIntegralRecord> list = userIntegralRecordMapper.selectList(query);
		PaginationResultVo<UserIntegralRecord> result = new PaginationResultVo<UserIntegralRecord>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(UserIntegralRecord bean) {
		return userIntegralRecordMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<UserIntegralRecord> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return userIntegralRecordMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<UserIntegralRecord> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return userIntegralRecordMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据RecordId查询
	 */
	@Override
	public UserIntegralRecord getByRecordId(Integer recordId) {
		return userIntegralRecordMapper.selectByRecordId(recordId);
	}

	/**
	 * 根据RecordId更新
	 */
	@Override
	public Integer updateByRecordId(UserIntegralRecord bean, Integer recordId) {
		return userIntegralRecordMapper.updateByRecordId(bean, recordId);
	}

	/**
	 * 根据RecordId删除
	 */
	@Override
	public Integer deleteByRecordId(Integer recordId) {
		return userIntegralRecordMapper.deleteByRecordId(recordId);
	}

}