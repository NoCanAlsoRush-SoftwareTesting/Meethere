package lionel.meethere.comment.service;

import lionel.meethere.comment.dao.CommentMapper;
import lionel.meethere.comment.dto.CommentDTO;
import lionel.meethere.comment.entity.Comment;
import lionel.meethere.comment.status.CommentStatus;
import lionel.meethere.comment.vo.CommentVO;
import lionel.meethere.paging.PageParam;
import lionel.meethere.user.dao.UserMapper;
import lionel.meethere.user.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    public void publishComment(CommentDTO commentDTO){
        commentMapper.publishCommnet(convertToComment(commentDTO));
    }

    private Comment convertToComment(CommentDTO commentDTO){
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDTO,comment);
        comment.setStatus(CommentStatus.UNAUDITED);
        return comment;
    }

    public void deleteComment(Integer commentId){
        commentMapper.deleteCommnet(commentId);
    }

    public void auditComment(Integer commentId, Integer status){
        commentMapper.updateCommentStatus(commentId,status);
    }

    public CommentVO getCommentById(Integer id){
        return convertToCommentVO(commentMapper.getCommentById(id));
    }

    private CommentVO convertToCommentVO(CommentDTO commentDTO){
        CommentVO commentVO = new CommentVO();
        BeanUtils.copyProperties( commentDTO,commentVO);
        UserVO reviewerVO = userMapper.getUserById(commentDTO.getReviewerId());
        commentVO.setReviewer(reviewerVO);
        return commentVO;
    }

    public List<CommentVO> getComments(PageParam pageParam){
        return convertToCommentVOList(commentMapper.getAuditedComments(pageParam));
    }

    private List<CommentVO> convertToCommentVOList(List<CommentDTO> commentDTOList){
        List<CommentVO> commentVOList = new ArrayList<>();
        for(CommentDTO commentDTO : commentDTOList){
            commentVOList.add(convertToCommentVO(commentDTO));
        }
        return commentVOList;
    }
}
