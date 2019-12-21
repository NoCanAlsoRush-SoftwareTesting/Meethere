package lionel.meethere.order.dao;

import lionel.meethere.order.entity.SiteBookingOrder;
import lionel.meethere.order.param.SiteBookingOrderUpdateParam;
import lionel.meethere.order.vo.SiteBookingOrderAdminVO;
import lionel.meethere.order.vo.SiteBookingOrderUserVO;
import lionel.meethere.paging.PageParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SiteBookingOrderMapperTest {
    @Autowired
    private SiteBookingOrderMapper siteBookingOrderMapper;

    private  LocalDateTime localDateTime = LocalDateTime.now();
    private  LocalDateTime localDateTime2 = LocalDateTime.now().plus(Duration.ofHours(2));
    @BeforeEach
    void setup(){
        this.siteBookingOrderMapper.insertOrder(new SiteBookingOrder(2,1,2,"场地1",new BigDecimal(20),0,localDateTime,localDateTime2));
        this.siteBookingOrderMapper.insertOrder(new SiteBookingOrder(3,1,3,"场地1",new BigDecimal(20),0,localDateTime,localDateTime2));
        this.siteBookingOrderMapper.insertOrder(new SiteBookingOrder(4,2,4,"场地1",new BigDecimal(20),0,localDateTime,localDateTime2));
        this.siteBookingOrderMapper.insertOrder(new SiteBookingOrder(5,2,5,"场地1",new BigDecimal(20),0,localDateTime,localDateTime2));
    }

    @Test
    @Transactional
    void when_insert_a_order_should_insert_success(){
        SiteBookingOrder s = new SiteBookingOrder(1,1,1,"场地1",new BigDecimal(20),0,localDateTime,localDateTime2);
        siteBookingOrderMapper.insertOrder(s);
        Assertions.assertAll(
                ()->assertEquals(1,s.getId()),
                ()->assertEquals(1,s.getUserId()),
                ()->assertEquals(1,s.getSiteId()),
                ()->assertEquals("场地1",s.getSiteName()),
                ()->assertEquals(20.00,s.getRent().floatValue()),
                ()->assertEquals(0,s.getStatus()),
                ()->assertEquals(localDateTime,s.getStartTime()),
                ()->assertEquals(localDateTime2,s.getEndTime())
        );
    }

    @Test
    void when_update_a_order_with_updateParam_should_update_the_property(){
        SiteBookingOrderUpdateParam s = new SiteBookingOrderUpdateParam(2,2,localDateTime,localDateTime2,localDateTime2.plus(Duration.ofHours(2)));
        assertEquals(1,siteBookingOrderMapper.updateOrderStatus(2,1));
        assertEquals(1,siteBookingOrderMapper.updateOrderBookTime(s));
        SiteBookingOrder sReturn = siteBookingOrderMapper.getOrderById(2);
        Assertions.assertAll(
                ()->assertEquals(localDateTime2,sReturn.getStartTime()),
                ()->assertEquals(localDateTime2.plus(Duration.ofHours(2)),sReturn.getEndTime()),
                ()->assertEquals(1,sReturn.getStatus())
        );
    }

    @Test
    void when_get_a_order_by_Id_should_return_the_order(){
        SiteBookingOrder s = siteBookingOrderMapper.getOrderById(2);
        assertNotNull(s);
        Assertions.assertAll(
                ()->assertEquals("场地1",s.getSiteName()),
                ()->assertEquals(localDateTime,s.getStartTime()),
                ()->assertEquals(localDateTime2,s.getEndTime())
        );
    }

    @Test
    void when_get_orders_by_user_should_return_orders() {
        PageParam pageParam = new PageParam(1,3);
        List<SiteBookingOrderUserVO> s = this.siteBookingOrderMapper.getOrderByUser(1,0,pageParam);
        SiteBookingOrderUserVO sReturn = s.get(0);
        Assertions.assertAll(
                ()->assertEquals(2,sReturn.getId()),
                ()->assertEquals("场地1",sReturn.getSiteName()),
                ()->assertEquals(2,sReturn.getSiteId()),
                ()->assertEquals(20.00,sReturn.getRent().floatValue()),
                ()->assertEquals(localDateTime,sReturn.getStartTime()),
                ()->assertEquals(localDateTime2,sReturn.getEndTime())
        );
    }

    @Test
    void when_get_orders_by_site_should_return_orders() {
        PageParam pageParam = new PageParam(1,3);
        List<SiteBookingOrderAdminVO> siteBookingOrderAdminVOS = this.siteBookingOrderMapper.getOrderBySite(2,0,pageParam);
        SiteBookingOrderAdminVO sReturn = siteBookingOrderAdminVOS.get(0);
        Assertions.assertAll(
                ()->assertEquals(2,sReturn.getId()),
                ()->assertEquals("场地1",sReturn.getSiteName()),
                ()->assertEquals(2,sReturn.getSiteId()),
                ()->assertEquals(20.00,sReturn.getRent().floatValue()),
                ()->assertEquals(localDateTime,sReturn.getStartTime()),
                ()->assertEquals(localDateTime2,sReturn.getEndTime())
        );
    }

    @Test
    void when_get_order_count_should_return_count(){
        assertEquals(4,siteBookingOrderMapper.getOrderCount());
    }
}