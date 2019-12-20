package lionel.meethere.order.service;

import lionel.meethere.booking.service.SiteBookTimeService;
import lionel.meethere.order.dao.SiteBookingOrderMapper;
import lionel.meethere.order.entity.SiteBookingOrder;
import lionel.meethere.order.exception.BookingTimeConflictException;
import lionel.meethere.order.param.SiteBookingOrderCreateParam;
import lionel.meethere.order.status.OrderStatus;
import lionel.meethere.site.entity.Site;
import lionel.meethere.site.service.SiteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SiteBookingOrderServiceTest {

    @Mock
    private SiteBookingOrderMapper orderMapper;

    @Mock
    private SiteBookTimeService bookTimeService;

    @InjectMocks
    private SiteBookingOrderService orderService;

    @Test
    void when_do_crateSiteBookingOrder_and_not_time_conflict_then_dispatch_to_mapper_create_order() throws Exception{
        LocalDateTime localDateTime = LocalDateTime.now();
        SiteBookingOrderCreateParam createParam = new SiteBookingOrderCreateParam(1,"site1",new BigDecimal(20),localDateTime, localDateTime);
        SiteBookingOrder order = new SiteBookingOrder(null,1,1,"site1",new BigDecimal(20), OrderStatus.UNAUDITED,localDateTime, localDateTime);
        when(bookTimeService.tryBooking(createParam.getSiteId(),createParam.getStartTime(),createParam.getEndTime())).thenReturn(true);

        orderService.crateSiteBookingOrder(1,createParam);
        verify(bookTimeService).tryBooking(createParam.getSiteId(),createParam.getStartTime(),createParam.getEndTime());
        verify(orderMapper).insertOrder(order);
    }

    @Test
    void when_do_crateSiteBookingOrder_and_have_time_conflict_then_throw_BookingTimeConflictException() throws Exception{
        LocalDateTime localDateTime = LocalDateTime.now();
        SiteBookingOrderCreateParam createParam = new SiteBookingOrderCreateParam(1,"site1",new BigDecimal(20),localDateTime, localDateTime);
        when(bookTimeService.tryBooking(createParam.getSiteId(),createParam.getStartTime(),createParam.getEndTime())).thenReturn(false);

        assertThrows(BookingTimeConflictException.class,()->orderService.crateSiteBookingOrder(1,createParam));
    }

    @Test
    void auditOrder() {
    }

    @Test
    void cancelOrderByUser() {
    }

    @Test
    void cancelOrderByAdmin() {
    }

    @Test
    void updateOrderBookTime() {
    }

    @Test
    void getOrderById() {
    }

    @Test
    void getOrderByUser() {
    }

    @Test
    void getOrderBySite() {
    }

    @Test
    void getOrderCount() {
    }
}