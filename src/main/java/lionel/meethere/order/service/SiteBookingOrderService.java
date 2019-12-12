package lionel.meethere.order.service;

import lionel.meethere.booking.service.SiteBookService;
import lionel.meethere.order.dao.SiteBookingOrderMapper;
import lionel.meethere.order.entity.SiteBookingOrder;
import lionel.meethere.order.exception.BookingTimeConflictException;
import lionel.meethere.order.exception.WrongOrderStatusException;
import lionel.meethere.order.param.SiteBookingOrderCreateParam;
import lionel.meethere.order.status.AuditStatus;
import lionel.meethere.order.status.OrderStatus;
import lionel.meethere.order.vo.SiteBookingOrderAdminVO;
import lionel.meethere.order.vo.SiteBookingOrderUserVO;
import lionel.meethere.paging.PageParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteBookingOrderService {

    @Autowired
    private SiteBookingOrderMapper siteBookingOrderMapper;

    @Autowired
    private SiteBookService siteBookService;


    public void crateSiteBookingOrder(Integer userId, SiteBookingOrderCreateParam createParam){
        if(!siteBookService.tryBooking(createParam.getSiteId(),createParam.getStartTime(),createParam.getEndTime())){
            throw new BookingTimeConflictException();
        }
        siteBookingOrderMapper.insertOrder(convertToSiteBookingOrder(userId,createParam));
    }

    private SiteBookingOrder convertToSiteBookingOrder(Integer userId,SiteBookingOrderCreateParam createParam){
        SiteBookingOrder siteBookingOrder = new SiteBookingOrder();
        BeanUtils.copyProperties(createParam,siteBookingOrder);
        siteBookingOrder.setUserId(userId);
        siteBookingOrder.setStatus(OrderStatus.UNAUDITED);

        return siteBookingOrder;
    }

    public void auditOrder(Integer orderId, Integer auditStatus){
        SiteBookingOrder order = siteBookingOrderMapper.getOrderById(orderId);

        if(order.getStatus() != OrderStatus.UNAUDITED){
            throw new WrongOrderStatusException();
        }

        if(auditStatus == AuditStatus.SUCCESS){
            siteBookingOrderMapper.updateOrderStatus(orderId,OrderStatus.AUDITED);
        }
        else {
            siteBookingOrderMapper.updateOrderStatus(orderId,OrderStatus.AUDITED_FAILED);
        }

    }

    public void cancelOrderByUser(Integer userId, Integer orderId){
        SiteBookingOrder order = siteBookingOrderMapper.getOrderById(orderId);

        if(userId != order.getUserId())
            return;

        if(order.getStatus()!=OrderStatus.CANCEL){
            siteBookingOrderMapper.updateOrderStatus(orderId,OrderStatus.CANCEL);
            siteBookService.cancelSiteBookTime(order.getSiteId(),order.getStartTime());
        }

    }

    public void cancelOrderByAdmin(Integer orderId){
        SiteBookingOrder order = siteBookingOrderMapper.getOrderById(orderId);

        if(order.getStatus()!=OrderStatus.CANCEL){
            siteBookingOrderMapper.updateOrderStatus(orderId,OrderStatus.CANCEL);
            siteBookService.cancelSiteBookTime(order.getSiteId(),order.getStartTime());
        }
    }

    public SiteBookingOrderAdminVO getOrderById(Integer id){
        return convertToSiteBookingOrderAdminVO(siteBookingOrderMapper.getOrderById(id));
    }

    public List<SiteBookingOrderUserVO> getOrderByUser(Integer userId, Integer status, PageParam pageParam){
        return siteBookingOrderMapper.getOrderByUser(userId,status,pageParam);
    }

    public List<SiteBookingOrderAdminVO> getOrderBySite(Integer userId, Integer status, PageParam pageParam){
        return siteBookingOrderMapper.getOrderBySite(userId,status,pageParam);
    }


    private SiteBookingOrderAdminVO convertToSiteBookingOrderAdminVO(SiteBookingOrder siteBookingOrder){
        SiteBookingOrderAdminVO orderAdminVO = new SiteBookingOrderAdminVO();
        BeanUtils.copyProperties(siteBookingOrder,orderAdminVO);
        return orderAdminVO;
    }


}
