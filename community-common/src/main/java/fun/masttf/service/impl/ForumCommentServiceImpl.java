package fun.masttf.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.stream.events.Comment;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.po.ForumComment;
import fun.masttf.entity.query.ForumCommentQuery;
import fun.masttf.service.ForumCommentService;
import fun.masttf.mapper.ForumArticleMapper;
import fun.masttf.mapper.ForumCommentMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.enums.CommentOrderTypeEnum;
import fun.masttf.entity.enums.CommentTopTypeEnum;
import fun.masttf.entity.enums.PageSize;
import fun.masttf.entity.enums.ResponseCodeEnum;

/**
 * @Description:评论Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("forumCommentService")
public class ForumCommentServiceImpl implements ForumCommentService {

	@Autowired
	private ForumCommentMapper<ForumComment, ForumCommentQuery> forumCommentMapper;
	@Autowired
	private ForumArticleMapper<ForumArticle, ForumCommentQuery> forumArticleMapper;
	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<ForumComment> findListByQuery(ForumCommentQuery query) {
		List<ForumComment> list = forumCommentMapper.selectList(query);
		if(query.getLoadChildren() != null && query.getLoadChildren()) {
			ForumCommentQuery subQuery = new ForumCommentQuery();
			subQuery.setArticleId(query.getArticleId());
			subQuery.setCurrentUserId(query.getCurrentUserId());
			subQuery.setOrderBy(CommentOrderTypeEnum.SEND.getOderSql());
			subQuery.setQueryLikeType(query.getQueryLikeType());
			subQuery.setStatus(query.getStatus());
			List<Integer> pCommentIdList = list.stream().map(ForumComment::getCommentId).distinct().collect(Collectors.toList());
			subQuery.setpCommentIdList(pCommentIdList);
			List<ForumComment> subList = forumCommentMapper.selectList(subQuery);
			Map<Integer, List<ForumComment>> subMap = subList.stream().collect(Collectors.groupingBy(ForumComment::getpCommentId));
			list.forEach(comment -> {
				comment.setReplyList(subMap.get(comment.getCommentId()));
			});
		}
		return list;
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(ForumCommentQuery query) {
		return forumCommentMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<ForumComment> findListByPage(ForumCommentQuery query) {
		Integer count = forumCommentMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<ForumComment> list = findListByQuery(query);
		PaginationResultVo<ForumComment> result = new PaginationResultVo<ForumComment>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(ForumComment bean) {
		return forumCommentMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<ForumComment> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return forumCommentMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<ForumComment> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return forumCommentMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据CommentId查询
	 */
	@Override
	public ForumComment getByCommentId(Integer commentId) {
		return forumCommentMapper.selectByCommentId(commentId);
	}

	/**
	 * 根据CommentId更新
	 */
	@Override
	public Integer updateByCommentId(ForumComment bean, Integer commentId) {
		return forumCommentMapper.updateByCommentId(bean, commentId);
	}

	/**
	 * 根据CommentId删除
	 */
	@Override
	public Integer deleteByCommentId(Integer commentId) {
		return forumCommentMapper.deleteByCommentId(commentId);
	}

	@Override
	public void changeTopType(String userId,Integer commentId, Integer topType){
		CommentTopTypeEnum topTypeEnum = CommentTopTypeEnum.getByType(topType);
		if(topTypeEnum == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		ForumComment comment = getByCommentId(commentId);
		if(comment == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}

		ForumArticle article = forumArticleMapper.selectByArticleId(comment.getArticleId());
		if(article == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		// 只有文章作者可以置顶评论,且只能置顶一级评论
		if(!article.getUserId().equals(userId) || comment.getpCommentId() != 0) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		if(topTypeEnum.getType().equals(comment.getTopType())) {
			return ;
		}
		if(topTypeEnum.equals(CommentTopTypeEnum.TOP)){
			forumCommentMapper.updateTopTypeByArticleId(article.getArticleId());
		}
		ForumComment updateComment = new ForumComment();
		updateComment.setTopType(topTypeEnum.getType());
		forumCommentMapper.updateByCommentId(updateComment, commentId);
		return ;
	}
}