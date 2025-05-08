package fun.masttf.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.po.UserMessage;
import fun.masttf.entity.query.UserMessageQuery;
import fun.masttf.service.UserMessageService;
import fun.masttf.mapper.UserMessageMapper;
import fun.masttf.entity.query.SimplePage;
import fun.masttf.entity.enums.PageSize;

/**
 * @Description:用户消息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
@Service("userMessageService")
public class UserMessageServiceImpl implements UserMessageService {

	@Autowired
	private UserMessageMapper<UserMessage, UserMessageQuery> userMessageMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<UserMessage> findListByQuery(UserMessageQuery query) {
		return userMessageMapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	@Override
	public Integer findCountByQuery(UserMessageQuery query) {
		return userMessageMapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PaginationResultVo<UserMessage> findListByPage(UserMessageQuery query) {
		Integer count = userMessageMapper.selectCount(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<UserMessage> list = userMessageMapper.selectList(query);
		PaginationResultVo<UserMessage> result = new PaginationResultVo<UserMessage>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(UserMessage bean) {
		return userMessageMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<UserMessage> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return userMessageMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增/修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<UserMessage> listBean) {
		if(listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return userMessageMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 根据MessageId查询
	 */
	@Override
	public UserMessage getByMessageId(Integer messageId) {
		return userMessageMapper.selectByMessageId(messageId);
	}

	/**
	 * 根据MessageId更新
	 */
	@Override
	public Integer updateByMessageId(UserMessage bean, Integer messageId) {
		return userMessageMapper.updateByMessageId(bean, messageId);
	}

	/**
	 * 根据MessageId删除
	 */
	@Override
	public Integer deleteByMessageId(Integer messageId) {
		return userMessageMapper.deleteByMessageId(messageId);
	}

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType查询
	 */
	@Override
	public UserMessage getByArticleIdAndCommentIdAndSendUserIdAndMessageType(String articleId, Integer commentId, String sendUserId, Integer messageType) {
		return userMessageMapper.selectByArticleIdAndCommentIdAndSendUserIdAndMessageType(articleId, commentId, sendUserId, messageType);
	}

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType更新
	 */
	@Override
	public Integer updateByArticleIdAndCommentIdAndSendUserIdAndMessageType(UserMessage bean, String articleId, Integer commentId, String sendUserId, Integer messageType) {
		return userMessageMapper.updateByArticleIdAndCommentIdAndSendUserIdAndMessageType(bean, articleId, commentId, sendUserId, messageType);
	}

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType删除
	 */
	@Override
	public Integer deleteByArticleIdAndCommentIdAndSendUserIdAndMessageType(String articleId, Integer commentId, String sendUserId, Integer messageType) {
		return userMessageMapper.deleteByArticleIdAndCommentIdAndSendUserIdAndMessageType(articleId, commentId, sendUserId, messageType);
	}

}