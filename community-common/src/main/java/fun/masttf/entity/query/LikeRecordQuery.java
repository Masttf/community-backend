package fun.masttf.entity.query;

import java.util.Date;

/**
 * @Description:点赞记录查询类
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class LikeRecordQuery extends BaseQuery {

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

	private String objectIdFuzzy;

	/**
	 * 用户ID
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 发布时间
	 */
	private Date createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 主体作者ID
	 */
	private String authorUserId;

	private String authorUserIdFuzzy;

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

	public String getObjectIdFuzzy() {
		return this.objectIdFuzzy;
	}

	public void setObjectIdFuzzy(String objectIdFuzzy) {
		this.objectIdFuzzy = objectIdFuzzy;
	}

	public String getUserIdFuzzy() {
		return this.userIdFuzzy;
	}

	public void setUserIdFuzzy(String userIdFuzzy) {
		this.userIdFuzzy = userIdFuzzy;
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

	public String getAuthorUserIdFuzzy() {
		return this.authorUserIdFuzzy;
	}

	public void setAuthorUserIdFuzzy(String authorUserIdFuzzy) {
		this.authorUserIdFuzzy = authorUserIdFuzzy;
	}


}