package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.context.UserContext;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.draw.DrawService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private DrawService drawService;

    // 1. 【MP自带】新增用户
    @PostMapping("/save")
    public Boolean saveUser(@RequestBody User user) {
        return userService.save(user);
    }

    // 2. 【MP自带】根据ID查单个用户
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getById(id);
    }

    // 3. 【MP自带】查询所有用户（你的5条数据全查出来）
  /*  @GetMapping("/list")
    public List<User> getUserList() {
        return userService.list();
    }*/

    // 4. 【MP自带】分页查询（比如第1页，每页2条）
    @GetMapping("/page")
    public IPage<User> getUserPage() {
        Page<User> page = new Page<>(1, 2);
        return userService.page(page, null);
    }

    // 5. 【MP自带】修改用户
    @PutMapping("/update")
    public Boolean updateUser(@RequestBody User user) {
        return userService.updateById(user);
    }

    // 6. 【MP自带】删除用户
    @DeleteMapping("/{id}")
    public Boolean deleteUser(@PathVariable Long id) {
        return userService.removeById(id);
    }

    // 7. 【自定义Service方法】查询年龄≥指定值的用户（比如查≥20岁的）
    @GetMapping("/age/{minAge}")
    public List<User> getUsersByAge(@PathVariable Integer minAge) {
        return userService.getUsersByAge(minAge);
    }


    // 数据权限分离
    @GetMapping("/list")
    public List<User> list(
            @RequestHeader("userId") Long userId,
            @RequestHeader("role") String role) {

        try {
            // 设置当前用户
            UserContext.set(userId, role);

            // 直接查
            return userMapper.selectList(null);

        } finally {
            UserContext.clear();
        }
    }
    //redis原子化扣减库存
    @GetMapping("/draw")
    public String draw(@RequestParam Long userId,@RequestParam String prompt){
        return drawService.draw(userId,prompt);


    }
}
