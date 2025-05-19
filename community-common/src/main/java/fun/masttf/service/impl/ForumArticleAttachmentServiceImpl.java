package fun.masttf.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.po.ForumArticleAttachment;
import fun.masttf.entity.po.ForumArticleAttachmentDownload;
import fun.masttf.entity.po.UserInfo;
import fun.masttf.entity.po.UserMessage;
import fun.masttf.entity.query.ForumArticleAttachmentQuery;
import fun.masttf.entity.query.ForumArticleQuery;
import fun.masttf.service.ForumArticleAttachmentService;
import fun.masttf.service.UserInfoService;
import fun.masttf.mapper.ForumArticleAttachmentDownloadMapper;
import fun.masttf.mapper.ForumArticleAttachmentMapper;
import fun.masttf.mapper.ForumArticleMapper;
import fun.masttf.mapper.UserMessageMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.query.UserMessageQuery;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.MessageStatusEnum;
import fun.masttf.entity.enums.MessageTypeEnum;
import fun.masttf.entity.enums.PageSize;
import fun.masttf.entity.enums.UserIntegralChangeTypeEnum;
import fun.masttf.entity.enums.UserIntegralOperTypeEnum;

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
	@Autowired
	private ForumArticleAttachmentDownloadMapper<ForumArticleAttachmentDownload, ForumArticleAttachmentQuery> forumArticleAttachmentDownloadMapper;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ForumArticleMapper<ForumArticle, ForumArticleQuery> forumArticleMapper;
	@Autowired
	private UserMessageMapper<UserMessage, UserMessageQuery> userMessageMapper;
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

	/*
	 * 先查询附件是否存在
	 * 然后查询积分是否足够/或者是自己发布的
	 * 扣除积分
	 * 增加积分
	 * 记录下载记录
	 * 发送消息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ForumArticleAttachment downloadAttachment(String fileId, SessionWebUserDto userDto){
		ForumArticleAttachment attachment = forumArticleAttachmentMapper.selectByFileId(fileId);
		if(attachment == null) {
			throw new BusinessException("附件不存在");
		}
		ForumArticleAttachmentDownload attachmentDownload = null;
		if(!attachment.getUserId().equals(userDto.getUserId())) {
			attachmentDownload = forumArticleAttachmentDownloadMapper.selectByFileIdAndUserId(fileId, userDto.getUserId());
			if(attachmentDownload == null) {
				UserInfo userInfo = userInfoService.getByUserId(userDto.getUserId());
				if(userInfo.getCurrentIntegral() < attachment.getIntegral()) {
					throw new BusinessException("积分不足");
				}
			}
		}
		//添加下载记录
		ForumArticleAttachmentDownload updateDownload = new ForumArticleAttachmentDownload();
		updateDownload.setArticleId(attachment.getArticleId());
		updateDownload.setDownloadCount(1);
		updateDownload.setFileId(fileId);
		updateDownload.setUserId(userDto.getUserId());
		forumArticleAttachmentDownloadMapper.insertOrUpdate(updateDownload);
		
		//如果是自己发布的或者已经下载过了
		if(attachmentDownload != null || attachment.getUserId().equals(userDto.getUserId())) {
			return attachment;
		}
		//扣除积分
		userInfoService.updateUserIntegral(userDto.getUserId(), UserIntegralOperTypeEnum.DOWNLOAD_ATTACHMENT,UserIntegralChangeTypeEnum.REDUCE.getChangeType(), attachment.getIntegral());
		//增加积分
		userInfoService.updateUserIntegral(attachment.getUserId(), UserIntegralOperTypeEnum.DOWNLOAD_ATTACHMENT,UserIntegralChangeTypeEnum.ADD.getChangeType(), attachment.getIntegral());

		//发送消息
		ForumArticle article = forumArticleMapper.selectByArticleId(attachment.getArticleId());
		UserMessage userMessage = new UserMessage();
		userMessage.setArticleId(attachment.getArticleId());
		userMessage.setArticleTitle(article.getTitle());
		userMessage.setCreateTime(new Date());
		userMessage.setMessageType(MessageTypeEnum.DOWNLOAD_ATTACHMENT.getType());
		userMessage.setMessageContent(userDto.getNickName() + "下载了你的附件");
		userMessage.setReceivedUserId(attachment.getUserId());
		userMessage.setSendUserId(userDto.getUserId());
		userMessage.setSendNickName(userDto.getNickName());
		userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
		userMessageMapper.insert(userMessage);
		return attachment;
	}
}