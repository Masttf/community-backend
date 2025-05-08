package fun.masttf.entity.query;

import java.util.Date;

/**
 * @Description:用户信息查询类
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class UserInfoQuery extends BaseQuery {

	/**
	 * 用户ID
	 */
	private String userId;

	private String userIdFuzzy;

	/**
	 * 昵称
	 */
	private String nickName;

	private String nickNameFuzzy;

	/**
	 * 邮箱
	 */
	private String email;

	private String emailFuzzy;

	/**
	 * 密码
	 */
	private String password;

	private String passwordFuzzy;

	/**
	 * 0:女 1:男
	 */
	private Integer sex;

	/**
	 * 个人描述
	 */
	private String personDescription;

	private String personDescriptionFuzzy;

	/**
	 * 加入时间
	 */
	private Date joinTime;

	private String joinTimeStart;

	private String joinTimeEnd;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	private String lastLoginTimeStart;

	private String lastLoginTimeEnd;

	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;

	private String lastLoginIpFuzzy;

	/**
	 * 最后登录ip地址
	 */
	private String lastLoginIpAddress;

	private String lastLoginIpAddressFuzzy;

	/**
	 * 积分
	 */
	private Integer totalIntegral;

	/**
	 * 当前积分
	 */
	private Integer currentIntegral;

	/**
	 * 0:禁用 1:正常
	 */
	private Integer status;

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPersonDescription() {
		return this.personDescription;
	}

	public void setPersonDescription(String personDescription) {
		this.personDescription = personDescription;
	}

	public Date getJoinTime() {
		return this.joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLastLoginIpAddress() {
		return this.lastLoginIpAddress;
	}

	public void setLastLoginIpAddress(String lastLoginIpAddress) {
		this.lastLoginIpAddress = lastLoginIpAddress;
	}

	public Integer getTotalIntegral() {
		return this.totalIntegral;
	}

	public void setTotalIntegral(Integer totalIntegral) {
		this.totalIntegral = totalIntegral;
	}

	public Integer getCurrentIntegral() {
		return this.currentIntegral;
	}

	public void setCurrentIntegral(Integer currentIntegral) {
		this.currentIntegral = currentIntegral;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserIdFuzzy() {
		return this.userIdFuzzy;
	}

	public void setUserIdFuzzy(String userIdFuzzy) {
		this.userIdFuzzy = userIdFuzzy;
	}

	public String getNickNameFuzzy() {
		return this.nickNameFuzzy;
	}

	public void setNickNameFuzzy(String nickNameFuzzy) {
		this.nickNameFuzzy = nickNameFuzzy;
	}

	public String getEmailFuzzy() {
		return this.emailFuzzy;
	}

	public void setEmailFuzzy(String emailFuzzy) {
		this.emailFuzzy = emailFuzzy;
	}

	public String getPasswordFuzzy() {
		return this.passwordFuzzy;
	}

	public void setPasswordFuzzy(String passwordFuzzy) {
		this.passwordFuzzy = passwordFuzzy;
	}

	public String getPersonDescriptionFuzzy() {
		return this.personDescriptionFuzzy;
	}

	public void setPersonDescriptionFuzzy(String personDescriptionFuzzy) {
		this.personDescriptionFuzzy = personDescriptionFuzzy;
	}

	public String getJoinTimeStart() {
		return this.joinTimeStart;
	}

	public void setJoinTimeStart(String joinTimeStart) {
		this.joinTimeStart = joinTimeStart;
	}

	public String getJoinTimeEnd() {
		return this.joinTimeEnd;
	}

	public void setJoinTimeEnd(String joinTimeEnd) {
		this.joinTimeEnd = joinTimeEnd;
	}

	public String getLastLoginTimeStart() {
		return this.lastLoginTimeStart;
	}

	public void setLastLoginTimeStart(String lastLoginTimeStart) {
		this.lastLoginTimeStart = lastLoginTimeStart;
	}

	public String getLastLoginTimeEnd() {
		return this.lastLoginTimeEnd;
	}

	public void setLastLoginTimeEnd(String lastLoginTimeEnd) {
		this.lastLoginTimeEnd = lastLoginTimeEnd;
	}

	public String getLastLoginIpFuzzy() {
		return this.lastLoginIpFuzzy;
	}

	public void setLastLoginIpFuzzy(String lastLoginIpFuzzy) {
		this.lastLoginIpFuzzy = lastLoginIpFuzzy;
	}

	public String getLastLoginIpAddressFuzzy() {
		return this.lastLoginIpAddressFuzzy;
	}

	public void setLastLoginIpAddressFuzzy(String lastLoginIpAddressFuzzy) {
		this.lastLoginIpAddressFuzzy = lastLoginIpAddressFuzzy;
	}


}