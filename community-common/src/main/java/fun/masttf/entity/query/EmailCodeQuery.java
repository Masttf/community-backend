package fun.masttf.entity.query;

import java.util.Date;

/**
 * @Description:邮箱验证码查询类
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class EmailCodeQuery extends BaseQuery {

	/**
	 * 邮箱
	 */
	private String email;

	private String emailFuzzy;

	/**
	 * 编号
	 */
	private String code;

	private String codeFuzzy;

	/**
	 * 创建时间
	 */
	private Date createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 0:未使用  1:已使用
	 */
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

	public String getEmailFuzzy() {
		return this.emailFuzzy;
	}

	public void setEmailFuzzy(String emailFuzzy) {
		this.emailFuzzy = emailFuzzy;
	}

	public String getCodeFuzzy() {
		return this.codeFuzzy;
	}

	public void setCodeFuzzy(String codeFuzzy) {
		this.codeFuzzy = codeFuzzy;
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


}