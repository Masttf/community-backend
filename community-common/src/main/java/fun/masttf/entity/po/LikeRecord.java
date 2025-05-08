package fun.masttf.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import fun.masttf.utils.DateUtils;
import fun.masttf.entity.enums.DateTimePatternEnum;

/**
 * @Description:点赞记录
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class LikeRecord implements Serializable {

	/**
	 * 自增ID
	 */
	private Integer opId;

	/**
	 * 操作类型0:文章点赞 1:评论点赞
	 */
	private Integer opType;

	/**
	 * 主体ID
	 */
	private String objectId;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 发布时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone ="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 主体作者ID
	 */
	private String authorUserId;

	public Integer getOpId() {
		return this.opId;
	}

	public void setOpId(Integer opId) {
		this.opId = opId;
	}

	public Integer getOpType() {
		return this.opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAuthorUserId() {
		return this.authorUserId;
	}

	public void setAuthorUserId(String authorUserId) {
		this.authorUserId = authorUserId;
	}

	@Override
	public String toString() {
		return "LikeRecord [" +
			"自增ID opId=" + (opId == null ? "空" : opId) + ", " +
			"操作类型0:文章点赞 1:评论点赞 opType=" + (opType == null ? "空" : opType) + ", " +
			"主体ID objectId=" + (objectId == null ? "空" : objectId) + ", " +
			"用户ID userId=" + (userId == null ? "空" : userId) + ", " +
			"发布时间 createTime=" + (createTime == null ? "空" : DateUtils.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ", " +
			"主体作者ID authorUserId=" + (authorUserId == null ? "空" : authorUserId) + ", " +
			"]";
	}

}