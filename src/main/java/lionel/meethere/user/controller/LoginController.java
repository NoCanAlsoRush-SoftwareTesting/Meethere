package lionel.meethere.user.controller;

import lionel.meethere.common.result.CommonResult;
import lionel.meethere.common.result.Result;
import lionel.meethere.session.UserSessionInfo;
import lionel.meethere.user.entity.User;
import lionel.meethere.user.exception.IncorrectUsernameOrPasswordException;
import lionel.meethere.user.exception.UsernameAlreadyExistException;
import lionel.meethere.user.exception.UsernameNotExistsException;
import lionel.meethere.user.param.LoginParam;
import lionel.meethere.user.param.RegisterParam;
import lionel.meethere.user.result.UserResult;
import lionel.meethere.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login/{username}/{password}")
    public Result<?> login(@PathVariable(value = "username", required = true) String username,
                        @PathVariable(value = "password", required = true) String password,
                        HttpSession httpSession){

        //参数校验
        if(username.length() < 2 || username.length() > 20
                || password.length() < 2 || password.length() > 20){
            return UserResult.incorrectUsernameOrPassword();
        }
        LoginParam loginParam = new LoginParam(username,password);

        UserSessionInfo userSessionInfo = new UserSessionInfo();

        //请求转发，会话管理
        try{
            User user = userService.login(loginParam);
            BeanUtils.copyProperties(user,userSessionInfo);
            httpSession.setAttribute("user",user);
        }catch (UsernameNotExistsException e){
            return UserResult.usernameNotExists();
        }
        catch (IncorrectUsernameOrPasswordException e){
            return UserResult.incorrectUsernameOrPassword();
        }

        return CommonResult.success().data(userSessionInfo);
    }

    @GetMapping("/register/{username}/{password}")
    public Result<?> register(@PathVariable(value = "username", required = true) String username,
                           @PathVariable(value = "password", required = true) String password){
        //参数校验
        if(username.length() < 2 || username.length() > 20
                || password.length() < 2 || password.length() > 20){
            return UserResult.incorrectUsernameOrPassword();
        }

        RegisterParam registerParam = new RegisterParam(username,password);

         try{
             userService.register(registerParam);
         }catch (UsernameAlreadyExistException e){
             return UserResult.usernameAlreadyExists();
         }
         return CommonResult.success();
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "logout successfully";
    }


 /*   @GetMapping("/login/{username}/{password}")
    public String login(@PathVariable(value = "username", required = true) String username,
                        @PathVariable(value = "password", required = true) String password,
                        HttpSession httpSession){

        //参数校验
        if(username.length() < 2 || username.length() > 20
                || password.length() < 2 || password.length() > 20){
            return "Login failed";
        }
        LoginParam loginParam = new LoginParam(username,password);

        //请求转发，会话管理
        try{
            httpSession.setAttribute("user",userService.login(loginParam));
        }catch (Exception e){
            return "login failed";
        }
        return "login successfully";
    }*/
}
