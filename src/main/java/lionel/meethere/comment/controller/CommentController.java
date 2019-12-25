package lionel.meethere.comment.controller;

import lionel.meethere.comment.dto.CommentDTO;
import lionel.meethere.comment.param.CommentPublishParam;
import lionel.meethere.comment.service.CommentService;
import lionel.meethere.comment.status.CommentAuditStatus;
import lionel.meethere.comment.status.CommentStatus;
import lionel.meethere.comment.vo.CommentVO;
import lionel.meethere.paging.PageParam;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.user.session.UserSessionInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/site/comment/")
public class CommentController {

    @Autowired
    private CommentService commentService;


    //OK
    @PostMapping("publish")
    public Result<?> publishComment(@SessionAttribute UserSessionInfo userSessionInfo,
                                   @RequestParam Integer siteId,
                                    @RequestParam String content){
        CommentPublishParam publishParam = new CommentPublishParam(siteId,content);
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(publishParam,commentDTO);
        commentDTO.setReviewerId(userSessionInfo.getId());
        commentService.publishComment(commentDTO);
        return CommonResult.success();
    }

    @PostMapping("delete")
    public Result<?> deleteComment(@SessionAttribute UserSessionInfo userSessionInfo,
                                   @RequestBody CommentVO commentVO){
        if(userSessionInfo.getAdmin() == 1){
            commentService.deleteComment(commentVO.getId());
            return CommonResult.success();
        }
        else if(userSessionInfo.getId().equals(commentVO.getReviewer().getId())){
            commentService.deleteComment(commentVO.getId());
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @PostMapping("audit")
    public Result<?> auditCommnet(@SessionAttribute UserSessionInfo userSessionInfo,
                                  @RequestBody CommentVO commentVO,
                                  @RequestBody Integer auditOption){
        if(userSessionInfo.getAdmin() != 1)
            return CommonResult.accessDenied();
        if(auditOption == CommentAuditStatus.SUCCESS){
            commentService.auditComment(commentVO.getId(), CommentStatus.AUDITED);
        }
        else {
            commentService.auditComment(commentVO.getId(),CommentStatus.AUDITED_FAILED);
        }
        return CommonResult.success();
    }
    //OK
    @PostMapping("getcomments")
    public Result<?> getCommentsBySite(@RequestParam Integer pageNum,@RequestParam Integer pageSize,
                                       @RequestParam Integer siteId){

        PageParam pageParam = new PageParam(pageNum,pageSize);
        return CommonResult.success().data(commentService.getCommentsBySite(pageParam,siteId)).total(commentService.getCommentCount());
    }

    @GetMapping("get")
    public Result<?> getCommnetById(@RequestParam Integer commentId){
        return CommonResult.success().data(commentService.getCommentById(commentId));
    }


}
