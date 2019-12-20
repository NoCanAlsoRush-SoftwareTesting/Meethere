package lionel.meethere.site.controller;

import com.alibaba.fastjson.JSON;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.site.service.SiteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(SiteController.class)
class SiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SiteService siteService;

    @Test
    void when_request_to_get_a_site_then_dispatch_to_service_and_return_suceccess() throws Exception {
/*        MvcResult result = mockMvc.perform(
                get("/site/get")
                        .param("SiteId","1")
        ).andReturn();

        verify(siteService).getSiteById(1);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());*/
    }

    @Test
    void getSiteList() {
    }

    @Test
    void getSiteListByStadium() {
    }

    @Test
    void createSite() {
    }

    @Test
    void deleteSite() {
    }

    @Test
    void updateSite() {
    }
}