package fun.masttf.entity.dto;

public class SessionWebUserDto {
    private String nickName;
    private String provice;
    private String userId;
    private Boolean isAdmin;
    public String getNickName() {
        return nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getProvice() {
        return provice;
    }
    public void setProvice(String provice) {
        this.provice = provice;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Boolean getIsAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    
    
}
