package fun.masttf.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.po.ForumArticleAttachment;
import fun.masttf.entity.query.ForumArticleQuery;

/**
 * @Description:文章信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface ForumArticleService {

	/**
	 * 根据条件查询列表
	 */
	List<ForumArticle> findListByQuery(ForumArticleQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByQuery(ForumArticleQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVo<ForumArticle> findListByPage(ForumArticleQuery query);

	/**
	 * 新增
	 */
	Integer add(ForumArticle bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<ForumArticle> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<ForumArticle> listBean);

	/**
	 * 根据ArticleId查询
	 */
	ForumArticle getByArticleId(String articleId);

	/**
	 * 根据ArticleId更新
	 */
	Integer updateByArticleId(ForumArticle bean, String articleId);

	/**
	 * 根据ArticleId删除
	 */
	Integer deleteByArticleId(String articleId);
	
	ForumArticle readArticle(String articleId);

	void postArticle(Boolean isAdmin,ForumArticle article, ForumArticleAttachment articleAttachment, MultipartFile cover, MultipartFile attachment);

	void updateArticle(Boolean isAdmin,ForumArticle article, ForumArticleAttachment articleAttachment, MultipartFile cover, MultipartFile attachment);
}