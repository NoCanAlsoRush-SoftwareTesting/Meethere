package lionel.meethere.booking.dao;

import lionel.meethere.booking.entity.SiteBookingTime;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;

@Mapper
public interface SiteBookTimeMapper {

    @Insert("insert into site_booked_time(id,site_id,start_time,end_time) values (#{id},#{siteId},#{startTime},#{endTime});")
    int insertBookTime(SiteBookingTime bookingTime);

    @Select("select * from site_booked_time where site_id=#{siteId} and start_time between #{startTime} and #{endTime} or site_id=#{siteId} and end_time between #{startTime} and #{endTime};")
    SiteBookingTime getSiteBookingTimeBetweenStimeAndEtime(@Param("siteId") Integer siteId,
                                                  @Param("startTime")LocalDateTime startTime,
                                                  @Param("endTime") LocalDateTime endTime);

    @Delete("delete from site_booked_time where site_id=#{siteId} and start_time=#{startTime};")
    int deleteBookTimeByStartTime(@Param("siteId")Integer siteId,
                                  @Param("startTime")LocalDateTime startTime);
}
