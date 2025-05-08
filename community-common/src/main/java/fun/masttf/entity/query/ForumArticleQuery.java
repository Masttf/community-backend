package fun.masttf.entity.query;

import java.util.Date;

/**
 * @Description:文章信息查询类
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class ForumArticleQuery extends BaseQuery {

	/**
	 * 文章ID
	 */
	private String articleId;

	private String articleIdFuzzy;

	/**
	 * 板块ID
	 */
	private Integer boardId;

	/**
	 * 板块名称
	 */
	private String boardName;

	private String boardNameFuzzy;

	/**
	 * 父级板块ID
	 */
	private Integer pBoardId;

	/**
	 * 父板块名称
	 */
	private String pBoardName;

	private String pBoardNameFuzzy;

	/**
	 * 用户ID
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 昵称
	 */
	private String nickName;

	private String nickNameFuzzy;

	/**
	 * 最后登录ip地址
	 */
	private String userIpAddress;

	private String userIpAddressFuzzy;

	/**
	 * 标题
	 */
	private String title;

	private String titleFuzzy;

	/**
	 * 封面
	 */
	private String cover;

	private String coverFuzzy;

	/**
	 * 内容
	 */
	private String content;

	private String contentFuzzy;

	/**
	 * markdown内容
	 */
	private String markdownContent;

	private String markdownContentFuzzy;

	/**
	 * 0:富文本编辑器 1:markdown编辑器
	 */
	private Integer editorType;

	/**
	 * 摘要
	 */
	private String summary;

	private String summaryFuzzy;

	/**
	 * 发布时间
	 */
	private Date postTime;

	private String postTimeStart;

	private String postTimeEnd;

	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;

	private String lastUpdateTimeStart;

	private String lastUpdateTimeEnd;

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
	private Integer status;

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

	public Integer getPBoardId() {
		return this.pBoardId;
	}

	public void setPBoardId(Integer pBoardId) {
		this.pBoardId = pBoardId;
	}

	public String getPBoardName() {
		return this.pBoardName;
	}

	public void setPBoardName(String pBoardName) {
		this.pBoardName = pBoardName;
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

	public String getArticleIdFuzzy() {
		return this.articleIdFuzzy;
	}

	public void setArticleIdFuzzy(String articleIdFuzzy) {
		this.articleIdFuzzy = articleIdFuzzy;
	}

	public String getBoardNameFuzzy() {
		return this.boardNameFuzzy;
	}

	public void setBoardNameFuzzy(String boardNameFuzzy) {
		this.boardNameFuzzy = boardNameFuzzy;
	}

	public String getPBoardNameFuzzy() {
		return this.pBoardNameFuzzy;
	}

	public void setPBoardNameFuzzy(String pBoardNameFuzzy) {
		this.pBoardNameFuzzy = pBoardNameFuzzy;
	}

	public String getUserIdFuzzy() {
		return this.userIdFuzzy;
	}

	public void setUserIdFuzzy(String userIdFuzzy) {
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getNickNameFuzzy() {
		return this.nickNameFuzzy;
	}

	public void setNickNameFuzzy(String nickNameFuzzy) {
		this.nickNameFuzzy = nickNameFuzzy;
	}

	public String getUserIpAddressFuzzy() {
		return this.userIpAddressFuzzy;
	}

	public void setUserIpAddressFuzzy(String userIpAddressFuzzy) {
		this.userIpAddressFuzzy = userIpAddressFuzzy;
	}

	public String getTitleFuzzy() {
		return this.titleFuzzy;
	}

	public void setTitleFuzzy(String titleFuzzy) {
		this.titleFuzzy = titleFuzzy;
	}

	public String getCoverFuzzy() {
		return this.coverFuzzy;
	}

	public void setCoverFuzzy(String coverFuzzy) {
		this.coverFuzzy = coverFuzzy;
	}

	public String getContentFuzzy() {
		return this.contentFuzzy;
	}

	public void setContentFuzzy(String contentFuzzy) {
		this.contentFuzzy = contentFuzzy;
	}

	public String getMarkdownContentFuzzy() {
		return this.markdownContentFuzzy;
	}

	public void setMarkdownContentFuzzy(String markdownContentFuzzy) {
		this.markdownContentFuzzy = markdownContentFuzzy;
	}

	public String getSummaryFuzzy() {
		return this.summaryFuzzy;
	}

	public void setSummaryFuzzy(String summaryFuzzy) {
		this.summaryFuzzy = summaryFuzzy;
	}

	public String getPostTimeStart() {
		return this.postTimeStart;
	}

	public void setPostTimeStart(String postTimeStart) {
		this.postTimeStart = postTimeStart;
	}

	public String getPostTimeEnd() {
		return this.postTimeEnd;
	}

	public void setPostTimeEnd(String postTimeEnd) {
		this.postTimeEnd = postTimeEnd;
	}

	public String getLastUpdateTimeStart() {
		return this.lastUpdateTimeStart;
	}

	public void setLastUpdateTimeStart(String lastUpdateTimeStart) {
		this.lastUpdateTimeStart = lastUpdateTimeStart;
	}

	public String getLastUpdateTimeEnd() {
		return this.lastUpdateTimeEnd;
	}

	public void setLastUpdateTimeEnd(String lastUpdateTimeEnd) {
		this.lastUpdateTimeEnd = lastUpdateTimeEnd;
	}


}