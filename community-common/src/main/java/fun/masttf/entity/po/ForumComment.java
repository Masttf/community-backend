package fun.masttf.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import fun.masttf.utils.DateUtils;
import fun.masttf.entity.enums.DateTimePatternEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description:评论
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class ForumComment implements Serializable {

	/**
	 * 评论ID
	 */
	private Integer commentId;

	/**
	 * 父级评论ID
	 */
	private Integer pCommentId;

	/**
	 * 文章ID
	 */
	private String articleId;

	/**
	 * 回复内容
	 */
	private String content;

	/**
	 * 图片
	 */
	private String imgPath;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 用户ip地址
	 */
	private String userIpAddress;

	/**
	 * 回复人ID
	 */
	private String replyUserId;

	/**
	 * 回复人昵称
	 */
	private String replyNickName;

	/**
	 * 0:未置顶  1:置顶
	 */
	private Integer topType;

	/**
	 * 发布时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone ="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	private Date postTime;

	/**
	 * good数量
	 */
	private Integer goodCount;

	/**
	 * 0:待审核  1:已审核
	 */
	@JsonIgnore
	private Integer status;

	public Integer getCommentId() {
		return this.commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getPCommentId() {
		return this.pCommentId;
	}

	public void setPCommentId(Integer pCommentId) {
		this.pCommentId = pCommentId;
	}

	public String getArticleId() {
		return this.articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgPath() {
		return this.imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserIpAddress() {
		return this.userIpAddress;
	}

	public void setUserIpAddress(String userIpAddress) {
		this.userIpAddress = userIpAddress;
	}

	public String getReplyUserId() {
		return this.replyUserId;
	}

	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}

	public String getReplyNickName() {
		return this.replyNickName;
	}

	public void setReplyNickName(String replyNickName) {
		this.replyNickName = replyNickName;
	}

	public Integer getTopType() {
		return this.topType;
	}

	public void setTopType(Integer topType) {
		this.topType = topType;
	}

	public Date getPostTime() {
		return this.postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public Integer getGoodCount() {
		return this.goodCount;
	}

	public void setGoodCount(Integer goodCount) {
		this.goodCount = goodCount;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ForumComment [" +
			"评论ID commentId=" + (commentId == null ? "空" : commentId) + ", " +
			"父级评论ID pCommentId=" + (pCommentId == null ? "空" : pCommentId) + ", " +
			"文章ID articleId=" + (articleId == null ? "空" : articleId) + ", " +
			"回复内容 content=" + (content == null ? "空" : content) + ", " +
			"图片 imgPath=" + (imgPath == null ? "空" : imgPath) + ", " +
			"用户ID userId=" + (userId == null ? "空" : userId) + ", " +
			"昵称 nickName=" + (nickName == null ? "空" : nickName) + ", " +
			"用户ip地址 userIpAddress=" + (userIpAddress == null ? "空" : userIpAddress) + ", " +
			"回复人ID replyUserId=" + (replyUserId == null ? "空" : replyUserId) + ", " +
			"回复人昵称 replyNickName=" + (replyNickName == null ? "空" : replyNickName) + ", " +
			"0:未置顶  1:置顶 topType=" + (topType == null ? "空" : topType) + ", " +
			"发布时间 postTime=" + (postTime == null ? "空" : DateUtils.format(postTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ", " +
			"good数量 goodCount=" + (goodCount == null ? "空" : goodCount) + ", " +
			"0:待审核  1:已审核 status=" + (status == null ? "空" : status) + ", " +
			"]";
	}

}