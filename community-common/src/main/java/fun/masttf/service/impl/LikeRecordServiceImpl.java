package fun.masttf.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.LikeRecord;
import fun.masttf.entity.query.LikeRecordQuery;
import fun.masttf.service.LikeRecordService;
import fun.masttf.mapper.LikeRecordMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.enums.PageSize;

/**
 * @Description:点赞记录Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("likeRecordService")
public class LikeRecordServiceImpl implements LikeRecordService {

	@Autowired
	private LikeRecordMapper<LikeRecord, LikeRecordQuery> likeRecordMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<LikeRecord> findListByQuery(LikeRecordQuery query) {
		return likeRecordMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(LikeRecordQuery query) {
		return likeRecordMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<LikeRecord> findListByPage(LikeRecordQuery query) {
		Integer count = likeRecordMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<LikeRecord> list = likeRecordMapper.selectList(query);
		PaginationResultVo<LikeRecord> result = new PaginationResultVo<LikeRecord>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(LikeRecord bean) {
		return likeRecordMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<LikeRecord> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return likeRecordMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<LikeRecord> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return likeRecordMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据OpId查询
	 */
	@Override
	public LikeRecord getByOpId(Integer opId) {
		return likeRecordMapper.selectByOpId(opId);
	}

	/**
	 * 根据OpId更新
	 */
	@Override
	public Integer updateByOpId(LikeRecord bean, Integer opId) {
		return likeRecordMapper.updateByOpId(bean, opId);
	}

	/**
	 * 根据OpId删除
	 */
	@Override
	public Integer deleteByOpId(Integer opId) {
		return likeRecordMapper.deleteByOpId(opId);
	}

	/**
	 * 根据ObjectIdAndUserIdAndOpType查询
	 */
	@Override
	public LikeRecord getByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType) {
		return likeRecordMapper.selectByObjectIdAndUserIdAndOpType(objectId, userId, opType);
	}

	/**
	 * 根据ObjectIdAndUserIdAndOpType更新
	 */
	@Override
	public Integer updateByObjectIdAndUserIdAndOpType(LikeRecord bean, String objectId, String userId, Integer opType) {
		return likeRecordMapper.updateByObjectIdAndUserIdAndOpType(bean, objectId, userId, opType);
	}

	/**
	 * 根据ObjectIdAndUserIdAndOpType删除
	 */
	@Override
	public Integer deleteByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType) {
		return likeRecordMapper.deleteByObjectIdAndUserIdAndOpType(objectId, userId, opType);
	}

}