package lionel.meethere.user.controller;

import lionel.meethere.paging.PageParam;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.result.UserResult;
import lionel.meethere.user.exception.UsernameAlreadyExistException;
import lionel.meethere.user.exception.UsernameNotExistsException;
import lionel.meethere.user.service.UserService;
import lionel.meethere.user.session.UserSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user/")
public class UserController {
    @Autowired
    UserService service;
//ok
    @PostMapping("update/username")
    public Result<?> updateUsername(@SessionAttribute UserSessionInfo userSessionInfo,
                                 @RequestParam String newName) {

        if(newName.length() < 2 || newName.length() > 20){
            return UserResult.invalidUsernameOrPassword();
        }
        try{
            service.updateUsername(userSessionInfo.getId(), newName);
        }catch (UsernameAlreadyExistException e){
            return CommonResult.failed();
        }

            return CommonResult.success();
    }
//ok
    @PostMapping("update/password")
    public Result<?> updatePassword(@SessionAttribute UserSessionInfo userSessionInfo,
                                    @RequestParam String oldPassword,
                                    @RequestParam String newPassword){

        if(newPassword.length() < 2 || newPassword.length() > 20){
            return UserResult.invalidUsernameOrPassword();
        }
            service.updatePassword(userSessionInfo, oldPassword, newPassword);
            return CommonResult.success();
    }
//ok
    @PostMapping("update/telephone")
    public Result<?> updateTelephone(@SessionAttribute UserSessionInfo userSessionInfo,
                                     @RequestParam String telephone){
        if(telephone.length() != 11 ){
            return UserResult.incorrectTelephone();
        }
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

    @PostMapping("list")
    public Result<?> getUerList(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestParam Integer pageNum,
                                @RequestParam Integer pageSize){

        PageParam pageParam = new PageParam(pageNum,pageSize);
        if(userSessionInfo.getAdmin() != 1){
            return CommonResult.accessDenied();
        }

        return CommonResult.success().data(service.getUserList(pageParam)).total(service.getCountOfUser());
    }
   //ok
    @PostMapping("get")
    public Result<?> getUserById(@SessionAttribute UserSessionInfo userSessionInfo,
                                 @RequestParam Integer id){
        return CommonResult.success().data(service.getUserById(id));
    }

}
