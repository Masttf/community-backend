package fun.masttf.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.ForumArticleAttachment;
import fun.masttf.entity.query.ForumArticleAttachmentQuery;
import fun.masttf.service.ForumArticleAttachmentService;
import fun.masttf.mapper.ForumArticleAttachmentMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.enums.PageSize;

/**
 * @Description:文件信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("forumArticleAttachmentService")
public class ForumArticleAttachmentServiceImpl implements ForumArticleAttachmentService {

	@Autowired
	private ForumArticleAttachmentMapper<ForumArticleAttachment, ForumArticleAttachmentQuery> forumArticleAttachmentMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<ForumArticleAttachment> findListByQuery(ForumArticleAttachmentQuery query) {
		return forumArticleAttachmentMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(ForumArticleAttachmentQuery query) {
		return forumArticleAttachmentMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<ForumArticleAttachment> findListByPage(ForumArticleAttachmentQuery query) {
		Integer count = forumArticleAttachmentMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<ForumArticleAttachment> list = forumArticleAttachmentMapper.selectList(query);
		PaginationResultVo<ForumArticleAttachment> result = new PaginationResultVo<ForumArticleAttachment>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(ForumArticleAttachment bean) {
		return forumArticleAttachmentMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<ForumArticleAttachment> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return forumArticleAttachmentMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<ForumArticleAttachment> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return forumArticleAttachmentMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据FileId查询
	 */
	@Override
	public ForumArticleAttachment getByFileId(String fileId) {
		return forumArticleAttachmentMapper.selectByFileId(fileId);
	}

	/**
	 * 根据FileId更新
	 */
	@Override
	public Integer updateByFileId(ForumArticleAttachment bean, String fileId) {
		return forumArticleAttachmentMapper.updateByFileId(bean, fileId);
	}

	/**
	 * 根据FileId删除
	 */
	@Override
	public Integer deleteByFileId(String fileId) {
		return forumArticleAttachmentMapper.deleteByFileId(fileId);
	}

}