package org.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public List<User> getUsersByAge(Integer minAge) {
      // 1. 构建查询条件：age >= 传入的minAge
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ge("age", minAge); // ge = greater or equal 等价于 SQL WHERE age >= ?

        // 2. 调用MP自带的list()方法执行查询，返回符合条件的用户列表
        return this.list(wrapper);
    }
}
