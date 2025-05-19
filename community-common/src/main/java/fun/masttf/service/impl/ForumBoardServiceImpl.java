package fun.masttf.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.po.ForumBoard;
import fun.masttf.entity.query.ForumArticleQuery;
import fun.masttf.entity.query.ForumBoardQuery;
import fun.masttf.service.ForumBoardService;
import fun.masttf.mapper.ForumArticleMapper;
import fun.masttf.mapper.ForumBoardMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.enums.PageSize;

/**
 * @Description:文章板块信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("forumBoardService")
public class ForumBoardServiceImpl implements ForumBoardService {

	@Autowired
	private ForumBoardMapper<ForumBoard, ForumBoardQuery> forumBoardMapper;
	@Autowired
	private ForumArticleMapper<ForumArticle, ForumArticleQuery> forumArticleMapper;
	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<ForumBoard> findListByQuery(ForumBoardQuery query) {
		return forumBoardMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(ForumBoardQuery query) {
		return forumBoardMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<ForumBoard> findListByPage(ForumBoardQuery query) {
		Integer count = forumBoardMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<ForumBoard> list = forumBoardMapper.selectList(query);
		PaginationResultVo<ForumBoard> result = new PaginationResultVo<ForumBoard>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(ForumBoard bean) {
		return forumBoardMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<ForumBoard> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return forumBoardMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<ForumBoard> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return forumBoardMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据BoardId查询
	 */
	@Override
	public ForumBoard getByBoardId(Integer boardId) {
		return forumBoardMapper.selectByBoardId(boardId);
	}

	/**
	 * 根据BoardId更新
	 */
	@Override
	public Integer updateByBoardId(ForumBoard bean, Integer boardId) {
		return forumBoardMapper.updateByBoardId(bean, boardId);
	}

	/**
	 * 根据BoardId删除
	 */
	@Override
	public Integer deleteByBoardId(Integer boardId) {
		return forumBoardMapper.deleteByBoardId(boardId);
	}

	@Override
	public List<ForumBoard> getBoardTree(Integer postType) {
		ForumBoardQuery query = new ForumBoardQuery();
		query.setOrderBy("sort asc");
		query.setPostType(postType);
		List<ForumBoard> list = forumBoardMapper.selectList(query);
		return convertLine2Tree(list, 0);
	}

	private List<ForumBoard> convertLine2Tree(List<ForumBoard> datalist, Integer pid) {
		List<ForumBoard> children = new ArrayList<>();
		for (ForumBoard m : datalist) {
			if(m.getpBoardId().equals(pid)) {
				m.setChildren(convertLine2Tree(datalist, m.getBoardId()));
				children.add(m);
			}
		}
		return children;
	}

	@Override
	public void saveBoard(ForumBoard board) {
		if(board.getBoardId() == null) {
			ForumBoardQuery query = new ForumBoardQuery();
			query.setpBoardId(board.getpBoardId());
			Integer count = forumBoardMapper.selectCount(query);
			board.setSort(count + 1);
			forumBoardMapper.insert(board);
		} else {
			ForumBoard dbInfo = forumBoardMapper.selectByBoardId(board.getBoardId());
			if(dbInfo == null){
				throw new BusinessException("板块不存在");
			}
			forumBoardMapper.updateByBoardId(board, board.getBoardId());
			if(!dbInfo.getBoardName().equals(board.getBoardName())){
				forumArticleMapper.updateBoardNameBatch(board.getpBoardId() == 0 ? 0 : 1, board.getBoardName(), board.getBoardId());
			}
		}
	}
}