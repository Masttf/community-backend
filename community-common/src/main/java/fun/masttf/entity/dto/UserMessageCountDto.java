package fun.masttf.entity.dto;

public class UserMessageCountDto {
    private Integer total = 0;
    public Integer sys = 0;
    public Integer reply = 0;
    private Integer likePost = 0;
    private Integer likeComment = 0;
    private Integer downloadAttachment = 0;
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Integer getSys() {
        return sys;
    }
    public void setSys(Integer sys) {
        this.sys = sys;
    }
    public Integer getReply() {
        return reply;
    }
    public void setReply(Integer reply) {
        this.reply = reply;
    }
    public Integer getLikePost() {
        return likePost;
    }
    public void setLikePost(Integer likePost) {
        this.likePost = likePost;
    }
    public Integer getLikeComment() {
        return likeComment;
    }
    public void setLikeComment(Integer likeComment) {
        this.likeComment = likeComment;
    }
    public Integer getDownloadAttachment() {
        return downloadAttachment;
    }
    public void setDownloadAttachment(Integer downloadAttachment) {
        this.downloadAttachment = downloadAttachment;
    }

    
}
