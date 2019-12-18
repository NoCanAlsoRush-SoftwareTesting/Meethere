package lionel.meethere.user.dao;

import lionel.meethere.user.entity.User;
import lionel.meethere.user.vo.UserVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    //根据用户名和密码获取用户
    @Select("select * from user where username=#{username} and password=#{password};")
    User getUserByUsernameAndPassword(@Param("username") String username,
                                      @Param("password") String password);

    //根据用户名获取用户
    @Select("SELECT * FROM user WHERE username=#{username};")
    User getUserByUsername(@Param("username") String username);

    @Select("select * from user where id=#{id};")
    UserVO getUserById(Integer id);

    //插入新用户
    @Insert("insert into user(id,username,password,telephone,admin) values (#{id},#{username},#{password},#{telephone},#{admin});")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);

    //更新用户密码
    @Update("update user set password=#{password} where id=#{id}")
    int updatePasswordById(@Param("id") Integer id,
                           @Param("password") String password);

    //更新用户名
    @Update("update user set username=#{username} where id=#{id}")
    int updateUsernameById(@Param("id")Integer id,
                           @Param("username") String username);

    @Update("udpate user set telephone=#{telephone} where id=#{id};")
    int updateTelephoneById(@Param("id")Integer id,
                            @Param("telephone")String telephone);

    //根据用户名获取用户列表
    @Results(
            id = "userList",value = {
            @Result(property="username", column="username"),
            @Result(property="password", column="password")
    }
    )
    @Select("SELECT * FROM user WHERE username=#{username};")
    List<User> getUserListByUsername(@Param("username") String username);


    //获取所有用户
    @ResultMap("userList")
    @Select("SELECT * FROM user ORDER BY ${order_by_sql};")
    List<User> getUserListOrderly(@Param("order_by_sql") String order_by_sql);

    //删除用户
    @Delete("delete from user where id = #{id};")
    int deleteUserById(Integer id);
}
