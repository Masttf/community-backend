package fun.masttf.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.po.ForumArticleAttachment;
import fun.masttf.entity.po.ForumBoard;
import fun.masttf.entity.query.ForumArticleAttachmentQuery;
import fun.masttf.entity.query.ForumArticleQuery;
import fun.masttf.entity.query.ForumBoardQuery;
import fun.masttf.service.ForumArticleService;
import fun.masttf.service.UserInfoService;
import fun.masttf.utils.FileUtils;
import fun.masttf.utils.ImageUtils;
import fun.masttf.utils.StringTools;
import fun.masttf.utils.SysCacheUtils;
import fun.masttf.mapper.ForumArticleAttachmentMapper;
import fun.masttf.mapper.ForumArticleMapper;
import fun.masttf.mapper.ForumBoardMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.FileUploadDto;
import fun.masttf.entity.dto.SysSetting4AuditDto;
import fun.masttf.entity.enums.ArticleStatusEnum;
import fun.masttf.entity.enums.AttachmentFileTypeEnum;
import fun.masttf.entity.enums.BoardPostTypeEnum;
import fun.masttf.entity.enums.FileUploadEnum;
import fun.masttf.entity.enums.PageSize;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.enums.UserIntegralChangeTypeEnum;
import fun.masttf.entity.enums.UserIntegralOperTypeEnum;
import fun.masttf.entity.enums.ArticleAttachmentTypeEnum;
import fun.masttf.entity.enums.ArticleCountTypeEnum;

