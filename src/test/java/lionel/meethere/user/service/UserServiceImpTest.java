package lionel.meethere.user.service;

import lionel.meethere.user.dao.UserMapper;
import lionel.meethere.user.entity.User;
import lionel.meethere.user.param.LoginParam;
import lionel.meethere.user.vo.UserVO;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceImpTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImp userService;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void login() {
/*
        User user = new User(1,"lyb","18982170688","123456789",1);
        userMapper.insertUser(user);
        User result = userService.login(loginParam);
        assertEquals(user,result);*/
        UserVO u = Mockito.mock(UserVO.class);
        u.setUsername("lyb");
        u.setId(1);
        LoginParam loginParam = new LoginParam("lyb","123456789","18982170688");

        Mockito.when((userMapper.getUserById(1))).thenReturn(u);
        User returnU = userService.login(loginParam);
        Mockito.verify(userMapper).getUserById(1);
        Assertions.assertEquals(u.getUsername(),returnU.getUsername());
    }

    @Test
    void getUserByUsername() {
    }

    @Test
    void register() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void updateUsername() {
    }

    @Test
    void getUserListOrderl() {
    }

    @Test
    void deleteUserById() {
    }
}