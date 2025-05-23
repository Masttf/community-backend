package fun.masttf.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.po.ForumComment;
import fun.masttf.entity.po.LikeRecord;
import fun.masttf.entity.po.UserMessage;
import fun.masttf.entity.query.ForumArticleQuery;
import fun.masttf.entity.query.ForumCommentQuery;
import fun.masttf.entity.query.LikeRecordQuery;
import fun.masttf.service.LikeRecordService;
import fun.masttf.mapper.ForumArticleMapper;
import fun.masttf.mapper.ForumCommentMapper;
import fun.masttf.mapper.LikeRecordMapper;
import fun.masttf.mapper.UserMessageMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.query.UserMessageQuery;
import fun.masttf.entity.enums.MessageStatusEnum;
import fun.masttf.entity.enums.MessageTypeEnum;
import fun.masttf.entity.enums.RecordOpTypeEnum;
import fun.masttf.entity.enums.PageSize;
import fun.masttf.entity.enums.ArticleCountTypeEnum;

/**
 * @Description:点赞记录Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("likeRecordService")
public class LikeRecordServiceImpl implements LikeRecordService {

	@Autowired
	private LikeRecordMapper<LikeRecord, LikeRecordQuery> likeRecordMapper;
	@Autowired
	private ForumArticleMapper<ForumArticle, ForumArticleQuery> forumArticleMapper;
	@Autowired
	private UserMessageMapper<UserMessage, UserMessageQuery> userMessageMapper;
	@Autowired
	private ForumCommentMapper<ForumComment, ForumCommentQuery> forumCommentMapper;
	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<LikeRecord> findListByQuery(LikeRecordQuery query) {
		return likeRecordMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(LikeRecordQuery query) {
		return likeRecordMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<LikeRecord> findListByPage(LikeRecordQuery query) {
		Integer count = likeRecordMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<LikeRecord> list = likeRecordMapper.selectList(query);
		PaginationResultVo<LikeRecord> result = new PaginationResultVo<LikeRecord>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(LikeRecord bean) {
		return likeRecordMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<LikeRecord> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return likeRecordMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<LikeRecord> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return likeRecordMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据OpId查询
	 */
	@Override
	public LikeRecord getByOpId(Integer opId) {
		return likeRecordMapper.selectByOpId(opId);
	}

	/**
	 * 根据OpId更新
	 */
	@Override
	public Integer updateByOpId(LikeRecord bean, Integer opId) {
		return likeRecordMapper.updateByOpId(bean, opId);
	}

	/**
	 * 根据OpId删除
	 */
	@Override
	public Integer deleteByOpId(Integer opId) {
		return likeRecordMapper.deleteByOpId(opId);
	}

	/**
	 * 根据ObjectIdAndUserIdAndOpType查询
	 */
	@Override
	public LikeRecord getByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType) {
		return likeRecordMapper.selectByObjectIdAndUserIdAndOpType(objectId, userId, opType);
	}

	/**
	 * 根据ObjectIdAndUserIdAndOpType更新
	 */
	@Override
	public Integer updateByObjectIdAndUserIdAndOpType(LikeRecord bean, String objectId, String userId, Integer opType) {
		return likeRecordMapper.updateByObjectIdAndUserIdAndOpType(bean, objectId, userId, opType);
	}

	/**
	 * 根据ObjectIdAndUserIdAndOpType删除
	 */
	@Override
	public Integer deleteByObjectIdAndUserIdAndOpType(String objectId, String userId, Integer opType) {
		return likeRecordMapper.deleteByObjectIdAndUserIdAndOpType(objectId, userId, opType);
	}

	/*
	 * 点赞
	 * 发消息
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void doLike(String objectId, String userId, String nickName, RecordOpTypeEnum opTypeEnum) {
		UserMessage userMessage = new UserMessage();
		switch(opTypeEnum) {
			case ARTICLE_LIKE:
				ForumArticle article =  forumArticleMapper.selectByArticleId(objectId);
				if(article == null) {
					throw new BusinessException("文章不存在");
				}
				articleLike(article, userId);
				userMessage.setArticleId(objectId);
				userMessage.setArticleTitle(article.getTitle());
				userMessage.setMessageType(MessageTypeEnum.ARTICLE_LIKE.getType());
				userMessage.setMessageContent(nickName + "点赞了你的文章");
				userMessage.setReceivedUserId(article.getUserId());
				break;
			case COMMENT_LIKE:
				ForumComment comment = forumCommentMapper.selectByCommentId(Integer.parseInt(objectId));
				if(comment == null) {
					throw new BusinessException("评论不存在");
				}
				commentLike(objectId, comment, userId);
				ForumArticle article2 = forumArticleMapper.selectByArticleId(comment.getArticleId());
				userMessage.setArticleId(article2.getArticleId());
				userMessage.setArticleTitle(article2.getTitle());
				userMessage.setCommentId(comment.getCommentId());
				userMessage.setMessageType(MessageTypeEnum.COMMENT_LIKE.getType());
				userMessage.setMessageContent(nickName + "点赞了你的评论");
				userMessage.setReceivedUserId(comment.getUserId());
				break;
			default:
				break;
		}
		userMessage.setCreateTime(new Date());
		userMessage.setSendUserId(userId);
		userMessage.setSendNickName(nickName);
		userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
		if(!userId.equals(userMessage.getReceivedUserId()) && userMessageMapper.selectByArticleIdAndCommentIdAndSendUserIdAndMessageType(userMessage.getArticleId(), userMessage.getCommentId(), userMessage.getSendUserId(), userMessage.getMessageType()) == null) {
			userMessageMapper.insert(userMessage);
		}
	}

	public void articleLike(ForumArticle article, String userId) {
		LikeRecord likeRecord = likeRecordMapper.selectByObjectIdAndUserIdAndOpType(article.getArticleId(), userId, RecordOpTypeEnum.ARTICLE_LIKE.getType());
		if(likeRecord == null) {
			likeRecord = new LikeRecord();
			likeRecord.setObjectId(article.getArticleId());
			likeRecord.setUserId(userId);
			likeRecord.setOpType(RecordOpTypeEnum.ARTICLE_LIKE.getType());
			likeRecord.setCreateTime(new Date());
			likeRecord.setAuthorUserId(article.getUserId());
			likeRecordMapper.insert(likeRecord);
			forumArticleMapper.updateArticleCount(ArticleCountTypeEnum.GOOD_COUNT.getType(), -1, article.getArticleId());
		} else {
			likeRecordMapper.deleteByObjectIdAndUserIdAndOpType(article.getArticleId(), userId, RecordOpTypeEnum.ARTICLE_LIKE.getType());
			forumArticleMapper.updateArticleCount(ArticleCountTypeEnum.GOOD_COUNT.getType(), 1, article.getArticleId());
		}
	}

	public void commentLike(String objectId, ForumComment comment, String userId) {
		LikeRecord likeRecord = likeRecordMapper.selectByObjectIdAndUserIdAndOpType(objectId, userId, RecordOpTypeEnum.COMMENT_LIKE.getType());
		if(likeRecord == null) {
			likeRecord = new LikeRecord();
			likeRecord.setObjectId(objectId);
			likeRecord.setUserId(userId);
			likeRecord.setOpType(RecordOpTypeEnum.COMMENT_LIKE.getType());
			likeRecord.setCreateTime(new Date());
			likeRecord.setAuthorUserId(comment.getUserId());
			likeRecordMapper.insert(likeRecord);
			forumCommentMapper.updateCommentGoodCount(-1, comment.getCommentId());
		} else {
			likeRecordMapper.deleteByObjectIdAndUserIdAndOpType(objectId, userId, RecordOpTypeEnum.COMMENT_LIKE.getType());
			forumCommentMapper.updateCommentGoodCount(1, comment.getCommentId());
		}
	}
}