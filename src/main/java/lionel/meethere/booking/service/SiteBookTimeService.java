package lionel.meethere.booking.service;

import lionel.meethere.booking.dao.SiteBookTimeMapper;
import lionel.meethere.booking.entity.SiteBookingTime;
import lionel.meethere.order.param.SiteBookingOrderUpdateParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class SiteBookTimeService {

    //@Autowired
    @Resource
    private SiteBookTimeMapper siteBookTimeMapper;

    public boolean tryBooking(Integer siteId, LocalDateTime startTime, LocalDateTime endTime){
        if(siteBookTimeMapper.getSiteBookingTimeBetweenStimeAndEtime(siteId,startTime,endTime)!=null)
            return false;
        return true;
    }

    public void insertBokingTime(Integer siteId, LocalDateTime startTime, LocalDateTime endTime){
        siteBookTimeMapper.insertBookTime(convertToSiteBookingTime(siteId,startTime,endTime));
    }

    public SiteBookingTime convertToSiteBookingTime(Integer siteId, LocalDateTime startTime, LocalDateTime endTime){
        SiteBookingTime siteBookingTime = new SiteBookingTime();
        siteBookingTime.setSiteId(siteId);
        siteBookingTime.setStartTime(startTime);
        siteBookingTime.setEndTime(endTime);
        return siteBookingTime;
    }

    public void cancelSiteBookTime(Integer siteId, LocalDateTime startTime){
        siteBookTimeMapper.deleteBookTimeByStartTime(siteId,startTime);
    }

    public void updateSiteBookTime(SiteBookingOrderUpdateParam updateParam)
    {
        siteBookTimeMapper.updateBookTime(updateParam);
    }

}
