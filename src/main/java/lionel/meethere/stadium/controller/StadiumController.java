package lionel.meethere.stadium.controller;

import lionel.meethere.paging.PageParam;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.stadium.entity.Stadium;
import lionel.meethere.stadium.service.StadiumService;
import lionel.meethere.user.session.UserSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/stadium/")
public class StadiumController {

    @Autowired
    private StadiumService stadiumService;

    @GetMapping("list")
    public Result<?> getStadium(@ModelAttribute PageParam pageParam){
        return CommonResult.success().data(stadiumService.getStadiums(pageParam)).total(stadiumService.getStadiumCount());
    }

    @PostMapping("create")
    public Result<?> createStadium(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestBody Stadium stadium){
        if(userSessionInfo.getAdmin() == 0){
            return CommonResult.accessDenied();
        }
        stadiumService.createStadium(stadium);
        return CommonResult.success();
    }

    @PostMapping("delete")
    public Result<?> deleteStadium(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestBody Integer id){
        if(userSessionInfo.getAdmin() == 0){
            return CommonResult.accessDenied();
        }

        stadiumService.delteStadium(id);
        return CommonResult.success();
    }

    @PostMapping("update")
    public Result<?> updateStadium(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestBody Stadium stadium){
        if(userSessionInfo.getAdmin() == 0){
            return CommonResult.accessDenied();
        }
        stadiumService.updateStadium(stadium);
        return CommonResult.success();
    }
}
