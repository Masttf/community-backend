package fun.masttf.entity.vo;

import java.io.Serializable;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;

import fun.masttf.entity.enums.UserIntegralOperTypeEnum;

public class UserIntegralRecordVo implements Serializable {
    /**
	 * 用户ID
	 */
	private String userId;

    /**
	 * 操作类型
	 */
	private Integer operType;

    private String operTypeDesc;

	/**
	 * 积分
	 */
	private Integer integral;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone ="GMT+8")
	private Date createTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getOperType() {
        return operType;
    }

    public void setOperType(Integer operType) {
        this.operType = operType;
    }

    public String getOperTypeDesc() {
        UserIntegralOperTypeEnum operTypeEnum = UserIntegralOperTypeEnum.getByType(getOperType());
        return operTypeEnum == null ? "" : operTypeEnum.getDesc();
    }

    public void setOperTypeDesc(String operTypeDesc) {
        this.operTypeDesc = operTypeDesc;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
}
