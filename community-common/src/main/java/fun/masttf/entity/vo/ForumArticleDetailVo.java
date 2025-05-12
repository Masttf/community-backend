package fun.masttf.entity.vo;

import java.util.List;

public class ForumArticleDetailVo {
    private ForumArticleVo article;
    private List<ForumArticleAttachmentVo> articleAttachments;
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
    public List<ForumArticleAttachmentVo> getArticleAttachments() {
        return articleAttachments;
    }
    public void setArticleAttachments(List<ForumArticleAttachmentVo> articleAttachments) {
        this.articleAttachments = articleAttachments;
    }

    
}
