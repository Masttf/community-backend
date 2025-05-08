package fun.masttf.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import fun.masttf.utils.DateUtils;
import fun.masttf.entity.enums.DateTimePatternEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description:用户消息
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class UserMessage implements Serializable {

	/**
	 * 自增ID
	 */
	private Integer messageId;

	/**
	 * 接收人用户ID
	 */
	private String receivedUserId;

	/**
	 * 文章ID
	 */
	private String articleId;

	/**
	 * 文章标题
	 */
	private String articleTitle;

	/**
	 * 评论ID
	 */
	private Integer commentId;

	/**
	 * 发送人用户ID
	 */
	private String sendUserId;

	/**
	 * 发送人昵称
	 */
	private String sendNickName;

	/**
	 * 0:系统消息 1:评论 2:文章点赞  3:评论点赞 4:附件下载
	 */
	private Integer messageType;

	/**
	 * 消息内容
	 */
	private String messageContent;

	/**
	 * 1:未读 2:已读
	 */
	@JsonIgnore
	private Integer status;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone ="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

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

	@Override
	public String toString() {
		return "UserMessage [" +
			"自增ID messageId=" + (messageId == null ? "空" : messageId) + ", " +
			"接收人用户ID receivedUserId=" + (receivedUserId == null ? "空" : receivedUserId) + ", " +
			"文章ID articleId=" + (articleId == null ? "空" : articleId) + ", " +
			"文章标题 articleTitle=" + (articleTitle == null ? "空" : articleTitle) + ", " +
			"评论ID commentId=" + (commentId == null ? "空" : commentId) + ", " +
			"发送人用户ID sendUserId=" + (sendUserId == null ? "空" : sendUserId) + ", " +
			"发送人昵称 sendNickName=" + (sendNickName == null ? "空" : sendNickName) + ", " +
			"0:系统消息 1:评论 2:文章点赞  3:评论点赞 4:附件下载 messageType=" + (messageType == null ? "空" : messageType) + ", " +
			"消息内容 messageContent=" + (messageContent == null ? "空" : messageContent) + ", " +
			"1:未读 2:已读 status=" + (status == null ? "空" : status) + ", " +
			"创建时间 createTime=" + (createTime == null ? "空" : DateUtils.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ", " +
			"]";
	}

}