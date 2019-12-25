package lionel.meethere.site.controller;

import lionel.meethere.paging.PageParam;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.site.entity.Site;
import lionel.meethere.site.param.SiteUpdateParam;
import lionel.meethere.site.service.SiteService;
import lionel.meethere.user.session.UserSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SiteController {

    @Autowired
    private SiteService siteService;

    //ok
    @PostMapping("/site/get")
    public Result<?> getSite(@RequestParam Integer id){
        return CommonResult.success().data(siteService.getSiteById(id));
    }


    @PostMapping("/site/list")
    public Result<?> getSiteList(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageParam pageParam = new PageParam(pageNum,pageSize);
        return CommonResult.success().data(siteService.getSites(pageParam)).total(siteService.getSiteCount());
    }
    //ok
    @PostMapping("/site/listByStadium")
    public Result<?> getSiteListByStadium(@RequestParam Integer pageNum,@RequestParam Integer pageSize,@RequestParam Integer id){
        PageParam pageParam = new PageParam(pageNum,pageSize);
        return CommonResult.success().data(siteService.getSitesByStadium(id,pageParam)).total(siteService.getSiteCountByStadium(id));
    }

    @PostMapping("/site/create")
    public Result<?> createSite(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestBody Site site){
        if(userSessionInfo.getAdmin() == 0){
            return CommonResult.accessDenied();
        }
        siteService.createSite(site);
        return CommonResult.success();
    }

    @PostMapping("/site/delete")
    public Result<?> deleteSite(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestBody Integer id){
        if(userSessionInfo.getAdmin() == 0){
            return CommonResult.accessDenied();
        }
        siteService.deleteSite(id);
        return CommonResult.success();
    }

    @PostMapping("/site/update")
    public Result<?> updateSite(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestBody SiteUpdateParam updateParam){
        if(userSessionInfo.getAdmin() == 0){
            return CommonResult.accessDenied();
        }
        siteService.updateSite(updateParam);
        return CommonResult.success();
    }

}
