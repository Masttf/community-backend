package fun.masttf.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import fun.masttf.utils.DateUtils;
import fun.masttf.entity.enums.DateTimePatternEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description:文章信息
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class ForumArticle implements Serializable {

	/**
	 * 文章ID
	 */
	private String articleId;

	/**
	 * 板块ID
	 */
	private Integer boardId;

	/**
	 * 板块名称
	 */
	private String boardName;

	/**
	 * 父级板块ID
	 */
	private Integer pBoardId;

	/**
	 * 父板块名称
	 */
	private String pBoardName;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 最后登录ip地址
	 */
	private String userIpAddress;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 封面
	 */
	private String cover;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * markdown内容
	 */
	private String markdownContent;

	/**
	 * 0:富文本编辑器 1:markdown编辑器
	 */
	private Integer editorType;

	/**
	 * 摘要
	 */
	private String summary;

	/**
	 * 发布时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone ="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	private Date postTime;

	/**
	 * 最后更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone ="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	private Date lastUpdateTime;

	/**
	 * 阅读数量
	 */
	private Integer readCount;

	/**
	 * 点赞数
	 */
	private Integer goodCount;

	/**
	 * 评论数
	 */
	private Integer commentCount;

	/**
	 * 0未置顶  1:已置顶
	 */
	private Integer topType;

	/**
	 * 0:没有附件  1:有附件
	 */
	private Integer attachmentType;

	/**
	 * -1已删除 0:待审核  1:已审核 
	 */
	@JsonIgnore
	private Integer status;

	public Integer getpBoardId() {
		return pBoardId;
	}

	public void setpBoardId(Integer pBoardId) {
		this.pBoardId = pBoardId;
	}

	public String getpBoardName() {
		return pBoardName;
	}

	public void setpBoardName(String pBoardName) {
		this.pBoardName = pBoardName;
	}

	public String getArticleId() {
		return this.articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public Integer getBoardId() {
		return this.boardId;
	}

	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}

	public String getBoardName() {
		return this.boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCover() {
		return this.cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMarkdownContent() {
		return this.markdownContent;
	}

	public void setMarkdownContent(String markdownContent) {
		this.markdownContent = markdownContent;
	}

	public Integer getEditorType() {
		return this.editorType;
	}

	public void setEditorType(Integer editorType) {
		this.editorType = editorType;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getPostTime() {
		return this.postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getReadCount() {
		return this.readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getGoodCount() {
		return this.goodCount;
	}

	public void setGoodCount(Integer goodCount) {
		this.goodCount = goodCount;
	}

	public Integer getCommentCount() {
		return this.commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getTopType() {
		return this.topType;
	}

	public void setTopType(Integer topType) {
		this.topType = topType;
	}

	public Integer getAttachmentType() {
		return this.attachmentType;
	}

	public void setAttachmentType(Integer attachmentType) {
		this.attachmentType = attachmentType;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ForumArticle [" +
			"文章ID articleId=" + (articleId == null ? "空" : articleId) + ", " +
			"板块ID boardId=" + (boardId == null ? "空" : boardId) + ", " +
			"板块名称 boardName=" + (boardName == null ? "空" : boardName) + ", " +
			"父级板块ID pBoardId=" + (pBoardId == null ? "空" : pBoardId) + ", " +
			"父板块名称 pBoardName=" + (pBoardName == null ? "空" : pBoardName) + ", " +
			"用户ID userId=" + (userId == null ? "空" : userId) + ", " +
			"昵称 nickName=" + (nickName == null ? "空" : nickName) + ", " +
			"最后登录ip地址 userIpAddress=" + (userIpAddress == null ? "空" : userIpAddress) + ", " +
			"标题 title=" + (title == null ? "空" : title) + ", " +
			"封面 cover=" + (cover == null ? "空" : cover) + ", " +
			"内容 content=" + (content == null ? "空" : content) + ", " +
			"markdown内容 markdownContent=" + (markdownContent == null ? "空" : markdownContent) + ", " +
			"0:富文本编辑器 1:markdown编辑器 editorType=" + (editorType == null ? "空" : editorType) + ", " +
			"摘要 summary=" + (summary == null ? "空" : summary) + ", " +
			"发布时间 postTime=" + (postTime == null ? "空" : DateUtils.format(postTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ", " +
			"最后更新时间 lastUpdateTime=" + (lastUpdateTime == null ? "空" : DateUtils.format(lastUpdateTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ", " +
			"阅读数量 readCount=" + (readCount == null ? "空" : readCount) + ", " +
			"点赞数 goodCount=" + (goodCount == null ? "空" : goodCount) + ", " +
			"评论数 commentCount=" + (commentCount == null ? "空" : commentCount) + ", " +
			"0未置顶  1:已置顶 topType=" + (topType == null ? "空" : topType) + ", " +
			"0:没有附件  1:有附件 attachmentType=" + (attachmentType == null ? "空" : attachmentType) + ", " +
			"-1已删除 0:待审核  1:已审核  status=" + (status == null ? "空" : status) + ", " +
			"]";
	}

}