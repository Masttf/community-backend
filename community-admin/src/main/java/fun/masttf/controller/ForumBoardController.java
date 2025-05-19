package fun.masttf.controller;

import javax.mail.Multipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.annotation.VerifyParam;
import fun.masttf.entity.dto.FileUploadDto;
import fun.masttf.entity.enums.BoardPostTypeEnum;
import fun.masttf.entity.enums.FileUploadEnum;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.po.ForumBoard;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.service.ForumBoardService;
import fun.masttf.utils.FileUtils;

@RestController
@RequestMapping("/board")
public class ForumBoardController extends ABaseController {
    
    @Autowired
    private ForumBoardService forumBoardService;
    @Autowired
    private FileUtils fileUtils;

    @RequestMapping("/loadBoard")
    public ResponseVo<Object> loadBoard() {
        return getSuccessResponseVo(forumBoardService.getBoardTree(null));
    }

    @RequestMapping("/saveBoard")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> saveBoard(Integer boardId,
                            @VerifyParam(required = true) Integer pBoardId,
                            @VerifyParam(required = true) String boardName,
                            String boardDesc,
                            Integer postType,
                            MultipartFile cover){

        BoardPostTypeEnum postTypeEnum = BoardPostTypeEnum.getByType(postType);
        if (postTypeEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        ForumBoard board = new ForumBoard();
        board.setBoardId(boardId);
        board.setpBoardId(pBoardId);
        board.setBoardName(boardName);
        board.setBoardDesc(boardDesc);
        board.setPostType(postType);
        if(cover != null && !cover.isEmpty()) {
            FileUploadDto uploadDto = fileUtils.uploadFile2Local(cover, FileUploadEnum.ARTICLE_COVER, null);
            board.setCover(uploadDto.getLocalPath());
        }
        forumBoardService.saveBoard(board);
        return getSuccessResponseVo(null);
    }
}
