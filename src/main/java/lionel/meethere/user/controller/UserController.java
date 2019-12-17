package lionel.meethere.user.controller;

import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.user.entity.User;
import lionel.meethere.user.param.LoginParam;
import lionel.meethere.user.param.RegisterParam;
import lionel.meethere.user.service.UserService;
import lionel.meethere.user.session.UserSessionInfo;
import lionel.meethere.user.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user/update/")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("username")
    public Result<?> updateUsername(@SessionAttribute UserSessionInfo userSessionInfo,
                                 @RequestBody String newName) {
        if (userSessionInfo != null) {
            service.updateUsername(userSessionInfo.getId(), newName);
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @PostMapping("password")
    public Result<?> updatePassword(@SessionAttribute UserSessionInfo userSessionInfo,
                                    @RequestBody String oldPassword,
                                    @RequestBody String newPassword){
        service.updatePassword(userSessionInfo,oldPassword,newPassword);
        return CommonResult.success();
    }

    @PostMapping("telephone")
    public Result<?> updateTelephone(@SessionAttribute UserSessionInfo userSessionInfo,
                                     @RequestBody String telephone){
        if(userSessionInfo != null){

            service.updateTelephone(userSessionInfo.getId(),telephone);
            return CommonResult.success();
        }
        return CommonResult.failed();
    }
}
