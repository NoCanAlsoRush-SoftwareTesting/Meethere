package lionel.meethere.site.dao;

import lionel.meethere.paging.PageParam;
import lionel.meethere.site.entity.Site;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SiteMapper {

    @Insert("insert into site(id,name,stadium_id,location,description,rent,image) values(#{id},#{name},#{stadiumId},#{location},#{description},#{rent},#{image});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertSite(Site site);

    @Delete("delete from site where id=#{id};")
    int deleteSite(Integer id);

    @Update("update site set name=#{name},stadium_id=#{stadiumId},location=#{location},description=#{description},rent=#{rent},image=#{image} where id=#{id};")
    int updateSite(Site site);

    @Select("select * from site where id=#{id};")
    Site getSite(Integer id);

    @Select("select id from site order by id limit ${pageSize * (pageNum - 1)},#{pageSize};")
    List<Integer> listSiteIds(PageParam pageParam);

    @Select("select * from site order by id limit ${pageSize * (pageNum - 1)},#{pageSize}; ")
    List<Site> listSites(PageParam pageParam);

    @Select("select * from site where stadium_id=#{stadiumId} order by id limit ${pageSize * (pageNum - 1)},#{pageSize}; ")
    List<Site> listSitesByStadium(Integer stadiumId, Integer pageNum, Integer pageSize);

    @Select("select count(*) from site")
    int getSiteCount();

    @Select("select count(*) from site where stadium_id=#{stadiumId}")
    int getSiteCountByStadium(Integer stadiumId);
}
