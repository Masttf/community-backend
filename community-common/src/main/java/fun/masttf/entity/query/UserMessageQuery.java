package fun.masttf.entity.query;

import java.util.Date;

/**
 * @Description:用户消息查询类
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class UserMessageQuery extends BaseQuery {

	/**
	 * 自增ID
	 */
	private Integer messageId;

	/**
	 * 接收人用户ID
	 */
	private String receivedUserId;

	private String receivedUserIdFuzzy;

	/**
	 * 文章ID
	 */
	private String articleId;

	private String articleIdFuzzy;

	/**
	 * 文章标题
	 */
	private String articleTitle;

	private String articleTitleFuzzy;

	/**
	 * 评论ID
	 */
	private Integer commentId;

	/**
	 * 发送人用户ID
	 */
	private String sendUserId;

	private String sendUserIdFuzzy;

	/**
	 * 发送人昵称
	 */
	private String sendNickName;

	private String sendNickNameFuzzy;

	/**
	 * 0:系统消息 1:评论 2:文章点赞  3:评论点赞 4:附件下载
	 */
	private Integer messageType;

	/**
	 * 消息内容
	 */
	private String messageContent;

	private String messageContentFuzzy;

	/**
	 * 1:未读 2:已读
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date createTime;

	private String createTimeStart;

	private String createTimeEnd;

	public Integer getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getReceivedUserId() {
		return this.receivedUserId;
	}

	public void setReceivedUserId(String receivedUserId) {
		this.receivedUserId = receivedUserId;
	}

	public String getArticleId() {
		return this.articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getArticleTitle() {
		return this.articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public Integer getCommentId() {
		return this.commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getSendUserId() {
		return this.sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendNickName() {
		return this.sendNickName;
	}

	public void setSendNickName(String sendNickName) {
		this.sendNickName = sendNickName;
	}

	public Integer getMessageType() {
		return this.messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public String getMessageContent() {
		return this.messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getReceivedUserIdFuzzy() {
		return this.receivedUserIdFuzzy;
	}

	public void setReceivedUserIdFuzzy(String receivedUserIdFuzzy) {
		this.receivedUserIdFuzzy = receivedUserIdFuzzy;
	}

	public String getArticleIdFuzzy() {
		return this.articleIdFuzzy;
	}

	public void setArticleIdFuzzy(String articleIdFuzzy) {
		this.articleIdFuzzy = articleIdFuzzy;
	}

	public String getArticleTitleFuzzy() {
		return this.articleTitleFuzzy;
	}

	public void setArticleTitleFuzzy(String articleTitleFuzzy) {
		this.articleTitleFuzzy = articleTitleFuzzy;
	}

	public String getSendUserIdFuzzy() {
		return this.sendUserIdFuzzy;
	}

	public void setSendUserIdFuzzy(String sendUserIdFuzzy) {
		this.sendUserIdFuzzy = sendUserIdFuzzy;
	}

	public String getSendNickNameFuzzy() {
		return this.sendNickNameFuzzy;
	}

	public void setSendNickNameFuzzy(String sendNickNameFuzzy) {
		this.sendNickNameFuzzy = sendNickNameFuzzy;
	}

	public String getMessageContentFuzzy() {
		return this.messageContentFuzzy;
	}

	public void setMessageContentFuzzy(String messageContentFuzzy) {
		this.messageContentFuzzy = messageContentFuzzy;
	}

	public String getCreateTimeStart() {
		return this.createTimeStart;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeEnd() {
		return this.createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}


}