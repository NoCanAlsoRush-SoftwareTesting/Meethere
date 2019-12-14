package lionel.meethere.news.service;

import lionel.meethere.news.dao.NewsMapper;
import lionel.meethere.news.dto.NewsCatalogDTO;
import lionel.meethere.news.dto.NewsDTO;
import lionel.meethere.news.entity.News;
import lionel.meethere.news.param.NewsPublishParam;
import lionel.meethere.news.param.NewsUpdateParam;
import lionel.meethere.news.vo.NewsCatalogVO;
import lionel.meethere.paging.PageParam;
import lionel.meethere.user.dao.UserMapper;
import lionel.meethere.user.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private UserMapper userMapper;

    public void publishNews(Integer adminId, NewsPublishParam newsPublishParam){
        newsMapper.insertNews(convertToNews(adminId,newsPublishParam));
    }

    private News convertToNews(Integer adminId, NewsPublishParam newsPublishParam){
        News news = new News();
        BeanUtils.copyProperties(newsPublishParam,news);
        news.setAdminId(adminId);
        return news;
    }
    public void deleteNews(Integer id){
        newsMapper.deleteNews(id);
    }

    public void updateNews(NewsUpdateParam updateParam){
        newsMapper.updateNews(updateParam);
    }

    public NewsCatalogVO getNewsCatalogList(PageParam pageParam){
        return convertToNewsCatalogVO(newsMapper.getNewsCatalogList(pageParam));
    }

    private NewsCatalogVO convertToNewsCatalogVO(NewsCatalogDTO newsCatalogDTO){
        NewsCatalogVO newsCatalogVO = new NewsCatalogVO();
        BeanUtils.copyProperties(newsCatalogDTO,newsCatalogVO);
        UserVO admin = userMapper.getUserById(newsCatalogDTO.getAdminId());
        newsCatalogVO.setAdmin(admin);
        return newsCatalogVO;
    }
    public NewsDTO getNews(Integer id){
        return newsMapper.getNewsById(id);
    }


}
