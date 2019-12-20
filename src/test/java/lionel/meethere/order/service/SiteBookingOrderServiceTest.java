package lionel.meethere.order.service;

import lionel.meethere.order.dao.SiteBookingOrderMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SiteBookingOrderServiceTest {

    @Mock
    private SiteBookingOrderMapper orderMapper;

    @InjectMocks
    private SiteBookingOrderService orderService;

    @Test
    void when_do_crateSiteBookingOrder_and_not() throws Exception{



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