package lionel.meethere.news.dao;

import lionel.meethere.news.dto.NewsCatalogDTO;
import lionel.meethere.news.dto.NewsDTO;
import lionel.meethere.news.entity.News;
import lionel.meethere.news.param.NewsUpdateParam;
import lionel.meethere.news.vo.NewsVO;
import lionel.meethere.paging.PageParam;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NewsMapper {

    @Insert("insert into news(id,writer_id,title,content,image,create_time,modified_time) values(#{id},#{adminId},#{title},#{content},#{image},#{createTime},#{modifiedTime};")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertNews(News news);

    @Delete("delete from news where id=#{id}")
    int deleteNews(Integer id);

    @Update("update news set title=#{title},content=#{content},image=#{image},modified_time=current_timestamp;")
    int updateNews(NewsUpdateParam newsUpdateParam);

    @Select("select from news where id=#{id};")
    NewsDTO getNewsById(Integer id);

    @Select("select from news order by time_create desc limit ${pageSize * (pageNum - 1)},#{pageSize}")
    NewsCatalogDTO getNewsCatalogList(PageParam pageParam);
}
