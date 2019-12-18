package lionel.meethere.news.service;

import lionel.meethere.news.dao.NewsMapper;
import lionel.meethere.user.dao.UserMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class NewsServiceTest {

    @InjectMocks
    private NewsService newsService;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private UserMapper userMapper;


    @Test
    void publishNews() {

    }

    @Test
    void deleteNews() {
    }

    @Test
    void updateNews() {
    }

    @Test
    void getNewsCatalogList() {
    }

    @Test
    void getNews() {
    }
}