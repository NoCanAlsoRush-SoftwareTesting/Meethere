package lionel.meethere.user.controller;

import lionel.meethere.user.entity.User;
import lionel.meethere.user.param.LoginParam;
import lionel.meethere.user.param.RegisterParam;
import lionel.meethere.user.service.UserService;
import lionel.meethere.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService service;

    @GetMapping("/insertuser/{username}/{password}")
    public int insertUser(@PathVariable(value = "username", required = true) String username,
                          @PathVariable(value = "password", required = true) String password) {

        RegisterParam registerParam = new RegisterParam(username,password);

        return service.register(registerParam);
    }

    @GetMapping("/updateUsername/{username}")
    public int updateUsername(@PathVariable String username, @SessionAttribute User user) {
        if (user != null) {
            return service.updateUsername(user.getId(), username);
        }
        return 0;
    }

    @GetMapping("/getuser/{username}")
    public UserVO getuser(@PathVariable(value = "username",required = true) String username) {
        return service.getUserByUsername(username);
    }

}
