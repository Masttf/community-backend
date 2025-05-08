package fun.masttf.entity.po;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import fun.masttf.utils.DateUtils;
import fun.masttf.entity.enums.DateTimePatternEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description:用户信息
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class UserInfo implements Serializable {

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 0:女 1:男
	 */
	private Integer sex;

	/**
	 * 个人描述
	 */
	private String personDescription;

	/**
	 * 加入时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone ="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	private Date joinTime;

	/**
	 * 最后登录时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone ="GMT+8")
	@DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;

	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;

	/**
	 * 最后登录ip地址
	 */
	private String lastLoginIpAddress;

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
	@JsonIgnore
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

	@Override
	public String toString() {
		return "UserInfo [" +
			"用户ID userId=" + (userId == null ? "空" : userId) + ", " +
			"昵称 nickName=" + (nickName == null ? "空" : nickName) + ", " +
			"邮箱 email=" + (email == null ? "空" : email) + ", " +
			"密码 password=" + (password == null ? "空" : password) + ", " +
			"0:女 1:男 sex=" + (sex == null ? "空" : sex) + ", " +
			"个人描述 personDescription=" + (personDescription == null ? "空" : personDescription) + ", " +
			"加入时间 joinTime=" + (joinTime == null ? "空" : DateUtils.format(joinTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ", " +
			"最后登录时间 lastLoginTime=" + (lastLoginTime == null ? "空" : DateUtils.format(lastLoginTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())) + ", " +
			"最后登录IP lastLoginIp=" + (lastLoginIp == null ? "空" : lastLoginIp) + ", " +
			"最后登录ip地址 lastLoginIpAddress=" + (lastLoginIpAddress == null ? "空" : lastLoginIpAddress) + ", " +
			"积分 totalIntegral=" + (totalIntegral == null ? "空" : totalIntegral) + ", " +
			"当前积分 currentIntegral=" + (currentIntegral == null ? "空" : currentIntegral) + ", " +
			"0:禁用 1:正常 status=" + (status == null ? "空" : status) + ", " +
			"]";
	}

}