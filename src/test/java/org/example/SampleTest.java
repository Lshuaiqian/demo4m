package org.example;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.DTO.PageVO;
import org.example.DTO.UserVO;
import org.example.config.RabbitmqConfig;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }

    @Test
    void testPage() {
        // 1. 构建分页
        Page<User> page = new Page<>(1, 5);

        // 2. 查询分页 → 得到 IPage（MP自带）
        IPage<User> iPage = userMapper.selectPage(page, null);

        // ======================
        // 这里！就是 convert 执行的时候
        // ======================
        PageVO<User> pageVO = PageVO.convert(iPage);

        // 输出你自己的 PageVO
        System.out.println(pageVO);
    }

    @Test
    public void testCustomPage() {
        // 1. 构建分页参数：第1页，每页5条
        IPage<UserVO> pageParam = new Page<>(1, 5);

        // 2. 调用自定义方法，查询 ID 为 1 的用户（实际场景可以改成动态条件）
        IPage<UserVO> resultPage = userMapper.selectPageVo(pageParam, 1L);

        // 3. 转换为你自己的 PageVO 输出
        PageVO<UserVO> pageVO = PageVO.convert(resultPage);

        System.out.println("总条数：" + pageVO.getTotal());
        System.out.println("数据：" + pageVO.getRecords());
    }

    @Test
    void testSaveUser() {
        User newUser = new User();
        newUser.setName("Test");
        newUser.setAge(22);
        newUser.setEmail("test@test.com");
        boolean save = userService.save(newUser);
        System.out.println("新增是否成功：" + save); // 输出 true
    }

    @Test
    void testPageUser() {
        Page<User> page = new Page<>(1, 2);
        IPage<User> userPage = userService.page(page, null);
        System.out.println("当前页数据：" + userPage.getRecords());
        System.out.println("总条数：" + userPage.getTotal()); // 输出 5
    }

    // ===================== 测试【自定义Service方法】 =====================
    // 测试你写的：查询年龄≥20岁的用户（会查出Jack/Tom/Sandy/Billie 4条）
    @Test
    void testGetUsersByAge() {
        List<User> userList = userService.getUsersByAge(20);
        System.out.println("年龄≥20的用户数：" + userList.size()); // 输出 4
        userList.forEach(System.out::println);
    }

    @Test
    void testSendMessage() {
        String task = "hello rabbitmq";
        rabbitTemplate.convertAndSend(
                RabbitmqConfig.DRAW_EXCHANGE,    // 交换机
                RabbitmqConfig.DRAW_ROUTING_KEY, // 路由键
                task                              // 消息内容
        );
        System.out.println("消息已发送: " + task);
    }
}