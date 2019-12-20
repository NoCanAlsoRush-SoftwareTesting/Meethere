package lionel.meethere.news.controller;

import lionel.meethere.news.param.NewsPublishParam;
import lionel.meethere.news.param.NewsUpdateParam;
import lionel.meethere.news.service.NewsService;
import lionel.meethere.paging.PageParam;
import lionel.meethere.result.CommonResult;
import lionel.meethere.result.Result;
import lionel.meethere.user.session.UserSessionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/news/")
public class NewsController {

    @Autowired
    private NewsService newsService;


    @PostMapping("publish")
    public Result<?> publishNews(@SessionAttribute UserSessionInfo userSessionInfo,
                                 @RequestBody @Valid NewsPublishParam newsPublishParam){
        if(userSessionInfo.getAdmin() != 1)
            return CommonResult.accessDenied();
        newsService.publishNews(userSessionInfo.getId(),newsPublishParam);
        return CommonResult.success();
    }

    @PostMapping("delete")
    public Result<?> deleteNews(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestBody Integer newsId){
        if(userSessionInfo.getAdmin() != 1)
            return CommonResult.accessDenied();
        newsService.deleteNews(newsId);
        return CommonResult.success();
    }

    @PostMapping("update")
    public Result<?> updateNews(@SessionAttribute UserSessionInfo userSessionInfo,
                                @RequestBody @Valid NewsUpdateParam updateParam){
        if(userSessionInfo.getAdmin() != 1)
            return CommonResult.accessDenied();
        newsService.updateNews(updateParam);
        return CommonResult.success();
    }

//    @PostMapping("get")
//    public Result<?> getNews(@RequestParam Map<String,Object> map){
//        System.out.println(map);
//        Integer id= (Integer) map.get("id");
//        System.out.println(id);
//        return CommonResult.success().data(newsService.getNews(id));
//    }
    @PostMapping("get")
    public Result<?> getNews(@RequestBody Map<String,Object> map){
        Integer id= (Integer) map.get("id");
        System.out.println(id);
        return CommonResult.success().data(newsService.getNews(id));
    }

    @PostMapping("getcatalog")
    public Result<?> getCatalog(@RequestBody PageParam pageParam){
        return CommonResult.success().data(newsService.getNewsCatalogList(pageParam)).total(newsService.getNewsCount());
    }

}
