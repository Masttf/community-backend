package fun.masttf.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import fun.masttf.utils.DateUtils;
import fun.masttf.entity.enums.DateTimePatternEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description:邮箱验证码
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class EmailCode implements Serializable {

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 编号
	 */
	private String code;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone ="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 0:未使用  1:已使用
	 */
	@JsonIgnore
	private Integer status;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "EmailCode [" +
			"邮箱 email=" + (email == null ? "空" : email) + ", " +
			"编号 code=" + (code == null ? "空" : code) + ", " +
			"创建时间 createTime=" + (createTime == null ? "空" : DateUtils.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ", " +
			"0:未使用  1:已使用 status=" + (status == null ? "空" : status) + ", " +
			"]";
	}

}