package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.DTO.UserVO;
import org.example.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    IPage<UserVO> selectPageVo(IPage<UserVO> page, @Param("id") Long id);
}
