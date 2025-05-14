package fun.masttf.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.po.ForumComment;
import fun.masttf.entity.po.UserInfo;
import fun.masttf.entity.po.UserMessage;
import fun.masttf.entity.query.ForumCommentQuery;
import fun.masttf.service.ForumCommentService;
import fun.masttf.service.UserInfoService;
import fun.masttf.utils.StringTools;
import fun.masttf.utils.SysCacheUtils;
import fun.masttf.mapper.ForumArticleMapper;
import fun.masttf.mapper.ForumCommentMapper;
import fun.masttf.mapper.UserMessageMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.query.UserMessageQuery;
import fun.masttf.entity.enums.ArticleOrCommentStatusEnum;
import fun.masttf.entity.enums.CommentOrderTypeEnum;
import fun.masttf.entity.enums.CommentTopTypeEnum;
import fun.masttf.entity.enums.MessageStatusEnum;
import fun.masttf.entity.enums.MessageTypeEnum;
import fun.masttf.entity.enums.PageSize;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.enums.UpdateArticleCountTypeEnum;
import fun.masttf.entity.enums.UserIntegralChangeTypeEnum;
import fun.masttf.entity.enums.UserIntegralOperTypeEnum;

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
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserMessageMapper<UserMessage,UserMessageQuery> userMessageMapper;
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
				comment.setChildren(subMap.get(comment.getCommentId()));
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
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void postComment(ForumComment comment, MultipartFile image){
		ForumArticle article = forumArticleMapper.selectByArticleId(comment.getArticleId());
		if(article == null || !article.getStatus().equals(ArticleOrCommentStatusEnum.AUDIT.getStatus())) {
			throw new BusinessException("回复的文章不存在");
		}
		ForumComment parent = null;
		if(comment.getpCommentId() != 0){
			parent = forumCommentMapper.selectByCommentId(comment.getpCommentId());
			if(parent == null) {
				throw new BusinessException("回复的评论不存在");
			}
		}
		if(!StringTools.isEmpty(comment.getReplyUserId())){
			UserInfo user = userInfoService.getByUserId(comment.getReplyUserId());
			if(user == null) {
				throw new BusinessException("回复的用户不存在");
			}
			comment.setReplyNickName(user.getNickName());
		}
		if(image != null) {

		}
		Boolean needAudit = SysCacheUtils.getSysSetting().getAuditSetting().getCommentAudit();
		if(needAudit) {
			comment.setStatus(ArticleOrCommentStatusEnum.NO_AUDIT.getStatus());
		} else {
			comment.setStatus(ArticleOrCommentStatusEnum.AUDIT.getStatus());
		}
		forumCommentMapper.insert(comment);
		if(needAudit) {
			return ;
		}
		updateCommentInfo(comment, article, parent);
	}
	public void updateCommentInfo(ForumComment comment, ForumArticle article, ForumComment pComment) {
		Integer commentIntegral = SysCacheUtils.getSysSetting().getCommentSetting().getCommentIntegral();
		if(commentIntegral != null && commentIntegral > 0) {
			userInfoService.updateUserIntegral(comment.getUserId(), UserIntegralOperTypeEnum.POST_COMMENT, UserIntegralChangeTypeEnum.ADD.getChangeType(), commentIntegral);
		}
		//更新评论数
		forumArticleMapper.updateArticleCount(UpdateArticleCountTypeEnum.COMMENT_COUNT.getType(), 1, article.getArticleId());
		UserMessage userMessage = new UserMessage();
		userMessage.setArticleId(article.getArticleId());
		userMessage.setArticleTitle(article.getTitle());
		userMessage.setCreateTime(new Date());
		userMessage.setMessageType(MessageTypeEnum.COMMENT.getType());
		userMessage.setSendNickName(comment.getNickName());
		userMessage.setSendUserId(comment.getUserId());
		userMessage.setStatus(MessageStatusEnum.NO_READ.getStatus());
		//自增id插入返回了
		userMessage.setCommentId(comment.getCommentId());
		if(comment.getpCommentId() == 0) {
			userMessage.setReceivedUserId(article.getUserId());
			userMessage.setMessageContent(comment.getNickName() + "评论了你的文章");
		} else if(comment.getpCommentId() != 0 && StringTools.isEmpty(comment.getReplyUserId())) {
			userMessage.setReceivedUserId(pComment.getUserId());
			userMessage.setMessageContent(comment.getNickName() + "回复了你的评论");
		} else if(comment.getpCommentId() != 0 && !StringTools.isEmpty(comment.getReplyUserId())) {
			userMessage.setReceivedUserId(comment.getReplyUserId());
			userMessage.setMessageContent(comment.getNickName() + "回复了你");
		} else {
			throw new BusinessException("消息发送失败");
		}
		if(!comment.getUserId().equals(userMessage.getReceivedUserId())) {
			userMessageMapper.insert(userMessage);
		}
	}
}