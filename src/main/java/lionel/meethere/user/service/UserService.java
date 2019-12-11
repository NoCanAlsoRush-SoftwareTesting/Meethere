package lionel.meethere.user.service;

import lionel.meethere.user.entity.User;
import lionel.meethere.user.param.LoginParam;
import lionel.meethere.user.param.RegisterParam;
import lionel.meethere.user.vo.UserVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public interface UserService {

    //登陆
    User login(LoginParam loginParam);

    //根据用户名获取用户
    UserVO getUserByUsername(String username);

    //注册新用户
    int register(RegisterParam registerParam);

    //更新用户密码
    int updatePassword(Integer id, String password);

    //更新用户名
    int updateUsername(Integer id, String username);

    //获取所有用户
    List<User> getUserListOrderl(String order_by_sql);

    //删除用户
    int deleteUserById(Integer id);

}
