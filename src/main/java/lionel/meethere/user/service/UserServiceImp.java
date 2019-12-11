package lionel.meethere.user.service;

import lionel.meethere.user.dao.UserMapper;
import lionel.meethere.user.entity.User;
import lionel.meethere.user.exception.IncorrectUsernameOrPasswordException;
import lionel.meethere.user.exception.UsernameAlreadyExistException;
import lionel.meethere.user.exception.UsernameNotExistsException;
import lionel.meethere.user.param.LoginParam;
import lionel.meethere.user.param.RegisterParam;
import lionel.meethere.user.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(LoginParam loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        User user = userMapper.getUserByUsername(username);
        if(user == null)
            throw new UsernameNotExistsException();
        if(!password.equals(user.getPassword()))
            throw new IncorrectUsernameOrPasswordException();
        return user;
    }

    @Override
    public UserVO getUserByUsername(String username) {
        User user = userMapper.getUserByUsername(username);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    @Override
    public int register(RegisterParam registerParam) {
        String username = registerParam.getUsername();
        String password = registerParam.getPassword();

        if(userMapper.getUserByUsername(username) != null)
            throw new UsernameAlreadyExistException();

        User user = new User();
        user.setAdmin(0);
        user.setUsername(username);
        user.setPassword(password);

        return userMapper.insertUser(user);
    }



    @Override
    public int updatePassword(Integer id, String password) {
        return userMapper.updatePasswordById(id,password);
    }

    @Override
    public int updateUsername(Integer id, String username) {
        return userMapper.updateUsernameById(id,username);
    }

    @Override
    public List<User> getUserListOrderl(String order_by_sql) {
        return userMapper.getUserListOrderly(order_by_sql);
    }

    @Override
    public int deleteUserById(Integer id) {
        return userMapper.deleteUserById(id);
    }
}
