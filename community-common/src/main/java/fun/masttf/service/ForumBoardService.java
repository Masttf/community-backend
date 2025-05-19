package fun.masttf.service;

import java.util.List;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.ForumBoard;
import fun.masttf.entity.query.ForumBoardQuery;

/**
 * @Description:文章板块信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface ForumBoardService {

	/**
	 * 根据条件查询列表
	 */
	List<ForumBoard> findListByQuery(ForumBoardQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByQuery(ForumBoardQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVo<ForumBoard> findListByPage(ForumBoardQuery query);

	/**
	 * 新增
	 */
	Integer add(ForumBoard bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<ForumBoard> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<ForumBoard> listBean);

	/**
	 * 根据BoardId查询
	 */
	ForumBoard getByBoardId(Integer boardId);

	/**
	 * 根据BoardId更新
	 */
	Integer updateByBoardId(ForumBoard bean, Integer boardId);

	/**
	 * 根据BoardId删除
	 */
	Integer deleteByBoardId(Integer boardId);

	List<ForumBoard> getBoardTree(Integer type);

	void saveBoard(ForumBoard board);
}