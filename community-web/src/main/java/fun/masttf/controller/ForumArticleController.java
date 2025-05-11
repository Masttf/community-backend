package fun.masttf.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.ArticleOrderTypeEnum;
import fun.masttf.entity.enums.ArticleStatusEnum;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.query.ForumArticleQuery;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.entity.vo.Web.ForumArticleVo;
import fun.masttf.service.ForumArticleService;

@RestController
@RequestMapping("/forum")
public class ForumArticleController extends ABaseController {

    @Autowired
    private ForumArticleService forumArticleService;
    
    @RequestMapping("/loadArticle")
    public ResponseVo<Object> loadArticle(HttpSession session, Integer boardId, Integer pBoardId, Integer orderType, Integer pageNo) {
        
        ForumArticleQuery articleQuery = new ForumArticleQuery();
        articleQuery.setBoardId((boardId == null || boardId == 0) ? null : boardId);
        articleQuery.setpBoardId(pBoardId);
        articleQuery.setPageNo(pageNo);
        SessionWebUserDto userDto = (SessionWebUserDto) session.getAttribute(Constans.SESSION_KEY);
        if(userDto != null) {
            articleQuery.setCurrentUserId(userDto.getUserId());
        }else{
            articleQuery.setStatus(ArticleStatusEnum.AUDIT.getStatus());
        }
        ArticleOrderTypeEnum orderTypeEnum = ArticleOrderTypeEnum.getByType(orderType);
        orderTypeEnum = orderTypeEnum == null ? ArticleOrderTypeEnum.HOT : orderTypeEnum;
        articleQuery.setOrderBy(orderTypeEnum.getOderSql());
        PaginationResultVo<ForumArticle> result = forumArticleService.findListByPage(articleQuery);
        return getSuccessResponseVo(convert2PaginationVo(result, ForumArticleVo.class));
    }
    
}
