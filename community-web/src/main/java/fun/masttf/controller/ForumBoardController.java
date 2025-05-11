package fun.masttf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.service.ForumBoardService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/board")
public class ForumBoardController extends ABaseController {

    @Autowired
    private ForumBoardService forumBoardService;

    @RequestMapping("/loadBoard")
    public ResponseVo<Object> loadBoard() {
        return getSuccessResponseVo(forumBoardService.getBoardTree(null));
    }
    
    
}
