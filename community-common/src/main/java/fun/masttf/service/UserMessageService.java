package fun.masttf.service;

import java.util.List;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.dto.UserMessageCountDto;
import fun.masttf.entity.po.UserMessage;
import fun.masttf.entity.query.UserMessageQuery;

/**
 * @Description:用户消息Serviece
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public interface UserMessageService {

	/**
	 * 根据条件查询列表
	 */
	List<UserMessage> findListByQuery(UserMessageQuery query);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByQuery(UserMessageQuery query);

	/**
	 * 分页查询
	 */
	PaginationResultVo<UserMessage> findListByPage(UserMessageQuery query);

	/**
	 * 新增
	 */
	Integer add(UserMessage bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<UserMessage> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<UserMessage> listBean);

	/**
	 * 根据MessageId查询
	 */
	UserMessage getByMessageId(Integer messageId);

	/**
	 * 根据MessageId更新
	 */
	Integer updateByMessageId(UserMessage bean, Integer messageId);

	/**
	 * 根据MessageId删除
	 */
	Integer deleteByMessageId(Integer messageId);

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType查询
	 */
	UserMessage getByArticleIdAndCommentIdAndSendUserIdAndMessageType(String articleId, Integer commentId, String sendUserId, Integer messageType);

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType更新
	 */
	Integer updateByArticleIdAndCommentIdAndSendUserIdAndMessageType(UserMessage bean, String articleId, Integer commentId, String sendUserId, Integer messageType);

	/**
	 * 根据ArticleIdAndCommentIdAndSendUserIdAndMessageType删除
	 */
	Integer deleteByArticleIdAndCommentIdAndSendUserIdAndMessageType(String articleId, Integer commentId, String sendUserId, Integer messageType);

	UserMessageCountDto getUserMessageCount(String userId);

	void readMessageByType(String receivedUserId, Integer messageType);
}