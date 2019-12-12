package lionel.meethere.booking.service;

import lionel.meethere.booking.dao.SiteBookTimeMapper;
import lionel.meethere.booking.entity.SiteBookingTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SiteBookService {

    @Autowired
    private SiteBookTimeMapper siteBookTimeMapper;

    @Transactional
    public boolean tryBooking(Integer siteId, LocalDateTime startTime, LocalDateTime endTime){
        if(siteBookTimeMapper.getSiteBookingTimeBetweenStimeAndEtime(siteId,startTime,endTime)!=null)
            return false;
        siteBookTimeMapper.insertBookTime(convertToSiteBookingTime(siteId,startTime,endTime));
        return true;
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
}
