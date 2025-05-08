package fun.masttf.entity.query;


/**
 * @Description:用户附件下载查询类
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class ForumArticleAttachmentDownloadQuery extends BaseQuery {

	/**
	 * 文件ID
	 */
	private String fileId;

	private String fileIdFuzzy;

	/**
	 * 用户id
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 文章ID
	 */
	private String articleId;

	private String articleIdFuzzy;

	/**
	 * 文件下载次数
	 */
	private Integer downloadCount;

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getArticleId() {
		return this.articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public Integer getDownloadCount() {
		return this.downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getFileIdFuzzy() {
		return this.fileIdFuzzy;
	}

	public void setFileIdFuzzy(String fileIdFuzzy) {
		this.fileIdFuzzy = fileIdFuzzy;
	}

	public String getUserIdFuzzy() {
		return this.userIdFuzzy;
	}

	public void setUserIdFuzzy(String userIdFuzzy) {
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getArticleIdFuzzy() {
		return this.articleIdFuzzy;
	}

	public void setArticleIdFuzzy(String articleIdFuzzy) {
		this.articleIdFuzzy = articleIdFuzzy;
	}


}