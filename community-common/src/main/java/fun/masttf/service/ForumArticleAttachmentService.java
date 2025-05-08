package fun.masttf.service;

import java.util.List;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.ForumArticleAttachment;
import fun.masttf.entity.query.ForumArticleAttachmentQuery;

/**
 * @Description:文件信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface ForumArticleAttachmentService {

	/**
	 * 根据条件查询列表
	 */
	List<ForumArticleAttachment> findListByQuery(ForumArticleAttachmentQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByQuery(ForumArticleAttachmentQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVo<ForumArticleAttachment> findListByPage(ForumArticleAttachmentQuery query);

	/**
	 * 新增
	 */
	Integer add(ForumArticleAttachment bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<ForumArticleAttachment> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<ForumArticleAttachment> listBean);

	/**
	 * 根据FileId查询
	 */
	ForumArticleAttachment getByFileId(String fileId);

	/**
	 * 根据FileId更新
	 */
	Integer updateByFileId(ForumArticleAttachment bean, String fileId);

	/**
	 * 根据FileId删除
	 */
	Integer deleteByFileId(String fileId);

}