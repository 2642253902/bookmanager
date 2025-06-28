package com.pan;

import com.pan.pojo.BookPojo;
import com.pan.service.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ImauBookmanagerApplicationTests {


    @Autowired
    BookServiceImpl bookService;


    @Test
    void contextLoads() {


		// 测试toString()方法
        BookPojo testBook = new BookPojo("测试书籍", "ISBN123", new BigDecimal("99.99"), "作者");
        System.out.println("手动创建对象: " + testBook);

        // 测试查询
        List<BookPojo> books = bookService.findBookByBook("123");
        System.out.println("查询结果: " + books);
    }

}
