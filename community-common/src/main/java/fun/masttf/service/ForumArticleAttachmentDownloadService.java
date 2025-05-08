package fun.masttf.service;

import java.util.List;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.ForumArticleAttachmentDownload;
import fun.masttf.entity.query.ForumArticleAttachmentDownloadQuery;

/**
 * @Description:用户附件下载Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface ForumArticleAttachmentDownloadService {

	/**
	 * 根据条件查询列表
	 */
	List<ForumArticleAttachmentDownload> findListByQuery(ForumArticleAttachmentDownloadQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByQuery(ForumArticleAttachmentDownloadQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVo<ForumArticleAttachmentDownload> findListByPage(ForumArticleAttachmentDownloadQuery query);

	/**
	 * 新增
	 */
	Integer add(ForumArticleAttachmentDownload bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<ForumArticleAttachmentDownload> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<ForumArticleAttachmentDownload> listBean);

	/**
	 * 根据FileIdAndUserId查询
	 */
	ForumArticleAttachmentDownload getByFileIdAndUserId(String fileId, String userId);

	/**
	 * 根据FileIdAndUserId更新
	 */
	Integer updateByFileIdAndUserId(ForumArticleAttachmentDownload bean, String fileId, String userId);

	/**
	 * 根据FileIdAndUserId删除
	 */
	Integer deleteByFileIdAndUserId(String fileId, String userId);

}