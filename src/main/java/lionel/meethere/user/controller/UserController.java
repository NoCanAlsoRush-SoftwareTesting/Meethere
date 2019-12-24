package lionel.meethere.user.controller;

import lionel.meethere.paging.PageParam;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.user.service.UserService;
import lionel.meethere.user.session.UserSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    UserService service;
//ok
    @PostMapping("update/username")
    public Result<?> updateUsername(@SessionAttribute UserSessionInfo userSessionInfo,
                                 @RequestParam String newName) {
            service.updateUsername(userSessionInfo.getId(), newName);
            return CommonResult.success();
    }
//ok
    @PostMapping("update/password")
    public Result<?> updatePassword(@SessionAttribute UserSessionInfo userSessionInfo,
                                    @RequestParam String oldPassword,
                                    @RequestParam String newPassword){
            service.updatePassword(userSessionInfo, oldPassword, newPassword);
            return CommonResult.success();
    }
//ok
    @PostMapping("update/telephone")
    public Result<?> updateTelephone(@SessionAttribute UserSessionInfo userSessionInfo,
                                     @RequestParam String telephone){
            service.updateTelephone(userSessionInfo.getId(),telephone);
            return CommonResult.success();
    }

    @PostMapping("update/permission")
    public Result<?> updatePermission(@SessionAttribute UserSessionInfo userSessionInfo,
                                      @RequestParam Integer userId,
                                      @RequestParam Integer permission){
        if(userSessionInfo.getAdmin() != 1){
            return CommonResult.accessDenied();
        }
        service.updatePermission(userId,permission);
        return CommonResult.success();
    }

    @PostMapping("delete")
    public Result<?> deleteUser(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestParam Integer userId){
        if(userSessionInfo.getAdmin() != 1){
            return CommonResult.accessDenied();
        }
        service.deleteUserById(userId);
        return CommonResult.success();
    }

    @GetMapping("list")
    public Result<?> getUerList(@SessionAttribute UserSessionInfo userSessionInfo,
                                @ModelAttribute PageParam pageParam){
        if(userSessionInfo.getAdmin() != 1){
            return CommonResult.accessDenied();
        }

        return CommonResult.success().data(service.getUserList(pageParam)).total(service.getCountOfUser());
    }
   //ok
    @GetMapping("get")
    public Result<?> getUserById(@SessionAttribute UserSessionInfo userSessionInfo,
                                 @RequestParam Integer id){
        return CommonResult.success().data(service.getUserById(id));
    }

}
