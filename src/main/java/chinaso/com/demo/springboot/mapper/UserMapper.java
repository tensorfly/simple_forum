package chinaso.com.demo.springboot.mapper;
import chinaso.com.demo.springboot.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * @author fangqian
 * @date 2018/6/28 11:35
 */
@Mapper
public interface UserMapper {


    @Select("SELECT * FROM user WHERE accountId = #{accountId} AND code = #{code}")
    @Results({
            @Result(property = "accountId",  column = "accountId"),
            @Result(property = "password", column = "password"),
            @Result(property = "nickname",  column = "nickname"),
            @Result(property = "email", column = "email"),
            @Result(property = "sex",  column = "sex"),
            @Result(property = "age", column = "age"),
            @Result(property = "status", column = "status"),
            @Result(property = "code", column = "code"),
            @Result(property = "createtime",  column = "createtime"),
            @Result(property = "updatetime", column = "updatetime")
    })
    User getUserByCode(@Param("accountId") String accountId, @Param("code") String code);

    @Select("SELECT * FROM user WHERE accountId = #{accountId}")
    @Results({
            @Result(property = "accountId",  column = "accountId"),
            @Result(property = "password", column = "password"),
            @Result(property = "nickname",  column = "nickname"),
            @Result(property = "email", column = "email"),
            @Result(property = "sex",  column = "sex"),
            @Result(property = "age", column = "age"),
            @Result(property = "status", column = "status"),
            @Result(property = "code", column = "code"),
            @Result(property = "createtime",  column = "createtime"),
            @Result(property = "updatetime", column = "updatetime")
    })
    User getUserByAccountId(@Param("accountId") String accountId);

    @Insert("INSERT INTO user(accountId,password,nickname,email,sex,age,status,code,createtime,updatetime) VALUES(#{accountId}, #{password}, #{nickname}, #{email}, #{sex}, #{age}, #{status}, #{code}, #{createtime}, #{updatetime})")
    void insert(User user);

    @Update("UPDATE user SET status=#{status},updatetime=#{updatetime} WHERE accountId =#{accountId}")
    void update(User user);

    @Select("SELECT * FROM user WHERE accountId = #{accountId} AND password = #{password}")
    @Results({
            @Result(property = "accountId",  column = "accountId"),
            @Result(property = "password", column = "password"),
            @Result(property = "nickname",  column = "nickname"),
            @Result(property = "email", column = "email"),
            @Result(property = "sex",  column = "sex"),
            @Result(property = "age", column = "age"),
            @Result(property = "status", column = "status"),
            @Result(property = "code", column = "code"),
            @Result(property = "createtime",  column = "createtime"),
            @Result(property = "updatetime", column = "updatetime")
    })
    User getUser(@Param("accountId") String accountId, @Param("password") String password);

}
