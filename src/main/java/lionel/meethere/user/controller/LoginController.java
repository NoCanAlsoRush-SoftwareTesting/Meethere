package lionel.meethere.user.controller;

import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.user.session.UserSessionInfo;
import lionel.meethere.user.entity.User;
import lionel.meethere.user.exception.IncorrectUsernameOrPasswordException;
import lionel.meethere.user.exception.UsernameAlreadyExistException;
import lionel.meethere.user.exception.UsernameNotExistsException;
import lionel.meethere.user.param.LoginParam;
import lionel.meethere.user.param.RegisterParam;
import lionel.meethere.result.UserResult;
import lionel.meethere.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    //@GetMapping("/login/{username}/{password}")
    //public Result<?> login(@ModelAttribute LoginParam loginParam,HttpSession httpSession){
    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginParam loginParam,
                           HttpSession httpSession){
        //参数校验

        if(loginParam.getUsername().length() < 2 || loginParam.getUsername().length() > 20
                ||loginParam.getPassword().length() < 2 ||loginParam.getPassword().length() > 20){
            return UserResult.invalidUsernameOrPassword();
        }


        UserSessionInfo userSessionInfo = new UserSessionInfo();

        //请求转发，会话管理
        try{
            User user = userService.login(loginParam);
            BeanUtils.copyProperties(user,userSessionInfo);
            httpSession.setAttribute("userSessionInfo",userSessionInfo);
        }catch (UsernameNotExistsException e){
            return UserResult.usernameNotExists();
        }
        catch (IncorrectUsernameOrPasswordException e){
            return UserResult.incorrectUsernameOrPassword();
        }

        return CommonResult.success().data(userSessionInfo);
    }

    //@GetMapping("/register/{username}/{password}")

    //public Result<?> register(@ModelAttribute RegisterParam registerParam){
    @PostMapping("/register")
    public Result<?> register(@RequestBody RegisterParam registerParam){
        //参数校验
        if(registerParam.getUsername().length() < 2 || registerParam.getUsername().length() > 20
                || registerParam.getPassword().length() < 2 || registerParam.getPassword().length() > 20){
            return UserResult.invalidUsernameOrPassword();
        }

         try{
             userService.register(registerParam);
         }catch (UsernameAlreadyExistException e){
             return UserResult.usernameAlreadyExists();
         }
         return CommonResult.success();
    }


    @PostMapping("/logout")
    public Result<?> logout(HttpSession session){
        session.invalidate();
        return CommonResult.success();
    }

}
