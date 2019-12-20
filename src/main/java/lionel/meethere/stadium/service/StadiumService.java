package lionel.meethere.stadium.service;

import lionel.meethere.paging.PageParam;
import lionel.meethere.stadium.dao.StadiumMapper;
import lionel.meethere.stadium.entity.Stadium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StadiumService {

    @Autowired
    private StadiumMapper stadiumMapper;

    public List<Stadium> getStadiums (PageParam pageParam){
        return stadiumMapper.getStadiumList(pageParam);
    }

    public int getStadiumCount(){
        return stadiumMapper.getStadiumCount();
    }

    public int createStadium(Stadium stadium){
        return stadiumMapper.createStadium(stadium);
    }

    public int delteStadium(Integer id){
        return stadiumMapper.deleteStadium(id);
    }

    public int updateStadium(Stadium stadium){
        return stadiumMapper.updateStadium(stadium);
    }

}
