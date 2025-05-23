package fun.masttf.entity.vo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @Description:用户信息Vo
 * 
 * @auther:Masttf
 * @date:2025-05-08
 */
public class UserInfoVo implements Serializable {

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 昵称
	 */
	private String nickName;


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
	@JsonFormat(pattern = "yyyy-MM-dd", timezone ="GMT+8")
	private Date joinTime;

	/**
	 * 最后登录时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone ="GMT+8")
	private Date lastLoginTime;

	/**
	 * 当前积分
	 */
	private Integer currentIntegral;


	private Integer postCount;

    private Integer likeCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPersonDescription() {
        return personDescription;
    }

    public void setPersonDescription(String personDescription) {
        this.personDescription = personDescription;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getCurrentIntegral() {
        return currentIntegral;
    }

    public void setCurrentIntegral(Integer currentIntegral) {
        this.currentIntegral = currentIntegral;
    }

    public Integer getPostCount() {
        return postCount;
    }

    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

}