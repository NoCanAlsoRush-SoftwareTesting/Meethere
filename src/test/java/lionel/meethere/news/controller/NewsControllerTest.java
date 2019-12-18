package lionel.meethere.news.controller;

import com.alibaba.fastjson.JSON;
import lionel.meethere.news.service.NewsService;
import lionel.meethere.paging.PageParam;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(NewsController.class)
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;
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
    void when_request_to_get_news_by_id_should_dispatcher_to_service_and_return_success_result() throws Exception {
        MvcResult result = mockMvc.perform(
                get("/news/get")
                .param("newsId","1")
        ).andReturn();

        verify(newsService).getNews(1);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(),Result.class);
        assertEquals(CommonResult.SUCCESS,res.getCode());
    }

    @Test
    void when_request_to_get_newscatalog_should_dispatcher_to_service_and_return_success_result() throws  Exception{
        MvcResult result = mockMvc.perform(
                get("/news/getcatalog")
                .param("pageNum","1")
                .param("pageSize","1")
        ).andReturn();

        verify(newsService).getNewsCatalogList(new PageParam(1,1));
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(),Result.class);
        assertEquals(CommonResult.SUCCESS,res.getCode());
    }
}