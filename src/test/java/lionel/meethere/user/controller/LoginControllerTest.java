package lionel.meethere.user.controller;

import lionel.meethere.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    void login() throws Exception{

    }

    @Test
    void register() {
    }

    @Test
    void logout() {
    }
}