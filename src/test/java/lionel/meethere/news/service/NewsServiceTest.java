package lionel.meethere.news.service;

import lionel.meethere.news.dao.NewsMapper;
import lionel.meethere.news.dto.NewsCatalogDTO;
import lionel.meethere.news.entity.News;
import lionel.meethere.news.param.NewsPublishParam;
import lionel.meethere.news.param.NewsUpdateParam;
import lionel.meethere.news.vo.NewsCatalogVO;
import lionel.meethere.paging.PageParam;
import lionel.meethere.user.dao.UserMapper;
import lionel.meethere.user.vo.UserVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class NewsServiceTest {

    @InjectMocks
    private NewsService newsService;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private UserMapper userMapper;


    @Test
    void when_service_publish_news_with_publishParam_should_dispatcher_to_mapper_to_insert_news() {

        NewsPublishParam publishParam = new NewsPublishParam("title","content","image");
        News news = new News();
        news.setAdminId(1);
        news.setTitle("title");
        news.setContent("content");
        news.setImage("image");

        newsService.publishNews(1,publishParam);
        verify(newsMapper,times(1)).insertNews(news);

    }

    @Test
    void when_service_delete_news_by_id_1_should_mapper_delete_news_by_id_1() {
        newsService.deleteNews(1);
        verify(newsMapper,times(1)).deleteNews(1);
    }

    @Test
    void when_service_update_news_with_updateParam_should_dispatcher_to_mapper_to_update_news() {
        NewsUpdateParam updateParam = new NewsUpdateParam(1,"title","content","image");
        newsService.updateNews(updateParam);
        verify(newsMapper, times(1)).updateNews(updateParam);
    }

    @Test
    void getNewsCatalogList(){
        PageParam pageParam = new PageParam(1,2);
        String createTime =LocalDateTime.now().toString();
        List<NewsCatalogDTO> newsCatalogDTOS = new ArrayList<>();
        newsCatalogDTOS.add(new NewsCatalogDTO(1,1,"title",createTime));
        newsCatalogDTOS.add(new NewsCatalogDTO(2,2,"title2",createTime));


        when(newsMapper.getNewsCatalogList(pageParam)).thenReturn(newsCatalogDTOS);
        when(userMapper.getUserById(1)).thenReturn(new UserVO(1,"admin1"));
        when(userMapper.getUserById(2)).thenReturn(new UserVO(2,"admin2"));

        List<NewsCatalogVO> newsCatalogVOS = newsService.getNewsCatalogList(pageParam);
        verify(newsMapper,times(1)).getNewsCatalogList(pageParam);
        verify(userMapper,times(1)).getUserById(1);
        verify(userMapper,times(1)).getUserById(2);

        NewsCatalogVO newsCatalogVO1 = newsCatalogVOS.get(0);
        Assertions.assertAll(
                ()-> assertEquals(1,newsCatalogVO1.getId()),
                ()-> assertEquals(1,newsCatalogVO1.getAdmin().getId()),
                ()-> assertEquals("admin1",newsCatalogVO1.getAdmin().getUsername()),
                ()-> assertEquals("title",newsCatalogVO1.getTitle()),
                ()-> assertEquals(createTime,newsCatalogVO1.getCreateTime())
                );
    }

    @Test
    void getNews() {


    }
}