/**
 * @Description:文章信息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("forumArticleService")
public class ForumArticleServiceImpl implements ForumArticleService {

	@Autowired
	private ForumArticleMapper<ForumArticle, ForumArticleQuery> forumArticleMapper;
	@Autowired
	private ForumBoardMapper<ForumBoard, ForumBoardQuery> forumBoardMapper;
	@Autowired
	private ForumArticleAttachmentMapper<ForumArticleAttachment, ForumArticleAttachmentQuery> forumArticleAttachmentMapper;
	@Autowired
	private FileUtils fileUtils;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ImageUtils imageUtils;
	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<ForumArticle> findListByQuery(ForumArticleQuery query) {
		return forumArticleMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(ForumArticleQuery query) {
		return forumArticleMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<ForumArticle> findListByPage(ForumArticleQuery query) {
		Integer count = forumArticleMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<ForumArticle> list = forumArticleMapper.selectList(query);
		PaginationResultVo<ForumArticle> result = new PaginationResultVo<ForumArticle>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(ForumArticle bean) {
		return forumArticleMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<ForumArticle> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return forumArticleMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<ForumArticle> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return forumArticleMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据ArticleId查询
	 */
	@Override
	public ForumArticle getByArticleId(String articleId) {
		return forumArticleMapper.selectByArticleId(articleId);
	}

	/**
	 * 根据ArticleId更新
	 */
	@Override
	public Integer updateByArticleId(ForumArticle bean, String articleId) {
		return forumArticleMapper.updateByArticleId(bean, articleId);
	}

	/**
	 * 根据ArticleId删除
	 */
	@Override
	public Integer deleteByArticleId(String articleId) {
		return forumArticleMapper.deleteByArticleId(articleId);
	}

	@Override
	public ForumArticle readArticle(String articleId) {
		ForumArticle article = forumArticleMapper.selectByArticleId(articleId);
		if (article == null) {
			throw new BusinessException(ResponseCodeEnum.CODE_404);
		}
		if(ArticleStatusEnum.AUDIT.getStatus().equals(article.getStatus())) {
			forumArticleMapper.updateArticleCount(ArticleCountTypeEnum.READ_COUNT.getType(), Constans.ONE, articleId);
			article.setReadCount(article.getReadCount() + Constans.ONE);
		}
		return article;
	}

	@Override
	public void postArticle(Boolean isAdmin,ForumArticle article, ForumArticleAttachment articleAttachment, MultipartFile cover, MultipartFile attachment){
		resetBoardInfo(isAdmin, article);
		Date curDate = new Date();
		String articleId = StringTools.getRandomString(Constans.LENGTH_10);
		article.setArticleId(articleId);
		article.setPostTime(curDate);
		article.setLastUpdateTime(curDate);
		if(cover != null) {
			FileUploadDto coverDto = fileUtils.uploadFile2Local(cover, FileUploadEnum.ARTICLE_COVER);
			article.setCover(coverDto.getLocalPath());
		}
		if(attachment != null) {
			updateAttachment(article, articleAttachment, attachment, false);
			article.setAttachmentType(ArticleAttachmentTypeEnum.HAVE_ATTACHMENT.getType());
		}else{
			article.setAttachmentType(ArticleAttachmentTypeEnum.NO_ATTACHMENT.getType());
		}
		//文章审核
		if(isAdmin) {
			article.setStatus(ArticleStatusEnum.AUDIT.getStatus());
		} else {
			SysSetting4AuditDto auditSetting = SysCacheUtils.getSysSetting().getAuditSetting();
			article.setStatus(auditSetting.getPostAudit() ? ArticleStatusEnum.NO_AUDIT.getStatus() : ArticleStatusEnum.AUDIT.getStatus());
		}
		
		//增加积分
		Integer postIntegral = SysCacheUtils.getSysSetting().getPostSetting().getPostIntegral();
		if(postIntegral > 0 && article.getStatus().equals(ArticleStatusEnum.AUDIT.getStatus())) {
			userInfoService.updateUserIntegral(article.getUserId(), UserIntegralOperTypeEnum.POST_ARTICLE, UserIntegralChangeTypeEnum.ADD.getChangeType(), postIntegral);
		}
		//待测试
		//替换图片
		String content = article.getContent();
		if(!StringTools.isEmpty(content)) {
			String month = imageUtils.resetImageHtml(content);
			String replaceMonth = month + "/";
			content = content.replaceAll(FileUploadEnum.TEMP.getFolder(), replaceMonth);
			article.setContent(content);
			String markdownContent = article.getMarkdownContent();
			if(!StringTools.isEmpty(markdownContent)) {
				markdownContent = markdownContent.replaceAll(FileUploadEnum.TEMP.getFolder(), replaceMonth);
				article.setMarkdownContent(markdownContent);
			}
		}
		forumArticleMapper.insert(article);
	}

	@Override
	public void updateArticle(Boolean isAdmin,ForumArticle article, ForumArticleAttachment articleAttachment, MultipartFile cover, MultipartFile attachment) {
		ForumArticle oldArticle = forumArticleMapper.selectByArticleId(article.getArticleId());
		if (oldArticle == null || !oldArticle.getUserId().equals(article.getUserId()) ) {
			throw new BusinessException(ResponseCodeEnum.CODE_600);
		}
		resetBoardInfo(isAdmin, article);
		Date curDate = new Date();
		article.setLastUpdateTime(curDate);
		if(cover != null) {
			//删除旧封面
			fileUtils.deleteFile(oldArticle.getCover());
			//上传新封面
			FileUploadDto coverDto = fileUtils.uploadFile2Local(cover, FileUploadEnum.ARTICLE_COVER);
			article.setCover(coverDto.getLocalPath());
		}
		/*
		 * 1. 先没有附件，然后传入附件
		 * 2. 先有附件，然后修改附件
		 * 3. 先有附件，然后删除附件
		 */
		if(attachment != null) {
			updateAttachment(article, articleAttachment, attachment, true);
			article.setAttachmentType(ArticleAttachmentTypeEnum.HAVE_ATTACHMENT.getType());
		}
		ForumArticleAttachment dbAttachment = null;
		ForumArticleAttachmentQuery attachmentQuery = new ForumArticleAttachmentQuery();
		attachmentQuery.setArticleId(article.getArticleId());
		List<ForumArticleAttachment> attachmentList = forumArticleAttachmentMapper.selectList(attachmentQuery);
		if(!attachmentList.isEmpty()) {
			dbAttachment = attachmentList.get(0);
		}
		//先有附件，然后删除附件
		if(dbAttachment != null && article.getAttachmentType().equals(ArticleAttachmentTypeEnum.NO_ATTACHMENT.getType())) {
			article.setAttachmentType(ArticleAttachmentTypeEnum.NO_ATTACHMENT.getType());
			//删除旧附件
			fileUtils.deleteFile(dbAttachment.getFilePath());
			//删除数据库记录
			forumArticleAttachmentMapper.deleteByFileId(dbAttachment.getFileId());
		}
		//文章审核
		if(isAdmin) {
			article.setStatus(ArticleStatusEnum.AUDIT.getStatus());
		} else {
			SysSetting4AuditDto auditSetting = SysCacheUtils.getSysSetting().getAuditSetting();
			article.setStatus(auditSetting.getPostAudit() ? ArticleStatusEnum.NO_AUDIT.getStatus() : ArticleStatusEnum.AUDIT.getStatus());
		}

		//待测试
		//替换图片
		String content = article.getContent();
		if(!StringTools.isEmpty(content)) {
			String month = imageUtils.resetImageHtml(content);
			String replaceMonth = month + "/";
			content = content.replaceAll(FileUploadEnum.TEMP.getFolder(), replaceMonth);
			article.setContent(content);
			String markdownContent = article.getMarkdownContent();
			if(!StringTools.isEmpty(markdownContent)) {
				markdownContent = markdownContent.replaceAll(FileUploadEnum.TEMP.getFolder(), replaceMonth);
				article.setMarkdownContent(markdownContent);
			}
		}
		
		forumArticleMapper.updateByArticleId(article, article.getArticleId());
		
	}

	private void resetBoardInfo(Boolean isAdmin, ForumArticle article) {
		ForumBoard pBoard = forumBoardMapper.selectByBoardId(article.getpBoardId());
		if (pBoard == null || (pBoard.getPostType() == BoardPostTypeEnum.ADMIN_POST.getType() && !isAdmin)) {
			throw new BusinessException("一级版块不存在或没有权限发帖");
		}
		article.setpBoardName(pBoard.getBoardName());
		if(article.getBoardId() != null && article.getBoardId() != 0) {
			ForumBoard board = forumBoardMapper.selectByBoardId(article.getBoardId());
			if (board == null || (board.getPostType() == BoardPostTypeEnum.ADMIN_POST.getType() && !isAdmin)) {
				throw new BusinessException("二级版块不存在或没有权限发帖");
			}
			article.setBoardName(board.getBoardName());
		} else {
			article.setBoardId(0);
			article.setBoardName("");
		}
	}

	private void updateAttachment(ForumArticle article,ForumArticleAttachment articleAttachment, MultipartFile attachment, Boolean isupdate) {
		Integer allowSizeMb = SysCacheUtils.getSysSetting().getPostSetting().getAttachmentSize();
		Long allowSize = Long.valueOf(allowSizeMb) * Constans.FILE_SIZE_1M;
		if (attachment.getSize() > allowSize) {
			throw new BusinessException("附件大小超过限制");
		}
		ForumArticleAttachment oldAttachment = null;
		if(isupdate) {
			ForumArticleAttachmentQuery attachmentQuery = new ForumArticleAttachmentQuery();
			attachmentQuery.setArticleId(article.getArticleId());
			List<ForumArticleAttachment> list = forumArticleAttachmentMapper.selectList(attachmentQuery);
			if(!list.isEmpty()) {
				oldAttachment = list.get(0);
				//删除旧附件
				fileUtils.deleteFile(oldAttachment.getFilePath());
			}
		}
		//fileUtils.uploadFile2Local这里已经处理了文件格式
		FileUploadDto attachmentDto = fileUtils.uploadFile2Local(attachment, FileUploadEnum.ARTICLE_ATTACHMENT);
		if(oldAttachment == null) {
			String originalFileName = attachment.getOriginalFilename();
			String fileSuffix = StringTools.getFileSuffix(originalFileName);
			articleAttachment.setFileId(StringTools.getRandomString(Constans.LENGTH_10));
			articleAttachment.setDownloadCount(0);
			articleAttachment.setArticleId(article.getArticleId());
			articleAttachment.setFileName(originalFileName);
			articleAttachment.setFileSize(attachment.getSize());
			articleAttachment.setFilePath(attachmentDto.getLocalPath());
			articleAttachment.setFileType(AttachmentFileTypeEnum.getBySuffix(fileSuffix).getType());
			articleAttachment.setUserId(article.getUserId());
			forumArticleAttachmentMapper.insert(articleAttachment);
		} else{
			ForumArticleAttachment updateInfo = new ForumArticleAttachment();
			updateInfo.setFilePath(attachmentDto.getLocalPath());
			updateInfo.setFileSize(attachment.getSize());
			updateInfo.setFileName(attachment.getOriginalFilename());
			updateInfo.setFileType(AttachmentFileTypeEnum.getBySuffix(StringTools.getFileSuffix(attachment.getOriginalFilename())).getType());
			updateInfo.setIntegral(articleAttachment.getIntegral());
			forumArticleAttachmentMapper.updateByFileId(updateInfo, articleAttachment.getFileId());
		}
	}

}