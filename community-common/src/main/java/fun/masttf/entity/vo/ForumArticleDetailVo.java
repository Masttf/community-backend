package fun.masttf.entity.vo;

public class ForumArticleDetailVo {
    private ForumArticleVo article;
    private ForumArticleAttachmentVo articleAttachments;
    private Boolean haveLike = false;
    public ForumArticleVo getArticle() {
        return article;
    }
    public void setArticle(ForumArticleVo article) {
        this.article = article;
    }
    
    public Boolean getHaveLike() {
        return haveLike;
    }
    public void setHaveLike(Boolean haveLike) {
        this.haveLike = haveLike;
    }
    public ForumArticleAttachmentVo getArticleAttachments() {
        return articleAttachments;
    }
    public void setArticleAttachments(ForumArticleAttachmentVo articleAttachments) {
        this.articleAttachments = articleAttachments;
    }
    
}
