package fun.masttf.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.ForumComment;
import fun.masttf.entity.query.ForumCommentQuery;

/**
 * @Description:评论Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface ForumCommentService {

	/**
	 * 根据条件查询列表
	 */
	List<ForumComment> findListByQuery(ForumCommentQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByQuery(ForumCommentQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVo<ForumComment> findListByPage(ForumCommentQuery query);

	/**
	 * 新增
	 */
	Integer add(ForumComment bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<ForumComment> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<ForumComment> listBean);

	/**
	 * 根据CommentId查询
	 */
	ForumComment getByCommentId(Integer commentId);

	/**
	 * 根据CommentId更新
	 */
	Integer updateByCommentId(ForumComment bean, Integer commentId);

	/**
	 * 根据CommentId删除
	 */
	Integer deleteByCommentId(Integer commentId);

	void changeTopType(String userId,Integer commentId, Integer topType);

	void postComment(ForumComment comment, MultipartFile image);

	void deleteComment(String commentIds);

	void deleteCommentSingle(Integer commentId);

	void auditComment(String commentIds);

	void auditCommentSingle(Integer commentId);
}