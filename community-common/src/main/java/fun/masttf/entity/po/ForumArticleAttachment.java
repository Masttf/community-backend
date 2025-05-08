package fun.masttf.entity.po;

import java.io.Serializable;

/**
 * @Description:文件信息
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class ForumArticleAttachment implements Serializable {

	/**
	 * 文件ID
	 */
	private String fileId;

	/**
	 * 文章ID
	 */
	private String articleId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 文件大小
	 */
	private Long fileSize;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 下载次数
	 */
	private Integer downloadCount;

	/**
	 * 文件路径
	 */
	private String filePath;

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

	@Override
	public String toString() {
		return "ForumArticleAttachment [" +
			"文件ID fileId=" + (fileId == null ? "空" : fileId) + ", " +
			"文章ID articleId=" + (articleId == null ? "空" : articleId) + ", " +
			"用户id userId=" + (userId == null ? "空" : userId) + ", " +
			"文件大小 fileSize=" + (fileSize == null ? "空" : fileSize) + ", " +
			"文件名称 fileName=" + (fileName == null ? "空" : fileName) + ", " +
			"下载次数 downloadCount=" + (downloadCount == null ? "空" : downloadCount) + ", " +
			"文件路径 filePath=" + (filePath == null ? "空" : filePath) + ", " +
			"文件类型 fileType=" + (fileType == null ? "空" : fileType) + ", " +
			"下载所需积分 integral=" + (integral == null ? "空" : integral) + ", " +
			"]";
	}

}