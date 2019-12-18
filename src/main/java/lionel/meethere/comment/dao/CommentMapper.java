package lionel.meethere.comment.dao;

import lionel.meethere.comment.dto.CommentDTO;
import lionel.meethere.comment.entity.Comment;
import lionel.meethere.paging.PageParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Insert("insert into comment(id,user_id,site_id,content,status,creat_time valus(#{id},#{userId},#{siteId},#{content},#{status},#{create_time};")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int publishCommnet(Comment comment);

    @Delete("delete from comment where id=#{id};")
    int deleteCommnet(Integer id);

    @Update("update comment set status=#{status} where id=#{id};")
    int updateCommentStatus(@Param("id") Integer id,
                            @Param("status") Integer status);

    @Select("select * from comment where id=#{id);")
    CommentDTO getCommentById(Integer id);

    @Select("select * from comment where status=1 limit $(pageSize*(pageNum-1),#{pageSize};")
    List<CommentDTO> getAuditedComments(PageParam pageParam);

    @Select("select count(*) from comment")
    int getCommentCount();

}
