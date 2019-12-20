package lionel.meethere.user.controller;

import lionel.meethere.paging.PageParam;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.user.service.UserService;
import lionel.meethere.user.session.UserSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/")
public class UserController {
    @Autowired
    UserService service;

    @PostMapping("update/username")
    public Result<?> updateUsername(@RequestBody Map<String,Object> map, @SessionAttribute(value = "userSessionInfo") UserSessionInfo userSessionInfo) {
        System.out.println(map);
        String newName = map.get("newName").toString();
        if (userSessionInfo != null) {
            service.updateUsername(userSessionInfo.getId(), newName);
            System.out.println(newName);
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @PostMapping("update/password")
    public Result<?> updatePassword(@SessionAttribute UserSessionInfo userSessionInfo,
                                    @RequestBody String oldPassword,
                                    @RequestBody String newPassword){
        service.updatePassword(userSessionInfo,oldPassword,newPassword);
        return CommonResult.success();
    }

    @PostMapping("update/telephone")
    public Result<?> updateTelephone(@SessionAttribute UserSessionInfo userSessionInfo,
                                     @RequestBody Map<String,Object> map){
        String telephone=map.get("telephone").toString();
        if(userSessionInfo != null){

            service.updateTelephone(userSessionInfo.getId(),telephone);
            System.out.println(telephone);
            return CommonResult.success();
        }
        return CommonResult.failed();
    }

    @PostMapping("update/permission")
    public Result<?> updatePermission(@SessionAttribute UserSessionInfo userSessionInfo,
                                      @RequestBody Integer permission){
        if(userSessionInfo.getAdmin() != 1){
            return CommonResult.accessDenied();
        }
        service.updatePermission(userSessionInfo.getId(),permission);
        return CommonResult.success();
    }

    @PostMapping("delete")
    public Result<?> deleteUser(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestBody Integer userId){
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

    @GetMapping("get")
    public Result<?> getUserById(@SessionAttribute UserSessionInfo userSessionInfo,
                                 @RequestParam Integer id){
        return CommonResult.success().data(service.getUserById(id));
    }

}
