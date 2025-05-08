package fun.masttf.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import fun.masttf.utils.DateUtils;
import fun.masttf.entity.enums.DateTimePatternEnum;

/**
 * @Description:用户积分记录表
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class UserIntegralRecord implements Serializable {

	/**
	 * 记录ID
	 */
	private Integer recordId;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 操作类型
	 */
	private Integer operType;

	/**
	 * 积分
	 */
	private Integer integral;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone ="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	public Integer getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getOperType() {
		return this.operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "UserIntegralRecord [" +
			"记录ID recordId=" + (recordId == null ? "空" : recordId) + ", " +
			"用户ID userId=" + (userId == null ? "空" : userId) + ", " +
			"操作类型 operType=" + (operType == null ? "空" : operType) + ", " +
			"积分 integral=" + (integral == null ? "空" : integral) + ", " +
			"创建时间 createTime=" + (createTime == null ? "空" : DateUtils.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ", " +
			"]";
	}

}