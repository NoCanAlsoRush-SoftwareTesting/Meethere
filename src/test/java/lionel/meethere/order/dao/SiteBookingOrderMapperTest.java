package lionel.meethere.order.dao;

import lionel.meethere.order.entity.SiteBookingOrder;
import org.junit.jupiter.api.BeforeEach;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SiteBookingOrderMapperTest {
    @Autowired
    private SiteBookingOrderMapper siteBookingOrderMapper;
    @BeforeEach
    void setup(){
        this.siteBookingOrderMapper.insertOrder(new SiteBookingOrder(2,1,2,"场地1",new BigDecimal(20),0, LocalDateTime.now(),LocalDateTime.now().plus(Duration.ofHours(2))));
        this.siteBookingOrderMapper.insertOrder(new SiteBookingOrder(3,1,3,"场地1",new BigDecimal(20),0, LocalDateTime.now(),LocalDateTime.now().plus(Duration.ofHours(2))));
        this.siteBookingOrderMapper.insertOrder(new SiteBookingOrder(4,2,4,"场地1",new BigDecimal(20),0, LocalDateTime.now(),LocalDateTime.now().plus(Duration.ofHours(2))));
        this.siteBookingOrderMapper.insertOrder(new SiteBookingOrder(5,2,5,"场地1",new BigDecimal(20),0, LocalDateTime.now(),LocalDateTime.now().plus(Duration.ofHours(2))));
    }
}