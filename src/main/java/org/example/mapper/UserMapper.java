package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.DTO.UserVO;
import org.example.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Update("UPDATE user SET token_balance = token_balance - #{costToken} WHERE id = #{userId} AND token_balance >= #{costToken}")
    int deductTokens(Long userId, Integer costToken);
    @Select("SELECT token_balance FROM user WHERE id = #{userId}")
    Long getTokenBalance(Long userId);
    IPage<UserVO> selectPageVo(IPage<UserVO> page, @Param("id") Long id);
}
