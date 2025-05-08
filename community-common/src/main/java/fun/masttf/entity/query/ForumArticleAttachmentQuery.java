package fun.masttf.entity.query;


/**
 * @Description:文件信息查询类
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class ForumArticleAttachmentQuery extends BaseQuery {

	/**
	 * 文件ID
	 */
	private String fileId;

	private String fileIdFuzzy;

	/**
	 * 文章ID
	 */
	private String articleId;

	private String articleIdFuzzy;

	/**
	 * 用户id
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 文件大小
	 */
	private Long fileSize;

	/**
	 * 文件名称
	 */
	private String fileName;

	private String fileNameFuzzy;

	/**
	 * 下载次数
	 */
	private Integer downloadCount;

	/**
	 * 文件路径
	 */
	private String filePath;

	private String filePathFuzzy;

	/**
	 * 文件类型
	 */
	private Integer fileType;

	/**
	 * 下载所需积分
	 */
	private Integer integral;

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getArticleId() {
		return this.articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getDownloadCount() {
		return this.downloadCount;
	}

	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getFileType() {
		return this.fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getFileIdFuzzy() {
		return this.fileIdFuzzy;
	}

	public void setFileIdFuzzy(String fileIdFuzzy) {
		this.fileIdFuzzy = fileIdFuzzy;
	}

	public String getArticleIdFuzzy() {
		return this.articleIdFuzzy;
	}

	public void setArticleIdFuzzy(String articleIdFuzzy) {
		this.articleIdFuzzy = articleIdFuzzy;
	}

	public String getUserIdFuzzy() {
		return this.userIdFuzzy;
	}

	public void setUserIdFuzzy(String userIdFuzzy) {
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getFileNameFuzzy() {
		return this.fileNameFuzzy;
	}

	public void setFileNameFuzzy(String fileNameFuzzy) {
		this.fileNameFuzzy = fileNameFuzzy;
	}

	public String getFilePathFuzzy() {
		return this.filePathFuzzy;
	}

	public void setFilePathFuzzy(String filePathFuzzy) {
		this.filePathFuzzy = filePathFuzzy;
	}


}