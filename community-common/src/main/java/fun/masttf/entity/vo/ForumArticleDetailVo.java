package fun.masttf.entity.vo;

import fun.masttf.entity.vo.Web.ForumArticleVo;

public class ForumArticleDetailVo {
    private ForumArticleVo article;
    private ForumArticleAttachmentVo articleAttachment;
    private Boolean haveLike = false;
    public ForumArticleVo getArticle() {
        return article;
    }
    public void setArticle(ForumArticleVo article) {
        this.article = article;
    }
    public ForumArticleAttachmentVo getArticleAttachment() {
        return articleAttachment;
    }
    public void setArticleAttachment(ForumArticleAttachmentVo articleAttachment) {
        this.articleAttachment = articleAttachment;
    }
    public Boolean getHaveLike() {
        return haveLike;
    }
    public void setHaveLike(Boolean haveLike) {
        this.haveLike = haveLike;
    }

    
}
