package fun.masttf.entity.po;

import java.io.Serializable;

/**
 * @Description:用户附件下载
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class ForumArticleAttachmentDownload implements Serializable {

	/**
	 * 文件ID
	 */
	private String fileId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 文章ID
	 */
	private String articleId;

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

	@Override
	public String toString() {
		return "ForumArticleAttachmentDownload [" +
			"文件ID fileId=" + (fileId == null ? "空" : fileId) + ", " +
			"用户id userId=" + (userId == null ? "空" : userId) + ", " +
			"文章ID articleId=" + (articleId == null ? "空" : articleId) + ", " +
			"文件下载次数 downloadCount=" + (downloadCount == null ? "空" : downloadCount) + ", " +
			"]";
	}

}