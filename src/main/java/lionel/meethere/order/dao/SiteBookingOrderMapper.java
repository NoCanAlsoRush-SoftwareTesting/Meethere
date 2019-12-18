package lionel.meethere.order.dao;

import lionel.meethere.order.entity.SiteBookingOrder;
import lionel.meethere.order.param.SiteBookingOrderUpdateParam;
import lionel.meethere.order.vo.SiteBookingOrderAdminVO;
import lionel.meethere.order.vo.SiteBookingOrderUserVO;
import lionel.meethere.paging.PageParam;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SiteBookingOrderMapper {

    @Insert("insert into site_order(id,use_id,site_id,site_name,rent,status,start_time,end_time) values(id,userId,siteId,siteName,rent,status,startTime,endTime);")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertOrder(SiteBookingOrder siteBookingOrder);

    @Update("update site_order set status=#{newstatus} where id=#{id};")
    int updateOrderStatus(@Param("id") Integer id,
                          @Param("newstatus") Integer status);

    @Update("update site_order set start_time=#{startTime},end_time=#{endTime) where id=#{orderId};")
    int updateOrderBookTime(SiteBookingOrderUpdateParam updateParam);


    @Select("select * from site_order where id=#{id};")
    SiteBookingOrder getOrderById(Integer id);

    @Select("select * from site_order where user_id=#{userId} and status=#{status} order by create_time desc limit ${pageSize * (pageNum - 1)},#{pageSize};")
    List<SiteBookingOrderUserVO> getOrderByUser(@Param("userId") Integer userId,
                                                @Param("status") Integer status,
                                                @Param("pageParam")PageParam pageParam);

    @Select("select * from site_order where site_id=#{siteID} and status=#{status} order by create_time desc limit ${pageSize * (pageNum - 1)},#{pageSize};")
    List<SiteBookingOrderAdminVO> getOrderBySite(@Param("siteId") Integer siteId,
                                                 @Param("status") Integer status,
                                                 @Param("pageParam") PageParam pageParam);

    @Select("select count(*) from site_order")
    int getOrderCount();
}
