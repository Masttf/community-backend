package fun.masttf.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.ForumArticleAttachmentDownload;
import fun.masttf.entity.query.ForumArticleAttachmentDownloadQuery;
import fun.masttf.service.ForumArticleAttachmentDownloadService;
import fun.masttf.mapper.ForumArticleAttachmentDownloadMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.enums.PageSize;

/**
 * @Description:用户附件下载Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("forumArticleAttachmentDownloadService")
public class ForumArticleAttachmentDownloadServiceImpl implements ForumArticleAttachmentDownloadService {

	@Autowired
	private ForumArticleAttachmentDownloadMapper<ForumArticleAttachmentDownload, ForumArticleAttachmentDownloadQuery> forumArticleAttachmentDownloadMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<ForumArticleAttachmentDownload> findListByQuery(ForumArticleAttachmentDownloadQuery query) {
		return forumArticleAttachmentDownloadMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(ForumArticleAttachmentDownloadQuery query) {
		return forumArticleAttachmentDownloadMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<ForumArticleAttachmentDownload> findListByPage(ForumArticleAttachmentDownloadQuery query) {
		Integer count = forumArticleAttachmentDownloadMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<ForumArticleAttachmentDownload> list = forumArticleAttachmentDownloadMapper.selectList(query);
		PaginationResultVo<ForumArticleAttachmentDownload> result = new PaginationResultVo<ForumArticleAttachmentDownload>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(ForumArticleAttachmentDownload bean) {
		return forumArticleAttachmentDownloadMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<ForumArticleAttachmentDownload> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return forumArticleAttachmentDownloadMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<ForumArticleAttachmentDownload> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return forumArticleAttachmentDownloadMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据FileIdAndUserId查询
	 */
	@Override
	public ForumArticleAttachmentDownload getByFileIdAndUserId(String fileId, String userId) {
		return forumArticleAttachmentDownloadMapper.selectByFileIdAndUserId(fileId, userId);
	}

	/**
	 * 根据FileIdAndUserId更新
	 */
	@Override
	public Integer updateByFileIdAndUserId(ForumArticleAttachmentDownload bean, String fileId, String userId) {
		return forumArticleAttachmentDownloadMapper.updateByFileIdAndUserId(bean, fileId, userId);
	}

	/**
	 * 根据FileIdAndUserId删除
	 */
	@Override
	public Integer deleteByFileIdAndUserId(String fileId, String userId) {
		return forumArticleAttachmentDownloadMapper.deleteByFileIdAndUserId(fileId, userId);
	}

}