package lionel.meethere.site.dao;

import lionel.meethere.paging.PageParam;
import lionel.meethere.site.entity.Site;
import lionel.meethere.site.param.SiteUpdateParam;
import org.apache.ibatis.annotations.*;
import sun.jvm.hotspot.debugger.Page;

import javax.persistence.GeneratedValue;
import java.util.List;

@Mapper
public interface SiteMapper {

    @Insert("insert into site(id,name,location,description,rent,image) values(#{id},#{name},#{location},#{description},#{rent},#{image};")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertSite(Site site);

    @Delete("delete from site where id=#{id};")
    int deleteSite(Integer id);

    @Update("update site set name=#{name},location=#{location},description=#{description},rent=#{rent},image=#{image} where id=#{id};")
    int updateSite(Site site);

    @Select("select * from site where id=#{id};")
    Site getSite(Integer id);

    @Select("select id from site order by id limit ${pageSize * (pageNum - 1)},#{pageSize};")
    List<Integer> listSiteIds(PageParam pageParam);

    @Select("select * from site order by id limit ${pageSize * (pageNum - 1)},#{pageSize}; ")
    List<Site> listSites(PageParam pageParam);



}
