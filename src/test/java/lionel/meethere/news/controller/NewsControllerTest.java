package lionel.meethere.news.controller;

import com.alibaba.fastjson.JSON;
import lionel.meethere.news.param.NewsPublishParam;
import lionel.meethere.news.param.NewsUpdateParam;
import lionel.meethere.news.service.NewsService;
import lionel.meethere.news.vo.NewsVO;
import lionel.meethere.paging.PageParam;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.user.session.UserSessionInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(NewsController.class)
class NewsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @Test
    void when_an_administrator_request_to_publish_news_then_dispatch_to_service_and_return_success_result() throws Exception {

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1, "lionel", 1);
        session.setAttribute("userSessionInfo", userSessionInfo);
        NewsPublishParam publishParam = new NewsPublishParam("title", "content", "1");

        MvcResult result = mockMvc.perform(
                post("/news/publish")
                        .content(JSON.toJSONString(publishParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .session(session)
        ).andReturn();

        verify(newsService, times(1)).publishNews(1, publishParam);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    void when_no_administrator_request_to_publish_news_then_return_access_denied_result() throws Exception {

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1, "lionel", 0);
        session.setAttribute("userSessionInfo", userSessionInfo);
        NewsPublishParam publishParam = new NewsPublishParam("title", "content", "1");

        MvcResult result = mockMvc.perform(
                post("/news/publish")
                        .content(JSON.toJSONString(publishParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .session(session)
        ).andReturn();

        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.ACCESS_DENIED, res.getCode());
    }

    @Test
    void when_an_administrator_request_to_delete_news_then_dispatch_to_service_and_return_success_result() throws Exception {

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1, "lionel", 1);
        session.setAttribute("userSessionInfo", userSessionInfo);

        MvcResult result = mockMvc.perform(
                post("/news/delete")
                        .content("1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .session(session)
        ).andReturn();

        verify(newsService, times(1)).deleteNews(1);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    void when_no_administrator_request_to_delete_news_then_return_access_denied_result() throws Exception {

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1, "lionel", 0);
        session.setAttribute("userSessionInfo", userSessionInfo);

        MvcResult result = mockMvc.perform(
                post("/news/delete")
                        .content("1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .session(session)
        ).andReturn();

        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.ACCESS_DENIED, res.getCode());
    }

    @Test
    void when_an_administrator_request_to_update_news_then_dispatch_to_service_and_return_success_result() throws Exception {

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1, "lionel", 1);
        session.setAttribute("userSessionInfo", userSessionInfo);
        NewsUpdateParam updateParam = new NewsUpdateParam(1, "update title", "update content", "1");

        MvcResult result = mockMvc.perform(
                post("/news/update")
                        .content(JSON.toJSONString(updateParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .session(session)
        ).andReturn();

        verify(newsService, times(1)).updateNews(updateParam);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    void when_no_administrator_request_to_update_news_then_return_access_denied_result() throws Exception {

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1, "lionel", 0);
        session.setAttribute("userSessionInfo", userSessionInfo);
        NewsUpdateParam updateParam = new NewsUpdateParam(1, "update title", "update content", "1");

        MvcResult result = mockMvc.perform(
                post("/news/update")
                        .content(JSON.toJSONString(updateParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .session(session)
        ).andReturn();

        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.ACCESS_DENIED, res.getCode());
    }

    @Test
    void when_request_to_get_news_by_id_then_dispatch_to_service_and_return_success_result() throws Exception {

        NewsVO newsVO = new NewsVO();
        newsVO.setId(1);
        when(newsService.getNews(1)).thenReturn(newsVO);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("newsId", 1);
        MvcResult result = mockMvc.perform(
                post("/news/get")
                        .param("id","1")
        ).andReturn();
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    void when_request_to_get_news_catalog_then_dispatch_to_service_and_return_success_result() throws Exception {
        PageParam pageParam = new PageParam(1, 1);
        MvcResult result = mockMvc.perform(
                post("/news/getcatalog")
                .param("pageNum","1")
                .param("pageSize","1")
                        /*.content(JSON.toJSONString(pageParam))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)*/
        ).andReturn();

        verify(newsService).getNewsCatalogList(new PageParam(1, 1));
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }
}