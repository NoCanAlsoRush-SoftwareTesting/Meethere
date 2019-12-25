package lionel.meethere.stadium.controller;

import com.alibaba.fastjson.JSON;
import lionel.meethere.paging.PageParam;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.stadium.service.StadiumService;
import lionel.meethere.user.session.UserSessionInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
@WebMvcTest(StadiumController.class)
class StadiumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StadiumService stadiumService;

    @Test
    void when_list_stadium_then_dispatcher_to_service_and_return_success_result() throws Exception {
        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1,"lyb",1);
        session.setAttribute("userSessionInfo", userSessionInfo);

        MvcResult result = mockMvc.perform(
                post("/stadium/list")
                        .param("pageNum", "1")
                        .param("pageSize","10")
                        .session(session)
        ).andReturn();

        verify(stadiumService,times(1)).getStadiums(new PageParam(1,10));
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    void when_get_stadium_by_id_then_dispatcher_to_service_and_return_success_result() throws Exception {
        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1,"lyb",1);
        session.setAttribute("userSessionInfo", userSessionInfo);

        MvcResult result = mockMvc.perform(
                post("/stadium/get")
                        .param("id", "1")
                        .session(session)
        ).andReturn();

        verify(stadiumService,times(1)).getStadiumById(1);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    void createStadium() {
    }

    @Test
    void deleteStadium() {
    }

    @Test
    void updateStadium() {
    }
}