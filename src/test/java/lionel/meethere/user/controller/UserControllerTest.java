package lionel.meethere.user.controller;

import com.alibaba.fastjson.JSON;
import lionel.meethere.paging.PageParam;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.user.service.UserService;
import lionel.meethere.user.session.UserSessionInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Value("${user-session-info.name}")
    private String userSessionInfoName;


    @Test
    void when_an_administrator_request_to_list_user_should_dispatcher_to_service_and_return_success_result() throws Exception {

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo();
        userSessionInfo.setAdmin(1);
        userSessionInfo.setId(1);
        session.setAttribute("userSessionInfo", userSessionInfo);

        MvcResult result = mockMvc.perform(
                get("/user/list")
                        .param("pageNum", "1")
                        .param("pageSize", "1")
                        .session(session)
        ).andReturn();

        verify(userService).getUserList(new PageParam(1,1));
        verify(userService).getCountOfUser();

        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
            assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    void when_no_administrator_request_to_list_user_should_dispatcher_to_service_and_return_denied_result() throws Exception {

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo();
        userSessionInfo.setAdmin(0);
        userSessionInfo.setId(1);
        session.setAttribute("userSessionInfo", userSessionInfo);

        MvcResult result = mockMvc.perform(
                get("/user/list")
                        .param("pageNum", "1")
                        .param("pageSize", "1")
                        .session(session)
        ).andReturn();

        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.ACCESS_DENIED, res.getCode());
    }

    @Test
    void when_request_to_get_user_should_dispatcher_service_to_return_success_result() throws Exception{

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1,"lyb",0);
        session.setAttribute("userSessionInfo", userSessionInfo);

        MvcResult result = mockMvc.perform(
                get("/user/get")
                        .param("id", "1")
                        .session(session)
        ).andReturn();

        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    void when_an_administrator_request_to_delete_user_should_dispatch_service_to_return_success_result() throws Exception{

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1,"lyb",1);
        session.setAttribute("userSessionInfo", userSessionInfo);

        MvcResult result = mockMvc.perform(
                post("/delete")
                        .param("userId", "2")
                        .session(session)
        ).andReturn();

        verify(userService,times(1)).deleteUserById(2);
        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.SUCCESS, res.getCode());
    }

    @Test
    void  when__administrator_request_to_delete_user_should_dispatch_service_to_return_success_result() throws Exception {

        MockHttpSession session = new MockHttpSession();
        UserSessionInfo userSessionInfo = new UserSessionInfo(1,"lyb",0);
        session.setAttribute("userSessionInfo", userSessionInfo);

        MvcResult result = mockMvc.perform(
                post("/delete")
                        .param("userId", "2")
                        .session(session)
        ).andReturn();

        Result<Object> res = JSON.parseObject(result.getResponse().getContentAsString(), Result.class);
        assertEquals(CommonResult.ACCESS_DENIED, res.getCode());
    }
}