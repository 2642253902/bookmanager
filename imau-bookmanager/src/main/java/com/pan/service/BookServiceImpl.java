package com.pan.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pan.dao.BookDao;
import com.pan.pojo.BookPojo;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.sql.Wrapper;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BookServiceImpl {
    @Autowired
    BookDao bookDao;


    //添加书籍
    public void adddBooks(String bookNme, String bookIsbn, BigDecimal bookPrice, String bookWriter) {
        bookDao.insert(new BookPojo(bookNme, bookIsbn, bookPrice, bookWriter));

    }

    //删
    public void deleteBook(String bookId) {
        bookDao.deleteById(bookId);

    }

    //改
    public void updBook(Integer bookId, String bookNme, String bookIsbn, BigDecimal bookPrice, String bookWriter) {
        bookDao.update(new BookPojo(bookNme, bookIsbn, bookPrice, bookWriter), new QueryWrapper<BookPojo>().eq("book_id", bookId));

    }

    //多查
    public List<BookPojo> findBookByBook(String book) {
        List<BookPojo> bookPojos = bookDao.selectList(new QueryWrapper<BookPojo>().like("book_name", book).or().like("book_isbn", book).or().like("book_writer", book));
        return bookPojos;
    }

    //单查
    public List<BookPojo> findBookByIsbn(String bookIsbn) {
        List<BookPojo> bookPojos = bookDao.selectList(new QueryWrapper<BookPojo>().like("book_isbn", bookIsbn));
        return bookPojos;
    }


}
