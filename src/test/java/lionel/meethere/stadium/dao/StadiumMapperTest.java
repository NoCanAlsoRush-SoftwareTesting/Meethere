package lionel.meethere.stadium.dao;

import org.junit.jupiter.api.BeforeEach;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StadiumMapperTest {
    @Autowired
    private StadiumMapper stadiumMapper;

    @BeforeEach
    void setup() {
//        this.stadiumMapper.createStadium(new Stadium(2,"OOAD体育馆","中山北路",null));
//        this.stadiumMapper.createStadium(new Stadium(3,"OS运动馆","东川路",null));
    }

//    @Test
//    @Transactional
//    void when_insert_a_stadium_should_insert_success() {
//        Stadium stadium = new Stadium(1,"Software Testing健身房","中山北路",null);
//        this.stadiumMapper.createStadium(stadium);
//        Stadium sReturn = stadiumMapper.getStadium(1);
//        Assertions.assertAll(
//                () -> assertEquals(1, sReturn.getId()),
//                () -> assertEquals("Software Testing健身房", sReturn.getName()),
//                () -> assertEquals("中山北路", sReturn.getLocation()),
//                () -> assertEquals(null, sReturn.getImage())
//        );
//    }
//
//    @Test
//    void when_delete_a_stadium_by_Id_should_delete_success() {
//        assertNotNull(this.stadiumMapper.getStadium(2));
//        assertEquals(1, this.stadiumMapper.deleteStadium(2));
//        assertNull(this.stadiumMapper.getStadium(2));
//    }

//    @Test
//    void when_get_stadium_by_valid_Id_should_return_a_stadium() {
//        assertNotNull(this.stadiumMapper.getStadium(2));
//    }
//
//    @Test
//    void when_get_stadium_by_invalid_Id_should_return_a_stadium() {
//        assertNull(this.stadiumMapper.getStadium(10));
//    }
//
//    @Test
//    void when_get_stadium_count_should_return_count(){
//
//    }
}